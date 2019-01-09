package client.ui;

import client.utilities.Utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

/**
 * Custom game button object.
 *
 * @author Katelyn
 * last updated 1/9/2019
 */
class Button {

    private int x;
    private int y;
    private int width;
    private int height;
    private String text;
    private Font buttonFont;
    private Color textColour;
    private Color bgColour;
    private boolean selected;
    private Runnable onSubmit;

    /**
     * Constructs a Button given its dimensions and colour
     *
     * @param text       String text on the button
     * @param x          button x coordinate
     * @param y          button y coordinate
     * @param width      button width
     * @param height     button height
     * @param bgColour   the button's colour
     * @param textColour the button's text colour
     */
    public Button(String text, int x, int y, int width, int height, Color bgColour, Color textColour) {
        super();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.textColour = textColour;
        this.bgColour = bgColour;
        this.buttonFont = Utils.getFont("resources/moon.otf", Math.round(this.height * 0.8));
    }

    void setSelected(boolean selected) {
        this.selected = selected;
    }

    void setOnSubmit(Runnable onSubmit) {
        this.onSubmit = onSubmit;
    }

    void submit() {
        if (onSubmit != null) {
            onSubmit.run();
        }
    }

    /**
     * draw
     * draws the button
     *
     * @param g2D
     */
    void draw(Graphics2D g2D) {
        // Set colour based on whether it should be selected or not
        if (selected) {
            g2D.setColor(textColour);
        } else {
            g2D.setColor(bgColour);
        }

        // Fill button rectangle
        g2D.fillRect(x, y, width, height);


        // Set font and text variables
        g2D.setFont(buttonFont);
        FontMetrics buttonFontMetrics = g2D.getFontMetrics(buttonFont);
        int textHeight = buttonFontMetrics.getMaxAscent();
        int textWidth = buttonFontMetrics.stringWidth(text);

        // Set text colour and draw it
        if (selected) {
            g2D.setColor(bgColour);
        } else {
            g2D.setColor(textColour);
        }
        g2D.drawString(text, x + width / 2 - textWidth / 2, y + height / 2 + textHeight / 4);
    }
}
