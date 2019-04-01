package core.controller;

import core.annotation.GameController;
import core.service.EnemyService;
import org.springframework.beans.factory.annotation.Autowired;

@GameController
public class EnemyController {
    @Autowired
    private EnemyService enemyService;

    private boolean isRightLeftFlowWorking = true;

    public void startRightLeftFlow() {

        Thread thread = new Thread(() -> {
            while (isRightLeftFlowWorking) {
                for (int i = 0; i < 3; i++) {
                    enemyService.doEnemyMoveLeft();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < 3; i++) {
                    enemyService.doEnemyMoveRight();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
}
