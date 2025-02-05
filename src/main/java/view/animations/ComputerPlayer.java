package view.animations;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.GameItems.Ball;
import model.GameItems.Racket;

import java.util.Random;

public class ComputerPlayer extends Transition {
    Racket racket;
    private Ball ball;

    public ComputerPlayer(Racket racket, Ball ball) {
        this.racket = racket;
        this.ball = ball;
        setCycleDuration(Duration.seconds(3));
        setCycleCount(INDEFINITE);
        setInterpolator(Interpolator.LINEAR);
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }
    Random random = new Random();
    @Override
    protected void interpolate(double frac) {
        System.out.println("+");
        double startX = racket.getX();
        double startY = racket.getY();

        double targetX = ball.getCenterX();
        double targetY = ball.getCenterY();

        double newX = startX + (targetX - startX - racket.getWidth() / 1.6) * frac * random.nextDouble();
        double newY = startY + (targetY - startY - racket.getHeight() / 3) * frac * random.nextDouble();

        newY = Math.max(0, Math.min(newY, racket.getHeight()));

        racket.setX(newX);
        racket.setY(newY);
    }
}
