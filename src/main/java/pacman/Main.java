package pacman;

import view.MenuView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("✅ START MENU!");

        SwingUtilities.invokeLater(MenuView::new);

    }
}
