package client.gameplay;

import client.utilities.Utils;

import java.awt.Graphics2D;
import java.util.concurrent.atomic.AtomicBoolean;

public class Note {
    private static final int WIDTH = 30;
    private static final int HEIGHT = 20;
    public static final int LEFT_TYPE = 0;
    public static final int RIGHT_TYPE = 1;

    private int x;
    private int y;

    private int height;
    private int width;
    public final int type;

    public final AtomicBoolean active = new AtomicBoolean(true);

    Note(int x, int y, int type){
        this.x = x;
        this.y = y;
        this.type = type;
    }

    void calculateSize(){

    }

    void updatePosition(int increase){
         if (type == 0) {
             this.x -= increase;
             this.y = (int) Math.round(((-700.0 / 111) * x) + 2910.0);
         } else {
             this.x += increase;
             this.y = (int) Math.round(((700.0 / 111) * x) - 1819.0);
         }
         if (y >= 1180){
             this.active.set(false);
         }
    }



    void draw(Graphics2D g2D){
        g2D.drawOval(Utils.scale(x), Utils.scale(y), Utils.scale(HEIGHT), Utils.scale(WIDTH));
    }

    int calculateDistance(int newX, int newY) {
        int value = (int) Math.round(Math.sqrt( (newX - x) * (newX - x) + (newY - y) * (newY - y)));
        return value;

    }

}
