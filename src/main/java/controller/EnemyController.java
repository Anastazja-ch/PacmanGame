package controller;

import model.Cell;
import model.Direction;
import model.Enemy;
import model.GameModel;
import view.GameView;

import java.util.*;

public class EnemyController implements Runnable {

    private final GameModel model;
    private final GameView view;
    private final Map<Enemy, int[]> lastPositions = new HashMap<>();

    public EnemyController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void run() {
        while (true) {
            if (!model.isEnemiesFrozen()) {
                for (Enemy e : model.getEnemies()) {
                    Direction dir = chooseDirectionAvoidingBacktracking(e);
                    if (dir != null) {
                        int[] last = new int[]{e.getRow(), e.getCol()};
                        model.moveEnemy(e, dir);
                        lastPositions.put(e, last);
                    }
                }
                view.updateGame();
            }

            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Direction chooseDirectionAvoidingBacktracking(Enemy e) {
        int row = e.getRow();
        int col = e.getCol();

        List<Direction> possible = new ArrayList<>();

        for (Direction dir : Direction.values()) {
            int[] pos = getNewPosition(row, col, dir);
            if (canMove(pos[0], pos[1])) {
                int[] last = lastPositions.getOrDefault(e, new int[]{-1, -1});
                if (pos[0] != last[0] || pos[1] != last[1]) {
                    possible.add(dir);
                }
            }
        }

        if (possible.isEmpty()) {
            for (Direction dir : Direction.values()) {
                int[] pos = getNewPosition(row, col, dir);
                if (canMove(pos[0], pos[1])) {
                    possible.add(dir);
                }
            }
        }

        if (possible.isEmpty()) return null;

        Collections.shuffle(possible);
        return possible.get(0);
    }

    private int[] getNewPosition(int row, int col, Direction dir) {
        return switch (dir) {
            case UP -> new int[]{row - 1, col};
            case DOWN -> new int[]{row + 1, col};
            case LEFT -> new int[]{row, col - 1};
            case RIGHT -> new int[]{row, col + 1};
        };
    }

    private boolean canMove(int r, int c) {
        if (r < 0 || r >= model.getRows() || c < 0 || c >= model.getCols()) return false;
        Cell target = model.getBoard()[r][c];
        return target.getType() != Cell.CellType.WALL && target.getType() != Cell.CellType.ENEMY;
    }
}
