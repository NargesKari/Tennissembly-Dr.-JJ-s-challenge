package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.Game;
import model.GameItems.Ball;
import model.GameItems.Racket;
import view.GameLauncher;
import view.animations.ComputerPlayer;

import java.util.Objects;
import java.util.Optional;

public class GameController {

    public Rectangle court;
    public ChoiceBox choiceBox;
    public Spinner a;
    public Spinner b;
    public Spinner c;
    public StackPane scorePane;
    public Label score1Label;
    public Label score2Label;
    public Label timerLabel;
    public StackPane machinePane;
    public StackPane timerPane;
    Ball ball;
    int secondsElapsed = 0;
    Timeline timeline;
    int maxScore;
    int selectedMode;
    ComputerPlayer computerPlayer;
    Racket racket1;
    Racket racket2;
    private StringProperty scoreTop = new SimpleStringProperty("0");
    private StringProperty scoreBottom = new SimpleStringProperty("0");
    private Game game;
    private GameLauncher viewPage;

    public void setViewPage(GameLauncher viewPage) {
        this.viewPage = viewPage;// Bind ارتفاع زمین به ارتفاع پنل
        machinePane.translateYProperty().bind(viewPage.getPane().heightProperty().multiply(0.5).subtract(85));
        machinePane.translateXProperty().bind(viewPage.getPane().widthProperty().multiply(0.05));
        timerPane.translateYProperty().bind(viewPage.getPane().heightProperty().multiply(0.5).subtract(250));
        timerPane.translateXProperty().bind(viewPage.getPane().widthProperty().multiply(0.8));
        scorePane.translateYProperty().bind(viewPage.getPane().heightProperty().multiply(0.5).subtract(85));
        scorePane.translateXProperty().bind(viewPage.getPane().widthProperty().multiply(0.8));

        court.heightProperty().bind(viewPage.getPane().heightProperty().multiply(0.9));
        court.widthProperty().bind(court.heightProperty().multiply(0.7));
        court.xProperty().bind(viewPage.getPane().widthProperty().subtract(court.widthProperty()).divide(2));
        court.yProperty().bind(viewPage.getPane().heightProperty().subtract(court.heightProperty()).divide(2));
        game = new Game(court);
        ball = game.getBall();
        racket1 = game.getPlayer1Racket();
        racket2 = game.getPlayer2Racket();
        ball.getAnimation().setItems(racket1, racket2, court, this);
        viewPage.getPane().getChildren()
                .addAll(racket1.getGroup(), racket2.getGroup(), ball.getGroup());
        ball.getAnimation().play();
        selectedMode = viewPage.getSelectedMode();
        applySelectedMode();
        maxScore = viewPage.getMaxScore();
    }

    private void applySelectedMode() {
        switch (selectedMode) {
            case 2 -> {
                Racket racket = racket1;
                racket.setOnMouseDragged(null);
                racket.getCollisionArea().setOnMouseDragged(null);
                computerPlayer = new ComputerPlayer(racket, ball);
                computerPlayer.play();
            }
            case 3 -> {
                Racket racket = racket1;
                viewPage.getPane().getChildren().remove(racket.getGroup());
                viewPage.getPane().getChildren().add(racket.getCollisionArea());
                Ellipse collisionArea = new Ellipse(court.getWidth() / 2, 1);
                collisionArea.centerXProperty().bind(court.xProperty().add(court.widthProperty().divide(2)));
                collisionArea.centerYProperty().bind(court.yProperty());
                collisionArea.setFill(Color.TRANSPARENT);
                racket.setCollisionArea(collisionArea);
            }
        }
    }

