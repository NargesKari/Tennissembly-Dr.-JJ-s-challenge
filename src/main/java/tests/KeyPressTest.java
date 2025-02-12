package tests;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;

import static model.library.MyNativeLibrary.*;

public class KeyPressTest extends Application {
    ArrayList<Long> javaTimes = new ArrayList<>();
    ArrayList<Long> asmTimes = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Button button = new Button("Click Me");
        button.setOnMouseClicked(this::print);
        StackPane root = new StackPane();
        root.getChildren().add(button);

        Scene scene = new Scene(root, 400, 300);

        scene.setOnKeyPressed(this::javaKeyPress);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (isKeyPressed(0x41)) {
                    asmTimes.add(System.nanoTime());
                }
            }
        }.start();


        primaryStage.setScene(scene);
        primaryStage.setTitle("Key Press Speed Test");
        primaryStage.show();
    }

    private void print(MouseEvent mouseEvent) {
        long totalJava = 0;
        long totalASM = 0;
        for (int i = 1; i < javaTimes.size(); i++) {
            totalJava += javaTimes.get(i) - javaTimes.get(i - 1);
            totalASM += asmTimes.get(i) - asmTimes.get(i - 1);
        }
        System.out.println("----------Check Key Pressed----------");
        System.out.println("JAVA Avg: " + (totalJava / javaTimes.size()) + " ns");
        System.out.println("C+ASM Avg: " + (totalASM / javaTimes.size()) + " ns");
        System.out.printf("Relative improvement: %.2f\n", 1 - (double) totalASM / (double) totalJava);
    }

    private void javaKeyPress(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.A) {
            javaTimes.add(System.nanoTime());
        }
    }
}
