package client.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Custom circular game button object. Instead of text, the button has an icon.
 *
 * @author Katelyn
 * last updated 1/9/2019
 */
class CircleButton {

    private int x;
    private int y;
    private int radius;

    private BufferedImage icon;
    private Color color = new Color(212, 212, 212);
    private Color selectedColor = new Color(255, 233, 116);

    private boolean selected;
    private Runnable onSubmit;


    CircleButton(BufferedImage icon, int x, int y, int radius) {
        super();
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.icon = icon;
    }

    /**
     * Submits or clicks the button.
     */
    void submit() {
        if (onSubmit != null) {
            onSubmit.run();
        }
    }

    /**
     * Sets the normal, unselected color of the button.
     *
     * @param color the color to be set
     */
    void setColor(Color color) {
        this.color = color;
    }

    /**
     * Sets the selected color of the button.
     *
     * @param color the color to be set
     */
    void setSelectedColor(Color color) {
        this.selectedColor = color;
    }

    /**
     * Sets whether or not the button is selected or focused on.
     *
     * @param selected true if the button is selected; false otherwise
     */
    void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Sets the Runnable action the button will perform on being clicked
     * or submitted by {@link CircleButton#submit()}.
     *
     * @param onSubmit the action the button will perform on being clicked
     */
    void setOnSubmit(Runnable onSubmit) {
        this.onSubmit = onSubmit;
    }

    /**
     * Draws the button.
     *
     * @param g2D
     */
    void draw(Graphics2D g2D) {
        g2D.setColor((selected) ? selectedColor : color);
        g2D.fillOval(x, y, radius * 2, radius * 2);
        g2D.drawImage(icon, x, y, radius, radius, null);
    }
}
