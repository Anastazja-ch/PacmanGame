package view;

import java.io.*;
import java.util.*;

public class HighScores {

    private static final String FILE_NAME = "scores.txt";

    public static void addScore(String playerName, int score) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(playerName + "," + score);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getTopScores(int limit) {
        List<String> scores = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            List<String[]> entries = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    entries.add(parts);
                }
            }


            entries.sort((a, b) -> Integer.compare(Integer.parseInt(b[1]), Integer.parseInt(a[1])));

            for (int i = 0; i < Math.min(limit, entries.size()); i++) {
                scores.add(entries.get(i)[0] + " - " + entries.get(i)[1]);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return scores;
    }
}
