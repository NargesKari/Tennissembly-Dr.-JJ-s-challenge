package view;

import controller.PregameMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.PaneDesigner;
public class PreGameMenu extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage =stage;
        stage.setTitle("Pre Game");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/PreGameMenu.fxml"));
        Pane pane = fxmlLoader.load();
        PaneDesigner.setUpPaneSettings(pane, stage,"/IMAGES/pinkBG.jpg");
        ((PregameMenuController)fxmlLoader.getController()).setViewPage(this);
        stage.show();
    }

    public void startNewGame(int selectedMode, int maxScore) {
//        MainMenuController.setGame(true);
        try {
            new GameLauncher(selectedMode, maxScore).start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
