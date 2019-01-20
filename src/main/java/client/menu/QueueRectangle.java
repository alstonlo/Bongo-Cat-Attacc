package client.menu;

import client.Drawable;
import client.utilities.Settings;
import client.utilities.Utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

class QueueRectangle implements Drawable {

    private BufferedImage sprite;

    private int x;
    private double y;
    private int width;
    private int height;
    private Color bgColor;

    QueueRectangle(int x, int width, int height, Color bgColor) {
        this.x = x;
        this.width = width;
        this.height = height;
        this.bgColor = bgColor;
    }

    public void configureSprites() {
        this.sprite = Utils.createCompatibleImage(width, height);
        Graphics2D g2D = (Graphics2D) sprite.getGraphics();
        g2D.setRenderingHints(Settings.QUALITY_RENDER_SETTINGS);
        g2D.setColor(bgColor);
        g2D.fillRect(0, 0, sprite.getWidth(), sprite.getHeight());
        g2D.dispose();
    }

    void setY(double y) {
        this.y = y;
    }

    public void draw(Graphics2D g2D) {
        g2D.drawImage(sprite, x, Utils.round(y), null);
    }
}
