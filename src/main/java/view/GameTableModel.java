package view;

import model.Cell;
import model.GameModel;


import javax.swing.*;
import javax.swing.table.AbstractTableModel;


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
        return gameModel.getBoard()[rowIndex][columnIndex];
    }


}

