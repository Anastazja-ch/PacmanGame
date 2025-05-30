package view;

import model.Cell;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CellRenderer extends JLabel implements TableCellRenderer {
    private static final Map<Cell.CellType, ImageIcon> scaledIcons = new HashMap<>();

    public CellRenderer() {
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setOpaque(true);

        // Przygotuj skalowane ikony raz
        for (Cell.CellType type : Cell.CellType.values()) {
            ImageIcon icon = CellTypeVisuals.getImageIcon(type.name());
            if (icon != null && icon.getIconWidth() > 0) {
                Image scaledImage = icon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
                scaledIcons.put(type, new ImageIcon(scaledImage));
            } else {
                scaledIcons.put(type, null);
            }
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        if (value instanceof Cell cell) {
            ImageIcon icon = scaledIcons.get(cell.getType());
            setIcon(icon);
            setText("");
            setBackground(Color.BLACK);
        } else {
            setIcon(null);
            setText("?");
            setBackground(Color.MAGENTA);
        }
        return this;
    }
}
