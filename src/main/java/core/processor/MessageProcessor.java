package core.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.entities.ServerPlayer;
import core.annotation.Processor;
import core.message.MessageType;
import core.message.MessageWrapper;
import core.message.PlayerJoinMessage;
import core.message.PlayerStateMessage;
import core.message.PlayerWaveMessage;
import core.service.EnemyService;
import core.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

@Processor
public class MessageProcessor {
    @Autowired
    private PlayerService playerService;

    @Autowired
    private EnemyService enemyService;

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

        sendFirstPlayerSyncMessageToPlayer(playerBufferedWriter);
        sendFirstEnemySyncMessageToPlayer(playerBufferedWriter);

        playerService.addPlayer(serverPlayer);
    }

    public void processPlayerStateMessage(PlayerStateMessage playerStateMessage) {
        String playerId = playerStateMessage.getPlayerId();

        playerService.getPlayers().stream()
                .filter(serverPlayer -> serverPlayer.getName().equals(playerId))
                .findFirst()
                .ifPresent(serverPlayer -> injectNewInformationAboutServerPlayer(serverPlayer, playerStateMessage));
    }


    //TODO
    public void processPlayerWaveMessage(PlayerWaveMessage playerWaveMessage) {

    }

    private void sendFirstPlayerSyncMessageToPlayer(BufferedWriter playerBufferedWriter) throws IOException {
        String playersMessageJson = objectMapper.writeValueAsString(playerService.getPlayers());
        sendMessageToPlayerWith(MessageType.FirstPlayersSync, playersMessageJson, playerBufferedWriter);
    }

    private void sendFirstEnemySyncMessageToPlayer(BufferedWriter playerBufferedWriter) throws IOException {
        String enemiesMessageJson = objectMapper.writeValueAsString(enemyService.getEnemies());
        sendMessageToPlayerWith(MessageType.FirstEnemiesSync, enemiesMessageJson, playerBufferedWriter);
    }

    private void sendMessageToPlayerWith(MessageType messageType, String payloadValueString, BufferedWriter playerBufferedWriter) throws IOException {
        MessageWrapper messageWrapper = new MessageWrapper();
        messageWrapper.setMessageType(messageType);

        messageWrapper.setPayload(payloadValueString);

        String messageWrapperJson = objectMapper.writeValueAsString(messageWrapper);

        playerBufferedWriter.write(messageWrapperJson);
        playerBufferedWriter.newLine();
        playerBufferedWriter.flush();
    }

    private void injectNewInformationAboutServerPlayer(ServerPlayer player, PlayerStateMessage playerStateMessage) {
        player.setX(playerStateMessage.getX());
        player.setY(playerStateMessage.getY());
        player.setZ(playerStateMessage.getZ());

        player.setRotationZ(playerStateMessage.getRotationZ());
    }
}
