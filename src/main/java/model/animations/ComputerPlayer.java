package model.animations;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.util.Duration;
import model.GameItems.Ball;
import model.GameItems.Racket;


import static model.library.MyNativeLibrary.*;

public class ComputerPlayer extends Transition {
    Racket racket;
    double x;
    double y;
    double racketSize;

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
        double targetX = ball.getCenterX() - x - racketSize / 1.6;
        double targetY = ball.getCenterY() - y - racketSize / 3;
        if (targetY == 0)
            targetY = 1;
        if (targetX == 0)
            targetX = 1;

        x += makeRandomMovement((targetX), frac);
        y += makeRandomMovement((targetY), frac);

        y = Math.max(0, Math.min(y, racket.getHeight()));

        racket.setX(x);
        racket.setY(y);
    }

    public void playFromTop() {
        y = 0;
        this.play();
    }
}
