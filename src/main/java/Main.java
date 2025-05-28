import model.GameModel;
import view.GameView;

public class Main {
    public static void main(String[] args) {
        GameModel model = new GameModel(20, 20);
        new GameView(model);
    }
}

