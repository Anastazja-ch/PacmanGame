package view;

import model.Cell;
import model.GameModel;

import javax.swing.table.AbstractTableModel;

import static model.Cell.CellType.PLAYER;

public class GameTableModel extends AbstractTableModel {
private final GameModel gameModel;

    public GameTableModel(GameModel gameModel) {
        this.gameModel = gameModel;
    }


    @Override
    public int getRowCount() {
        return gameModel.getRows();
    }

    @Override
    public int getColumnCount() {
        return gameModel.getCols();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cell cell = gameModel.getBoard()[rowIndex][columnIndex];
        return switch (cell.getType()) {
            case WALL -> "🟥️";
            case PLAYER -> "🙂";
            case ENEMY -> "👻";
            case POINT -> "🍒";
            case POWER_UP -> "🌟";
            default -> "";
        };

        }

    }

