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

    private int currSelected = 0;
    private CircleButton[] buttons = new CircleButton[3];

    /**
     * Constructs a MenuPanel.
     *
     * @param window the Window that this MenuPanel is displayed on
     */
    MenuPanel(Window window) {
        super(window);

        cat = new BongoCat();
        background = Utils.loadImage("resources/menu/yellow.png");

        CircleButton loginButton = new CircleButton(null, 620, 940, 50);
        loginButton.setOnSubmit(() -> {
            window.switchState(Window.LOGIN_STATE);
        });
        buttons[0] = loginButton;

        CircleButton playButton = new CircleButton(null, 620, 1070, 50);
        playButton.setOnSubmit(() -> {
            window.switchState(Window.SONG_SELECT_STATE);
        });
        buttons[1] = playButton;

        CircleButton instructionButton = new CircleButton(null, 620, 1200, 50);
        instructionButton.setOnSubmit(() -> {
            window.switchState(Window.INSTRUCTION_STATE);
        });
        buttons[2] = instructionButton;

        buttons[currSelected].setSelected(true);
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
        if (currSelected > 0) {
            buttons[currSelected].setSelected(false);
            currSelected--;
            buttons[currSelected].setSelected(true);
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
        if (currSelected < buttons.length - 1) {
            buttons[currSelected].setSelected(false);
            currSelected++;
            buttons[currSelected].setSelected(true);
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
        buttons[currSelected].submit();
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

        for (CircleButton button : buttons) {
            button.draw(g2D);
        }
    }
}
