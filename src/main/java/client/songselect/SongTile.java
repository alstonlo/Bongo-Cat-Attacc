package client.songselect;

import client.utilities.Settings;
import client.utilities.Utils;

import java.awt.Graphics2D;

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

    private final int panelWidth = Settings.PANEL_SIZE.width;

    SongTile(Song song) {
        this.song = song;
    }

    public int getX() {
        return Utils.round(x);
    }

    void setX(double x) {
        this.x = x;
    }

    void draw(Graphics2D g2D) {
        g2D.fillRoundRect(getX() + (panelWidth - WIDTH)/2 , Y_POS, WIDTH, HEIGHT, 30, 30);
    }
}
