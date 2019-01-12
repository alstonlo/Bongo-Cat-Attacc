package client.songselect;

import client.GamePanel;
import client.Window;
import protocol.Protocol;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class SongSelectPanel extends GamePanel {

    private Song[] songs;
    private SongAnimationHandler handler;
    private int currIndex;

    public SongSelectPanel(Window window){
        super(window);
        this.songs = SongBank.getSongs();
        this.handler = new SongAnimationHandler(songs);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        g2D.scale(window.scale, window.scale); //we set the scaling
        handler.draw(g2D, this);
    }


    @Override
    public void notifyRightPress() {
        if (!handler.isAnimating()) {
            if (currIndex < handler.length() - 1) {
                currIndex++;
                handler.setCurrIndex(currIndex);
                handler.rightMove();
            }
        }
    }

    @Override
    public void notifyRightRelease() {

    }

    @Override
    public void notifyLeftPress() {
        if (!handler.isAnimating()) {
            if (currIndex > 0) {
                currIndex--;
                handler.setCurrIndex(currIndex);
                handler.leftMove();
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
        handler.playSong(0);
    }

    @Override
    public void stop(){
        super.stop();
        handler.stop();
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
