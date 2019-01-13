package client.songselect;

import client.utilities.Utils;

import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;

public class Song {

    private String dirPath;
    private String name;
    private int difficulty;
    private int start, end;

    Song(String dirPath) {
        this.dirPath = dirPath;
    }

    public String getName() {
        return name;
    }

    public int getDifficulty() {
        return difficulty;
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

    public Clip getAudioExcerpt() {
        Clip excerpt = getAudio();
        excerpt.setLoopPoints(start, end);
        return excerpt;
    }
}
