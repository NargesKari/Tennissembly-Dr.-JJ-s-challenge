package controller;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.Result;
import model.User;
import view.ProfileMenu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileMenuController {
    public TextField newName;
    public TextField newPassword;

    public static Matcher getMatcher(String pattern, String input) {
        return Pattern.compile(pattern).matcher(input);
    }

    public void changeUsername(MouseEvent mouseEvent) {
        if (User.getUserWithName(newName.getText()) != null &&
                !User.getUserWithName(newName.getText()).equals(User.getLoggedInUser()))
            ProfileMenu.showChangesAlert(new Result(false, "username already exists"));
        else if (!getMatcher("\\w+", newName.getText()).matches())
            ProfileMenu.showChangesAlert(new Result(false, "invalid username format"));
        else User.getLoggedInUser().setUsername(newName.getText());
    }

    public void changePassword(MouseEvent mouseEvent) {
        if (!getMatcher("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z0-9]+", newPassword.getText()).matches())
            ProfileMenu.showChangesAlert(new Result(false, "invalid password format"));
        else User.getLoggedInUser().setPassword(newPassword.getText());
    }

    public void deleteAccount() {
        User.removeFromAllUsers(User.getLoggedInUser());
        logout();
    }

    public void goToAvatarMenu(MouseEvent mouseEvent) {
//        ProfileMenu.goToAvatarMenu();
    }

    public void logout() {
        if (User.getLoggedInUser().isGuest()) {
            User.removeFromAllUsers(User.getLoggedInUser());
        }
//        ProfileMenu.logout();
    }

    public void back(MouseEvent mouseEvent) {
        ProfileMenu.back();
    }
}
