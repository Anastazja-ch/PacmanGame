package view;

import model.Cell;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class CellRenderer extends JLabel implements TableCellRenderer {

    public CellRenderer() {
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        if (value instanceof Cell cell) {
            switch (cell.getType()) {
                case WALL -> setBackground(Color.DARK_GRAY);
                case PLAYER -> setBackground(Color.YELLOW);
                case ENEMY -> setBackground(Color.RED);
                case POINT -> setBackground(Color.WHITE);
                case POWER_UP -> setBackground(Color.PINK);
                case EMPTY -> setBackground(Color.BLACK);
            }
        } else {
            setBackground(Color.MAGENTA);
        }
        return this;
    }
}
