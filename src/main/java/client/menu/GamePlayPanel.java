package client.menu;

import client.GamePanel;
import client.Window;
import client.utilities.Utils;
import protocol.Protocol;

import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class GamePlayPanel extends GamePanel {
    private BufferedImage image = Utils.loadScaledImage("resources/menu/cathead.png");
    private BufferedImage transformedImage;
    public GamePlayPanel(Window window){
        super(window);
    }

    void convertImage(BufferedImage img){
        int[] imageData = image.getRGB(0,0,image.getWidth(), image.getHeight(),null, 0, image.getWidth());


    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

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
    public void notifyReceived(Protocol protocol) {

    }
}
