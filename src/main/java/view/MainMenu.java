package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.PaneDesigner;

public class MainMenu extends Application {
    private static Alert alert;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Main Menu");
        FXMLLoader fxmlLoader = new FXMLLoader();
        Pane pane = fxmlLoader.load(Main.class.getResource("/FXML/MainMenu.fxml"));
        PaneDesigner.setUpPaneSettings(pane, stage,"/IMAGES/firstPage.gif", "");
//        ImageView avatar = new ImageView(User.getLoggedInUser().getAvatar());
//        avatar.setFitHeight(150);
//        avatar.setFitWidth(150);
//        pane.getChildren().add(avatar);
//        pane.setEffect(User.getLoggedInUser().getSetting().getColour());
        stage.show();
    }

    public void startNewGame() {
        try {
            new PreGameMenu().start(Main.stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
