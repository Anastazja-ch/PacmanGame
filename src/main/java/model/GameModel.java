package model;

import java.util.Random;

public class GameModel {
    private final int rows;
    private final int cols;
    private Cell[][] board;
    private Player player;
    private Enemy enemy;
    private Random random;

    public GameModel(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.random = new Random();

        MazeGenerator generator = new MazeGenerator(rows, cols);
        this.board = generator.generate();

        initiatePlayer();
        initiateEnemy();


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

        board[currentRow][currentCol].setType(Cell.CellType.EMPTY);
        player.setRow(newRow);
        player.setCol(newCol);
        board[newRow][newCol].setType(Cell.CellType.PLAYER);
    }

    public void initiateEnemy() {
        int r;
        int c;
        do {
            r = random.nextInt(rows);
            c = random.nextInt(cols);
        } while (
                board[r][c].getType() != Cell.CellType.EMPTY ||
                        (r == player.getRow() && c == player.getCol())
        );

        enemy = new Enemy(r, c);
        board[r][c].setType(Cell.CellType.ENEMY);
    }

    public void moveEnemy(Direction direction) {
        int currentRow = enemy.getRow();
        int currentCol = enemy.getCol();
        int newRow = currentRow;
        int newCol = currentCol;

        if (direction == Direction.UP) {
            newRow--;
        } else if (direction == Direction.DOWN) {
            newRow++;
        } else if (direction == Direction.LEFT) {
            newCol--;
        } else if (direction == Direction.RIGHT) newCol++;


        if (newRow < 0 || newRow >= rows || newCol < 0 || newCol >= cols) {
            System.out.println("enemy wanted to move out of the board");
            return;
        }
        if (board[newRow][newCol].getType() == Cell.CellType.WALL) {
            System.out.println("enemy hit the wall");
            return;
        }
        board[currentRow][currentCol].setType(Cell.CellType.EMPTY);
        enemy.setRow(newRow);
        enemy.setCol(newCol);
        board[newRow][newCol].setType(Cell.CellType.ENEMY);

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

    public Enemy getEnemy() {
        return enemy;
    }
}

