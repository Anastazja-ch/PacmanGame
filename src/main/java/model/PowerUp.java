package model;

public class PowerUp {
    private int row;
    private int col;
    private PowerUpType type;

    public PowerUp(int row, int col, PowerUpType type) {
        this.row = row;
        this.col = col;
        this.type = type;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public PowerUpType getType() {
        return type;
    }

    public void applyEffect(Player player) {
        player.addScore(50);
        System.out.println("Power-up: +50 points!");
    }

}


