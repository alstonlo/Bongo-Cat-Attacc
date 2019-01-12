package client.songselect;

import client.GamePanel;
import client.Window;
import protocol.Protocol;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class SongSelectionPanel extends GamePanel {
    private SplashImages background = new SplashImages();
    private int currIndex;

    public SongSelectionPanel(Window window){
        super(window);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        g2D.scale(window.scale, window.scale); //we set the scaling

        background.draw(g2D, this);

    }


    @Override
    public void notifyRightPress() {
        if (!background.isAnimating()) {
            if (currIndex < background.length() - 1) {
                currIndex++;
                background.setCurrIndex(currIndex);
                background.rightMove();


            }
            //background.moveRight()
        }
    }

    @Override
    public void notifyRightRelease() {

    }

    @Override
    public void notifyLeftPress() {
        if (!background.isAnimating()) {
            if (currIndex > 0) {
                currIndex--;
                background.setCurrIndex(currIndex);
                background.leftMove();

            }

        }
    }

    @Override
    public void notifyLeftRelease() {

    }

    @Override
    public void notifyHold() {

    }

    @Override
    public void run(){
        super.run();
        background.playSong(0);
    }

    @Override
    public void stop(){
        super.stop();
        background.stop();
    }

    @Override
    public void notifyConnected() {

    }

    @Override
    public void notifyReceived(Protocol protocol) {

    }

    @Override
    public void notifyDisconnected() {

    }
}
