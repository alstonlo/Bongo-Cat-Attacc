package client.menu;

import client.utilities.Utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

class QueueRectangle extends Rectangle2D.Double {

    private int x;
    private double y;
    private int width;
    private int height;
    private Color bgColor;

    QueueRectangle(int x, int width, int height, Color bgColor){
        this.x = x;
        this.width = width;
        this.height = height;
        this.bgColor = bgColor;
    }

    void setY(double y) {
        this.y = y;
    }

    void draw(Graphics2D g2D){
        int drawY = Utils.round(y);

        g2D.setColor(bgColor);
        g2D.fillRect(x, drawY,width, height);
    }
}
