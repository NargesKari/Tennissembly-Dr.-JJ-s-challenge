package model;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;
import java.util.Random;

public class Music {
    private static MediaPlayer mediaPlayer;
    private static boolean isMusicON;
    private static Image musicON;
    private static Image musicOFF;
    private static ArrayList<Media> medias = new ArrayList<>();

    static {
        musicON = new Image(Music.class.getResource("/IMAGES/musicON.png").toExternalForm());
        musicOFF = new Image(Music.class.getResource("/IMAGES/musicOFF.png").toExternalForm());
        for (int i = 0; i < 4; i++) {
            medias.add(new Media(Music.class.getResource("/MUSICS/music" + i + ".mp3").toExternalForm()));
        }
    }

    public static void playMusic(int index) {
        isMusicON = true;
        if (index == -1)
            index = new Random().nextInt(3);
        mediaPlayer = new MediaPlayer(medias.get(index));
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    public static void stopMusic() {
        isMusicON = false;
        mediaPlayer.stop();
    }

    public static void changeMusicState() {
        if (isMusicON) {
            stopMusic();
            return;
        }
        playMusic(-1);
    }

    public static Image getMusicStateImage() {
        return isMusicON ? musicON : musicOFF;
    }
}
