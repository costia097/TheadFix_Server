package core.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BombExplosionMessage {

    @JsonProperty("BombId")
    private String bombId;

    @JsonProperty("EnemiesHitted")
    private List<EnemyHittedMessage> enemiesHitted;

    public String getBombId() {
        return bombId;
    }

    public void setBombId(String bombId) {
        this.bombId = bombId;
    }

    public List<EnemyHittedMessage> getEnemiesHitted() {
        return enemiesHitted;
    }

    public void setEnemiesHitted(List<EnemyHittedMessage> enemiesHitted) {
        this.enemiesHitted = enemiesHitted;
    }
}
