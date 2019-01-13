package client.songselect;

import client.utilities.Utils;

import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;

public class Song {

    private String dirPath;

    Song(String dirPath) {
        this.dirPath = dirPath;
    }

    public String getName() {
        return "Dummy";
    }

    public int getDifficulty() {
        return -1;
    }

    public BufferedImage getAlbum() {
        return Utils.loadImage(dirPath + "/album.jpg");
    }

    public BufferedImage getSplash() {
        return Utils.loadScaledImage(dirPath + "/background.jpg");
    }

    public Clip getAudio() {
        return Utils.loadAudio(dirPath + "/music.wav");
    }
}
