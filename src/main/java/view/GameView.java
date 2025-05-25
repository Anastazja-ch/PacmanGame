package view;

import model.GameModel;

import javax.swing.*;
import java.awt.*;

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


    }

    private void initUI() {

        jTable = new JTable(new GameTableModel(gameModel));

        jTable.setRowHeight(25);
        jTable.setEnabled(false);
        jTable.setTableHeader(null);

        JScrollPane scrollPane = new JScrollPane(jTable);
        add(scrollPane);
        pack();

    }


}
