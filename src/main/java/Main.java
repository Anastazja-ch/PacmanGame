import model.GameModel;
import view.GameView;
import view.MenuView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameModel model = new GameModel(20, 20);
            new GameView(model);
        });
    }
}

