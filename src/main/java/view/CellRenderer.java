package view;

import model.Cell;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CellRenderer extends JLabel implements TableCellRenderer {

    private final Map<Cell.CellType, ImageIcon> icons = new HashMap<>();
    private final int ICON_SIZE = 40;

    public CellRenderer() {
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setOpaque(true);

        loadIcons();
    }

    private void loadIcons() {
        icons.put(Cell.CellType.PLAYER, loadScaledIcon("/images/pacman.png"));
        icons.put(Cell.CellType.ENEMY, loadScaledIcon("/images/ghost.png"));
        icons.put(Cell.CellType.WALL, loadScaledIcon("/images/wall.png"));
        icons.put(Cell.CellType.POINT, loadScaledIcon("/images/dot.png"));
        icons.put(Cell.CellType.POWER_UP, loadScaledIcon("/images/strawberry.png"));

    }

    private ImageIcon loadScaledIcon(String path) {
        try {
            ImageIcon rawIcon = new ImageIcon(getClass().getResource(path));
            Image scaled = rawIcon.getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {

        setIcon(null);
        setBackground(Color.BLACK);

        if (value instanceof Cell cell) {
            Cell.CellType type = cell.getType();
            if (icons.containsKey(type)) {
                setIcon(icons.get(type));
            } else {
                setBackground(Color.BLACK);
            }
        }

        return this;
    }
}
