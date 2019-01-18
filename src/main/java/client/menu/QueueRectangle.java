package client.menu;

import java.awt.Color;
import java.awt.Graphics2D;

public class QueueRectangle {
    private int width;
    private int height;
    private Color bgColor;

    QueueRectangle(int width, int height, Color bgColor){
        this.width = width;
        this.height = height;
        this.bgColor = bgColor;
    }

    void draw(Graphics2D g2D, int x, int y){
        g2D.setColor(bgColor);
        g2D.fillRect(x, y,width, height);
    }
}
