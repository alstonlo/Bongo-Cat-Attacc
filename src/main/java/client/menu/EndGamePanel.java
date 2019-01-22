package client.menu;


import client.GamePanel;
import client.utilities.Pallette;
import client.utilities.Settings;
import client.utilities.ThreadPool;
import client.utilities.Utils;
import protocol.Message;
import client.Window;

import javax.swing.JButton;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * The panel which displays who won and each player's final score
 *
 * @Author Katelyn Wang
 * last updated 01/21/2019
 */
public class EndGamePanel extends GamePanel {
    private static final long SLIDE_DURATION = 500;
    private static final long VS_ANIMATION_DURATION = 500;

    private final double winnerAccuracy;
    private final double loserAccuracy;

    private final String winnerUsername;
    private final String loserUsername;


    private final PlayerRectangle leftWinnerPanel;
    private final PlayerRectangle rightLoserPanel;

    private BufferedImage foregroundSprite;

    private float opacity = 0f; //opacity of the foreground sprite (not the playerqueuerectangles because those are always drawn at 1f opacity)

    /**
     * Constructs an EndGamePanel
     *
     * @param window         the Window to which this panel belongs and is drawn upon
     * @param winnerAccuracy the accuracy of the winner
     * @param loserAccuracy  the accuracy of the loser
     * @param winnerUsername the username of the winner
     * @param loserUsername  the username of the loser
     */
    public EndGamePanel(Window window, double winnerAccuracy, double loserAccuracy, String winnerUsername, String loserUsername) {
        super(window);
        this.winnerAccuracy = winnerAccuracy;
        this.loserAccuracy = loserAccuracy;
        this.winnerUsername = winnerUsername;
        this.loserUsername = loserUsername;

        setLayout(null);


        this.leftWinnerPanel = new PlayerRectangle( //left panel is the winner panel
                0, Settings.PANEL_SIZE.width / 2, Settings.PANEL_SIZE.height,
                new Color(255, 201, 215), "resources/menu/left bongo cat.png");
        this.leftWinnerPanel.setY(-Settings.PANEL_SIZE.height);
        this.rightLoserPanel = new PlayerRectangle( //right panel is the loser panel
                Settings.PANEL_SIZE.width / 2, Settings.PANEL_SIZE.width / 2, Settings.PANEL_SIZE.height,
                new Color(198, 244, 255), "resources/menu/right bongo cat.png");
        this.rightLoserPanel.setY(Settings.PANEL_SIZE.height);

        JButton menuButton = new JButton("Main Menu");
        menuButton.setFont(Pallette.getScaledFont(Pallette.TITLE_FONT, 25));
        menuButton.setSize(Utils.scale(200), Utils.scale(70));
        menuButton.setLocation(Utils.scale(275), Utils.scale(1200));
        menuButton.addActionListener(e -> ThreadPool.execute(() -> returnToMenu()));
        menuButton.setBorder(null);
        menuButton.setBackground(new Color(255, 221, 216));
        menuButton.setForeground(Pallette.OUTLINE_COLOR);
        menuButton.setFocusPainted(false);

        add(menuButton);

        configureBackground();

        setVisible(true);

    }

    //Animatable method which runs upon starting the panel
    @Override
    public void run() {
        super.run();
        leftWinnerPanel.setUsername(winnerUsername); //sets the username of each of the panels
        rightLoserPanel.setUsername(loserUsername);
        leftWinnerPanel.configureSprites(); //configures the sprites accordingly
        rightLoserPanel.configureSprites();
        transition(); //begins the animation
    }

    /**
     * Returns to the menu panel by creating a new menupanel and switching panels
     */
    private void returnToMenu() {
        window.requestFocus();
        window.addBasePanel(new MenuPanel(window));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        leftWinnerPanel.draw(g2D);
        rightLoserPanel.draw(g2D);

        if (opacity != 0f) {
            g2D.setComposite(AlphaComposite.SrcOver.derive(opacity));
            g2D.drawImage(foregroundSprite, 0, 0, null); //drawing the foreground sprite
            g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER)); //resetting opacity
        }
    }

    /**
     * Configures all the constant elements of the endgamepanel, and draws it to a buffedimage
     */
    void configureBackground() {
        foregroundSprite = Utils.createCompatibleImage(Utils.scale(750), Utils.scale(1334));
        Graphics2D g2D = (Graphics2D) foregroundSprite.getGraphics();

        g2D.setRenderingHints(Settings.QUALITY_RENDER_SETTINGS);
        g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER)); //ensuring solid opacity

        //drawing the hats upon the cats

        g2D.setFont(Pallette.getScaledFont(Pallette.TITLE_FONT, 60));
        g2D.setColor(Pallette.OUTLINE_COLOR);
        FontMetrics fontMetrics = g2D.getFontMetrics();
        //drawing the title at the top
        g2D.drawString("Game  Over!", Utils.scale(375) - fontMetrics.stringWidth("Game  Over!") / 2, Utils.scale(200));

        g2D.setFont(Pallette.getScaledFont(Pallette.TITLE_FONT, 30));
        fontMetrics = g2D.getFontMetrics();
        //drawing the win/loser titles
        g2D.drawString("You Win!",
                Utils.scale(187) - fontMetrics.stringWidth("You Win!") / 2, Utils.scale(300));
        g2D.drawString("Better luck next time.",
                Utils.scale(562) - fontMetrics.stringWidth("Better luck next time.") / 2, Utils.scale(300));
        //drawing each player's accuracy
        g2D.drawString(String.format("Accuracy: %.2f", winnerAccuracy) + "%",
                Utils.scale(187) - fontMetrics.stringWidth(String.format("Accuracy: %.2f", winnerAccuracy) + "%") / 2, Utils.scale(880));
        g2D.drawString(String.format("Accuracy: %.2f", loserAccuracy) + "%",
                Utils.scale(562) - fontMetrics.stringWidth(String.format("Accuracy: %.2f", loserAccuracy) + "%") / 2, Utils.scale(880));

        g2D.dispose();
    }

    /**
     * Animates the two panel sliding onto the screen in different directions (left comes down, right comes up)
     * Slowly changes the opacity of the foreground after the two rectangles have slid on screen
     */
    private void transition() {
        long startTime = System.currentTimeMillis();
        double deltaTime = 0;
        while (deltaTime < SLIDE_DURATION) {
            double y = Settings.PANEL_SIZE.height * (deltaTime / SLIDE_DURATION);
            leftWinnerPanel.setY(-Settings.PANEL_SIZE.height + y);
            rightLoserPanel.setY(Settings.PANEL_SIZE.height - y);
            deltaTime = System.currentTimeMillis() - startTime;
        }
        leftWinnerPanel.setY(0);
        rightLoserPanel.setY(0);

        opacity = 0f;
        startTime = System.currentTimeMillis();
        deltaTime = 0;
        while (deltaTime < VS_ANIMATION_DURATION) {
            opacity = (float) (deltaTime / VS_ANIMATION_DURATION);
            deltaTime = System.currentTimeMillis() - startTime;
        }
        opacity = 1f;
    }
}
