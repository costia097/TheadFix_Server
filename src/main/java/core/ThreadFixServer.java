package core;

import core.config.ServerConfig;
import core.controller.EnemyController;
import core.entities.enemy.Enemy;
import core.service.EnemyService;
import core.service.ServerService;
import core.tickBeat.EnemyTickBeater;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.io.IOException;

public class ThreadFixServer {
    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();

        annotationConfigApplicationContext.register(ServerConfig.class);
        annotationConfigApplicationContext.refresh();

        ServerService severService = (ServerService) annotationConfigApplicationContext.getBean("serverService");

        //TODO configuration of server

        EnemyTickBeater enemyTickBeater = (EnemyTickBeater) annotationConfigApplicationContext.getBean("enemyTickBeater");
        EnemyService enemyService = (EnemyService) annotationConfigApplicationContext.getBean("enemyService");
        EnemyController enemyController = (EnemyController) annotationConfigApplicationContext.getBean("enemyController");

        Enemy enemy = new Enemy();
        enemy.setName("Enemy");

        enemy.setX(1.34f);
        enemy.setY(-3.5f);

        enemyService.addEnemy(enemy);

        enemyTickBeater.startEnemyTick();

        enemyController.startRightLeftFlow();

        severService.startServer();
    }
}
