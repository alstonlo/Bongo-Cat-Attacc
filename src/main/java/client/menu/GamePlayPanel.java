package client.menu;

import client.GamePanel;
import client.Window;
import client.utilities.Pallette;
import client.utilities.Utils;
import protocol.Protocol;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class GamePlayPanel extends GamePanel {
    private BufferedImage background;

    public GamePlayPanel(Window window){
        super(window);
    }

    @Override
    public void run() {
        super.run();
        configureSprites();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(background,0,0,null);
        g2D.drawLine(Utils.scale(361),Utils.scale(634), Utils.scale(250), Utils.scale(1334));
        g2D.drawLine(Utils.scale(389),Utils.scale(634), Utils.scale(500), Utils.scale(1334));
        g2D.drawOval(Utils.scale(350),Utils.scale(634), Utils.scale(30), Utils.scale(20));

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

    void configureSprites(){
        background = Utils.createCompatibleImage(Utils.scale(750), Utils.scale(1334));
        Graphics2D g2D = (Graphics2D) background.getGraphics();

        g2D.setColor(new Color(138, 219, 91));
        g2D.fillRect(0,Utils.scale(634), Utils.scale(750), Utils.scale(1334));
        g2D.setColor(Pallette.OUTLINE_COLOR);
        g2D.setStroke(new BasicStroke(5));
        g2D.drawLine(Utils.scale(300),Utils.scale(634), Utils.scale(0), Utils.scale(1334));
        g2D.drawLine(Utils.scale(450),Utils.scale(634), Utils.scale(750), Utils.scale(1334));
        g2D.dispose();
    }

}
