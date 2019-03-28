package core;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.message.ClientJoinMessage;
import core.message.MessageType;
import core.message.MessageWrapper;
import core.message.PlayerMoveMessage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer {

    private static List<ServerPlayer> players = new ArrayList<>();

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(27015);

        while (serverSocket.isBound()) {
            Socket accept = serverSocket.accept();

            System.out.println("Client connected " + accept.getLocalAddress() + accept.getPort());

            Thread thread = new Thread(() -> {
                try {
                    InputStream inputStream = accept.getInputStream();
                    OutputStream outputStream = accept.getOutputStream();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

                    while (accept.isConnected()) {

                        String msg = bufferedReader.readLine();

                        if (msg == null || msg.equals("")) {
                            return;
                        }

                        MessageWrapper inputMessageWrapper = objectMapper.readValue(msg, MessageWrapper.class);

                        switch (inputMessageWrapper.getMessageType()) {
                            case PLAYER_JOIN:
                                System.out.println("RETRIEVE PLAYER_JOIN message" + msg);

                                ClientJoinMessage clientJoinMessage = objectMapper.readValue(inputMessageWrapper.getPayload(), ClientJoinMessage.class);
                                ServerPlayer serverPlayer = new ServerPlayer();

                                serverPlayer.setName(clientJoinMessage.getPlayerId());

                                serverPlayer.setX(0);
                                serverPlayer.setY(0);
                                serverPlayer.setZ(0);

                                serverPlayer.setSocket(accept);
                                serverPlayer.setBufferedReader(bufferedReader);
                                serverPlayer.setBufferedWriter(bufferedWriter);

                                String playersMessageJson = objectMapper.writeValueAsString(players);

                                MessageWrapper messageWrapper = new MessageWrapper();
                                messageWrapper.setMessageType(MessageType.FIRST_SYNC);
                                messageWrapper.setPayload(playersMessageJson);

                                String messageWrapperJson = objectMapper.writeValueAsString(messageWrapper);

                                bufferedWriter.write(messageWrapperJson);
                                bufferedWriter.newLine();
                                bufferedWriter.flush();

                                players.add(serverPlayer);

                                break;
                            case PLAYER_MOVE:
                                System.out.println("RETRIEVE PLAYER_MOVE message" + msg);

                                PlayerMoveMessage playerMoveMessage = objectMapper.readValue(inputMessageWrapper.getPayload(), PlayerMoveMessage.class);

                                String playerId = playerMoveMessage.getPlayerId();

                                for (ServerPlayer player : players) {
                                    if (player.getName().equals(playerId)) {
                                        player.setX(playerMoveMessage.getX());
                                        player.setY(playerMoveMessage.getY());
                                    }
                                }

                                break;
                            default:
                                throw new RuntimeException();
                        }


                        players.forEach(serverPlayer -> {
                            if (serverPlayer.getSocket() != accept) {
                                BufferedWriter bufferedWriter1 = serverPlayer.getBufferedWriter();
                                try {
                                    bufferedWriter1.write(msg);
                                    bufferedWriter1.newLine();
                                    bufferedWriter1.flush();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
            thread.start();
        }
    }
}
