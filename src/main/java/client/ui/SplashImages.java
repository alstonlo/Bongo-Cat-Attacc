package client.ui;

import client.utilities.Utils;
import org.lwjgl.system.CallbackI;

import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class SplashImages {
    BufferedImage[] images = {Utils.loadImage("resources/songs/song1/background.jpg"),
            Utils.loadImage("resources/songs/song2/background2.jpg")};
    private int currXPos;
    private int prevIndex;
    private int currIndex;
    private Timer timer = new Timer();
    private int state = 0; // if 0 not animating, if 1 it is animating
    private Runnable left = new Runnable(){
        @Override
        public void run() {
            currXPos = 750;
            do {
                currXPos -= 2;
                try{
                    Thread.sleep(100);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            } while (currXPos >= 0);
            state = 0;

        }
    };

    private Runnable right = new Runnable() {
        @Override
        public void run() {
            currXPos = -750;
            do {
                currXPos += 2;
                try{
                    Thread.sleep(100);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            } while (currXPos <= 0);
            state = 0;
        }
    };

    SplashImages() {

    }

    int getXPos() {
        return currXPos;
    }

    void setCurrIndex(int index){
        this.prevIndex = currIndex;
        this.currIndex = index;
    }
    void leftMove() {
        if (state == 0) {
            state = 1;
            left.run();
        }

    }

    void rightMove() {
        if (state == 0){
            state = 2;
            right.run();
        }
    }

    void draw(Graphics2D g2D, JPanel panel){
        if (state == 1){
            g2D.drawImage(images[currIndex], currXPos,0,panel);
            g2D.drawImage(images[prevIndex], currXPos -750, 0, panel);
        } else if (state == 2){
            g2D.drawImage(images[currIndex], currXPos,0,panel);
            g2D.drawImage(images[prevIndex], currXPos +750, 0, panel);
        } else {
            g2D.drawImage(images[currIndex], currXPos,0,panel);
        }

    }

}

