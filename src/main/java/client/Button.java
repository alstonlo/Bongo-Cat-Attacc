package client;

import client.ui.Window;
import client.utilities.Utils;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;

public class Button extends JButton {
    // Important class variables
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected String text;
    private Font buttonFont;
    private Color textColour;
    private Color bgColour;
    private Window window;
    private int nextFrame;

    /**
     * Constructor
     * @param text String text on the button
     * @param x button position x
     * @param y button position y
     * @param width button width
     * @param height button height
     */
    public Button(String text, int x, int y, int width, int height, Color bgColour, Color textColour, Window window, int nextFrame) {
        super();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.textColour = textColour;
        this.bgColour = bgColour;
        this.buttonFont = Utils.getFont("resources/moon.otf", Math.round(this.height * 0.8));
        this.window = window;
        this.nextFrame = nextFrame;
    }

    /**
     * isMouseOnButton
     * Checks if the mouse is over the button
     * @param panel that the button is on
     * @return true if on and false if not
     */
    public boolean isMouseOnButton(JPanel panel){
        Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
        Point relScreenLocation = panel.getLocationOnScreen().getLocation();
        int x = (int) Math.round(mouseLocation.getX() - relScreenLocation.getX());
        int y = (int) Math.round(mouseLocation.getY() - relScreenLocation.getY());

        return ((x >= this.x) && (x <= this.x + this.width) && (y >= this.y) && (y <= this.y + this.height));
    }

    /**
     * draw
     * draws the button
     * @param g
     * @param panel
     */
    public void draw(Graphics g, JPanel panel) {
        // Set colour based on whether it should be selected or not
        if (isMouseOnButton(panel)) {
            g.setColor(textColour);
        } else {
            g.setColor(bgColour);
        }

        // Fill button rectangle
        g.fillRect(x,y, width, height);


        // Set font and text variables
        g.setFont(buttonFont);
        FontMetrics buttonFontMetrics = g.getFontMetrics(buttonFont);
        int textHeight = buttonFontMetrics.getMaxAscent();
        int textWidth = buttonFontMetrics.stringWidth(text);

        // Set text colour and draw it
        if (isMouseOnButton(panel)) {
            g.setColor(bgColour);
        } else {
            g.setColor(textColour);
        }
        g.drawString(text, x + width /2 - textWidth/2, y + height/2 + textHeight/4);

    }


}
