package client.ui;

import client.utilities.Utils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;
import java.io.File;

public class Song implements Comparable<Song>{

    BufferedImage splashImage;
    Clip music;

    Song(String fileName) {
        splashImage = Utils.loadImage(fileName+"/background.jpg");
        try {
            File song = new File("resources/menu/music.wav");
            AudioInputStream stream = AudioSystem.getAudioInputStream(song);
            music = AudioSystem.getClip();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    String getName() {
        return null;
    }

    double[] getNotes() {
        return null;
    }

    public int compareTo(Song o) {
        return 0;
    }
}
