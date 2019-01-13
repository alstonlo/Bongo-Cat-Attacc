package client.songselect;

import client.utilities.Settings;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

/**
 * Custom circular game button object. Instead of text, the button has an icon.
 *
 * @author Katelyn and Alston
 * last updated 1/9/2019
 */
class SongTile {

    private static final int WIDTH = 650;
    private static final int HEIGHT = 360;
    private static final int Y_POS = 800;

    private Song song;
    private final Dimension dimension = Settings.PANEL_SIZE;
    private double x = -dimension.getWidth();

    SongTile(Song song) {
        this.song = song;
    }

    public void setX(double x) {
        this.x = x;
    }

    void draw(Graphics2D g2D) {
//        g2D.drawRoundRect(x, Y_POS, WIDTH, HEIGHT, 30, 30);
//
//        g2D.drawImage(icon, x + 30, Y_POS + 30, null);
    }
}
