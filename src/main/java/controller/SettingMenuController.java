package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.Result;
import model.User;
import view.Main;
import view.SettingMenu;

public class SettingMenuController {
    private static MediaPlayer mediaPlayer;
    @FXML
    private ComboBox<String> comboBox;

    public static void playMusic() {
        String path = User.getLoggedInUser() == null ? "/MUSICS/music1.mp3" :
                User.getLoggedInUser().getSetting().getMusicPath();
        Media sound = new Media(Main.class.getResource(path).toExternalForm());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    public static void stopMusic() {
        mediaPlayer.stop();
    }

    @FXML
    private void comboBoxChanged() {
        String selectedValue = comboBox.getValue();
        User.getLoggedInUser().getSetting().setDifficulty(Integer.parseInt(selectedValue));
    }

    public void changeMute(ActionEvent actionEvent) {
        if (User.getLoggedInUser().getSetting().isMute()) {
            playMusic();
            User.getLoggedInUser().getSetting().setMute(false);
        } else {
            stopMusic();
            User.getLoggedInUser().getSetting().setMute(true);
        }
    }

    public void changeColor(ActionEvent actionEvent) {
        if (User.getLoggedInUser().getSetting().getColour() == null) {
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setSaturation(-1);
            colorAdjust.setBrightness(-0.5);
            User.getLoggedInUser().getSetting().setColour(colorAdjust);
        } else {
            User.getLoggedInUser().getSetting().setColour(null);
        }
        SettingMenu.updateColor();
    }

    public void back(MouseEvent mouseEvent) {
        SettingMenu.back();
    }

    @FXML
    public void upKeyPress(KeyEvent keyEvent) {
        User.getLoggedInUser().getSetting().setUp(keyEvent.getCode());
        SettingMenu.showAlert(new Result(true, "your new Up Key is " + keyEvent.getText()
                .toString()));
    }

    public void downKeyPress(KeyEvent keyEvent) {
        User.getLoggedInUser().getSetting().setDown(keyEvent.getCode());
        SettingMenu.showAlert(new Result(true, "your new Down Key is " + keyEvent.getText()
                .toString()));
    }

    public void rightKeyPress(KeyEvent keyEvent) {
        User.getLoggedInUser().getSetting().setRight(keyEvent.getCode());
        SettingMenu.showAlert(new Result(true, "your new Right Key is " + keyEvent.getText()
                .toString()));
    }

    public void leftKeyPress(KeyEvent keyEvent) {
        User.getLoggedInUser().getSetting().setLeft(keyEvent.getCode());
        SettingMenu.showAlert(new Result(true, "your new Left Key is " + keyEvent.getText()
                .toString()));
    }
}
