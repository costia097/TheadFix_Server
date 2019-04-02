package core.event;

import core.message.Direction;

public class PlayerWavePlayerActionEvent implements PlayerActionEvent {

    private String playerName;

    private Direction direction;

    public String getPlayerName() {
        return playerName;
    }

    @Override
    public ActionType getActionType() {
        return ActionType.SWORD_WAVE;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
