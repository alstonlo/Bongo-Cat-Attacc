package client.songselect;

import client.Utils;

import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;

public class Song {

    private String dirPath;
    private String name;
    private int difficulty;
    private SongTile tile;
    private BufferedImage splash;

    Song(String dirPath) {
        this.dirPath = dirPath;
        this.splash = Utils.loadImage(dirPath + "/background.jpg");

        BufferedImage album = Utils.loadImage(dirPath + "/album.jpg");
        this.tile = new SongTile(album, -1);
    }

    public String getName() {
        return name;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public SongTile getTile() {
        return tile;
    }

    public BufferedImage getSplash() {
        return splash;
    }

    public Clip playSong() {
        return Utils.loadAudio(dirPath + "/music.wav");
    }
}
