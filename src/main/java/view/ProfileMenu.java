package view;


import controller.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Result;
import model.User;

public class ProfileMenu extends Application {
    private static Alert alert;
    private Pane pane = new Pane();

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



    public static void showChangesAlert(Result result) {
        if (result.isSuccessful()) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(result.getMessage());
            alert.setContentText("Don't forget it for next login!");
            alert.show();
            return;
        }
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("SUCCESS");
        alert.setHeaderText(result.getMessage());
        alert.setContentText("Try Again!");
        alert.show();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Profile Menu");
        Main.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader();
        pane = fxmlLoader.load(Main.class.getResource("/FXML/ProfileMenu.fxml"));
        pane.setBackground(new Background(createBackgroundImage()));
        pane.setEffect(User.getLoggedInUser().getSetting().getColour());
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
}
