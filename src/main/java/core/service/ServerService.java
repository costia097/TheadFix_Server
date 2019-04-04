package core.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.message.PlayerWaveMessage;
import core.processor.MessageProcessor;
import core.message.MessageWrapper;
import core.message.PlayerJoinMessage;
import core.message.PlayerStateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

@Service
public class ServerService {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MessageProcessor messageProcessor;

    @Autowired
    private NetworkService networkService;

    public void startServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(27015);

        while (serverSocket.isBound()) {
            Socket playerConnectedSocket = serverSocket.accept();

            processJoinedPlayer(playerConnectedSocket);
        }
    }

    private void processJoinedPlayer(Socket playerConnectedSocket) {
        System.out.println("Client connected " + playerConnectedSocket.getLocalAddress() + playerConnectedSocket.getPort());

        Thread thread = new Thread(() -> {
            try {
                InputStream inputStream = playerConnectedSocket.getInputStream();
                OutputStream outputStream = playerConnectedSocket.getOutputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

                while (playerConnectedSocket.isConnected()) {

                    String inputMessage = bufferedReader.readLine();

                    if (inputMessage == null || inputMessage.equals("")) {
                        return;
                    }

                    MessageWrapper inputMessageWrapper = objectMapper.readValue(inputMessage, MessageWrapper.class);

                    switch (inputMessageWrapper.getMessageType()) {
                        case PlayerJoin:

                            PlayerJoinMessage clientJoinMessage = objectMapper.readValue(inputMessageWrapper.getPayload(), PlayerJoinMessage.class);
                            messageProcessor.processPlayerJoinMessage(clientJoinMessage, playerConnectedSocket, bufferedWriter, bufferedReader);
                            break;
                        case PlayerState:

                            PlayerStateMessage playerStateMessage = objectMapper.readValue(inputMessageWrapper.getPayload(), PlayerStateMessage.class);
                            messageProcessor.processPlayerStateMessage(playerStateMessage);
                            break;
                        case PlayerWave:

                            PlayerWaveMessage playerWaveMessage = objectMapper.readValue(inputMessageWrapper.getPayload(), PlayerWaveMessage.class);
                            messageProcessor.processPlayerWaveMessage(playerWaveMessage);

                            /*
                            we dont need to send this message for all player
                             */
                            continue;

                        case MapChanged:

                            /*
                            we dont need to send this message for all player
                             */
                            continue;
                        default:
                            throw new RuntimeException();
                    }

                    networkService.sendMessageForPlayersExceptGiven(playerConnectedSocket, inputMessage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        thread.start();
    }
}
