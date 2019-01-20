package client.gameplay;

import client.GamePanel;
import client.Window;
import client.components.Clock;
import client.utilities.Pallette;
import client.utilities.Utils;
import protocol.Message;

import javax.sound.sampled.Clip;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class GamePlayPanel extends GamePanel {
    private BufferedImage background;
    private NoteManager noteManager;

    private Clock clock;

    public GamePlayPanel(Window window){
        super(window);
    }

    @Override
    public void run() {
        super.run();
        configureSprites();
        clock = new Clock(Utils.scale(100), Utils.scale(100), window.getSong().getDuration()*1000,Utils.scale(60));
        clock.configureSprites();
        clock.start();
        noteManager = new NoteManager(window.getSong());
        noteManager.run();
        window.getSong().getAudio().loop(Clip.LOOP_CONTINUOUSLY);
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(background,0,0,null);
        noteManager.draw(g2D);
        clock.draw(g2D);
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
    public void notifyReceived(Message message) {

    }

    void configureSprites(){
        background = Utils.createCompatibleImage(Utils.scale(750), Utils.scale(1334));
        Graphics2D g2D = (Graphics2D) background.getGraphics();

        g2D.setColor(new Color(138, 219, 91));
        g2D.fillRect(0,Utils.scale(634), Utils.scale(750), Utils.scale(1334));
        g2D.setColor(Pallette.OUTLINE_COLOR);
        g2D.drawLine(Utils.scale(100), Utils.scale(1150), Utils.scale(650), Utils.scale(1150));
        g2D.setStroke(new BasicStroke(5));
        g2D.drawLine(Utils.scale(300),Utils.scale(634), Utils.scale(0), Utils.scale(1334));
        g2D.drawLine(Utils.scale(450),Utils.scale(634), Utils.scale(750), Utils.scale(1334));
        g2D.dispose();
    }

}
