package core.message;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerJoinMessage {
    @JsonProperty("PlayerId")
    private String playerId;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }
}
