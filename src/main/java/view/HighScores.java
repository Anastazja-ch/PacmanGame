package view;

import model.ScoreEntry;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HighScores {
    private static final String FILE_NAME = "scores.ser";

    public static void addScore(String name, int score) {
        List<ScoreEntry> scores = loadScores();
        scores.add(new ScoreEntry(name, score));
        saveScores(scores);
    }

    public static List<String> getTopScores(int limit) {
        List<ScoreEntry> scores = loadScores();
        scores.sort(Comparator.comparingInt(ScoreEntry::getScore).reversed());

        List<String> top = new ArrayList<>();
        for (int i = 0; i < Math.min(limit, scores.size()); i++) {
            ScoreEntry s = scores.get(i);
            top.add(s.getName() + " - " + s.getScore());
        }
        return top;
    }

    private static List<ScoreEntry> loadScores() {
        List<ScoreEntry> result = new ArrayList<>();

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            Object obj = in.readObject();
            if (obj instanceof List<?> rawList) {
                for (Object item : rawList) {
                    if (item instanceof ScoreEntry score) {
                        result.add(score);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return result;
    }


    private static void saveScores(List<ScoreEntry> scores) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(scores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
