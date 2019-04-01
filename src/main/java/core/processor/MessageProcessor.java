package core.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.ServerPlayer;
import core.annotation.Processor;
import core.message.MessageType;
import core.message.MessageWrapper;
import core.message.PlayerJoinMessage;
import core.message.PlayerStateMessage;
import core.service.NetworkPlayersService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

@Processor
public class MessageProcessor {
    @Autowired
    private NetworkPlayersService networkPlayersService;

    @Autowired
    private ObjectMapper objectMapper;


    public void processPlayerJoinMessage(PlayerJoinMessage playerJoinMessage,
                                  Socket playerSocket,
                                  BufferedWriter playerBufferedWriter,
                                  BufferedReader playerBufferedReader
    ) throws IOException {
        ServerPlayer serverPlayer = new ServerPlayer();

        serverPlayer.setName(playerJoinMessage.getPlayerId());

        serverPlayer.setSocket(playerSocket);

        serverPlayer.setBufferedReader(playerBufferedReader);
        serverPlayer.setBufferedWriter(playerBufferedWriter);

        sendFirstSyncMessageToPlayer(playerBufferedWriter);

        networkPlayersService.addPlayer(serverPlayer);
    }

    public void processPlayerStateMessage(PlayerStateMessage playerStateMessage) {
        String playerId = playerStateMessage.getPlayerId();

        networkPlayersService.getPlayers().stream()
                .filter(serverPlayer -> serverPlayer.getName().equals(playerId))
                .findFirst()
                .ifPresent(serverPlayer -> initServerPlayer(serverPlayer, playerStateMessage));
    }

    private void sendFirstSyncMessageToPlayer(BufferedWriter playerBufferedWriter) throws IOException {
        String playersMessageJson = objectMapper.writeValueAsString(networkPlayersService.getPlayers());

        MessageWrapper messageWrapper = new MessageWrapper();
        messageWrapper.setMessageType(MessageType.FirstSync);
        messageWrapper.setPayload(playersMessageJson);

        String messageWrapperJson = objectMapper.writeValueAsString(messageWrapper);

        playerBufferedWriter.write(messageWrapperJson);
        playerBufferedWriter.newLine();
        playerBufferedWriter.flush();
    }

    private void initServerPlayer(ServerPlayer player, PlayerStateMessage playerStateMessage) {
        player.setX(playerStateMessage.getX());
        player.setY(playerStateMessage.getY());
        player.setZ(playerStateMessage.getZ());

        player.setRotationZ(playerStateMessage.getRotationZ());
    }
}
