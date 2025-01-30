package controller;


import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.Setting;
import model.User;
import view.PauseMenu;

public class PauseMenuController {
    public Label up;
    public Label down;
    public Label left;
    public Label right;

    public void back(MouseEvent mouseEvent) {
        PauseMenu.backToGame();
    }

    public void saveExit(MouseEvent mouseEvent) {
        if (User.getLoggedInUser().isGuest()) {
            PauseMenu.showAlert("Your guest babe! you cant save game!");
            return;
        }
        PauseMenu.backToMainMenu();
    }

    public void justExit(MouseEvent mouseEvent) {
        User.getLoggedInUser().setLastGame(null);
        PauseMenu.backToMainMenu();
    }

    public void stopMusic(MouseEvent mouseEvent) {
        SettingMenuController controller = new SettingMenuController();
        controller.changeMute(null);
    }

    public void music1(MouseEvent mouseEvent) {
        User.getLoggedInUser().getSetting().setMusicPath("/MUSICS/music1.mp3");
        stopMusic(mouseEvent);
        stopMusic(mouseEvent);
    }

    public void music2(MouseEvent mouseEvent) {
        User.getLoggedInUser().getSetting().setMusicPath("/MUSICS/music2.mp3");
        stopMusic(mouseEvent);
        stopMusic(mouseEvent);
    }

    public void music3(MouseEvent mouseEvent) {
        User.getLoggedInUser().getSetting().setMusicPath("/MUSICS/music3.mp3");
        stopMusic(mouseEvent);
        stopMusic(mouseEvent);
    }

    public void initialize() {
        Setting setting = User.getLoggedInUser().getSetting();
        up.setText("Up:" + setting.getUp().toString());
        down.setText("Down: " + setting.getDown().toString());
        right.setText("Right: " + setting.getRight().toString());
        left.setText("Left: " + setting.getLeft().toString());
    }
}
