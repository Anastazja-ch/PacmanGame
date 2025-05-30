package controller;

import model.Direction;
import model.GameModel;
import view.GameView;

public class PlayerController implements Runnable {

    private final GameModel model;
    private final GameView view;
    private Direction currentDirection = null;
    private boolean running = true;

    public PlayerController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
    }

    public void setDirection(Direction dir) {
        this.currentDirection = dir;
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            if (currentDirection != null) {
                model.movePlayer(currentDirection);
                view.updateGame();
            }
            try {
                Thread.sleep(150); // Płynność ruchu
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
