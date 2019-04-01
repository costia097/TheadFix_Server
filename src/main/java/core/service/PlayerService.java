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

    public List<ServerPlayer> getPlayers() {
        return players;
    }
}
