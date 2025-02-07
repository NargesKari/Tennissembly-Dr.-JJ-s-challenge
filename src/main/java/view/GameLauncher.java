package view;

import controller.GameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.PaneDesigner;

public class GameLauncher extends Application {
    public GameLauncher(int selectedMode, int maxScore) {
        this.selectedMode = selectedMode;
        this.maxScore = maxScore;
    }

    public static void main(String[] args) {
        launch(args);
    }
    private Pane pane;
    private final int selectedMode;
    private final int maxScore;

    public Pane getPane() {
        return pane;
    }
    Stage stage;
    @Override
    public void start(Stage stage) throws Exception {
        this.stage =stage;
        stage.setTitle("Tennissembly");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/Game.fxml"));
        pane = fxmlLoader.load();
        PaneDesigner.setUpPaneSettings(pane, stage,"/IMAGES/b7.jpg");
        ((GameController)fxmlLoader.getController()).setViewPage(this);
        stage.setResizable(false);
        stage.show();
    }

    public int getSelectedMode() {
        return selectedMode;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void endGame(int topScore, int bottomScore, String time) {
        try {
            stage.setResizable(true);
            new ResultPage(topScore,bottomScore, selectedMode, time).start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void back() {
        try {
            stage.setResizable(true);
            new PreGameMenu().start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
