package client.songselect;

import client.utilities.Settings;
import client.utilities.Utils;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Katelyn and Alston
 * last updated 1/9/2019
 */
class SongTile {

    private static final int WIDTH = Utils.scale(600);
    private static final int HEIGHT = Utils.scale(350);
    private static final int Y_POS = Utils.scale(800);

    private Song song;
    private double x = 0;
    private double drawX = 0;

    private BufferedImage splash;

    private final int panelWidth = Settings.PANEL_SIZE.width;

    SongTile(Song song) {
        this.song = song;
    }

    int getX() {
        return Utils.round(x);
    }

    void setX(double x) {
        this.x = x;
    }

    void clamp() {
        this.drawX = x;
    }

    void loadTile() {
        this.splash = song.getSplash();
    }

    void drawBackground(Graphics2D g2D) {

        float alpha = 0.5f;
        if (drawX < -panelWidth || drawX > panelWidth) {
            alpha = 0.0f;
        } else {
            alpha = 1 - Math.abs((float)drawX)/panelWidth;
        }

        g2D.setComposite(AlphaComposite.SrcOver.derive(alpha));
        g2D.drawImage(splash, 0, 0, null);
        g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
    }

    void drawForeground(Graphics2D g2D) {
        g2D.fillRoundRect(Utils.round(drawX) + (panelWidth - WIDTH)/2 , Y_POS, WIDTH, HEIGHT, 30, 30);
    }
}
