package model;

public class Cell {

    private boolean hasPointUnderneath = false;

    public Cell(boolean hasPointUnderneath) {
        this.hasPointUnderneath = hasPointUnderneath;


    }

    public boolean hasPointUnderneath() {
        return hasPointUnderneath;
    }


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

    public boolean isHasPointUnderneath() {
        return hasPointUnderneath;
    }

    public void setHasPointUnderneath(boolean hasPointUnderneath) {
        this.hasPointUnderneath = hasPointUnderneath;
    }
}

