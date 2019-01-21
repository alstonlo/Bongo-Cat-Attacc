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

class PlayerQueueRectangle extends Rectangle implements Drawable {

    private BufferedImage sprite;
    private String catFileName;
    private String username;

    private int x;
    private double y;
    private int width;
    private int height;
    private Color bgColor;

    PlayerQueueRectangle(int x, int width, int height, Color bgColor, String fileName) {
        this.x = x;
        this.width = width;
        this.height = height;
        this.bgColor = bgColor;
        this.catFileName = fileName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    void setY(double y) {
        this.y = y;
    }

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

    public void draw(Graphics2D g2D) {
        g2D.drawImage(sprite, x, Utils.round(y), null);
    }
}
