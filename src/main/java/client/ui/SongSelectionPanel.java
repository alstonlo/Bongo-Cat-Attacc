package client.ui;

import client.utilities.Utils;
import protocol.Protocol;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;

public class SongSelectionPanel extends GamePanel {
    private String[] songFiles = {"resources/songs/music.wav", "resources/songs/music2.wav"};
    private Clip currMusic;
    private SplashImages background = new SplashImages();
    private int currIndex;
    private int prevIndex;
    private int state = 0;

    SongSelectionPanel(Window window){
        super(window);
        playSong(songFiles[currIndex]);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        g2D.scale(window.getScale(), window.getScale()); //we set the scaling

        background.draw(g2D, this);
        if (state == 1){
            if (prevIndex > currIndex){
                background.leftMove();
            } else {
                background.rightMove();
            }
            playSong(songFiles[currIndex]);
            state = 0;
        }
    }


    @Override
    public void notifyRightPress() {
        if (state == 0) {
            if (currIndex < songFiles.length - 1) {
                state = 1;
                prevIndex = currIndex;
                currIndex++;
                background.setCurrIndex(currIndex);
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
                background.setCurrIndex(currIndex);
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
