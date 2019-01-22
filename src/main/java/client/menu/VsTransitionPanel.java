package client.menu;

import client.GamePanel;
import client.Window;
import client.utilities.Pallette;
import client.utilities.Settings;
import client.utilities.ThreadPool;
import client.utilities.Utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * A vs. transition panel that covers the screen from the queue panel -> song select panel.
 *
 * @author Alston
 * last updated 1/22/2019
 */
public class VsTransitionPanel extends GamePanel {

    private boolean hosting;
    private String you, opponent;

    private BufferedImage leftPlayerSprite;
    private BufferedImage rightPlayerSprite;

    private double y = 0;
    private float opacity = 0f;
    private final long SLIDE_DURATION = 500;
    private final long VS_ANIMATION_DURATION = 500;

    private Font vsFont = Pallette.getScaledFont(Pallette.TITLE_FONT, 80);

    /**
     * Constructs a VsTransitionPanel.
     *
     * @param window   the window that this panel belongs to
     * @param hosting  true, if you are hosting the game
     * @param opponent the username of your opponent
     */
    public VsTransitionPanel(Window window, boolean hosting, String opponent) {
        super(window);
        this.setOpaque(false);

        this.you = window.getUsername();
        this.hosting = hosting;
        this.opponent = opponent;
        this.leftPlayerSprite = loadLeftPlayerSprite();
        this.rightPlayerSprite = loadRightPlayerSprite();
    }

    @Override
    public void run() {
        ThreadPool.execute(this::animateIn);
    }

    @Override
    public void stop() {
        animateOut();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;

        g2D.drawImage(leftPlayerSprite, 0, Utils.round(-getHeight() + y), null);
        g2D.drawImage(rightPlayerSprite, Utils.scale(375), Utils.round(getHeight() - y), null);

        if (opacity != 0f) {
            g2D.setComposite(AlphaComposite.SrcOver.derive(opacity)); //drawing the "vs."
            g2D.setRenderingHints(Settings.QUALITY_RENDER_SETTINGS);
            g2D.setFont(vsFont);
            g2D.setColor(Pallette.OUTLINE_COLOR);
            FontMetrics fontMetrics = g2D.getFontMetrics();
            g2D.drawString("vs.", Utils.scale(375) - fontMetrics.stringWidth("vs.") / 2, Utils.scale(690));
            g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER)); //resetting opacity
            g2D.setRenderingHints(Settings.DEFAULT_RENDER_SETTINGS);
        }
    }

    /**
     * Animates the sliding in of the vs. transition panel.
     */
    private void animateIn() {
        long startTime = System.currentTimeMillis();
        double deltaTime = 0;
        while (deltaTime < SLIDE_DURATION) {
            y = (double) getHeight() * (deltaTime / SLIDE_DURATION);
            deltaTime = System.currentTimeMillis() - startTime;
        }
        y = getHeight();

        opacity = 0f;
        startTime = System.currentTimeMillis();
        deltaTime = 0;
        while (deltaTime < VS_ANIMATION_DURATION) {
            opacity = (float) (deltaTime / VS_ANIMATION_DURATION);
            deltaTime = System.currentTimeMillis() - startTime;
        }
        opacity = 1f;
    }

    /**
     * Animates the sliding out of the transition panel.
     */
    private void animateOut() {
        opacity = 1f;
        long startTime = System.currentTimeMillis();
        double deltaTime = 0;
        while (deltaTime < VS_ANIMATION_DURATION) {
            opacity = 1.0f - (float) (deltaTime / VS_ANIMATION_DURATION);
            deltaTime = System.currentTimeMillis() - startTime;
        }
        opacity = 0f;

        startTime = System.currentTimeMillis();
        deltaTime = 0;
        y = getHeight();
        while (deltaTime < SLIDE_DURATION) {
            y = getHeight() + (double) getHeight() * (deltaTime / SLIDE_DURATION);
            deltaTime = System.currentTimeMillis() - startTime;
        }
        y = getHeight() * 2;
    }

    /**
     * @return the sprite showing the left player rectangle
     */
    private BufferedImage loadLeftPlayerSprite() {
        BufferedImage sprite = Utils.createCompatibleImage(Utils.scale(375), Utils.scale(1334));
        Graphics2D g2D = (Graphics2D) sprite.getGraphics();
        g2D.setRenderingHints(Settings.QUALITY_RENDER_SETTINGS);

        g2D.setColor(new Color(245, 132, 148));
        g2D.fillRect(0, 0, sprite.getWidth(), sprite.getHeight());

        g2D.drawImage(Utils.loadScaledImage("resources/menu/left bongo cat.png", 325, 200),
                Utils.scale(20), Utils.scale(480), null);

        g2D.setColor(Pallette.OUTLINE_COLOR);
        g2D.setFont(Pallette.getScaledFont(Pallette.TITLE_FONT, 50)); //drawing the two usernames and corresponding bongo cats
        FontMetrics fontMetrics = g2D.getFontMetrics();
        g2D.drawString(you, Utils.scale(187) - fontMetrics.stringWidth(you) / 2, Utils.scale(800));

        g2D.dispose();

        return sprite;
    }

    /**
     * @return the sprite showing the right player panel
     */
    private BufferedImage loadRightPlayerSprite() {
        BufferedImage sprite = Utils.createCompatibleImage(Utils.scale(375), Utils.scale(1334));
        Graphics2D g2D = (Graphics2D) sprite.getGraphics();
        g2D.setRenderingHints(Settings.QUALITY_RENDER_SETTINGS);

        g2D.setColor(new Color(125, 151, 230));
        g2D.fillRect(0, 0, sprite.getWidth(), sprite.getHeight());

        g2D.drawImage(Utils.loadScaledImage("resources/menu/right bongo cat.png", 325, 200),
                Utils.scale(20), Utils.scale(480), null);

        g2D.setColor(Pallette.OUTLINE_COLOR);
        g2D.setFont(Pallette.getScaledFont(Pallette.TITLE_FONT, 50)); //drawing the two usernames and corresponding bongo cats
        FontMetrics fontMetrics = g2D.getFontMetrics();
        g2D.drawString(opponent, Utils.scale(187) - fontMetrics.stringWidth(opponent) / 2, Utils.scale(800));

        g2D.dispose();
        return sprite;
    }
}
