package client;

import java.awt.Graphics2D;

/**
 * Representation of an object that can be drawn.
 *
 * @author Alston
 * last udpated 1/19/2018
 */
public interface Drawable {

    /**
     * Preload any sprites or resources necessary for drawing this object.
     * This must be called before {@link Drawable#draw(Graphics2D)} in order
     * to draw this object as expected.
     * <p>
     * The reason for this method is to offer more flexibility with loading
     * the objects sprites (and therefore, memory management).
     */
    void configureSprites();

    /**
     * Draws this object.
     *
     * @param g2D the graphics context in which this object is drawn
     */
    void draw(Graphics2D g2D);
}
