package core.service;

import core.entities.ServerPlayer;
import core.entities.enemy.Enemy;
import core.message.Direction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnemyDamagedService {

    @Autowired
    private EnemyService enemyService;


    public List<Enemy> findAppropriativeEnemiesForBomb(ServerPlayer targetServerPlayer, Direction direction){
        float startPosition;
        float endPosition;

        if (Direction.Right.equals(direction)) {
            startPosition = targetServerPlayer.getX();
            endPosition = startPosition + 5f;
        } else {
            startPosition = targetServerPlayer.getX() - 5f;
            endPosition = targetServerPlayer.getX();
        }

        return enemyService.findWithPositionInGivenRange(startPosition, endPosition);
    }

    public List<Enemy> findAppropriativeEnemiesForSword(ServerPlayer targetServerPlayer, Direction direction) {
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
}
