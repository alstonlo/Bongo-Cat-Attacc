package client.gameplay;

import client.utilities.Pallette;
import client.utilities.Utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The object representing a single note within a game
 *
 * @Author Katelyn and Alston
 * last updated 01/21/2019
 */
public class Note {

    private static final int[][] SRC = {{361, 634}, {389, 634}};
    private static final int[][] DES = {{279, 1150}, {471, 1150}};

    static final int LEFT_TYPE = 0; //note can either be a left or right note
    static final int RIGHT_TYPE = 1;

    private static final double WIDTH = 30; //base sizes, can be used for scaling the notes to become bigger as they come closer to player
    private static final double HEIGHT = 20;

    final AtomicBoolean active = new AtomicBoolean(true); //whether or not the note can be played
    final AtomicBoolean offScreen = new AtomicBoolean(false); //whether or not the note still has to be drawn (recycling method is based on which notes are still on screen)

    private final long spawnTime;
    private final long duration = 2000;
    private double centreX;//the center coordinates of the note
    private double centreY;
    private double height; //calculated height and width
    private double width;
    public final int type;

    public int hitState = -1;

    public Color neutralColor = Pallette.OUTLINE_COLOR;

    /**
     * Constructor to create a Note
     *
     * @param type whether this note is a left note or right note
     */
    Note(int type) {
        this.spawnTime = System.currentTimeMillis();
        this.centreX = SRC[type][0];
        this.centreY = SRC[type][1];
        this.width = WIDTH;
        this.height = HEIGHT;
        this.type = type;
    }

    /**
     * Uses the current distance from the origin compared to the basedistance from the origin to calculate a multiplier factor
     * Using this multiplier, adjusts the size of the note
     */
    void changeSize() {

    }

    /**
     * Updates the position of the note by checking the amount of time elapsed since its creation
     * Deactivates notes which pass the line
     * Updates the offscreen boolean for notes which have left the screen
     */
    void reposition() {
        double elapsedTime = System.currentTimeMillis() - spawnTime;
        double percent = elapsedTime / duration;

        if (type == LEFT_TYPE) {
            centreX = percent * (DES[LEFT_TYPE][0] - SRC[LEFT_TYPE][0]) + SRC[LEFT_TYPE][0];
            centreY = percent * (DES[LEFT_TYPE][1] - SRC[LEFT_TYPE][1]) + SRC[LEFT_TYPE][1];
        } else if (type == RIGHT_TYPE) {
            centreX = percent * (DES[RIGHT_TYPE][0] - SRC[RIGHT_TYPE][0]) + SRC[RIGHT_TYPE][0];
            centreY = percent * (DES[RIGHT_TYPE][1] - SRC[RIGHT_TYPE][1]) + SRC[RIGHT_TYPE][1];
        }

        if (centreY >= 1170) {
            this.active.set(false);
            setMissed();
        }
        if (centreY >= 1400) {
            this.offScreen.set(true);
        }
    }

    /**
     * Changes the hit state to successfully hit (1)
     */
    void setHit() {
        hitState = 1;
    }

    /**
     * Checks that the note hasn't been played yet
     * Changes the hit state to missed (0)
     */
    void setMissed() {
        if (hitState == -1) {
            hitState = 0;
        }
    }

    /**
     * Draws the note in its appropriate colour
     *
     * @param g2D the Graphics2D object to draw the oval
     */
    void draw(Graphics2D g2D) {
        if (hitState == -1) {
            g2D.setColor(neutralColor);
        } else if (hitState == 0) {
            g2D.setColor(Color.RED);
        } else if (hitState == 1) {
            g2D.setColor(Color.GREEN);
        }

        g2D.fillOval(Utils.scale(centreX - width / 2), Utils.scale(centreY - height / 2), Utils.scale(width), Utils.scale(height));
    }

    /**
     * Calculates the distance between the note's current position and another given coordinate
     *
     * @return the distance between this note's position and the other position
     */
    double calculateDistance(double x, double y) {
        return Math.sqrt((x - centreX) * (x - centreX) + (y - centreY) * (y - centreY));
    }

}
