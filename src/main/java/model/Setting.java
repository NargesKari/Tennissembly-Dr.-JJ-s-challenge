package model;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.KeyCode;

import java.io.Serializable;

public class Setting  implements Serializable {
    private int difficulty = 1;
    private boolean mute = false;
    private ColorAdjust colour = null;
    private KeyCode up = KeyCode.W;
    private KeyCode down = KeyCode.S;
    private KeyCode right = KeyCode.D;
    private KeyCode left = KeyCode.A;
    private KeyCode shoot = KeyCode.SPACE;
    private KeyCode  nuclearRelease = KeyCode.R;
    private KeyCode clusterRelease = KeyCode.C;
    private KeyCode  freeze = KeyCode.TAB;
    private String musicPath = "/MUSICS/music1.mp3";
    public KeyCode getUp() {
        return up;
    }

    public KeyCode getDown() {
        return down;
    }

    public KeyCode getRight() {
        return right;
    }

    public KeyCode getLeft() {
        return left;
    }

    public KeyCode getShoot() {
        return shoot;
    }

    public KeyCode getNuclearRelease() {
        return nuclearRelease;
    }

    public KeyCode getClusterRelease() {
        return clusterRelease;
    }

    public KeyCode getFreeze() {
        return freeze;
    }

    public void setUp(KeyCode up) {
        this.up = up;
    }

    public void setDown(KeyCode down) {
        this.down = down;
    }

    public void setRight(KeyCode right) {
        this.right = right;
    }

    public void setLeft(KeyCode left) {
        this.left = left;
    }

    public void setShoot(KeyCode shoot) {
        this.shoot = shoot;
    }

    public void setNuclearRelease(KeyCode nuclearRelease) {
        this.nuclearRelease = nuclearRelease;
    }

    public void setClusterRelease(KeyCode clusterRelease) {
        this.clusterRelease = clusterRelease;
    }

    public void setFreeze(KeyCode freeze) {
        this.freeze = freeze;
    }

    public  String getMusicPath() {
        return this.musicPath;
    }

    public void setMusicPath(String musicPath) {
        this.musicPath = musicPath;
    }

    public void setMute(boolean mute) {
        this.mute = mute;
    }

    public boolean isMute() {
        return mute;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public ColorAdjust getColour() {
        return colour;
    }

    public void setColour(ColorAdjust colour) {
        this.colour = colour;
    }
}
