package model.animations;

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

import static model.library.MyNativeLibrary.*;

public class BallAnimation extends Transition {

    private final Ball ball;
    private final DoubleProperty X = new SimpleDoubleProperty();
    private final DoubleProperty Y = new SimpleDoubleProperty();
    private final DoubleProperty radius;
    private final ArrayList<DoubleProperty> trailsX = new ArrayList<>(Arrays.asList(new SimpleDoubleProperty(), new SimpleDoubleProperty(), new SimpleDoubleProperty(), new SimpleDoubleProperty()));
    private final ArrayList<DoubleProperty> trailsY = new ArrayList<>(Arrays.asList(new SimpleDoubleProperty(), new SimpleDoubleProperty(), new SimpleDoubleProperty(), new SimpleDoubleProperty()));
    Racket racket1;
    Racket racket2;
    double[] cal = new double[4];
    private int SPEEDY = 4;
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
    private int step;


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
        trailsYValues = updateTrailValues(trailsYValues, y);
        trailsXValues = updateTrailValues(trailsXValues, x);
        for (int i = 0; i < 4; i++) {
            trailsY.get(i).set(trailsYValues[i]);
            trailsX.get(i).set(trailsXValues[i]);
        }


        y += speedY;
        Y.set(y);

        if (movementMode == -1) {
            x += speedX;
            X.set(x);
        }

        ball.setRotate(ball.getRotate() + myHypot(speedX, speedY));
        System.out.println("-");
        if (ball.getRadius() == courtWidth * 0.03) {
            if (ball.getBoundsInParent().intersects(racket1.getCollisionArea().getBoundsInParent())) racketHit(racket1);
            else if (ball.getBoundsInParent().intersects(racket2.getCollisionArea().getBoundsInParent()))
                racketHit(racket2);
        }
        if (!isBetween(x, courtX, courtX + courtWidth)) {
            speedX *= -1;
        }
        if (ball.getCenterY() < 0) controller.ballExited(true);
        if (ball.getCenterY() > courtY + courtHeight + 10) controller.ballExited(false);
        if (movementMode != -1 && speedY != 0) {
            switch (movementMode) {
                case 1 -> //Parabola
                {
                    if (step == 0) {
                        // کد مورد نظر هر 4 بار یک بار اجرا شود
                        cal = calculateParabola(time, 0.01 * a, b, c, speedY);
                    }
                    if (cal[step] > 0) {
                        radius.set(makeInBound(cal[step], 0.03, 0.1) * courtWidth);
                    } else radius.set(courtWidth * 0.03);
                    time += speedY;
                    x += speedX;
                    X.set(x);
                    step = (step + 1) % 4;
                }
                case 2 -> //Sin
                {
                    if (isBetween(x + sin(time, a, b, c), courtX, courtX + courtWidth)) {
                        x += sin(time, a, b, c);
                        X.set(x);
                    }
                    time += (speedY * 0.001);
                }
                case 3 -> {
                    double t = Math.abs(y - courtHeight / 2 - courtY) * 0.1;
                    x = a == 1 ? Math.min((t * t), courtWidth) + courtX : courtX + Math.max(0, courtWidth - t * t);
                    X.set(x);
                }
            }
        }
    }

    void racketHit(Racket racket) {
        speedX = (ball.getCenterX() - racket.getCollisionArea().getCenterX()) / 14;
        int bPrime = movementMode == -1 ? b : 1;
        speedY = (ball.getCenterY() < racket.getCollisionArea().getCenterY()) ? -bPrime * SPEEDY : bPrime * SPEEDY;
    }

    public void setArguments(int a, int b, int c) {
        time = 0;
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public void setSPEEDY(int speedy) {
        SPEEDY = speedy;
    }

    public void setMode(int i) {
        if (i == 1) {
            step = 0;
            time = (ball.getCenterY() - (courtY + courtHeight / 2));
        } else if (i == 2) {
            speedX = 0;
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
