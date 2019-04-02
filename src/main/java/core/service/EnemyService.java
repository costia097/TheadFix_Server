package core.service;

import core.entities.enemy.Enemy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnemyService {

    private List<Enemy> enemies = new ArrayList<>();

    public void doEnemyMoveRight() {
        enemies.forEach(enemy -> enemy.setX(enemy.getX() + 1));
    }

    public void doEnemyMoveLeft() {
        enemies.forEach(enemy -> enemy.setX(enemy.getX() - 1));
    }

    public List<Enemy> findWithPositionInGivenRange(float startPosition, float endPosition) {
        return enemies.stream()
                .filter(enemy -> enemy.getX() <= endPosition && enemy.getX() >= startPosition)
                .collect(Collectors.toList());
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }

}
