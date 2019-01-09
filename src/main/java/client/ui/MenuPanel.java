package client.ui;

import client.Button;
import client.utilities.Utils;
import protocol.Protocol;
import java.awt.Color;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

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
    private Button playButton;

    private ArrayList<Button> buttons = new ArrayList<>();
    private int currSelected = 0;

    /**
     * Constructs a MenuPanel.
     *
     * @param window the Window that this MenuPanel is displayed on
     */
    MenuPanel(Window window) {
        super(window);

        this.cat = new BongoCat();
        this.background = Utils.loadImage("resources/menu/yellow.png");
        this.playButton = new Button("Play", (int) Math.round(window.getWidth()/2-window.getScale()*50),
                (int) Math.round(window.getScale()*500),
                (int) Math.round(window.getScale()*100),
                (int) Math.round(window.getScale()*50),
                new Color(101, 101, 255),
                new Color(212, 212, 212));
        buttons.add(playButton);
        buttons.get(currSelected).setSelected(true);
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
        if (currSelected > 0){
            buttons.get(currSelected).setSelected(false);
            currSelected--;
            buttons.get(currSelected).setSelected(true);
        }
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
        if (currSelected < buttons.size()-1){
            buttons.get(currSelected).setSelected(false);
            currSelected++;
            buttons.get(currSelected).setSelected(true);
        }
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
        repaint();
        //window.switchState(Window.QUEUE_STATE);
        window.switchState(Window.SONG_SELECT_STATE);
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

        playButton.draw(g2D);

        g2D.drawImage(background, 0, 0, this);
        g2D.drawImage(cat.getImage(), 0, 0, this);

      //  playButton.draw(g2D);
    }
}
