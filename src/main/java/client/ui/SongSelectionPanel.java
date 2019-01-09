package client.ui;

import client.utilities.Utils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

public class SongSelectionPanel extends GamePanel {
    private String[] songFiles = {"resources/songs/music.wav", "resources/songs/music2.wav"};
    private BufferedImage[] backgrounds = {Utils.loadImage("resources/songs/background1.jpg"),
            Utils.loadImage("resources/songs/background2.jpg")};
    private int prevIndex = 0;
    private int currIndex = 0;
    private Clip currMusic;
    private int state = 0;
    private Window window;

    SongSelectionPanel(Window window){
        this.window = window;
        playSong(songFiles[currIndex]);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        g2D.scale(window.getScale(), window.getScale()); //we set the scaling

        g2D.drawImage(backgrounds[currIndex], 0, 0, this);
        if (state == 1){
            if (prevIndex > currIndex){
                moveLeft(g2D);
            } else {
                moveRight(g2D);
            }
            playSong(songFiles[currIndex]);
            state = 0;
        }
    }

    void moveLeft(Graphics2D g2D){
        int xPos = 0;
        do {
            xPos -= 2;
            g2D.drawImage(backgrounds[prevIndex], xPos, 0, this);
            g2D.drawImage(backgrounds[currIndex], xPos + backgrounds[prevIndex].getWidth(), 0, this);
            repaint();
        } while (xPos + backgrounds[prevIndex].getWidth() >= 0);
    }

    void moveRight(Graphics2D g2D){
        int xPos = 0;
        do {
            xPos += 2;
            g2D.drawImage(backgrounds[prevIndex], xPos, 0, this);
            g2D.drawImage(backgrounds[currIndex], xPos - backgrounds[currIndex].getWidth(), 0, this);
            repaint();
        } while (xPos <= backgrounds[prevIndex].getWidth());
    }

    @Override
    public void notifyRightPress() {
        if (state == 0) {
            if (currIndex < songFiles.length - 1) {
                state = 1;
                prevIndex = currIndex;
                currIndex++;
            }
            //background.moveRight()
        }
    }

    @Override
    public void notifyRightRelease() {

    }

    @Override
    public void notifyLeftPress() {
        if (state == 0) {
            if (currIndex > 0) {
                state = 1;
                prevIndex = currIndex;
                currIndex--;
            }
            //background.moveLeft()
        }
    }

    @Override
    public void notifyLeftRelease() {

    }

    @Override
    public void notifyHold() {

    }

    public void playSong(String file){
        if (currMusic != null) {
            currMusic.stop();
        }

        try {
            File song = new File(file);
            AudioInputStream stream = AudioSystem.getAudioInputStream(song);
            currMusic = AudioSystem.getClip();
            currMusic.open(stream);
            currMusic.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop(){
        super.stop();
        if (currMusic != null){
            currMusic.stop();
        }
    }


}
