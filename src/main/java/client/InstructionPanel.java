package client;

import protocol.Protocol;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class InstructionPanel extends GamePanel {
    private Window window;

    InstructionPanel(Window window){
        super(window);
        this.window = window;
    }
    @Override
    public void notifyLeftPress() {

    }

    @Override
    public void notifyLeftRelease() {

    }

    @Override
    public void notifyRightPress() {

    }

    @Override
    public void notifyRightRelease() {

    }

    @Override
    public void notifyHold() {

    }

    @Override
    public void notifyConnected() {

    }

    @Override
    public void notifyDisconnected() {

    }

    @Override
    public void notifyReceived(Protocol protocol) {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        g2D.scale(window.getScale(), window.getScale());

        g2D.drawString("Instructions lmao", 0, 0);
    }
}
