package controller;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import model.Game;
import model.GameItems.Ball;
import model.GameItems.Racket;
import model.Music;
import view.GameLauncher;
import model.animations.ComputerPlayer;

import java.util.*;

import static model.library.MyNativeLibrary.isKeyPressed;

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
    public StackPane timerPane;
    public ImageView music;
    public CheckBox simple;
    public CheckBox sin1;
    public CheckBox sin2;
    public CheckBox sin3;
    public CheckBox parabola1;
    public CheckBox parabola2;
    public CheckBox parabola3;
    public CheckBox slow;
    public CheckBox normal;
    public CheckBox fast;
    public StackPane modePane;
    List<CheckBox> movements;
    Ball ball;
    int secondsElapsed = 0;
    Timeline timeline;
    int maxScore;
    int selectedMode;
    ComputerPlayer computerPlayer;
    Racket racket1;
    Racket racket2;
    double[] racketXBounds = new double[2];
    double[] racketYBounds = new double[4];
    private StringProperty scoreTop = new SimpleStringProperty("0");
    private StringProperty scoreBottom = new SimpleStringProperty("0");
    private Game game;
    private GameLauncher viewPage;

    public void setViewPage(GameLauncher viewPage) {
        this.viewPage = viewPage;// Bind ارتفاع زمین به ارتفاع پنل
        modePane.translateYProperty().bind(viewPage.getPane().heightProperty().multiply(0.2));
        modePane.translateXProperty().bind(viewPage.getPane().widthProperty().multiply(0.05));
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
        racketXBounds[0] = court.getX() - (game.getPlayer1Racket().getHeight() / 2);
        racketXBounds[1] = racketXBounds[0] + court.getWidth();
        racketYBounds[0] = -0.5 * game.getPlayer1Racket().getHeight();
        racketYBounds[1] = -3 * racketYBounds[0];
        racketYBounds[2] = -racketYBounds[1] + court.getHeight();
        racketYBounds[3] = court.getHeight();

        movements.forEach(box -> box.setOnAction(event -> applyMovement(box)));
        slow.setOnAction(event -> applySpeed(slow, 2));
        normal.setOnAction(event -> applySpeed(normal, 4));
        fast.setOnAction(event -> applySpeed(fast, 6));
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                moveRacket();
            }
        }.start();
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
        score1Label.textProperty().bind(scoreTop);
        score2Label.textProperty().bind(scoreBottom);
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            secondsElapsed++; // افزایش زمان سپری‌شده
            int minutes = secondsElapsed / 60;
            int seconds = secondsElapsed % 60;
            timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        music.setImage(Music.getMusicStateImage());
        movements = Arrays.asList(simple, sin1, sin2, sin3, parabola1, parabola2, parabola3);
        movements.forEach(this::applyStyles);
        applyStyles(slow);
        applyStyles(normal);
        applyStyles(fast);
        simple.setSelected(true);
        normal.setSelected(true);
    }
int speed = 4;
    public void applySpeed(CheckBox selected, int speed) {
        this.speed = speed;
        ball.getAnimation().setSPEEDY(speed);
        slow.setSelected(selected == slow);
        normal.setSelected(selected == normal);
        fast.setSelected(selected == fast);
    }

    public EventHandler<ActionEvent> applyMovement(CheckBox selected) {
        if (selected != null)
            movements.forEach(box -> box.setSelected(selected == box));
        for (int i = 0; i < 7; i++) {
            if (movements.get(i).isSelected()) {
                if (i == 0) {
                    ball.getAnimation().setMode(-1);
                } else if (i < 4) {
                    ball.getAnimation().setMode(2);
                    if (i == 1)
                        ball.getAnimation().setArguments(5, 100, 0);
                    else if (i == 2)
                        ball.getAnimation().setArguments(10, 100, 0);
                    else
                        ball.getAnimation().setArguments(20, 70, 0);
                } else if (i == 6) {
                    ball.getAnimation().setArguments(-3, 0, 150);
                    ball.getAnimation().setMode(1);
                } else {
                    ball.getAnimation().setMode(3);
                    if (i==4)
                        ball.getAnimation().setArguments(1, 0, 0);
                    else
                        ball.getAnimation().setArguments(0, 0, 0);
                }
                return null;
            }
        }
        return null;
    }

    public void information(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                (selectedMode == 1 ? "To move the top racket up, down, left, and right, use the W, S, A, and D keys, respectively.\n" : "")
                        + "To move the top racket bottom, down, left, and right, use the I, K, J, and L keys, respectively.\n"
                        + "Select a method from the options on the right.  \n" +
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
        ball.getAnimation().setSPEEDY(speed);
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

    private void applyStyles(CheckBox checkBox) {
        checkBox.setFont(Font.font("Comic Sans MS", 12)); // تنظیم فونت
        checkBox.setTextFill(Color.WHITE);
        checkBox.setEffect(new DropShadow(10, Color.web("00A87E"))); // سایه
    }

    private void moveRacket() {
        double y = racket2.getY();
        double x = racket2.getX();
        if (isKeyPressed(0x49) && y - 5 > racketYBounds[2]) { // I
            racket2.setY(y - 5);
        }
        if (isKeyPressed(0x4B) && y + 5 < racketYBounds[3]) { // K
            racket2.setY(y + 5);
        }
        if (isKeyPressed(0x4A) && x - 5 > racketXBounds[0]) { // J
            racket2.setX(x - 5);
        }
        if (isKeyPressed(0x4C) && x + 5 < racketXBounds[1]) { // L
            racket2.setX(x + 5);
        }
        if (selectedMode == 1) {
            y = racket1.getY();
            x = racket1.getX();
            if (isKeyPressed(0x57) && y - 5 > racketYBounds[0]) { // W
                racket1.setY(y - 5);
            }
            if (isKeyPressed(0x53) && y + 5 < racketYBounds[1]) { // S
                racket1.setY(y + 5);
            }
            if (isKeyPressed(0x41) && x - 5 > racketXBounds[0]) { // A
                racket1.setX(x - 5);
            }
            if (isKeyPressed(0x44) && x + 5 < racketXBounds[1]) { // D
                racket1.setX(x + 5);
            }
        }
    }


    public void changeMusicState(MouseEvent mouseEvent) {
        Music.changeMusicState();
        music.setImage(Music.getMusicStateImage());
    }
}