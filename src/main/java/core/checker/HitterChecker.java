package core.checker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.annotation.GameChecker;
import core.entities.ServerPlayer;
import core.entities.enemy.Enemy;
import core.event.PlayerActionEvent;
import core.message.Direction;
import core.message.EnemyHittedMessage;
import core.message.MessageType;
import core.message.MessageWrapper;
import core.service.EnemyService;
import core.service.NetworkService;
import core.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@GameChecker
public class HitterChecker {
    @Autowired
    private PlayerService playerService;

    @Autowired
    private EnemyService enemyService;

    @Autowired
    private NetworkService networkService;

    @Autowired
    private ObjectMapper objectMapper;

    private BlockingQueue<PlayerActionEvent> actionEvents = new ArrayBlockingQueue<>(100);

    public void startCheckHits() {

        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    PlayerActionEvent targetEvent = actionEvents.take();
                    String playerName = targetEvent.getPlayerName();
                    ServerPlayer targetServerPlayer = playerService.getByName(playerName);

                    switch (targetEvent.getActionType()) {
                        case SWORD_WAVE:
                            processPlayerSwordEvent(targetServerPlayer, targetEvent.getDirection());
                            break;
                        default:
                            throw new RuntimeException();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void addEventToProcess(PlayerActionEvent playerActionEvent) {
        actionEvents.add(playerActionEvent);
    }

    private void processPlayerSwordEvent(ServerPlayer targetServerPlayer, Direction direction) {
        findAppropriativeEnemies(targetServerPlayer, direction)
                .stream()
                .map(this::createEnemyHittedMessageWrapper)
                .map(this::generatePayload)
                .filter(Objects::nonNull)
                .forEach(message -> networkService.sendMessageForAllPlayers(message));
    }

    private List<Enemy> findAppropriativeEnemies(ServerPlayer targetServerPlayer, Direction direction) {
        float startPosition;
        float endPosition;

        if (Direction.Right.equals(direction)) {
            startPosition = targetServerPlayer.getX();
            endPosition = startPosition + 2;
        } else {
            startPosition = targetServerPlayer.getX() - 2;
            endPosition = targetServerPlayer.getX();
        }

        return enemyService.findWithPositionInGivenRange(startPosition, endPosition);
    }

    private String generatePayload(MessageWrapper messageWrapper) {
        try {
            return objectMapper.writeValueAsString(messageWrapper);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //TODO
        return null;
    }

    private MessageWrapper createEnemyHittedMessageWrapper(Enemy enemy) {
        MessageWrapper messageWrapper = new MessageWrapper();

        messageWrapper.setMessageType(MessageType.EnemyHitted);

        EnemyHittedMessage enemyHittedMessage = new EnemyHittedMessage();
        enemyHittedMessage.setEnemyName(enemy.getName());

        try {
            String enemyHittedMessageString = objectMapper.writeValueAsString(enemyHittedMessage);

            messageWrapper.setPayload(enemyHittedMessageString);

            return messageWrapper;

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        //TODO
        return null;
    }
}
