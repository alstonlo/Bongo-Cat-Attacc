package client.menu;

import client.GamePanel;
import client.Window;
import client.components.BongoCat;
import client.components.CircleButton;
import client.utilities.Pallette;
import client.utilities.Settings;
import client.utilities.Utils;

import javax.sound.sampled.Clip;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Panel for the main menu.
 *
 * @author Katelyn and Alston
 * last updated 1/18/2019
 */
public class MenuPanel extends GamePanel {

    private BongoCat cat;
    private BufferedImage titleSprite;
    private BufferedImage backgroundSprite;
    private BufferedImage usernameSprite;

    private int buttonIndex = 0;
    private CircleButton[] buttons = new CircleButton[3];
    private DropDownPanel[] panels = new DropDownPanel[3];

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
        this.cat.configureSprites();
        this.backgroundSprite = Utils.loadScaledImage("resources/menu/yellow.png");
        this.titleSprite = loadTitleSprite();
        this.usernameSprite = loadUsernameSprite();

        //create the buttons and the panels they trigger
        BufferedImage loginIcon = Utils.loadImage("resources/icons/login.png");
        BufferedImage playIcon = Utils.loadImage("resources/icons/play.png");
        BufferedImage controlsIcon = Utils.loadImage("resources/icons/controls.png");

        buttons[0] = new CircleButton(loginIcon, Utils.scale(670), Utils.scale(990), Utils.scale(50));
        panels[0] = new LoginPanel(window, this);
        buttons[1] = new CircleButton(playIcon, Utils.scale(670), Utils.scale(1120), Utils.scale(50));
        panels[1] = new QueuePanel(window);
        buttons[2] = new CircleButton(controlsIcon, Utils.scale(670), Utils.scale(1250), Utils.scale(50));
        panels[2] = new InstructionPanel(window);

        int counter = 0;
        for (CircleButton button : buttons) {
            final int i = counter;  //needed because lambdas need final value
            button.configureSprites();
            button.setOnSubmit(() -> panels[i].pullDown());
            counter++;
        }

        selectButton(buttonIndex);

        this.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;

        g2D.drawImage(backgroundSprite, 0, 0, this);
        g2D.drawImage(titleSprite, 0, 0, null);
        g2D.drawImage(usernameSprite, Utils.scale(30), Utils.scale(1200), null);

        cat.draw(g2D);
        for (CircleButton button : buttons) {
            button.draw(g2D);
        }
    }

    /**
     * Notify to the menu panel that the user has logged in.
     */
    void notifyLogin() {
        if (!window.getUsername().equals("")) {
            usernameSprite = loadUsernameSprite();
        }
    }

    /**
     * Selects the button at the specified index and deselects all other.
     *
     * @param index the index of the selected button
     */
    private synchronized void selectButton(int index) {
        for (int i = 0; i < buttons.length; i++) {
            if (i == index) {
                buttons[i].select();
            } else {
                buttons[i].deselect();
            }
        }
    }

    // GamePanel methods -----------------------------------------------------------------------------------

    @Override
    public void run() {
        super.run();

        //playing backgroundSprite music
        bgMusic = Utils.loadAudio("resources/menu/music.wav");
        if (bgMusic != null) {
            bgMusic.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    @Override
    public void stop() {

        //stop the backgroundSprite music
        if (bgMusic != null) {
            bgMusic.close();
        }
    }

    @Override
    public void notifyLeftPress() {
        buttonIndex = (buttonIndex + 2) % buttons.length;
        selectButton(buttonIndex);

        cat.leftPawDown();
    }

    @Override
    public void notifyLeftRelease() {
        cat.leftPawUp();
    }

    @Override
    public void notifyRightPress() {
        buttonIndex = (buttonIndex + 1) % buttons.length;
        selectButton(buttonIndex);

        cat.rightPawDown();
    }

    @Override
    public void notifyRightRelease() {
        cat.rightPawUp();
    }

    @Override
    public void notifyHold() {
        buttons[buttonIndex].submit();
    }

    // Loading sprites methods -----------------------------------------------------------------------

    /**
     * @return the sprite for the title
     */
    private BufferedImage loadTitleSprite() {
        BufferedImage sprite = Utils.createCompatibleImage(Utils.scale(500), Utils.scale(250));
        Graphics2D g2D = (Graphics2D) sprite.getGraphics();
        g2D.setRenderingHints(Settings.QUALITY_RENDER_SETTINGS);
        g2D.setColor(Pallette.OUTLINE_COLOR);
        g2D.setFont(Pallette.getScaledFont(Pallette.TITLE_FONT, 80));
        g2D.drawString("Bongo Cat", Utils.scale(50), Utils.scale(100));
        g2D.drawString("Attacc!", Utils.scale(50), Utils.scale(200));
        g2D.dispose();
        return sprite;
    }

    /**
     * @return the sprite for the username banner
     */
    private BufferedImage loadUsernameSprite() {
        BufferedImage sprite = Utils.createCompatibleImage(Utils.scale(550), Utils.scale(100));
        Graphics2D g2D = (Graphics2D) sprite.getGraphics();
        g2D.setRenderingHints(Settings.QUALITY_RENDER_SETTINGS);
        g2D.drawImage(Utils.loadScaledImage("resources/menu/cat icon.png", 550, 100), 0, 0, null);

        Font usernameFont = Pallette.getScaledFont(Pallette.TEXT_FONT, 50);
        FontMetrics metrics = g2D.getFontMetrics(usernameFont);
        g2D.setFont(usernameFont);
        g2D.setColor(Pallette.OUTLINE_COLOR);
        int y = (sprite.getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
        g2D.drawString(window.getUsername(), Utils.scale(110), y);

        g2D.dispose();
        return sprite;
    }
}
