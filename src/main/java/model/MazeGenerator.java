package model;

import java.util.Random;

public class MazeGenerator {

    private final int rows;
    private final int cols;
    private final Random random;

    public MazeGenerator(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.random = new Random();
    }

    public Cell[][] generate() {
        Cell[][] board = new Cell[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                board[r][c] = new Cell(Cell.CellType.WALL);
            }
        }


        generateMaze(board, 1, 1);


        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j].getType() == Cell.CellType.EMPTY && random.nextDouble() < 0.99) {
                    board[i][j].setType(Cell.CellType.POINT);
                }
            }
        }

        return board;
    }


    private void generateMaze(Cell[][] board, int r, int c) {
        Direction[] directions = Direction.values();
        shuffleArray(directions);

        board[r][c].setType(Cell.CellType.EMPTY);

        for (Direction dir : directions) {
            int newR = r, newC = c;

            switch (dir) {
                case UP -> newR = r - 2;
                case DOWN -> newR = r + 2;
                case LEFT -> newC = c - 2;
                case RIGHT -> newC = c + 2;
            }

            if (newR > 0 && newR < rows - 1 && newC > 0 && newC < cols - 1) {
                if (board[newR][newC].getType() == Cell.CellType.WALL) {
                    board[(r + newR) / 2][(c + newC) / 2].setType(Cell.CellType.EMPTY);
                    generateMaze(board, newR, newC);
                }
            }
        }
    }

    private void shuffleArray(Direction[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Direction temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
}
