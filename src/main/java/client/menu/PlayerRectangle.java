package client.menu;

import client.Drawable;
import client.utilities.Pallette;
import client.utilities.Settings;
import client.utilities.Utils;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * The images which slide in during the endgamepanel
 *
 * @Author Katelyn Wang
 * last updated 01/21/2019
 */
class PlayerRectangle extends Rectangle implements Drawable {

    private BufferedImage sprite;
    private String catFileName;
    private String username;

    private int x;
    private double y;
    private int width;
    private int height;
    private Color bgColor;

    /**
     * Constructs a PlayerRectangle
     * @param x the x coordinate of the player rectangle
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     * @param bgColor the background colour
     * @param fileName the file name of the cat
     */
    PlayerRectangle(int x, int width, int height, Color bgColor, String fileName) {
        this.x = x;
        this.width = width;
        this.height = height;
        this.bgColor = bgColor;
        this.catFileName = fileName;
    }

    /**
     * Set username of the panel
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the y coordinate of the panel
     * @param y the new y coordinate
     */
    void setY(double y) {
        this.y = y;
    }

    /**
     * Draws the graphics to a buffered image to be drawn on the rectangle
     */
    public void configureSprites() {
        this.sprite = Utils.createCompatibleImage(width, height);
        Graphics2D g2D = (Graphics2D) sprite.getGraphics();
        g2D.setRenderingHints(Settings.QUALITY_RENDER_SETTINGS);

        g2D.setColor(bgColor);
        g2D.fillRect(0, 0, sprite.getWidth(), sprite.getHeight());

        g2D.drawImage(Utils.loadScaledImage(catFileName, 325, 200), Utils.scale(20), Utils.scale(480), null);

        g2D.setFont(Pallette.getScaledFont(Pallette.TITLE_FONT, 50)); //drawing the two usernames and corresponding bongo cats
        FontMetrics fontMetrics = g2D.getFontMetrics();
        g2D.setColor(Pallette.OUTLINE_COLOR);
        g2D.drawString(username, Utils.scale(187) - fontMetrics.stringWidth(username) / 2, Utils.scale(800));

        g2D.dispose();
    }

    /**
     * Draws the bufferedimage which makes up the PlayerRectangle
     * @param g2D the graphics context in which this object is drawn
     */
    public void draw(Graphics2D g2D) {
        g2D.drawImage(sprite, x, Utils.round(y), null);
    }
}
