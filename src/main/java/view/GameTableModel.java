package view;

import model.Cell;
import model.GameModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static model.Cell.CellType.PLAYER;

public class GameTableModel extends AbstractTableModel {
    private final GameModel gameModel;


    public GameTableModel(GameModel gameModel) {
        this.gameModel = gameModel;

        try {
            BufferedImage wallImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/wall.png")));
        } catch (IOException e) {
            throw new RuntimeException("Could not load wall image", e);
        }
    }


    @Override
    public int getRowCount() {
        return gameModel.getRows();
    }

    @Override
    public int getColumnCount() {
        return gameModel.getCols();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return ImageIcon.class;
    }

    private ImageIcon loadAndScaleImage(String path, int width, int height) {
        try {
            ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(path)));
            Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        } catch (Exception e) {
            System.err.println("Could not load image: " + path);
            return null;
        }
    }


    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cell cell = gameModel.getBoard()[rowIndex][columnIndex];
        int size = 32;

        return switch (cell.getType()) {
            case WALL -> loadAndScaleImage("/images/wall.png", size, size);
            case PLAYER -> loadAndScaleImage("/images/pacman.png", size, size);
            case ENEMY -> loadAndScaleImage("/images/ghost.png", size, size);
            case POINT -> loadAndScaleImage("/images/strawberry.png", size, size);
            default -> null;
        };
    }

}

