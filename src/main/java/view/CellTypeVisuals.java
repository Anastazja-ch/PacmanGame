package view;

import javax.swing.*;
import java.net.URL;
import java.util.HashMap;

public class CellTypeVisuals {
    private static final HashMap<String, ImageIcon> iconMap = new HashMap<>();

    static {
        loadIcon("PLAYER", "images/pacman.png");
        loadIcon("ENEMY", "images/ghost.png");
        loadIcon("POINT", "images/dot.png");
        loadIcon("WALL", "images/wall.png");
        loadIcon("POWER_UP", "/images/strawberry.png");
    }

    private static void loadIcon(String key, String path) {
        URL resource = CellTypeVisuals.class.getResource(path);
        if (resource != null) {
            iconMap.put(key, new ImageIcon(resource));
        } else {
            System.out.println("brak obrazka" + path);
            iconMap.put(key, new ImageIcon());
        }
    }

    public static ImageIcon getImageIcon(String key) {
        return iconMap.getOrDefault(key, new ImageIcon());

    }


}

