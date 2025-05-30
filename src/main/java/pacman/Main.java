package pacman;

import controller.EnemyController;
import model.GameModel;
import view.GameView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("✅ START GAME!");


        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {

            e.printStackTrace();
        });

        SwingUtilities.invokeLater(() -> {
            try {
                GameModel model = new GameModel(15, 15);
                GameView view = new GameView(model);
                view.updateGame();

                EnemyController enemyController = new EnemyController(model, view);
                Thread enemyThread = new Thread(enemyController);
                enemyThread.setDaemon(true);
                enemyThread.start();

            } catch (Exception e) {

                e.printStackTrace();
            }
        });
    }
}
