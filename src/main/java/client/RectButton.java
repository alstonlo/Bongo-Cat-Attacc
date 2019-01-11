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
class RectButton {

    private BufferedImage icon;
    private java.awt.Color color = new Color(212, 212, 212);
    private Color selectedColor = new Color(255, 246, 154);
    private BasicStroke outline = new BasicStroke(5);
    private Color outlineColor = new Color(60, 59, 21);

    private boolean selected;
    private Runnable onSubmit;

    private static final int WIDTH = 650;
    private static final int HEIGHT = 360;
    private static final int Y_POS = 800;
    private int xPos;
    /**
     * Constructs a CircleButton based on its icon, position, and size.
     *
     * @param icon    the icon displayed on this button
     */
    RectButton(BufferedImage icon, int xPos) {
        this.icon = icon;
        this.xPos = xPos;
    }

    /**
     * Selects or places focus on this button.
     */
    void select() {
        if (!selected) { //increase the button size to show the selection
        }
        selected = true;
    }

    /**
     * Deselects or removes focus from this button.
     */
    void deselect() {
        if (selected) { //decrease the button size back to normal
        }
        selected = false;
    }

    /**
     * Submits or clicks the button.
     */
    void submit() {
        if (onSubmit != null) {
            onSubmit.run();
        }
    }

    void setX(int x){
        xPos = x;
    }

    int getX(){
        return this.xPos;
    }

    /**
     * Draws the button. The icon of this button is drawn at (x - radius, y - radius)
     * with width and height of 2 * radius.
     *
     * @param g2D
     */
    void draw(Graphics2D g2D) {
        g2D.setColor((selected) ? selectedColor : color);

        g2D.drawRoundRect(xPos, Y_POS, WIDTH,HEIGHT,30,30);

        g2D.drawImage(icon, xPos + 30, Y_POS + 30,null);
    }

    //SETTERS -----------------------------------------------------------------------

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
     * Sets the Runnable action the button will perform on being clicked
     * or submitted by {@link CircleButton#submit()}.
     *
     * @param onSubmit the action the button will perform on being clicked
     */
    void setOnSubmit(Runnable onSubmit) {
        this.onSubmit = onSubmit;
    }

}
