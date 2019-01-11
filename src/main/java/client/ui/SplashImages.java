package client.ui;

import client.utilities.Utils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicBoolean;

public class SplashImages {
    BufferedImage[] images = {Utils.loadImage("resources/songs/song1/background.jpg"),
            Utils.loadImage("resources/songs/song2/background2.jpg")};
    String[] songs = {"resources/songs/song1/music.wav", "resources/songs/song2/music2.wav"};

    private Clip currMusic;
    private int currXPos;
    private int prevIndex;
    private int currIndex;

    private int state = 0; // if 0 not animating, if 1 it is animating
    private double velocity = 1.7; //pixels per millisecond
    private AtomicBoolean animating = new AtomicBoolean(false);

    private Runnable left = new Runnable() {
        @Override
        public void run() {
            state = 2;
            long startTime = System.currentTimeMillis();
            do {
                currXPos = 750 - (int) Math.round((System.currentTimeMillis()-startTime)*velocity);
            } while (currXPos >= 0);
            currXPos = 0;
            playSong(currIndex);
            animating.set(false);

        }
    };
    private Runnable right = new Runnable() {
        @Override
        public void run() {
            state = 1;
            long startTime = System.currentTimeMillis();
            do {
                currXPos = -750 + (int) Math.round((System.currentTimeMillis()-startTime)*velocity);
            } while (currXPos <= 0);
            currXPos = 0;
            state = 0;
            playSong(currIndex);
            animating.set(false);
        }
    };

    private Runnable fadeOut = new Runnable() {
        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            do {

            } while (true);
        }
    };

    private Runnable fadeIn = new Runnable() {
        @Override
        public void run() {

        }
    };

    SplashImages() {

    }

    boolean isAnimating(){
        return animating.get();
    }

    void setCurrIndex(int index) {
        this.prevIndex = currIndex;
        this.currIndex = index;
    }

    void leftMove() {
        if (!animating.get()){
            animating.set(true);
            new Thread(right).start();
        }

    }

    void rightMove() {
        if (!animating.get()) {
            animating.set(true);
            new Thread(left).start();
        }
    }

    void draw(Graphics2D g2D, JPanel panel) {
        if (state == 1) {
            g2D.drawImage(images[currIndex], currXPos, 0, panel);
            g2D.drawImage(images[prevIndex], currXPos + 750, 0, panel);
        } else if (state == 2) {
            g2D.drawImage(images[currIndex], currXPos, 0, panel);
            g2D.drawImage(images[prevIndex], currXPos - 750, 0, panel);
        } else {
            g2D.drawImage(images[currIndex], currXPos, 0, panel);
        }

    }

    public void playSong(int index){
        if (currMusic != null) {
            currMusic.stop();
        }

        try {
            File song = new File(songs[index]);
            AudioInputStream stream = AudioSystem.getAudioInputStream(song);
            currMusic = AudioSystem.getClip();
            currMusic.open(stream);
            currMusic.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int length(){
        return images.length;
    }

    public void stop() {
        if (currMusic != null) {
            currMusic.stop();
        }
    }
}

