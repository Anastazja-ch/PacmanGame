package view;

import model.Direction;
import model.GameModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameView extends JFrame {

    private final GameModel gameModel;
    private final JTable gameTable;
    private final GameTableModel tableModel;
    private final JLabel scoreLabel = new JLabel("Score: 0");

    public GameView(GameModel gameModel) {
        this.gameModel = gameModel;
        this.tableModel = new GameTableModel(gameModel);
        this.gameTable = new JTable(tableModel);

        setTitle("Pac-Man");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();

        setVisible(true);

        // Ustaw focus na tabelę po wyświetleniu okna
        SwingUtilities.invokeLater(() -> gameTable.requestFocusInWindow());

        setupKeyBindings();
    }

    private void initUI() {
        int rows = gameModel.getRows();
        int cols = gameModel.getCols();
        int windowSize = 600;
        int cellSize = windowSize / Math.max(rows, cols);

        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setForeground(Color.YELLOW);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gameTable.setRowHeight(cellSize);
        // WAŻNE: nie wyłączaj setEnabled, by móc odbierać zdarzenia klawiatury
        gameTable.setEnabled(true);
        gameTable.setTableHeader(null);
        gameTable.setBackground(new Color(10, 15, 40));
        gameTable.setShowGrid(false);
        gameTable.setCellSelectionEnabled(false);
        gameTable.setRowSelectionAllowed(false);
        gameTable.setColumnSelectionAllowed(false);

        for (int i = 0; i < cols; i++) {
            gameTable.getColumnModel().getColumn(i).setPreferredWidth(cellSize);
        }

        gameTable.setDefaultRenderer(Object.class, new CellRenderer());

        setLayout(new BorderLayout());
        add(scoreLabel, BorderLayout.NORTH);
        add(gameTable, BorderLayout.CENTER);

        setSize(windowSize, windowSize);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void setupKeyBindings() {
        InputMap inputMap = gameTable.getInputMap(JComponent.WHEN_FOCUSED);
        ActionMap actionMap = gameTable.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("UP"), "moveUp");
        actionMap.put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movePlayer(Direction.UP);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
        actionMap.put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movePlayer(Direction.DOWN);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        actionMap.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movePlayer(Direction.LEFT);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
        actionMap.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movePlayer(Direction.RIGHT);
            }
        });
    }

    private void movePlayer(Direction direction) {
        gameModel.movePlayer(direction);
        tableModel.fireTableDataChanged();
        scoreLabel.setText("Score: " + gameModel.getPlayer().getScore());
    }
}
