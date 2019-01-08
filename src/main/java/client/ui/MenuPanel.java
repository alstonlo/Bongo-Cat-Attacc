package client.ui;

import client.utilities.Utils;

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
 * last updated 2019/1/4
 */
class MenuPanel extends GamePanel {

    private Window window;

    private BongoCat cat;
    private BufferedImage background;

    private Clip bgMusic;

    /**
     * Constructs a MenuPanel.
     *
     * @param window the Window that this MenuPanel is displayed on
     */
    MenuPanel(Window window) {
        super();
        this.window = window;
        this.cat = new BongoCat();
        this.background = Utils.loadImage("resources/menu/yellow.png");
    }


    @Override
    public void run() {
        super.run();

        //start playing the background music
        try {
            File song = new File("resources/menu/music.wav ");
            AudioInputStream stream = AudioSystem.getAudioInputStream(song);
            bgMusic = AudioSystem.getClip();
            bgMusic.open(stream);
            bgMusic.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        super.stop();

        if (bgMusic != null) {
            bgMusic.close();
        }
    }

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
        window.switchState(Window.QUEUE_STATE);
    }

    /**
     * {@inheritDoc}
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

