package client.ui;

import client.utilities.Utils;
import protocol.Protocol;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Panel for the main menu.
 *
 * @author Katelyn Wang and Alston
 * last updated 1/9/2019
 */
class MenuPanel extends GamePanel {

    private BongoCat cat;
    private BufferedImage background;

    private Clip bgMusic;

    /**
     * Constructs a MenuPanel.
     *
     * @param window the Window that this MenuPanel is displayed on
     */
    MenuPanel(Window window) {
        super(window);

        this.cat = new BongoCat();
        this.background = Utils.loadImage("resources/menu/yellow.png");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        super.run();

        //playing background music
        try {
            File song = new File("resources/menu/music.wav");
            AudioInputStream stream = AudioSystem.getAudioInputStream(song);
            bgMusic = AudioSystem.getClip();
            bgMusic.open(stream);
//            bgMusic.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        super.stop();

        //stop the background music
        if (bgMusic != null) {
            bgMusic.close();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyLeftPress() {
        cat.leftPawDown();
        repaint(); //repaint panel to ensure that the state change is animated (even if it violates fps)
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyLeftRelease() {
        cat.leftPawUp();
        repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyRightPress() {
        cat.rightPawDown();
        repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyRightRelease() {
        cat.rightPawUp();
        repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyHold() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyConnected() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyReceived(Protocol protocol) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyDisconnected() {

    }

    /**
     * {@inheritDoc}
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        g2D.scale(window.getScale(), window.getScale()); //we set the scaling

        g2D.drawImage(background, 0, 0, this);
        g2D.drawImage(cat.getImage(), 0, 0, this);
    }
}

