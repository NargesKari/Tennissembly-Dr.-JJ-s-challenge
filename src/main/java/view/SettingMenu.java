package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Result;
import model.User;

public class SettingMenu extends Application {
    private static Pane pane = new Pane();

    public static void main(String[] args) {
        launch(args);
    }


    public static void back() {
        try {
            new MainMenu().start(Main.stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateColor() {
        pane.setEffect(User.getLoggedInUser().getSetting().getColour());
    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Setting");
        FXMLLoader fxmlLoader = new FXMLLoader();
        pane = fxmlLoader.load(Main.class.getResource("/FXML/SettingMenu.fxml"));
        pane.setBackground(new Background(createBackgroundImage()));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    private BackgroundImage createBackgroundImage() {
        Image image = new Image(Main.class.getResource("/IMAGES/MainMenu.gif").toExternalForm(),
                800, 500, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        return backgroundImage;
    }

    public static void showAlert(Result result) {
        Alert alert;
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(result.getMessage());
            alert.setContentText("Don't forget it in Game!");
            alert.show();
            return;
    }
}
