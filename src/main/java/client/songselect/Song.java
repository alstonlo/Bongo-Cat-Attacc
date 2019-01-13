package client.songselect;

import client.utilities.Utils;

import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;

public class Song {

    private String dirPath;
    private String name;
    private int difficulty;
    private BufferedImage album;
    private BufferedImage splash;

    Song(String dirPath) {
        this.dirPath = dirPath;
    }

    public void loadResources() {
        this.splash = Utils.loadScaledImage(dirPath + "/background.jpg");
        this.album = Utils.loadImage(dirPath + "/album.jpg");
    }

    public String getName() {
        return name;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public BufferedImage getAlbum() {
        return album;
    }

    public BufferedImage getSplash() {
        return splash;
    }

    public Clip playSong() {
        return Utils.loadAudio(dirPath + "/music.wav");
    }
}
