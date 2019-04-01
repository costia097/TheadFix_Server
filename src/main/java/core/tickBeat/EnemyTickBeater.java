package core.tickBeat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.annotation.TickBeater;
import core.entities.enemy.Enemy;
import core.message.EnemyStateMessage;
import core.message.MessageType;
import core.message.MessageWrapper;
import core.service.EnemyService;
import core.service.NetworkService;
import core.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@TickBeater
public class EnemyTickBeater {
    @Autowired
    private EnemyService enemyService;

    @Autowired
    private NetworkService networkService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private ObjectMapper objectMapper;

    private boolean isEnemyTickBeaterWorking = true;

    public void startEnemyTick() {

        Thread thread = new Thread(() -> {
            while (isEnemyTickBeaterWorking) {
                List<String> enemyStatePayloads = enemyService.getEnemies().stream()
                        .map(this::createEnemyStateMessages)
                        .map(this::generateJsonStringPayload)
                        .map(this::generateMessageWrapper)
                        .map(this::generateJsonStringPayload)
                        .collect(Collectors.toList());

                playerService.getPlayers().forEach(serverPlayer ->
                        enemyStatePayloads.forEach(payload -> networkService.sendMessageTo(serverPlayer, payload)));

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private EnemyStateMessage createEnemyStateMessages(Enemy enemy) {
        EnemyStateMessage enemyStateMessage = new EnemyStateMessage();

        enemyStateMessage.setName(enemy.getName());
        enemyStateMessage.setX(enemy.getX());
        enemyStateMessage.setY(enemy.getY());

        return enemyStateMessage;
    }

    private MessageWrapper generateMessageWrapper(String payload) {
        MessageWrapper messageWrapper = new MessageWrapper();

        messageWrapper.setPayload(payload);
        messageWrapper.setMessageType(MessageType.EnemyState);

        return messageWrapper;
    }

    private String generateJsonStringPayload(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public boolean isEnemyTickBeaterWorking() {
        return isEnemyTickBeaterWorking;
    }

    //TODO
    public void setEnemyTickBeaterWorking(boolean enemyTickBeaterWorking) {
        isEnemyTickBeaterWorking = enemyTickBeaterWorking;
    }
}
