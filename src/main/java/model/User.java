package model;

import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private static ArrayList<User> allUsers = new ArrayList<>();
    private static User loggedInUser;
    private String username;
    private String password;
    private boolean guest;
    private Game lastGame = null;
    private Image avatar;
    private Setting setting;
    private int totalKill = 0;
    private int lastGameWave = 0;
    private int difficultyScore = 0;
    private int allShots = 1;
    private int successfulShots = 0;
    private int accuracy = 0;


    public User(String username, String password, boolean isGuest) {
        this.username = username;
        this.password = password;
        this.guest = isGuest;
        this.setting = new Setting();
        allUsers.add(this);
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser) {
        User.loggedInUser = loggedInUser;
    }

    public static User getUserWithName(String username) {
        for (User user : allUsers) {
            if (user.username.equals(username))
                return user;
        }
        return null;
    }

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public static void setAllUsers(ArrayList<User> allUsers) {
        User.allUsers = allUsers;
    }

    public static void removeFromAllUsers(User user) {
        allUsers.remove(user);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isGuest() {
        return guest;
    }

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    public Game getLastGame() {
        return lastGame;
    }

    public void setLastGame(Game lastGame) {
        this.lastGame = lastGame;
    }

    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    public int getLastGameWave() {
        return lastGameWave;
    }

    public void setLastGameWave(int lastGameWave) {
        this.lastGameWave = lastGameWave;
    }

    public int getTotalKill() {
        return totalKill;
    }

    public void setTotalKill(int totalKill) {
        this.totalKill = totalKill;
    }

    public int getDifficultyScore() {
        return difficultyScore;
    }

    public void setDifficultyScore(int difficultyScore) {
        this.difficultyScore = difficultyScore;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public int getAllShots() {
        return allShots;
    }

    public void setAllShots(int allShots) {
        this.allShots = allShots;
    }

    public int getSuccessfulShots() {
        return successfulShots;
    }

    public void setSuccessfulShots(int successfulShots) {
        this.successfulShots = successfulShots;
    }
}
