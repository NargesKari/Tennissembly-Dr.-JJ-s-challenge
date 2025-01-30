package controller;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.Game;
import model.GameItems.Ball;
import view.GameLauncher;

public class GameController {

    public Label kill;
    public Rectangle court;
    public Label accuracy;
    public VBox vbox;
    public ChoiceBox choiceBox;
    public TextField a;
    public TextField b;
    public TextField c;
    Ball ball;
    private Game game;
    private GameLauncher viewPage;

    public void pause(MouseEvent mouseEvent) {
    }


    public void setViewPage(GameLauncher viewPage) {
        this.viewPage = viewPage;// Bind ارتفاع زمین به ارتفاع پنل
        vbox.translateYProperty().bind(viewPage.getPane().heightProperty().multiply(0.5).subtract(85));
        vbox.translateXProperty().bind(viewPage.getPane().widthProperty().multiply(0.15));
        court.heightProperty().bind(viewPage.getPane().heightProperty().multiply(0.9));
        court.widthProperty().bind(court.heightProperty().multiply(0.7));
        court.xProperty().bind(viewPage.getPane().widthProperty().subtract(court.widthProperty()).divide(2));
        court.yProperty().bind(viewPage.getPane().heightProperty().subtract(court.heightProperty()).divide(2));
        game = new Game(court);
        ball = game.getBall();
        ball.getAnimation().setRackets(game.getPlayer1Racket(), game.getPlayer2Racket());
        ball.getAnimation().setCourt(court);
        ball.getAnimation().play();
        ball.addChildren(viewPage.getPane());
        viewPage.getPane().getChildren()
                .addAll(game.getPlayer1Racket().getGroup(), ball, game.getPlayer2Racket().getGroup());

    }

    public void initialize() {
        court.setFill(Color.GREEN); // رنگ زمین
        court.setStroke(Color.WHITE); // خطوط حاشیه
        court.setStrokeWidth(5); // ضخامت خطوط
        court.setFill(new ImagePattern(new Image(getClass().getResource("/IMAGES/court.jpg").toExternalForm())));
        vbox.setSpacing(10);
        choiceBox.getItems().addAll("ax^2 + bx + c", "a sin(bx + c)", "a mySin(bx + c)");
        choiceBox.setStyle(
                "-fx-background-color: black; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-family: 'Comic Sans MS'; " +
                        "-fx-prompt-text-fill: white; "
        );


    }

    public void back(MouseEvent mouseEvent) {
    }

    public void applyMovement(MouseEvent mouseEvent) {
        switch (choiceBox.getValue().toString()) {
            case "ax^2 + bx + c":
                //TODO: invalid entry handling
                ball.getAnimation().setParabola(Integer.parseInt(a.getText()),
                        Integer.parseInt(b.getText()), Integer.parseInt(c.getText()));
            case "a sin(bx + c)":
            case "a mySin(bx + c)":
                break;
        }
    }
}