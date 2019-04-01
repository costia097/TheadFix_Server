package core.message;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerWaveMessage {

    @JsonProperty("PlayerName")
    private String playerName;

    @JsonProperty("Direction")
    private Direction direction;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