    public void initialize() {
        court.setFill(Color.GREEN); // رنگ زمین
        court.setStroke(Color.WHITE); // خطوط حاشیه
        court.setStrokeWidth(5); // ضخامت خطوط
        court.setFill(new ImagePattern(new Image(Objects.requireNonNull(getClass().getResource("/IMAGES/court.jpg")).toExternalForm())));
        choiceBox.getItems().addAll("at^2 + bt + c", "a Sin(bt + c)", "aX + bY");
        choiceBox.setValue("Simple");
        score1Label.textProperty().bind(scoreTop);
        score2Label.textProperty().bind(scoreBottom);
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            secondsElapsed++; // افزایش زمان سپری‌شده
            int minutes = secondsElapsed / 60;
            int seconds = secondsElapsed % 60;
            timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
        }));
        a.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(-30, 30, 10));
        a.getEditor().setStyle("-fx-background-color: black; -fx-text-fill: white;");
        b.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(-30, 30, 1));
        b.getEditor().setStyle("-fx-background-color: black; -fx-text-fill: white;");
        c.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(-30, 30, 0));
        c.getEditor().setStyle("-fx-background-color: black; -fx-text-fill: white;");
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void applyMovement(MouseEvent mouseEvent) {
        ball.getAnimation().setArguments((Integer)a.getValue(),
                (Integer)b.getValue(), (Integer)c.getValue());
        switch (choiceBox.getValue().toString()) {
            case "ax^2 + bx + c" -> ball.getAnimation().setMode(1);
            case "a sin(bx + c)" -> ball.getAnimation().setMode(2);
            case "Simple" -> ball.getAnimation().setMode(-1);
        }
    }

    private int getIntInput(TextField x) {
        try {
            return Integer.parseInt(x.getText());
        } catch (Exception ignored) {
        }
        return 0;
    }

    public void information(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Select a method from the options on the right.  \n" +
                "For the first two options, you can enter three custom integer values (a, b, c). However, the third option only requires a and b.\n" +
                "We recommend using (-10, 0, 30) for the first option, (30, 30, 30) for the second, and (10, 10) for the last one.\n" +
                "To apply your selected changes, press the orange button.  \n" +
                "\n" +
                "Also, pay attention to your racket, ball, and the point of impact.", ButtonType.OK);
        alert.setTitle("HELP");
        alert.setHeaderText("let me help you :)");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle(
                "-fx-background-color: black; " +   // پس‌زمینه مشکی
                        "-fx-text-fill: white; " +          // متن سفید
                        "-fx-font-size: 14px; " +           // اندازه فونت
                        "-fx-font-family: 'Comic Sans MS';" // فونت جذاب
        );
        dialogPane.lookupButton(ButtonType.OK).setStyle(
                "-fx-background-color: black; " +  // پس‌زمینه خاکستری تیره
                        "-fx-text-fill: white; " +            // متن دکمه مشکی
                        "-fx-font-size: 14px; " +             // اندازه فونت دکمه
                        "-fx-font-weight: bold; "             // ضخیم کردن متن
        );
        dialogPane.lookup(".header-panel").setStyle("-fx-background-color: black; -fx-text-fill: white;");
        Glow glow = new Glow(0.8);
        dialogPane.setEffect(glow);
        alert.showAndWait();
    }

    public void ballExited(boolean fromTop) {
        StringProperty scoreP = fromTop ? scoreBottom : scoreTop;
        int score = Integer.parseInt(scoreP.get()) + 1;
        scoreP.set(String.valueOf(score));
        if (selectedMode == 2) {
            computerPlayer.stop();
        }
        ball.getAnimation().stop();
        if (score >= maxScore) {
            timeline.stop();
            viewPage.endGame(Integer.parseInt(scoreTop.get()), Integer.parseInt(scoreBottom.get()),
                    timerLabel.getText());
            return;
        }
        viewPage.getPane().getChildren().remove(ball.getGroup());
        ball = new Ball(
                court.xProperty().get() + (court.getWidth() / 2),
                court.yProperty().get() + (court.getHeight()) * (fromTop || selectedMode == 3 ? 0.8 : 0.1),
                court.getWidth() * 0.03
        );
        viewPage.getPane().getChildren().add(ball.getGroup());
        ball.getAnimation().setItems(racket1, racket2, court, this);
        if (selectedMode != 3) {
            Racket racket = fromTop ? racket2 : racket1;
            racket.setX(court.getX());
        }
        applyMovement(null);
        ball.getAnimation().play();
        if (selectedMode == 2) {
            computerPlayer.setBall(ball);
            computerPlayer.playFromTop();
        }
    }

    public void back(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Confirmation");
        alert.setHeaderText("Quit Game");
        alert.setContentText("Are you sure you want to exit the game and return to the pre-game menu?");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle(
                "-fx-background-color: black; " +   // پس‌زمینه مشکی
                        "-fx-text-fill: white; " +          // متن سفید
                        "-fx-font-size: 14px; " +           // اندازه فونت
                        "-fx-font-family: 'Comic Sans MS';" // فونت جذاب
        );
        dialogPane.lookupButton(ButtonType.OK).setStyle(
                "-fx-background-color: black; " +  // پس‌زمینه خاکستری تیره
                        "-fx-text-fill: white; " +            // متن دکمه مشکی
                        "-fx-font-size: 14px; " +             // اندازه فونت دکمه
                        "-fx-font-weight: bold; "             // ضخیم کردن متن
        );
        dialogPane.lookupButton(ButtonType.CANCEL).setStyle(
                "-fx-background-color: black; " +  // پس‌زمینه خاکستری تیره
                        "-fx-text-fill: white; " +            // متن دکمه مشکی
                        "-fx-font-size: 14px; " +             // اندازه فونت دکمه
                        "-fx-font-weight: bold; "             // ضخیم کردن متن
        );
        dialogPane.lookup(".header-panel").setStyle("-fx-background-color: black; -fx-text-fill: white;");
        Glow glow = new Glow(0.8);
        dialogPane.setEffect(glow);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            viewPage.back();
        }
    }

    public void moveRacket(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        if (code == KeyCode.I) {
            racket2.setY(racket2.getY() - 10);
        } else if (code == KeyCode.K) {
            racket2.setY(racket2.getY() + 10);
        } else if (code == KeyCode.J) {
            racket2.setX(racket2.getX() - 10);
        } else if (code == KeyCode.L) {
            racket2.setX(racket2.getX() + 10);
        }
        else if (selectedMode==1){
            if (code == KeyCode.W) {
                racket1.setY(racket1.getY() - 10);
            } else if (code == KeyCode.S) {
                racket1.setY(racket1.getY() + 10);
            } else if (code == KeyCode.A) {
                racket1.setX(racket1.getX() - 10);
            } else if (code == KeyCode.D) {
                racket1.setX(racket1.getX() + 10);
            }
        }
    }
}