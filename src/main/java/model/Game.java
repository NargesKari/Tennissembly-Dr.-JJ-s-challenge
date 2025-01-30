package model;

import javafx.scene.shape.Rectangle;
import model.GameItems.Ball;
import model.GameItems.Racket;

public class Game {
    private final Ball ball;
    Racket player1Racket;
    Racket player2Racket;
    Rectangle court;

    public Game(Rectangle court) {
        this.court = court;
        ball = new Ball(
                court.xProperty().get() + (court.getWidth() / 2),
                court.yProperty().get() + (court.getHeight()),
                court.getWidth() * 0.03
        );
        player1Racket = new Racket("/IMAGES/racket1.jpg", court, +1);
        player2Racket = new Racket("/IMAGES/racket2.jpg", court, -1);
    }

    public Ball getBall() {
        return ball;
    }

    public Racket getPlayer1Racket() {
        return player1Racket;
    }

    public Racket getPlayer2Racket() {
        return player2Racket;
    }
}
