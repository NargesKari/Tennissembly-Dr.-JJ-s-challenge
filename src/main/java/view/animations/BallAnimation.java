package view.animations;

import controller.GameController;
import javafx.animation.Transition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.GameItems.Ball;
import model.GameItems.Racket;

import java.util.ArrayList;
import java.util.Arrays;

public class BallAnimation extends Transition {
    private final Ball ball;
    private final int SPEEDY = 4;
    private final double factor = 0.1;
    private final DoubleProperty X = new SimpleDoubleProperty();
    private final DoubleProperty Y = new SimpleDoubleProperty();
    private final DoubleProperty radius;
    private final ArrayList<DoubleProperty> trailsX = new ArrayList<>(Arrays.asList(new SimpleDoubleProperty(),
            new SimpleDoubleProperty(), new SimpleDoubleProperty(), new SimpleDoubleProperty()));
    private final ArrayList<DoubleProperty> trailsY = new ArrayList<>(Arrays.asList(new SimpleDoubleProperty(),
            new SimpleDoubleProperty(), new SimpleDoubleProperty(), new SimpleDoubleProperty()));
    Racket racket1;
    Racket racket2;
    double cal;
    private GameController controller;
    private double speedX = 0;
    private double speedY = 0;
    private Rectangle court;
    private double time;
    private int movementMode = -1;
    private int a;
    private int b;
    private int c;

    public BallAnimation(Ball ball) {
        this.ball = ball;
        X.set(ball.getCenterX());
        ball.centerXProperty().bind(X);
        Y.set(ball.getCenterY());
        ball.centerYProperty().bind(Y);
        radius = ball.getBallRadius();
        ball.setTrails(trailsX, trailsY);
        this.setCycleDuration(Duration.seconds(2));
        this.setCycleCount(INDEFINITE); // اجرای بی‌نهایت
    }

    @Override
    protected void interpolate(double frac) {
        trailsY.get(3).set(trailsY.get(3).get() + factor * (trailsY.get(2).get() - trailsY.get(3).get()));
        trailsY.get(2).set(trailsY.get(2).get() + factor * (trailsY.get(1).get() - trailsY.get(2).get()));
        trailsY.get(1).set(trailsY.get(1).get() + factor * (trailsY.get(0).get() - trailsY.get(1).get()));
        trailsY.get(0).set(trailsY.get(0).get() + factor * (Y.get() - trailsY.get(0).get()));

        trailsX.get(3).set(trailsX.get(3).get() + factor * (trailsX.get(2).get() - trailsX.get(3).get()));
        trailsX.get(2).set(trailsX.get(2).get() + factor * (trailsX.get(1).get() - trailsX.get(2).get()));
        trailsX.get(1).set(trailsX.get(1).get() + factor * (trailsX.get(0).get() - trailsX.get(1).get()));
        trailsX.get(0).set(trailsX.get(0).get() + factor * (X.get() - trailsX.get(0).get()));

        Y.set(Y.get() + speedY);

        ball.setRotate(ball.getRotate() + Math.hypot(speedX, speedY) * 3);

//        System.out.println("-");
        if (ball.getRadius() == court.getWidth() * 0.03) {
            if (ball.getBoundsInParent().intersects(racket1.getCollisionArea().getBoundsInParent()))
                racketHit(racket1);
            else if (ball.getBoundsInParent().intersects(racket2.getCollisionArea().getBoundsInParent()))
                racketHit(racket2);
        }
        if (ball.getCenterX() < court.getX() || ball.getCenterX() > court.getX() + court.getWidth()) {
            speedX *= -1;
        }
        if (ball.getCenterY() < 0)
            controller.ballExited(true);
        if (ball.getCenterY() > court.getY() + court.getHeight() + 10)
            controller.ballExited(false);
        if (movementMode != -1 && speedY != 0) {
            switch (movementMode) {
                case 1 -> //Parabola
                {
                    if ((cal = calculateParabola()) > 0) {
                        radius.set(Math.max(Math.min(court.getWidth() * cal * 0.001,
                                court.getWidth() * 0.1), court.getWidth() * 0.03));
                    } else radius.set(court.getWidth() * 0.03);
                    time += speedY;
                    X.set(X.get() + speedX);
                }
                case 2 -> //Sin
                {
                    double x = X.get() + 5 * Math.min(a, 100) * Math.sin(10 * b * time + c);
                    if (x > court.getX() && x < court.getX() + court.getWidth()) {
                        X.set(x);
                    }
                    time += (speedY * 0.001);
                }
            }
        } else
            X.set(X.get() + speedX);
    }

    private double calculateParabola() {
        return (time * (0.01 * a * time + b) + c);
    }

    void racketHit(Racket racket) {
        speedX = (ball.getCenterX() - racket.getCollisionArea().getCenterX()) / 14;
        speedY = (ball.getCenterY() < racket.getCollisionArea().getCenterY()) ? -SPEEDY : SPEEDY;
    }


    public void setArguments(int a, int b, int c) {
        time = 0;
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public void setMode(int i) {
        if (i == 1)
            time = (ball.getCenterY() - (court.getY() + court.getHeight() / 2));
        movementMode = i;
    }

    public void setItems(Racket player1Racket, Racket player2Racket, Rectangle court, GameController gameController) {
        racket1 = player1Racket;
        racket2 = player2Racket;
        this.court = court;
        controller = gameController;
    }
}
