package model;

public class Cell {

    public enum CellType {
        EMPTY,
        WALL,
        POINT,
        POWER_UP,
        PLAYER,
        ENEMY
    }

    private CellType type;

    public Cell(CellType type) {
        this.type = type;
    }

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }
}

