package client.menu;

import client.CircleButton;
import client.GamePanel;
import client.Window;
import client.utilities.Settings;
import client.utilities.Utils;
import exceptions.GameException;
import protocol.AuthenticateProtocol;
import protocol.ExceptionProtocol;
import protocol.Protocol;
import protocol.RegisterProtocol;
import protocol.ResponseProtocol;

import javax.sound.sampled.Clip;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Panel for the main menu.
 *
 * @author Katelyn Wang and Alston
 * last updated 1/12/2019
 */
public class MenuPanel extends GamePanel {

    private BongoCat cat;
    private BufferedImage background;

    private LoginPanel loginPanel;
    private QueuePanel queuePanel;
    private SettingPanel settingPanel;

    private int currSelected = 0;
    private CircleButton[] buttons = new CircleButton[3];

    private Clip bgMusic;

    private AtomicInteger idCounter = new AtomicInteger(0);
    private ConcurrentHashMap<String, Protocol> requests = new ConcurrentHashMap<>();

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
        this.background = Utils.loadScaledImage("resources/menu/yellow.png");

        //create the drawer panels
        this.loginPanel = new LoginPanel(window, this);
        this.queuePanel = new QueuePanel(window);
        this.settingPanel = new SettingPanel(window, this);
        this.add(loginPanel);
        this.add(queuePanel);
        this.add(settingPanel);

        //create the buttons
        BufferedImage loginIcon = Utils.loadImage("resources/icons/login.png");
        CircleButton loginButton = new CircleButton(loginIcon, Utils.scale(670), Utils.scale(990), Utils.scale(50));
        loginButton.setOnSubmit(() -> loginPanel.pullDown());

        BufferedImage playIcon = Utils.loadImage("resources/icons/play.png");
        CircleButton playButton = new CircleButton(playIcon, Utils.scale(670), Utils.scale(1120), Utils.scale(50));
        playButton.setOnSubmit(() -> queuePanel.pullDown());

        BufferedImage controlsIcon = Utils.loadImage("resources/icons/controls.png");
        CircleButton controlsButton = new CircleButton(controlsIcon, Utils.scale(670), Utils.scale(1250), Utils.scale(50));
        controlsButton.setOnSubmit(() -> settingPanel.pullDown());

        buttons[0] = loginButton;
        buttons[1] = playButton;
        buttons[2] = controlsButton;
        buttons[currSelected].select();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        super.run();

        //playing background music
//        bgMusic = Utils.loadAudio("resources/menu/music.wav");
        if (bgMusic != null) {
            bgMusic.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        super.update();

        //since update() is called as a part of the EDT thread,
        //we relocate the panels inside update (for convenience instead of invoking later)
        loginPanel.relocate();
        queuePanel.relocate();
        settingPanel.relocate();
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
        currSelected = (currSelected + 2) % buttons.length;
        selectButton(currSelected);

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
        currSelected = (currSelected + 1) % buttons.length;
        selectButton(currSelected);

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
    public void notifyReceived(Protocol protocol) {
        processMessage(protocol);
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

        g2D.drawImage(background, 0, 0, this);
        g2D.drawImage(cat.getImage(), 0, 0, this);

        for (CircleButton button : buttons) {
            button.draw(g2D);
        }
        g2D.setRenderingHints(Settings.QUALITY_RENDER_SETTINGS);
        g2D.setFont(Utils.loadFont("resources/cloud.ttf", Utils.scale(80)));
        g2D.drawString("Bongo Cat", Utils.scale(50),Utils.scale(100));
        g2D.drawString("Attacc!", Utils.scale(50), Utils.scale(200));
    }

    /**
     * Sends a message to the server
     *
     * @param message the message to be sent
     */
    void sendMessage(Protocol message) {
        message.id = String.valueOf(idCounter.incrementAndGet());
        requests.put(message.id, message);
        window.sendMessage(message);
    }

    private void processMessage(Protocol message) {
        if (message instanceof ExceptionProtocol) {
            processMessage((ExceptionProtocol) message);
        } else if (message instanceof ResponseProtocol) {
            processMessage((ResponseProtocol) message);
        }
    }

    private void processMessage(ResponseProtocol message) {
        Protocol response = requests.get(message.response);

        if (response instanceof AuthenticateProtocol) {

        } else if (response instanceof RegisterProtocol) {

        }
    }

    private void processMessage(ExceptionProtocol message) {
        switch (message.errorState) {
            case GameException.DATABASE_ERROR_STATE:
                loginPanel.displayErrorMessage("Our database ran into an issue. Please try again.");
                break;

            case GameException.INVALID_REGISTER_STATE:
                loginPanel.displayErrorMessage("Username taken. Please try again.");
                break;

            case GameException.DOUBLE_LOGIN_STATE:
                loginPanel.displayErrorMessage("You are already logged in.");
                break;

            case GameException.INVALID_LOGIN_STATE:
                loginPanel.displayErrorMessage("Incorrect username or password.");
                break;

            default:
                System.out.println("Received error with state " + message.errorState);
        }
    }

    private synchronized void selectButton(int index) {
        for (int i = 0; i < buttons.length; i++) {
            if (i == index) {
                buttons[i].select();
            } else {
                buttons[i].deselect();
            }
        }
    }
}
