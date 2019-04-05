package core.service;

import core.entities.ServerPlayer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private static List<ServerPlayer> players = new ArrayList<>();

    public void addPlayer(ServerPlayer serverPlayer) {
        players.add(serverPlayer);
    }

    public Optional<ServerPlayer> findByName(String playerName) {
        return players.stream()
                .filter(serverPlayer -> serverPlayer.getName().equals(playerName))
                .findFirst();
    }

    public List<ServerPlayer> getPlayers() {
        return players;
    }
}
