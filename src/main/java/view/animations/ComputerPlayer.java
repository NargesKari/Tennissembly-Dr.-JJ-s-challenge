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
    double x;
    double y;
    double racketSize;
    Random random = new Random();
    private Ball ball;

    public ComputerPlayer(Racket racket, Ball ball) {
        this.racket = racket;
        this.ball = ball;
        x = racket.getX();
        y = racket.getY();
        racketSize = racket.getHeight();
        setCycleDuration(Duration.seconds(2));
        setCycleCount(INDEFINITE);
        setInterpolator(Interpolator.LINEAR);
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    @Override
    protected void interpolate(double frac) {
        System.out.println("+");
        double targetX = ball.getCenterX();
        double targetY = ball.getCenterY();

        x+=  (targetX - x - racketSize / 1.6) * frac * random.nextDouble() * random.nextDouble() * random.nextDouble();
        y += (targetY - y - racketSize / 3) * frac * random.nextDouble() * random.nextDouble() * random.nextDouble();

        y = Math.max(0, Math.min(y, racket.getHeight()));

        racket.setX(x);
        racket.setY(y);
    }

    public void playFromTop() {
        y = 0;
        this.play();
    }
}
