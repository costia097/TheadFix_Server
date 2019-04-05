package core.message;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerThrowBombMessage {

    @JsonProperty("PlayerName")
    private String playerName;

    @JsonProperty("BombName")
    private String bombName;

    @JsonProperty("Direction")
    private Direction direction;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getBombName() {
        return bombName;
    }

    public void setBombName(String bombName) {
        this.bombName = bombName;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
