package view;

import controller.SettingMenuController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.PaneDesigner;

public class Main extends Application {
    private Pane root;
    public static Stage stage;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Main.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/IntroStage.fxml"));
        root = fxmlLoader.load();
        PaneDesigner.setUpPaneSettings(root, stage,"/IMAGES/firstPage.gif", "");
        stage.setResizable(true);
        stage.setWidth(1300);
        stage.setHeight(700);
        stage.setMinHeight(400);
        stage.setMinWidth(600);
        stage.show();
//        SettingMenuController.playMusic();
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(2.6), event -> {
            try {
                new MainMenu().start(Main.stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }
}
