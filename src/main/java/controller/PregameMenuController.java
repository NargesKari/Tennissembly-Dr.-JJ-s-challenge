package controller;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import view.PreGameMenu;
public class PregameMenuController {
    public CheckBox checkBox1;
    public CheckBox checkBox2;
    public CheckBox checkBox3;
    public Spinner spinner;
    private PreGameMenu viewPage;
    private int selectedMode;

    public void setViewPage(PreGameMenu viewPage) {
        this.viewPage = viewPage;
    }

    public void startGame(MouseEvent mouseEvent) {
        viewPage.startNewGame(selectedMode, (Integer) spinner.getValue());
    }

    public void initialize() {
        checkBox1.setSelected(true);
        selectedMode = 1;
        applyStyles(checkBox1);
        applyStyles(checkBox2);
        applyStyles(checkBox3);
        checkBox1.setOnAction(event -> handleCheckBoxSelection(checkBox1, 1)); //Two-player
        checkBox2.setOnAction(event -> handleCheckBoxSelection(checkBox2, 2)); //Play with computer
        checkBox3.setOnAction(event -> handleCheckBoxSelection(checkBox3, 3)); //Practise
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 5));
        spinner.getEditor().setStyle("-fx-background-color: black; -fx-text-fill: white;");

    }

    private void applyStyles(CheckBox checkBox) {
        checkBox.setFont(Font.font("Comic Sans MS", 19)); // تنظیم فونت
        checkBox.setTextFill(Color.WHITE);
        checkBox.setEffect(new DropShadow(15, Color.web("00A87E"))); // سایه
    }

    private void handleCheckBoxSelection(CheckBox selected, int selectedMode) {
        this.selectedMode = selectedMode;
        checkBox1.setSelected(selected == checkBox1);
        checkBox2.setSelected(selected == checkBox2);
        checkBox3.setSelected(selected == checkBox3);
    }
}

