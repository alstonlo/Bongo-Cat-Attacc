package client;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Custom circular game button object. Instead of text, the button has an icon.
 *
 * @author Katelyn and Alston
 * last updated 1/9/2019
 */
public class CircleButton {

    private int x;
    private int y;
    private int radius;

    private BufferedImage icon;
    private Color color = new Color(212, 212, 212);
    private Color selectedColor = new Color(255, 246, 154);
    private BasicStroke outline = new BasicStroke(5);
    private Color outlineColor = new Color(60, 59, 21);

    private boolean selected;
    private Runnable onSubmit;

    /**
     * Constructs a CircleButton based on its icon, position, and size.
     *
     * @param icon    the icon displayed on this button
     * @param centerX the center x-coordinate of this button
     * @param centerY the center y-coordinate of this button
     * @param radius  the radius of this button
     */
    public CircleButton(BufferedImage icon, int centerX, int centerY, int radius) {
        super();
        this.x = centerX;
        this.y = centerY;
        this.radius = radius;
        this.icon = icon;
    }

    /**
     * Selects or places focus on this button.
     */
    public void select() {
        if (!selected) { //increase the button size to show the selection
            this.radius += 5;
        }
        selected = true;
    }

    /**
     * Deselects or removes focus from this button.
     */
    public void deselect() {
        if (selected) { //decrease the button size back to normal
            this.radius -= 5;
        }
        selected = false;
    }

    /**
     * Submits or clicks the button.
     */
    public void submit() {
        if (onSubmit != null) {
            onSubmit.run();
        }
    }

    /**
     * Draws the button. The icon of this button is drawn at (x - radius, y - radius)
     * with width and height of 2 * radius.
     *
     * @param g2D
     */
    public void draw(Graphics2D g2D) {
        g2D.setColor((selected) ? selectedColor : color);
        g2D.fillOval(x - radius, y - radius, radius * 2, radius * 2);

        g2D.setColor(outlineColor);
        g2D.setStroke(outline);
        g2D.drawOval(x - radius, y - radius, radius * 2, radius * 2);

        g2D.drawImage(icon, x - radius, y - radius, radius * 2, radius * 2, null);
    }

    //SETTERS -----------------------------------------------------------------------

    /**
     * Sets the normal, unselected color of the button.
     *
     * @param color the color to be set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Sets the selected color of the button.
     *
     * @param color the color to be set
     */
    public void setSelectedColor(Color color) {
        this.selectedColor = color;
    }

    /**
     * Sets the Runnable action the button will perform on being clicked
     * or submitted by {@link CircleButton#submit()}.
     *
     * @param onSubmit the action the button will perform on being clicked
     */
    public void setOnSubmit(Runnable onSubmit) {
        this.onSubmit = onSubmit;
    }

}
