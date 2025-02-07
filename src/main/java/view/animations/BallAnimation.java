package view.animations;

import controller.GameController;
import javafx.animation.Transition;
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
    private final int SPEEDY = 2;
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
    private double[] trailsXValues = new double[4];
    private double[] trailsYValues = new double[4];
    private GameController controller;
    private double speedX = 0;
    private double speedY = 0;
    private double time;
    private int movementMode = -1;
    private int a = 1;
    private int b = 1;
    private int c = 1;
    private double x;
    private double y;
    private double courtWidth;
    private double courtHeight;
    private double courtX;
    private double courtY;

    public BallAnimation(Ball ball) {
        this.ball = ball;
        x = ball.getCenterX();
        X.set(x);
        ball.centerXProperty().bind(X);
        y = ball.getCenterY();
        Y.set(y);
        ball.centerYProperty().bind(Y);
        radius = ball.getBallRadius();
        ball.setTrails(trailsX, trailsY, trailsXValues, trailsYValues);
        this.setCycleDuration(Duration.seconds(2));
        this.setCycleCount(INDEFINITE); // اجرای بی‌نهایت
    }

    @Override
    protected void interpolate(double frac) {
        trailsYValues = updateTrailValues(trailsYValues,y);
        trailsY.get(3).set(trailsYValues[3]);
        trailsY.get(2).set(trailsYValues[2]);
        trailsY.get(1).set(trailsYValues[1]);
        trailsY.get(0).set(trailsYValues[0]);

        trailsXValues = updateTrailValues(trailsXValues, x);
        trailsX.get(3).set(trailsXValues[3]);
        trailsX.get(2).set(trailsXValues[2]);
        trailsX.get(1).set(trailsXValues[1]);
        trailsX.get(0).set(trailsXValues[0]);

        y += speedY;
        Y.set(y);

        if (movementMode == -1) {
            x += speedX;
            X.set(x);
        }

        ball.setRotate(ball.getRotate() + Math.hypot(speedX, speedY) * 3);

        System.out.println("-");
        if (ball.getRadius() == courtWidth * 0.03) {
            if (ball.getBoundsInParent().intersects(racket1.getCollisionArea().getBoundsInParent()))
                racketHit(racket1);
            else if (ball.getBoundsInParent().intersects(racket2.getCollisionArea().getBoundsInParent()))
                racketHit(racket2);
        }
        if (!isBetween(x, courtX, courtX + courtWidth)) {
            speedX *= -1;
        }
        if (ball.getCenterY() < 0)
            controller.ballExited(true);
        if (ball.getCenterY() > courtY + courtHeight + 10)
            controller.ballExited(false);
        if (movementMode != -1 && speedY != 0) {
            switch (movementMode) {
                case 1 -> //Parabola
                {
                    if ((cal = calculateParabola()) > 0) {
                        radius.set(Math.max(Math.min(courtWidth * cal * 0.001,
                                courtWidth * 0.1), courtWidth * 0.03));
                    } else radius.set(courtWidth * 0.03);
                    time += speedY;
                    x += speedX;
                    X.set(x);
                }
                case 2 -> //Sin
                {
                    x += 5 * a * Math.sin(10 * b * time + c);
                    if (isBetween(x, courtX, courtX + courtWidth)) {
                        X.set(x);
                    }
                    time += (speedY * 0.001);
                }
            }
        }
    }

    private double[] updateTrailValues(double[] trailsValues, double value){
        trailsValues[3] += 0.1 * (trailsValues[2] - trailsValues[3]);
        trailsValues[2] += 0.1 * (trailsValues[1] - trailsValues[2]);
        trailsValues[1] += 0.1 * (trailsValues[0] - trailsValues[1]);
        trailsValues[0] += 0.1 * (value - trailsValues[0]);
        return trailsValues;
    }

    public static boolean isBetween(double p, double q, double r) {
        return (p > q && p < r) ;
    }


    private double calculateParabola() {
        return (time * (0.01 * a * time + b) + c);
    }

    void racketHit(Racket racket) {
        speedX = a * (ball.getCenterX() - racket.getCollisionArea().getCenterX()) / 14;
        int bPrime = movementMode == -1 ? b : 1;
        speedY = (ball.getCenterY() < racket.getCollisionArea().getCenterY()) ? -bPrime * SPEEDY : bPrime * SPEEDY;
    }



    public void setArguments(int a, int b, int c) {
        time = 0;
        this.a = a < 0 ? (int) Math.floor(a / 10.0) : (int) Math.ceil(a / 10.0);
        this.b = b;
        this.c = c;
    }

    public void setMode(int i) {
        if (i == 1) {
            time = (ball.getCenterY() - (courtY + courtHeight / 2));
            b = (int) Math.ceil(b / 3.0);
            c *= 5;
        } else if (i == 2) {
            b = (int) Math.ceil(b / 3.0);
            c = (int) Math.ceil(c / 3.0);
        } else {
            a = Math.abs(a);
            b = b < 0 ? -(int) Math.floor(b / 5.0) : (int) Math.ceil(b / 5.0);
        }
        movementMode = i;
    }

    public void setItems(Racket player1Racket, Racket player2Racket, Rectangle court, GameController gameController) {
        racket1 = player1Racket;
        racket2 = player2Racket;
        courtWidth = court.getWidth();
        courtHeight = court.getHeight();
        courtX = court.getX();
        courtY = court.getY();
        controller = gameController;
    }
}
