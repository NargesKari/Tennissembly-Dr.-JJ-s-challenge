package view;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Game;
import model.User;

public class PauseMenu extends Application {
    public static Pane pane = new Pane();
    private Game game;

    public static void main(String[] args) {
        launch(args);
    }

    public static void backToGame() {
        try {
            new GameLauncher().start(Main.stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showAlert(String massage) {
        Alert alert;
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("KhiKhi");
        alert.setHeaderText(massage);
        alert.show();
    }

    public static void backToMainMenu() {
        try {
            new MainMenu().start(Main.stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        pane = FXMLLoader.load(PauseMenu.class.getResource("/FXML/PauseMenu.fxml"));
        stage.setTitle("Pause");
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
