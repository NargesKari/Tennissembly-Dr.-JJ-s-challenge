package view;

import controller.MainMenuController;
import controller.PregameMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.PaneDesigner;
import model.User;

public class PreGameMenu extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Pre Game");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/PreGameMenu.fxml"));
        Pane pane = fxmlLoader.load();
        PaneDesigner.setUpPaneSettings(pane, stage,"/IMAGES/b2.jpg", "");
        ((PregameMenuController)fxmlLoader.getController()).setViewPage(this);
        stage.show();
    }

    public void startNewGame() {
//        MainMenuController.setGame(true);
        try {
            new GameLauncher().start(Main.stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
