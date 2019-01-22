package client.gameplay;

import client.utilities.Pallette;
import client.utilities.Utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The object representing a single note within a game
 *
 * @Author Katelyn Wang
 * last updated 01/21/2019
 */
public class Note {
    private static final int WIDTH = 30; //base sizes, can be used for scaling the notes to become bigger as they come closer to player
    private static final int HEIGHT = 20;
    public static final int LEFT_TYPE = 0; //note can either be a left or right note
    public static final int RIGHT_TYPE = 1;

    private int centreX;//the center coordinates of the note
    private int centreY;

    private int height; //calculated height and width
    private int width;

    public final int type;

    public Color color = Pallette.OUTLINE_COLOR;

    public final AtomicBoolean active = new AtomicBoolean(true); //whether or not the note can be played
    public final AtomicBoolean offScreen = new AtomicBoolean(false); //whether or not the note still has to be drawn (recycling method is based on which notes are still on screen)

    /**
     * Constructor to create a Note
     *
     * @param centreX the x coordinate of the oval which visually represents the note
     * @param centreY the y coordinate of the oval which visually represents the note
     * @param type    whether this note is a left note or right note
     */
    Note(int centreX, int centreY, int type) {
        this.centreX = centreX;
        this.centreY = centreY;
        this.type = type;
    }

    /**
     * Uses the current distance from the origin compared to the basedistance from the origin to calculate a multiplier factor
     * Using this multiplier, adjusts the size of the note
     */
    void calculateSize() {

    }

    /**
     * Updates the position
     * Changes the x position by the stipulated amount
     * Calculates the y position based on the equation of the line it is on (whether left or right)
     *
     * @param changeX the amount the x coordinate is being changed
     */
    void updatePosition(int changeX) {
        if (type == 0) {
            this.centreX -= changeX;
            this.centreY = (int) Math.round(((-700.0 / 111) * centreX) + 2910.0);
        } else {
            this.centreX += changeX;
            this.centreY = (int) Math.round(((700.0 / 111) * centreX) - 1819.0);
        }
        if (centreY >= 1170) {
            this.active.set(false);
        }
        if (centreY >= 1400) {
            this.offScreen.set(true);
        }
    }

    /**
     * Changes the colour of the note to red
     */
    void setRed() {
        color = Color.RED;
    }

    /**
     * Changes the colour of the note to green
     */
    void setGreen() {
        color = Color.GREEN;
    }

    /**
     * Draws the note in its appropriate colour
     *
     * @param g2D the Graphics2D object to draw the oval
     */
    void draw(Graphics2D g2D) {
        g2D.setColor(color);
        g2D.fillOval(Utils.scale(centreX - WIDTH / 2), Utils.scale(centreY - HEIGHT / 2), Utils.scale(WIDTH), Utils.scale(HEIGHT));
    }

    /**
     * Calculates the distance between the note's current position and another given coordinate
     *
     * @param otherX the other coordinate's x position
     * @param otherY the other coordinate's y position
     * @return the distance between this note's position and the other position
     */
    int calculateDistance(int otherX, int otherY) {
        int value = (int) Math.round(Math.sqrt((otherX - centreX) * (otherX - centreX) + (otherY - centreY) * (otherY - centreY)));
        return value;

    }

}
