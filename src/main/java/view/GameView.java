package view;

import controller.EnemyController;
import controller.PlayerController;
import model.Direction;
import model.GameModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class GameView extends JFrame {

    private final GameModel gameModel;
    private final JTable gameTable;
    private final GameTableModel tableModel;
    private final JLabel scoreLabel = new JLabel("Score: 0");

    private final PlayerController playerController;
    private final JPanel livesPanel = new JPanel();
    private int previousLives = 3;
    private boolean gameOverDisplayed = false;


    public GameView(GameModel gameModel) {
        this.gameModel = gameModel;
        this.tableModel = new GameTableModel(gameModel);
        this.gameTable = new JTable(tableModel);
        gameModel.setView(this);


        setTitle("Pac-Man");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();

        playerController = new PlayerController(gameModel, this);
        Thread playerThread = new Thread(playerController);
        playerThread.setDaemon(true);
        playerThread.start();


        EnemyController enemyController = new EnemyController(gameModel, this);
        Thread enemyThread = new Thread(enemyController);
        enemyThread.setDaemon(true);
        enemyThread.start();

        updateGame();
        SwingUtilities.invokeLater(gameTable::requestFocusInWindow);

        setupKeyBindings();
        setVisible(true);
    }


    private void initUI() {
        int rows = gameModel.getRows();
        int cols = gameModel.getCols();

        int maxWindowSize = 800;
        int cellSize = Math.max(24, maxWindowSize / Math.max(rows, cols));

        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setForeground(Color.YELLOW);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gameTable.setRowHeight(cellSize);
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

        livesPanel.setBackground(new Color(10, 15, 40));
        livesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        updateLivesDisplay();

        setLayout(new BorderLayout());
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(10, 15, 40));
        topBar.add(scoreLabel, BorderLayout.WEST);
        topBar.add(livesPanel, BorderLayout.EAST);
        add(topBar, BorderLayout.NORTH);

        add(gameTable, BorderLayout.CENTER);

        int windowWidth = cellSize * cols;
        int windowHeight = cellSize * rows + 50;
        setSize(windowWidth, windowHeight);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void updateLivesDisplay() {
        int lives = gameModel.getPlayer().getLives();

        if (livesPanel.getComponentCount() == 0) {
            for (int i = 0; i < 3; i++) {
                JLabel heart = createHeartLabel();
                livesPanel.add(heart);
            }
        }


        for (int i = 0; i < 3; i++) {
            livesPanel.getComponent(i).setVisible(i < lives);
        }


        if (lives < previousLives && lives >= 0 && lives < livesPanel.getComponentCount()) {
            JLabel lostHeart = (JLabel) livesPanel.getComponent(lives);

            new Thread(() -> {
                try {
                    for (int i = 0; i < 6; i++) {
                        boolean visible = i % 2 == 0;
                        final boolean finalVisible = visible;
                        SwingUtilities.invokeLater(() -> lostHeart.setVisible(finalVisible));
                        Thread.sleep(100);
                    }
                    SwingUtilities.invokeLater(() -> lostHeart.setVisible(false));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }


        previousLives = lives;
        livesPanel.revalidate();
        livesPanel.repaint();
    }

    private JLabel createHeartLabel() {
        ImageIcon heartIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/heart.png")));
        Image scaled = heartIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        return new JLabel(new ImageIcon(scaled));
    }


    private void setupKeyBindings() {
        InputMap inputMap = gameTable.getInputMap(JComponent.WHEN_FOCUSED);
        ActionMap actionMap = gameTable.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("UP"), "moveUp");
        actionMap.put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerController.setDirection(Direction.UP);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
        actionMap.put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerController.setDirection(Direction.DOWN);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        actionMap.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerController.setDirection(Direction.LEFT);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
        actionMap.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerController.setDirection(Direction.RIGHT);
            }
        });

        InputMap windowInputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap windowActionMap = getRootPane().getActionMap();

        windowInputMap.put(KeyStroke.getKeyStroke("meta shift X"), "quitToMenu");
        windowActionMap.put("quitToMenu", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                        GameView.this,
                        "Czy na pewno chcesz wrócić do menu?",
                        "Powrót do menu",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    dispose();
                    new MenuView();
                }
            }
        });
    }
//na macbooku systomowo odpala sie komunikat czy chce wylaczyc dzialanie wszystkich aplikacji, stad zmiana na "x"
// oraz zmienilam crtl na meta ze wzgledu na to ze pracuje na macbooku:)



    public void updateGame() {
        if (gameModel.isGameOver() && !gameOverDisplayed) {
            gameOverDisplayed = true;
            SwingUtilities.invokeLater(() -> showGameOverDialog());
            return;
        }


        tableModel.fireTableDataChanged();
        scoreLabel.setText("Score: " + gameModel.getPlayer().getScore());
        updateLivesDisplay();
        gameTable.repaint();
    }


    public void showGameOverDialog() {
        JDialog dialog = new JDialog(this, "Game Over", true);
        dialog.setLayout(new BorderLayout());

        JLabel title = new JLabel("GAME OVER!", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.RED);


        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/pacman.png")));
        Image scaled = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaled));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton menuButton = new JButton("Back to Menu");
        menuButton.setFont(new Font("Arial", Font.BOLD, 16));
        menuButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Podaj nazwę:");
            if (name != null && !name.isBlank()) {
                HighScores.addScore(name.trim(), gameModel.getPlayer().getScore());
            }
            dialog.dispose();
            dispose();
            new MenuView();
        });


        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(20, 20, 40));
        centerPanel.add(title, BorderLayout.NORTH);
        centerPanel.add(imageLabel, BorderLayout.CENTER);
        centerPanel.add(menuButton, BorderLayout.SOUTH);

        dialog.add(centerPanel, BorderLayout.CENTER);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        dialog.setVisible(true);


    }

}




