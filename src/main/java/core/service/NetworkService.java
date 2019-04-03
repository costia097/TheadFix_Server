package core.service;

import core.entities.ServerPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

@Service
public class NetworkService {
    @Autowired
    private PlayerService playerService;

    public void sendMessageTo(ServerPlayer playerToSend, String message) {
        doProcessOfSend(playerToSend, message);
    }

    public void sendMessageForAllPlayers(String messageToSend) {
        playerService.getPlayers().forEach(serverPlayer -> doProcessOfSend(serverPlayer, messageToSend));
    }

    public void sendMessageForPlayersExceptGiven(Socket exceptPlayerSocket, String messageToSend) {
        playerService.getPlayers().stream()
                .filter(serverPlayer -> !serverPlayer.getSocket().equals(exceptPlayerSocket))
                .forEach(serverPlayer -> doProcessOfSend(serverPlayer, messageToSend));
    }

    private void doProcessOfSend(ServerPlayer serverPlayer, String messageToSend) {
        BufferedWriter bufferedWriter1 = serverPlayer.getBufferedWriter();
        try {
            bufferedWriter1.write(messageToSend);
            bufferedWriter1.newLine();
            bufferedWriter1.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
