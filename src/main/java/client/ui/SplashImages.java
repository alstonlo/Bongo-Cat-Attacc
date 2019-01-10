package client.ui;

import client.utilities.Utils;

import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Timer;

public class SplashImages {
    BufferedImage[] images = {Utils.loadImage("resources/songs/song1/background.jpg"),
            Utils.loadImage("resources/songs/song2/background2.jpg")};

    private int currXPos;
    private int prevIndex;
    private int currIndex;
    private Timer timer = new Timer();
    private int state = 0; // if 0 not animating, if 1 it is animating
    private double velocity = 1.7; //pixels per millisecond

    private Runnable left = new Runnable() {
        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            currXPos = 750;
            do {
                currXPos = 750 - (int) Math.round((System.currentTimeMillis()-startTime)*velocity);
            } while (currXPos >= 0);
            currXPos = 0;
            state = 0;

        }
    };

    private Runnable right = new Runnable() {
        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            currXPos = -750;
            do {
                currXPos = -750 + (int) Math.round((System.currentTimeMillis()-startTime)*velocity);
            } while (currXPos <= 0);
            currXPos = 0;
            state = 0;
        }
    };

    SplashImages() {

    }
    void setCurrIndex(int index) {
        this.prevIndex = currIndex;
        this.currIndex = index;
    }

    void leftMove() {
        if (state == 0) {
            state = 1;
            new Thread(right).start();
        }

    }

    void rightMove() {
        if (state == 0) {
            state = 2;
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

}

