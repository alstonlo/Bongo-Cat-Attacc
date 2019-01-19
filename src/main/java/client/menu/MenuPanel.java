package client.menu;

import client.components.CircleButton;
import client.GamePanel;
import client.Window;
import client.utilities.Utils;
import exceptions.GameException;
import protocol.AuthenticateProtocol;
import protocol.ExceptionProtocol;
import protocol.MatchMadeProtocol;
import protocol.Protocol;
import protocol.RegisterProtocol;
import protocol.ResponseProtocol;
import protocol.TimeOverProtocol;

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
    private BufferedImage title;

    private LoginPanel loginPanel;
    private QueuePanel queuePanel;
    private SettingPanel settingPanel;

    private int buttonIndex = 0;
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
        this.cat.configureSprites();

        this.background = Utils.loadScaledImage("resources/menu/yellow.png");

        //create the drawer panels
        this.loginPanel = new LoginPanel(window, this);
        this.queuePanel = new QueuePanel(window, this);
        this.settingPanel = new SettingPanel(window);
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

        for (CircleButton button : buttons) {
            button.configureSprites();
        }
        selectButton(buttonIndex);

        this.setVisible(true);
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
        loginPanel.relocate();
        queuePanel.relocate();
        settingPanel.relocate();

        super.update();
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
        buttonIndex = (buttonIndex + 2) % buttons.length;
        selectButton(buttonIndex);

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
        buttonIndex = (buttonIndex + 1) % buttons.length;
        selectButton(buttonIndex);

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
        buttons[buttonIndex].submit();
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
        cat.draw(g2D);
        for (CircleButton button : buttons) {
            button.draw(g2D);
        }
//
//        g2D.setFont(Utils.loadFont("resources/cloud.ttf", Utils.scale(80)));
//        g2D.drawString("Bongo Cat", Utils.scale(50),Utils.scale(100));
//        g2D.drawString("Attacc!", Utils.scale(50), Utils.scale(200));
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
        } else if (message instanceof TimeOverProtocol) {
            processMessage((MatchMadeProtocol) message);
        }
    }

    private void processMessage(MatchMadeProtocol message){
        queuePanel.matchMade(message.username1, message.username2);
    }

    private void processMessage(ResponseProtocol message) {
        Protocol response = requests.get(message.response);

        if (response instanceof AuthenticateProtocol) {
            //successfully logged in
        } else if (response instanceof RegisterProtocol) {
            //successfully registered
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

    private void loadSprites() {

    }
}
