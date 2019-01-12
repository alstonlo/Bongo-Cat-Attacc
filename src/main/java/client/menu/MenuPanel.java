package client.menu;

import client.CircleButton;
import client.GamePanel;
import client.Utils;
import client.Window;
import protocol.Protocol;

import javax.sound.sampled.Clip;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Panel for the main menu.
 *
 * @author Katelyn Wang and Alston
 * last updated 1/9/2019
 */
public class MenuPanel extends GamePanel {

    private BongoCat cat;
    private BufferedImage background;

    private DropDownPanel loginPanel;
    private DropDownPanel instructPanel;

    private int currSelected = 0;
    private CircleButton[] buttons = new CircleButton[3];

    private Clip bgMusic;

    /**
     * Constructs a MenuPanel.
     *
     * @param window the Window that this MenuPanel is displayed on
     */
    public MenuPanel(Window window) {
        super(window);

        this.setLayout(null);

        //load the image assets
        this.cat = new BongoCat();
        this.background = Utils.loadImage("resources/menu/yellow.png");

        //create the drawer panels
        this.loginPanel = new LoginPanel(window);
        this.instructPanel = new InstructionPanel(window);
        this.add(loginPanel);
        this.add(instructPanel);

        //create the buttons
        BufferedImage loginIcon = Utils.loadImage("resources/icons/login.png");
        CircleButton loginButton = new CircleButton(loginIcon, 670, 990, 50);
        loginButton.setOnSubmit(() -> loginPanel.pullDown());

        BufferedImage playIcon = Utils.loadImage("resources/icons/play.png");
        CircleButton playButton = new CircleButton(playIcon, 670, 1120, 50);
        playButton.setOnSubmit(() -> window.switchState(Window.SONG_SELECT_STATE));

        BufferedImage controlsIcon = Utils.loadImage("resources/icons/controls.png");
        CircleButton instructionButton = new CircleButton(controlsIcon, 670, 1250, 50);
        instructionButton.setOnSubmit(() -> instructPanel.pullDown());

        buttons[0] = loginButton;
        buttons[1] = playButton;
        buttons[2] = instructionButton;
        buttons[currSelected].select();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        super.run();

        //playing background music
        //bgMusic = Utils.loadAudio("resources/menu/music.wav");
        if (bgMusic != null) {
            bgMusic.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    @Override
    public void update() {
        super.update();

        //since update() is called as a part of the EDT thread,
        //we relocate the panels inside update (for convenience instead of invoking later)
        loginPanel.relocate();
        instructPanel.relocate();
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
        buttons[currSelected].deselect();
        currSelected = (3 + currSelected - 1) % buttons.length;
        buttons[currSelected].select();

        cat.leftPawDown();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyLeftRelease() {
        cat.leftPawUp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyRightPress() {
        buttons[currSelected].deselect();
        currSelected = (currSelected + 1) % buttons.length;
        buttons[currSelected].select();

        cat.rightPawDown();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyRightRelease() {
        cat.rightPawUp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyHold() {
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
        g2D.scale(window.scale, window.scale); //we set the scaling

        g2D.drawImage(background, 0, 0, this);
        g2D.drawImage(cat.getImage(), 0, 0, this);

        for (CircleButton button : buttons) {
            button.draw(g2D);
        }
    }
}
