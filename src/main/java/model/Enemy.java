package model;

public class Enemy {
    private int row;
    private int col;
    private boolean isAlive;

    public Enemy(int row, int col) {
        this.row = row;
        this.col = col;
        this.isAlive = true;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        this.isAlive = alive;
    }
}
