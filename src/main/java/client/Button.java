package client;

import client.utilities.Utils;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

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
    private boolean selected;
    private int nextPanel;

    /**
     * Constructor
     * @param text String text on the button
     * @param x button position x
     * @param y button position y
     * @param width button width
     * @param height button height
     */
    public Button(String text, int x, int y, int width, int height, Color bgColour, Color textColour, int nextPanel) {
        super();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.textColour = textColour;
        this.bgColour = bgColour;
        this.buttonFont = Utils.getFont("resources/moon.otf", Math.round(this.height * 0.8));
        this.nextPanel = nextPanel;
    }

    public int getNextPanel(){
        return nextPanel;
    }

    @Override
    public void setSelected(boolean selected){
        this.selected = selected;
    }

    /**
     * draw
     * draws the button
     * @param g2D
     */
    public void draw(Graphics2D g2D) {
        // Set colour based on whether it should be selected or not
        if (selected) {
            g2D.setColor(textColour);
        } else {
            g2D.setColor(bgColour);
        }

        // Fill button rectangle
        g2D.fillRect(x,y, width, height);


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
        g2D.drawString(text, x + width /2 - textWidth/2, y + height/2 + textHeight/4);

    }


}
