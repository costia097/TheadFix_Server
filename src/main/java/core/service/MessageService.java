package core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.entities.enemy.Enemy;
import core.message.BombExplosionMessage;
import core.message.EnemyHittedMessage;
import core.message.MessageType;
import core.message.MessageWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    private ObjectMapper objectMapper;

    public String generatePayload(MessageWrapper messageWrapper) {
        try {
            return objectMapper.writeValueAsString(messageWrapper);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //TODO
        return null;
    }

    public MessageWrapper createBombExplosionMessageWrapper(List<Enemy> enemies, String bombId) {
        MessageWrapper messageWrapper = new MessageWrapper();

        messageWrapper.setMessageType(MessageType.BombExploded);

        BombExplosionMessage bombExplosionMessage = new BombExplosionMessage();

        bombExplosionMessage.setBombId(bombId);

        List<EnemyHittedMessage> enemyHittedMessages = enemies.stream()
                .map(this::createEnemyHittedMessage)
                .collect(Collectors.toList());

        bombExplosionMessage.setEnemiesHitted(enemyHittedMessages);

        try {
            String bombExplosionMessageString = objectMapper.writeValueAsString(bombExplosionMessage);
            messageWrapper.setPayload(bombExplosionMessageString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return messageWrapper;
    }

    private EnemyHittedMessage createEnemyHittedMessage(Enemy enemy) {
        EnemyHittedMessage enemyHittedMessage = new EnemyHittedMessage();

        enemyHittedMessage.setEnemyName(enemy.getName());

        return enemyHittedMessage;
    }

    public MessageWrapper createEnemyHittedMessageWrapper(Enemy enemy) {
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
