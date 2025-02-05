package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.PaneDesigner;

import java.util.Objects;

public class Main extends Application {
    private Pane root;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/IntroStage.fxml"));
        root = fxmlLoader.load();
        PaneDesigner.setUpPaneSettings(root, stage, "/IMAGES/firstPage.gif");
        stage.setResizable(true);
        stage.setWidth(1300);
        stage.setHeight(700);
        stage.setMinHeight(400);
        stage.setMinWidth(600);
        stage.show();
        stage.setTitle("Tennissembly");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/IMAGES/logo.png")).toExternalForm()));
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(3), event -> {
            try {
                new PreGameMenu().start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }
}
