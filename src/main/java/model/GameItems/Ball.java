package model.GameItems;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import view.animations.BallAnimation;

import java.util.ArrayList;
import java.util.List;

public class Ball extends Circle {
    private final Ellipse shadow;
    private final List<Circle> trails;
    private final DoubleProperty ballRadius = new SimpleDoubleProperty();
    private final BallAnimation animation;

    public Ball(double x, double y, double radius) {
        this.setCenterX(x);
        this.setCenterY(y);
        ballRadius.set(radius);

        this.setFill(new ImagePattern(new Image(getClass().getResource("/IMAGES/ball.png").toExternalForm())));
        this.setStroke(Color.WHITE);
        this.setStrokeWidth(0.5);
        this.radiusProperty().bind(ballRadius);

        // ایجاد سایه
        shadow = new Ellipse();
        shadow.setFill(Color.rgb(0, 0, 0, 0.3));

        // بایندینگ سایز و موقعیت سایه به توپ
        shadow.centerXProperty().bind(this.centerXProperty());
        shadow.centerYProperty().bind(this.centerYProperty().add(50));
        shadow.radiusXProperty().bind(ballRadius.multiply(1.2));
        shadow.radiusYProperty().bind(ballRadius.multiply(0.6));
        shadow.opacityProperty().bind(
                Bindings.createDoubleBinding(
                        () -> Math.max(0.1, 1 - ballRadius.get() * 0.01),
                        ballRadius
                )
        );

        // ایجاد دنباله‌ها
        trails = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Circle trail = new Circle();
            trail.radiusProperty().bind(ballRadius.divide(i + 0.5));
            trail.setFill(Color.WHITE);
            trails.add(trail);
            trail.setOpacity(1.0 - i * 0.2);
        }

        animation = new BallAnimation(this);
    }

    public DoubleProperty getBallRadius() {
        return ballRadius;
    }

    public BallAnimation getAnimation() {
        return animation;
    }

    public void setTrails(ArrayList<DoubleProperty> trailsX, ArrayList<DoubleProperty> trailsY, double[] trailsXValues, double[] trailsYValues) {
        for (int i = 0; i < 4; i++) {
            trailsXValues[i] = trails.get(i).getCenterX();
            trailsYValues[i] = trails.get(i).getCenterY();
            trails.get(i).centerXProperty().bind(trailsX.get(i));
            trails.get(i).centerYProperty().bind(trailsY.get(i));
        }
    }

    public void addChildren(Pane page) {
        page.getChildren().add(shadow);
        for (int i = 0; i < 4; i++) {
            page.getChildren().add(trails.get(i));
        }
    }

    public Group getGroup() {
        return new Group(shadow, trails.get(0), trails.get(1), trails.get(2), trails.get(3), this);
    }
}
