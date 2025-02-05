package model;


import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.Main;

import java.util.Objects;
import java.util.Random;

import static javafx.scene.paint.Color.WHITE;

public class PaneDesigner {
    public static void setUpPaneSettings(Pane pane, Stage stage, String backGroundPath) {
        pane.prefWidthProperty().bind(stage.widthProperty());
        pane.prefHeightProperty().bind(stage.heightProperty().subtract(20));
        getBackGround(pane, backGroundPath);
        setImageOnButtons(pane);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setMinHeight(500);
        stage.setMinWidth(800);
        stage.sizeToScene();
    }

    public static void getBackGround(Pane pane, String backGroundPath) {
        Image image = new Image(Objects.requireNonNull(PaneDesigner.class.getResource(backGroundPath)).toExternalForm());
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true,
                true, false, true);
        BackgroundFill backgroundFill = new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null);
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                backgroundSize);
        if (backGroundPath.equals("/IMAGES/oh.gif")){
            backgroundImage = new BackgroundImage(
                    image,
                    BackgroundRepeat.REPEAT,
                    BackgroundRepeat.REPEAT,
                    BackgroundPosition.CENTER,
                    null
            );
        }
        pane.setBackground(new Background(new BackgroundFill[]{backgroundFill}, new BackgroundImage[]{backgroundImage}));
    }

    public static void setImageOnButtons(Pane pane) {
        Image image = new Image(PaneDesigner.class.getResource("/IMAGES/button.png").toExternalForm());
        for (int i = 0; i < pane.getChildren().size(); i++) {
            Node node = pane.getChildren().get(i);
            if (node instanceof Button button) {
                ImageView imageView = new ImageView(image);
                Text text = new Text(button.getText());
                text.setFont(Font.font("ROG Fonts", FontWeight.BOLD, FontPosture.REGULAR, 14));
                text.setFill(WHITE);
                imageView.setFitWidth(button.getPrefWidth());
                imageView.setFitHeight(button.getPrefHeight());
                StackPane stackPane = new StackPane();
                stackPane.getChildren().addAll(imageView, text);
                stackPane.setOnMouseClicked(button.getOnMouseClicked());
                pane.getChildren().set(i, stackPane);
            } else if (node instanceof Pane) {
                setImageOnButtons((Pane) node);
            }
        }
    }
}
