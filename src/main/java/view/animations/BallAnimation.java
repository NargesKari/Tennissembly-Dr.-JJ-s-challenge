package view.animations;

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
    private final double factor = 0.05;
    private final DoubleProperty X = new SimpleDoubleProperty();
    private final DoubleProperty Y = new SimpleDoubleProperty();
    Racket racket1;
    Racket racket2;
    private DoubleProperty radius;
    private double speedX = 0;
    private double speedY = -1;
    private Rectangle court;
    private ArrayList<DoubleProperty> trailsX = new ArrayList<>(Arrays.asList(new SimpleDoubleProperty(),
            new SimpleDoubleProperty(), new SimpleDoubleProperty(), new SimpleDoubleProperty()));
    private ArrayList<DoubleProperty> trailsY = new ArrayList<>(Arrays.asList(new SimpleDoubleProperty(),
            new SimpleDoubleProperty(), new SimpleDoubleProperty(), new SimpleDoubleProperty()));
    private double time;
    private double temp;
    private boolean isParabola;
    private int a;
    private int b;
    private int c;

    public BallAnimation(Ball ball) {
        this.ball = ball;
        isParabola = false;

        X.set(ball.getCenterX());
        ball.centerXProperty().bind(X);
        Y.set(ball.getCenterY());
        ball.centerYProperty().bind(Y);
        radius = ball.getBallRadius();
        ball.setTrails(trailsX, trailsY);


        // تنظیم مدت زمان صحیح
        this.setCycleDuration(Duration.seconds(2));
        this.setCycleCount(INDEFINITE); // اجرای بی‌نهایت
    }

    public void setCourt(Rectangle court) {
        this.court = court;
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

        X.set(X.get() + speedX);
        Y.set(Y.get() + speedY);

        ball.setRotate(ball.getRotate() + Math.hypot(speedX, speedY) * 3);

        System.out.println("-");

        if (ball.getBoundsInParent().intersects(racket1.getCollisionArea().getBoundsInParent()))
            racketHit(racket1);
        else if (ball.getBoundsInParent().intersects(racket2.getCollisionArea().getBoundsInParent()))
            racketHit(racket2);

        if (ball.getCenterX() < court.getX() || ball.getCenterX() > court.getX() + court.getWidth()) {
            speedX *= -1;
        }
        if (isParabola) {
            if (calculateParabola() > 0) {
                radius.set(Math.max(Math.min(court.getWidth() * (calculateParabola()) * 0.001, court.getWidth() * 0.1), court.getWidth() * 0.03));
                System.out.print("+++> ");
            } else radius.set(court.getWidth() * 0.03);
            time += (temp * 0.001);
            System.out.println(time + " " + (calculateParabola()));
        }
    }

    private double calculateParabola() {
        return (time * (a * time + b) + c);
    }

    void racketHit(Racket racket) {
        speedX = (ball.getCenterX() - racket.getCollisionArea().getCenterX()) / 14;
        speedY = (ball.getCenterY() < racket.getCollisionArea().getCenterY()) ? -2 : 2;
        temp = speedY;
    }

    public void setRackets(Racket player1Racket, Racket player2Racket) {
        racket1 = player1Racket;
        racket2 = player2Racket;
    }

    public void setParabola(int a, int b, int c) {
        isParabola = true;
        time = 0;
        this.a = a;
        this.b = b;
        this.c = c;
    }
}
