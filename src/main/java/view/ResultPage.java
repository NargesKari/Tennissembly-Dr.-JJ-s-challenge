package view;

import controller.PregameMenuController;
import controller.ResultPageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.PaneDesigner;

public class ResultPage extends Application {
    private final int topScore;
    private final int bottomScore;
    private final int gameMode;
    private final String time;

    public int getTopScore() {
        return topScore;
    }

    public int getBottomScore() {
        return bottomScore;
    }

    public ResultPage(int topScore, int bottomScore, int gameMode, String time) {
        this.topScore = topScore;
        this.bottomScore = bottomScore;
        this.gameMode = gameMode;
        this.time = time;
    }

    public static void main(String[] args) {
        launch(args);
    }
private Pane pane;

    public Pane getPane() {
        return pane;
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage =stage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/Result.fxml"));
        pane = fxmlLoader.load();
        PaneDesigner.setUpPaneSettings(pane, stage, "/IMAGES/oh.gif");
        ((ResultPageController)fxmlLoader.getController()).setViewPage(this);
        stage.show();
    }

    Stage stage;
    public void back() {
        try {
            new PreGameMenu().start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getGameMode() {
        return gameMode;
    }

    public String getTime() {
        return time;
    }
}
