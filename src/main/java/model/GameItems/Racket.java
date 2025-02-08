package model.GameItems;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import static model.MyNativeLibrary.*;

public class Racket extends Rectangle {
    private Ellipse collisionArea;
    private Group group;

    public Racket(String imagePath, Rectangle court, int mul) {
        this.setX(court.getX() + court.getWidth() / 2);
        this.setY(court.getY() + (0.35 * (1 - mul) * (court.getHeight())));

        this.setFill(new ImagePattern(new Image(getClass().getResource(imagePath).toExternalForm())));
        this.heightProperty().bind(court.heightProperty().multiply(0.2));
        this.widthProperty().bind(this.heightProperty());
        this.setOnMouseDragged(event -> movePaddle(event, court, mul));

        collisionArea = new Ellipse(this.getWidth() / 4.3, this.getWidth() / 15);
        collisionArea.centerXProperty().bind(this.xProperty().add(this.widthProperty().divide(1.6)));
        collisionArea.centerYProperty().bind(this.yProperty().add(this.heightProperty().divide(3)));
        collisionArea.setFill(Color.TRANSPARENT);
        collisionArea.setOnMouseDragged(event -> movePaddle(event, court, mul));

        Rotate rotate = new Rotate();
        rotate.angleProperty().set(-48);
        rotate.pivotXProperty().bind(collisionArea.centerXProperty());
        rotate.pivotYProperty().bind(collisionArea.centerYProperty());
        collisionArea.getTransforms().add(rotate);
        group = new Group(this, collisionArea);
    }

    public Group getGroup() {
        return group;
    }

    private void movePaddle(MouseEvent event, Rectangle court, int mul) {
        double a = court.getHeight() - 1.5 * this.getHeight();
        double newX = makeInBound(event.getX() - this.getWidth() / 2, court.getX() - (this.getWidth() / 2),
                court.getX() + court.getWidth() - (this.getWidth() / 2));
        this.setX(newX);
        double newY = makeInBound(event.getY() - this.getHeight() / 2 - 0.5 * (1 - mul) * a,-0.5 * this.getHeight(),
                this.getHeight() * 1.5);
        this.setY(newY + 0.5 * (1 - mul) * a);
    }

    public Ellipse getCollisionArea() {
        return collisionArea;
    }

    public void setCollisionArea(Ellipse collisionArea) {
        this.collisionArea = collisionArea;
    }
}
