package view;

import model.Direction;
import model.GameModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class GameView extends JFrame {

    private GameModel gameModel;
    private JTable jTable;


    public GameView(GameModel gameModel) throws HeadlessException {
        this.gameModel = gameModel;

        setTitle("Pac Man");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);
        initUI();
        setVisible(true);
        setFocusable(true);
        requestFocusInWindow();
        enableEvents(AWTEvent.KEY_EVENT_MASK);


    }


    private void initUI() {
        int rows = gameModel.getRows();
        int cols = gameModel.getCols();
        int windowSize = 600; // rozmiar całego okna
        int cellSize = windowSize / Math.max(rows, cols); // rozmiar komórki

        jTable = new JTable(new GameTableModel(gameModel));
        jTable.setRowHeight(cellSize);
        jTable.setEnabled(false);
        jTable.setTableHeader(null);

        // Granatowe tło i brak siatki
        jTable.setBackground(new Color(10, 15, 40));
        jTable.setShowGrid(false);

        // Ustaw szerokość kolumn
        for (int i = 0; i < cols; i++) {
            jTable.getColumnModel().getColumn(i).setPreferredWidth(cellSize);
        }

        // Dodaj tabelę bez scrolla
        setLayout(new BorderLayout());
        add(jTable, BorderLayout.CENTER);

        setSize(windowSize, windowSize);
        setResizable(false);
        setLocationRelativeTo(null);
    }


    @Override
    protected void processKeyEvent(KeyEvent e) {
        if (e.getID() != KeyEvent.KEY_PRESSED) return;

        Direction direction = switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> Direction.UP;
            case KeyEvent.VK_DOWN -> Direction.DOWN;
            case KeyEvent.VK_LEFT -> Direction.LEFT;
            case KeyEvent.VK_RIGHT -> Direction.RIGHT;
            default -> null;
        };

        if (direction != null) {
            gameModel.movePlayer(direction);
            jTable.repaint();
        }
    }


}
