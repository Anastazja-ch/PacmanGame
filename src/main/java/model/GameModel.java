package model;

import view.GameView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameModel {
    private final int rows;
    private final int cols;
    private final Cell[][] board;
    private Player player;
    private final List<Enemy> enemies = new ArrayList<>();

    private GameView view;
    private boolean gameOverShown = false;

    public void setView(GameView view) {
        this.view = view;
    }


    private final Random random;


    private final List<PowerUp> powerUps = new ArrayList<>();


    public GameModel(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.random = new Random();


        MazeGenerator generator = new MazeGenerator(rows, cols);
        this.board = generator.generate();


        initiatePlayer();
        initiateEnemies(2);
        startPowerUpSpawner();


    }


    public void initiatePlayer() {
        int r;
        int c;
        do {
            r = random.nextInt(rows);
            c = random.nextInt(cols);
        } while (board[r][c].getType() != Cell.CellType.EMPTY);

        player = new Player(r, c);
        board[r][c].setType(Cell.CellType.PLAYER);
    }


    public void movePlayer(Direction direction) {
        int currentRow = player.getRow();
        int currentCol = player.getCol();
        int newRow = currentRow;
        int newCol = currentCol;

        if (direction == Direction.UP) {
            newRow--;
        } else if (direction == Direction.DOWN) {
            newRow++;
        } else if (direction == Direction.LEFT) {
            newCol--;
        } else if (direction == Direction.RIGHT) {
            newCol++;
        }

        if (newRow < 0 || newRow >= rows || newCol < 0 || newCol >= cols) {
            System.out.println("player wanted to move out of the board!");
            return;
        }

        if (board[newRow][newCol].getType() == Cell.CellType.WALL) {
            System.out.println("player hit the wall!");
            return;
        }

        if (board[newRow][newCol].getType() == Cell.CellType.POINT) {
            player.addScore(10);
            System.out.println("player got a score " + player.getScore());
        }

        if (board[newRow][newCol].getType() == Cell.CellType.POWER_UP) {
            PowerUp collected = null;
            for (PowerUp p : powerUps) {
                if (p.getRow() == newRow && p.getCol() == newCol) {
                    collected = p;
                    break;
                }
            }

            if (collected != null) {
                collected.applyEffect(player);
                powerUps.remove(collected);
            }
        }


        board[currentRow][currentCol].setType(Cell.CellType.EMPTY);
        player.setRow(newRow);
        player.setCol(newCol);
        board[newRow][newCol].setType(Cell.CellType.PLAYER);

        for (Enemy enemy : enemies) {
            if (enemy.getRow() == player.getRow() && enemy.getCol() == player.getCol()) {
                player.loseLife();
                System.out.println("Kolizja po ruchu gracza! Życia: " + player.getLives());

                if (player.getLives() <= 0 && !gameOverShown) {
                    gameOverShown = true;
                    SwingUtilities.invokeLater(() -> {
                        if (view != null) {
                            view.showGameOverDialog();
                        }
                    });
                    return;
                } else {
                    int r, c;
                    do {
                        r = random.nextInt(rows);
                        c = random.nextInt(cols);
                    } while (board[r][c].getType() != Cell.CellType.EMPTY);

                    board[player.getRow()][player.getCol()].setType(Cell.CellType.EMPTY);
                    player.setRow(r);
                    player.setCol(c);
                    board[r][c].setType(Cell.CellType.PLAYER);
                }
                break;
            }
        }

    }

    public void initiateEnemies(int count) {
        for (int i = 0; i < count; i++) {
            int r, c;
            do {
                r = random.nextInt(rows);
                c = random.nextInt(cols);
            } while (
                    board[r][c].getType() != Cell.CellType.EMPTY ||
                            (r == player.getRow() && c == player.getCol())
            );

            Enemy e = new Enemy(r, c);
            enemies.add(e);
            board[r][c].setType(Cell.CellType.ENEMY);
        }
    }


    public void moveEnemy(Enemy enemy, Direction direction) {
        if (player.getLives() <= 0 || gameOverShown) return;

        int currentRow = enemy.getRow();
        int currentCol = enemy.getCol();
        int newRow = currentRow;
        int newCol = currentCol;

        switch (direction) {
            case UP -> newRow--;
            case DOWN -> newRow++;
            case LEFT -> newCol--;
            case RIGHT -> newCol++;
        }

        if (newRow < 0 || newRow >= rows || newCol < 0 || newCol >= cols) return;

        Cell target = board[newRow][newCol];
        if (target.getType() == Cell.CellType.WALL || target.getType() == Cell.CellType.ENEMY) return;


        if (target.getType() == Cell.CellType.POINT) {
            target.setHasPointUnderneath(true);
        }

        if (target.getType() == Cell.CellType.PLAYER) {
            player.loseLife();
            System.out.println("Kolizja! Pozostałe życia: " + player.getLives());

            if (player.getLives() <= 0 && !gameOverShown) {
                gameOverShown = true;
                SwingUtilities.invokeLater(() -> {
                    if (view != null) {
                        view.showGameOverDialog();
                    }
                });
                return;
            } else {
                int r, c;
                do {
                    r = random.nextInt(rows);
                    c = random.nextInt(cols);
                } while (board[r][c].getType() != Cell.CellType.EMPTY);

                board[player.getRow()][player.getCol()].setType(Cell.CellType.EMPTY);
                player.setRow(r);
                player.setCol(c);
                board[r][c].setType(Cell.CellType.PLAYER);
            }
        }

        Cell previous = board[currentRow][currentCol];
        if (previous.hasPointUnderneath()) {
            previous.setType(Cell.CellType.POINT);
            previous.setHasPointUnderneath(false);
        } else {
            previous.setType(Cell.CellType.EMPTY);
        }

        board[newRow][newCol].setType(Cell.CellType.ENEMY);
        enemy.setRow(newRow);
        enemy.setCol(newCol);
    }


    private void startPowerUpSpawner() {
        Thread spawner = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (random.nextDouble() <= 0.25) {
                    int r, c;
                    do {
                        r = random.nextInt(rows);
                        c = random.nextInt(cols);
                    } while (board[r][c].getType() != Cell.CellType.EMPTY);

                    PowerUpType[] types = PowerUpType.values();
                    PowerUpType randomType = types[random.nextInt(types.length)];

                    PowerUp powerUp = new PowerUp(r, c, randomType);
                    powerUps.add(powerUp);
                    board[r][c].setType(Cell.CellType.POWER_UP);

                    System.out.println("Generated power-up: " + randomType + " at " + r + "," + c);
                }
            }
        });
        spawner.setDaemon(true);
        spawner.start();
    }

    public boolean isGameOver() {
        return player.getLives() <= 0;
    }


    public boolean isGameOverShown() {
        return gameOverShown;
    }

    public void setGameOverShown(boolean shown) {
        this.gameOverShown = shown;
    }


    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Cell[][] getBoard() {
        return board;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }


}

