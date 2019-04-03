package core.tickBeat;

import core.annotation.TickBeater;
import core.entities.enemy.Enemy;
import core.message.EnemyStateMessage;
import core.message.MessageType;
import core.service.EnemyService;
import core.service.NetworkService;
import core.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@TickBeater
public class EnemyTickBeater extends AbstractTickBeater{
    @Autowired
    private EnemyService enemyService;

    @Autowired
    private NetworkService networkService;

    @Autowired
    private PlayerService playerService;

    private boolean isEnemyTickBeaterWorking = true;

    public void startEnemyTick() {

        Thread thread = new Thread(() -> {
            while (isEnemyTickBeaterWorking) {
                List<String> enemyStatePayloads = enemyService.getEnemies().stream()
                        .map(this::createEnemyStateMessages)
                        .map(this::generateJsonStringPayload)
                        .map(s -> generateMessageWrapper(s, MessageType.EnemyState))
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

    public boolean isEnemyTickBeaterWorking() {
        return isEnemyTickBeaterWorking;
    }

    //TODO
    public void setEnemyTickBeaterWorking(boolean enemyTickBeaterWorking) {
        isEnemyTickBeaterWorking = enemyTickBeaterWorking;
    }
}
