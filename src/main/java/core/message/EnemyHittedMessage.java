package core.message;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EnemyHittedMessage {

    @JsonProperty("EnemyName")
    public String enemyName;

    public String getEnemyName() {
        return enemyName;
    }

    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }
}
