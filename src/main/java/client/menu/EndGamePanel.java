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

public class EndGamePanel extends GamePanel {
    private final double winnerAccuracy;
    private final double loserAccuracy;
    private final String winner;
    private final String loser;

    private final long SLIDE_DURATION = 500;
    private final long VS_ANIMATION_DURATION = 500;

    private final PlayerQueueRectangle leftPanel;
    private final PlayerQueueRectangle rightPanel;

    private BufferedImage foregroundSprite;

    private float opacity = 0f;

    public EndGamePanel(Window window, double winnerAccuracy, double loserAccuracy, String winner, String loser){
        super(window);
        this.winnerAccuracy = winnerAccuracy;
        this.loserAccuracy = loserAccuracy;
        this.winner = winner;
        this.loser = loser;

        setLayout(null);

        this.leftPanel = new PlayerQueueRectangle(
                0, Settings.PANEL_SIZE.width / 2, Settings.PANEL_SIZE.height,
                new Color(255,201,215), "resources/menu/left bongo cat.png");
        this.leftPanel.setY(-Settings.PANEL_SIZE.height);
        this.rightPanel = new PlayerQueueRectangle(
                Settings.PANEL_SIZE.width / 2, Settings.PANEL_SIZE.width / 2, Settings.PANEL_SIZE.height,
                new Color(198, 244, 255), "resources/menu/right bongo cat.png");
        this.rightPanel.setY(Settings.PANEL_SIZE.height);

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

    @Override
    public void run() {
        super.run();
        leftPanel.setUsername(winner);
        rightPanel.setUsername(loser);
        leftPanel.configureSprites();
        rightPanel.configureSprites();
        transition();
    }

    private void returnToMenu(){
        window.requestFocus();
        window.displayBasePanel(new MenuPanel(window));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D)g;
        leftPanel.draw(g2D);
        rightPanel.draw(g2D);

        if (opacity != 0f) {
            g2D.setComposite(AlphaComposite.SrcOver.derive(opacity)); //drawing the "vs."
            g2D.setRenderingHints(Settings.QUALITY_RENDER_SETTINGS);
            g2D.drawImage(foregroundSprite,0,0,null);
            g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER)); //resetting opacity
        }
    }

    void configureBackground(){
        foregroundSprite = Utils.createCompatibleImage(Utils.scale(750),Utils.scale(1334));
        Graphics2D g2D = (Graphics2D) foregroundSprite.getGraphics();
        g2D.setRenderingHints(Settings.QUALITY_RENDER_SETTINGS);
        g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

        g2D.setFont(Pallette.getScaledFont(Pallette.TITLE_FONT,60));
        g2D.setColor(Pallette.OUTLINE_COLOR);
        FontMetrics fontMetrics = g2D.getFontMetrics();
        g2D.drawString("Game  Over!", Utils.scale(375)-fontMetrics.stringWidth("Game  Over!")/2, Utils.scale(200));

        g2D.setFont(Pallette.getScaledFont(Pallette.TITLE_FONT,30));
        fontMetrics = g2D.getFontMetrics();

        g2D.drawString("You Win!",
                Utils.scale( 187)-fontMetrics.stringWidth("You Win!")/2, Utils.scale(300));
        g2D.drawString("Better luck next time.",
                Utils.scale( 562)-fontMetrics.stringWidth("Better luck next time.")/2, Utils.scale(300));

        g2D.drawString(String.format("Accuracy: %.2f", winnerAccuracy)+"%",
                Utils.scale( 187)-fontMetrics.stringWidth(String.format("Accuracy: %.2f", winnerAccuracy)+"%")/2, Utils.scale(880));
        g2D.drawString(String.format("Accuracy: %.2f", loserAccuracy)+"%",
                Utils.scale( 562)-fontMetrics.stringWidth(String.format("Accuracy: %.2f", loserAccuracy)+"%")/2, Utils.scale(880));

        g2D.dispose();
    }

    private void transition() {
        long startTime = System.currentTimeMillis();
        double deltaTime = 0;
        while (deltaTime < SLIDE_DURATION) {
            double y = Settings.PANEL_SIZE.height * (deltaTime / SLIDE_DURATION);
            leftPanel.setY(-Settings.PANEL_SIZE.height + y);
            rightPanel.setY(Settings.PANEL_SIZE.height - y);
            deltaTime = System.currentTimeMillis() - startTime;
        }
        leftPanel.setY(0);
        rightPanel.setY(0);

        opacity = 0f;
        startTime = System.currentTimeMillis();
        deltaTime = 0;
        while (deltaTime < VS_ANIMATION_DURATION) {
            opacity = (float) (deltaTime / VS_ANIMATION_DURATION);
            deltaTime = System.currentTimeMillis() - startTime;
        }
        opacity = 1f;
    }

    @Override
    public void notifyReceived(Message message) {

    }

    @Override
    public void notifyLeftPress() {

    }

    @Override
    public void notifyLeftRelease() {

    }

    @Override
    public void notifyRightPress() {

    }

    @Override
    public void notifyRightRelease() {

    }

    @Override
    public void notifyHold() {

    }
}
