package view;

import model.GameModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public class MenuView extends JFrame {

    public MenuView() {
        setTitle("Pac-Man - Menu Główne");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initUI();
        setVisible(true);
    }

    private void initUI() {

        setSize(500, 500);
        setLayout(new BorderLayout());


        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(20, 20, 50));


        ImageIcon logoIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/pacman.png")));
        Image scaled = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaled));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);


        JLabel title = new JLabel("PAC MAN", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(Color.YELLOW);

        topPanel.add(logoLabel, BorderLayout.NORTH);
        topPanel.add(title, BorderLayout.SOUTH);


        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 15, 15));
        buttonPanel.setBackground(new Color(20, 20, 50));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));

        JButton newGameButton = new JButton("New Game");
        JButton highScoresButton = new JButton("High Scores");
        JButton exitButton = new JButton("Exit");

        newGameButton.setFont(new Font("Arial", Font.BOLD, 18));
        highScoresButton.setFont(new Font("Arial", Font.BOLD, 18));
        exitButton.setFont(new Font("Arial", Font.BOLD, 18));

        newGameButton.addActionListener(e -> startNewGame());
        highScoresButton.addActionListener(e -> showHighScores());
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(newGameButton);
        buttonPanel.add(highScoresButton);
        buttonPanel.add(exitButton);


        add(topPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        setResizable(false);
        setLocationRelativeTo(null);
    }


    private void startNewGame() {
        JDialog dialog = new JDialog(this, "Enter Board Size", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 300);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(this);


        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/pacman.png")));
        Image scaledIcon = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(scaledIcon));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);


        JLabel prompt = new JLabel("Enter board size (10–100):", SwingConstants.CENTER);
        prompt.setFont(new Font("Arial", Font.BOLD, 20));
        prompt.setForeground(Color.YELLOW);
        prompt.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        JTextField inputField = new JTextField();
        inputField.setHorizontalAlignment(SwingConstants.CENTER);
        inputField.setFont(new Font("Arial", Font.PLAIN, 18));


        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.addActionListener(e -> {
            String input = inputField.getText().trim();
            try {
                int size = Integer.parseInt(input);
                if (size < 10 || size > 100) throw new NumberFormatException();

                dialog.dispose();
                dispose();
                GameModel model = new GameModel(size, size);
                new GameView(model);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter a number between 10 and 100.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
            }
        });


        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        centerPanel.setBackground(new Color(20, 20, 50));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));
        centerPanel.add(prompt);
        centerPanel.add(inputField);
        centerPanel.add(startButton);

        dialog.add(iconLabel, BorderLayout.NORTH);
        dialog.add(centerPanel, BorderLayout.CENTER);
        dialog.getContentPane().setBackground(new Color(20, 20, 50));

        dialog.setVisible(true);
    }


    private void showHighScores() {
        List<String> topScores = HighScores.getTopScores(10);

        JDialog dialog = new JDialog(this, "Wyniki", true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        dialog.setResizable(false);

        JLabel title = new JLabel("Wyniki", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.YELLOW);
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        dialog.add(title, BorderLayout.NORTH);


        String[] columnNames = {"Miejsce", "Gracz", "Wynik"};
        String[][] data = new String[topScores.size()][3];

        for (int i = 0; i < topScores.size(); i++) {
            String[] parts = topScores.get(i).split(" - ");
            data[i][0] = String.valueOf(i + 1);
            data[i][1] = parts[0];
            data[i][2] = parts[1];
        }

        JTable table = new JTable(data, columnNames);
        table.setFont(new Font("Monospaced", Font.PLAIN, 18));
        table.setRowHeight(30);
        table.setEnabled(false);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        table.getTableHeader().setReorderingAllowed(false);
        table.setGridColor(new Color(50, 50, 70));
        table.setBackground(new Color(30, 30, 60));
        table.setForeground(Color.YELLOW);
        table.setSelectionBackground(new Color(60, 60, 100));
        table.setSelectionForeground(Color.YELLOW);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));


        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(new Color(30, 30, 60));
        dialog.add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Zamknij");
        closeButton.setFont(new Font("Arial", Font.BOLD, 16));
        closeButton.addActionListener(e -> dialog.dispose());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(20, 20, 40));
        bottomPanel.add(closeButton);

        dialog.add(bottomPanel, BorderLayout.SOUTH);
        dialog.getContentPane().setBackground(new Color(20, 20, 40));
        dialog.setVisible(true);
    }


}
