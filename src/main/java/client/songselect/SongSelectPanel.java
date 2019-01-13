package client.songselect;

import client.GamePanel;
import client.Window;
import protocol.Protocol;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class SongSelectPanel extends GamePanel {

    private Song[] songs;
    private int currSelected;
    private SongAnimationHandler handler;

    public SongSelectPanel(Window window){
        super(window);
        this.songs = SongBank.getSongs();
        this.currSelected = 0;
        this.handler = new SongAnimationHandler();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        handler.draw(g2D, this);
    }

    @Override
    public void run(){
        super.run();
    }

    @Override
    public void stop(){
        super.stop();
    }

    @Override
    public void notifyRightPress() {
    }

    @Override
    public void notifyRightRelease() {

    }

    @Override
    public void notifyLeftPress() {
    }

    @Override
    public void notifyLeftRelease() {
    }

    @Override
    public void notifyHold() {
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
