package core.event;

import core.message.Direction;

public interface PlayerActionEvent {

    String getPlayerName();

    ActionType getActionType();

    Direction getDirection();
}
