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
        switch (type) {
            case SPEED_BOOST -> {
                player.setSpeed(player.getSpeed() + 1);
                System.out.println("Speed boost applied!");
            }
            case INVINCIBILITY -> {
                player.setHasPowerUp(true);
                System.out.println("Invincibility applied!");
            }
            case EXTRA_POINTS -> {
                player.addScore(50);
                System.out.println("Extra points!");
            }
            case FREEZE_ENEMIES -> {
                System.out.println("Enemies frozen!");
            }
            case REVERSE_TIME -> {
                System.out.println("Time reversed!");
            }
        }
    }
}

