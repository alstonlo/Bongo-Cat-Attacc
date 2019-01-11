package client.ui;

import client.utilities.Utils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;
import java.io.File;

public class Song implements Comparable<Song>{

    BufferedImage splashImage;
    String fileName;
    Clip music;

    Song(String fileName) {
        splashImage = Utils.loadImage(fileName+"/background.jpg");
        this.fileName = fileName;
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

    void play(Clip currMusic){
        try {
            File song = new File(fileName);
            AudioInputStream stream = AudioSystem.getAudioInputStream(song);
            currMusic = AudioSystem.getClip();
            currMusic.open(stream);
            currMusic.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
