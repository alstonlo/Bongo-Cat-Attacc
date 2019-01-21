package client.gameplay;

import client.GamePanel;
import client.Window;
import client.components.Clock;
import client.components.Song;
import client.menu.EndGamePanel;
import client.utilities.Pallette;
import client.utilities.ThreadPool;
import client.utilities.Utils;
import protocol.Message;

import javax.sound.sampled.Clip;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class GamePlayPanel extends GamePanel {
    private BufferedImage background;
    private NoteManager noteManager;

    private Clock clock;
    private Song song;
    private Clip playingSong;
    private float alpha = 1f;
    private double accuracy;

    public GamePlayPanel(Window window, Song song){
        super(window);
        this.song = song;
    }

    @Override
    public void run() {
        super.run();
        configureSprites();
        window.requestFocus();
        clock = new Clock(Utils.scale(100), Utils.scale(100), song.getDuration()*1000,Utils.scale(60));
        clock.configureSprites();
        clock.start();
        noteManager = new NoteManager(song,this);
        noteManager.run();
        if (song.getAudio() != null) {
            playingSong = song.getAudio();
            playingSong.start();
        }
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setComposite(AlphaComposite.SrcOver.derive(alpha));
        g2D.drawImage(background,0,0,null);
        noteManager.draw(g2D);
        clock.draw(g2D);
    }

    protected void closeGame(double accuracy){
        this.accuracy = accuracy;
        ThreadPool.execute(this::close);
    }

    private void close(){
        long startTime = System.currentTimeMillis();
        if (playingSong!= null){
            playingSong.stop();
        }
        while (alpha > 0f){
            alpha = 1f-((System.currentTimeMillis()-startTime)/2500f);
        }
        window.switchPanel(new EndGamePanel(window,accuracy));
    }

    @Override
    public void notifyRightPress() {
        noteManager.notifyRightPress();
    }

    @Override
    public void notifyRightRelease() {

    }

    @Override
    public void notifyLeftPress() {
        noteManager.notifyLeftPress();
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
