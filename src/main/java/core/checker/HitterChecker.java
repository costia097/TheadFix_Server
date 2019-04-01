package core.checker;

import core.annotation.GameChecker;
import core.service.EnemyService;
import core.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;

@GameChecker
public class HitterChecker {
    @Autowired
    private PlayerService playerService;

    @Autowired
    private EnemyService enemyService;

    //TODO
    public void startCheckHits() {
//        Thread thread = new Thread(() -> {
//        });
//        thread.start();
    }
}
