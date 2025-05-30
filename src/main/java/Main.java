import model.GameModel;
import view.GameView;


import javax.swing.*;

import controller.EnemyController;


public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                GameModel model = new GameModel(20, 20);
                GameView view = new GameView(model);
                model.initiateEnemies(3);

                EnemyController enemyController = new EnemyController(model, view);
                Thread enemyThread = new Thread(enemyController);
                enemyThread.setDaemon(true);
                enemyThread.start();
            } catch (Exception e) {
                e.printStackTrace(); // <- TO WYDRUKUJE NAM PROBLEM
            }
        });
    }

}




