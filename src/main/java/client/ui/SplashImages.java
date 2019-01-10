package client.ui;

import client.utilities.Utils;
import org.lwjgl.system.CallbackI;

import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class SplashImages {
    BufferedImage[] images = {Utils.loadImage("resources/songs/background1.jpg"),
            Utils.loadImage("resources/songs/background2.jpg")};
    private int currXPos;
    private int prevIndex;
    private int currIndex;
    private Timer timer = new Timer();
    private int state = 0; // if 0 not animating, if 1 it is animating

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
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                state = 1;
                currXPos = 750;
                do {
                    currXPos -= 2;
                } while (currXPos >= 0);
                state = 0;
                timer.cancel();
            }

        }, 0, 100);
    }

    void rightMove() {
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                state = 2;
                currXPos = -750;
                do {
                    currXPos += 2;
                } while (currXPos <= 0);
                state = 0;
                timer.cancel();
            }

        }, 0, 100);
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

