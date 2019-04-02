package core.service;

import core.entities.ServerPlayer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerService {

    private static List<ServerPlayer> players = new ArrayList<>();

    public void addPlayer(ServerPlayer serverPlayer) {
        players.add(serverPlayer);
    }

    public ServerPlayer getByName(String playerName) {
        //TODO in optional
        return players.stream()
                .filter(serverPlayer -> serverPlayer.getName().equals(playerName))
                .findFirst()
                .orElse(null);
    }

    public List<ServerPlayer> getPlayers() {
        return players;
    }
}
