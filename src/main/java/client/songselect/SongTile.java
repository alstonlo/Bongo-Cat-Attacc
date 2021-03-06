package client.songselect;

import client.components.Song;
import client.utilities.Pallette;
import client.utilities.Settings;
import client.utilities.Utils;

import javax.sound.sampled.Clip;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Graphical representation of a song, used by {@link SongSelectPanel}.
 * Implicitly, the size of a SongTile is equal to {@link Settings#PANEL_SIZE}.
 *
 * @author Katelyn and Alston
 * last updated 1/9/2019
 */
class SongTile {

    private Song song;
    private double x = 0; //the actual and time-accurate x-coordinate
    private double drawX = 0; //the x-coordinate the tile should be drawn at

    private Clip audio;

    private BufferedImage splashSprite;
    private BufferedImage albumSprite;
    private BufferedImage foregroundSprite;

    private final int panelWidth = Settings.PANEL_SIZE.width; //for convenience

    /**
     * Constructs a SongTile that displays the song argument.
     *
     * @param song the song that is displayed by this tile
     */
    SongTile(Song song) {
        this.song = song;
    }

    /**
     * Sets the x-coordinate of this tile.
     *
     * @param x the new x-coordinate
     */
    void setX(double x) {
        this.x = x;
    }

    /**
     * When clamp() is called, the current x-coordinate of this tile is saved.
     * When {@link SongTile#drawBackground(Graphics2D)} and
     * {@link SongTile#drawForeground(Graphics2D)} are called, the tile is drawn at
     * the saved x-coordinate from the most recent clamp() call. The purpose of clamp()
     * is to because the actual x-coordinate is continuously modified on a separate thread,
     * and would change as different parts of the tile are drawn.
     */
    void clamp() {
        this.drawX = x;
    }

    /**
     * Loads the resources used by this tile (e.g. splashSprite art). Although,
     * we could load in the constructor, having it as a method allows for better
     * flexibility with managing resources.
     */
    void loadTile() {
        this.splashSprite = song.getSplash();
        this.albumSprite = Utils.scale(song.getAlbum(), 300, 300);
        this.foregroundSprite = loadForegroundSprite();
        this.audio = song.getAudioExcerpt();
    }

    Clip getAudio() {
        return this.audio;
    }

    Song getSong() {
        return this.song;
    }

    /**
     * Draws the background of the tile onto the graphics object.
     *
     * @param g2D the Graphics context in which to paint
     */
    void drawBackground(Graphics2D g2D) {

        float alpha; //calculate the alpha composite value from the clamped x-coordinate
        if (drawX < -panelWidth || drawX > panelWidth) {
            alpha = 0.0f;
        } else {
            alpha = 1 - Math.abs((float) drawX) / panelWidth;
        }

        g2D.setComposite(AlphaComposite.SrcOver.derive(alpha));
        g2D.drawImage(splashSprite, 0, 0, null);
        g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER)); //reset composite
    }

    /**
     * Draws the foregroundSprite of the tile onto the graphics object.
     *
     * @param g2D the Graphics context in which to paint
     */
    void drawForeground(Graphics2D g2D) {
        g2D.drawImage(foregroundSprite,
                Utils.round(drawX) + (panelWidth - foregroundSprite.getWidth()) / 2,
                Utils.scale(800), null);
    }

    private BufferedImage loadForegroundSprite() {
        BufferedImage sprite = Utils.createCompatibleImage(Utils.scale(600), Utils.scale(350));
        Graphics2D g2D = (Graphics2D) sprite.getGraphics();
        g2D.setRenderingHints(Settings.QUALITY_RENDER_SETTINGS);

        g2D.setColor(new Color(44, 44, 44, 200));
        g2D.fillRoundRect(0, 0, sprite.getWidth(), sprite.getHeight(), 30, 30);

        g2D.setColor(Color.WHITE);
        g2D.setFont(Pallette.getScaledFont(Pallette.TEXT_FONT, 20));
        g2D.drawString(song.getName(), Utils.scale(360), Utils.scale(45));
        g2D.drawString("Difficulty: ", Utils.scale(360), Utils.scale(80));

        BufferedImage star = Utils.loadScaledImage("resources/songs/bongo.png", 40, 40);
        for (int i = 0; i < song.getDifficulty(); i++) {
            g2D.drawImage(star, Utils.scale(360 + (45 * i)), Utils.scale(90), null);
        }

        g2D.drawImage(albumSprite, Utils.scale(30), Utils.scale(25), null);
        g2D.dispose();

        return sprite;
    }
}
