package core.service;

import core.ServerPlayer;
import org.springframework.stereotype.Service;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Service
public class NetworkPlayersService {

    private static List<ServerPlayer> players = new ArrayList<>();

    public void addPlayer(ServerPlayer serverPlayer) {
        players.add(serverPlayer);
    }

    public List<ServerPlayer> getPlayers() {
        return players;
    }

    void sendMessageForPlayersExceptGiven(Socket exceptPlayerSocket, String messageToSend) {
        players.stream()
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
