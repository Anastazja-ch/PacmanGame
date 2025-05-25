import model.GameModel;
import view.GameView;

public class Main {
    public static void main(String[] args) {

        GameModel gameModel = new GameModel(20, 20);


        new GameView(gameModel);
    }
}
