package view;

import controller.GameController;
import controller.PregameMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.PaneDesigner;

public class GameLauncher extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    private Pane pane;

    public Pane getPane() {
        return pane;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Tennissembly");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/Game.fxml"));
        pane = fxmlLoader.load();
        PaneDesigner.setUpPaneSettings(pane, stage,"/IMAGES/b7.jpg", "");
        ((GameController)fxmlLoader.getController()).setViewPage(this);
        stage.show();
    }
}
