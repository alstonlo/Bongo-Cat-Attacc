package client.gameplay;

import client.utilities.Utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Objects;
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

    public Color color = Color.BLACK;

    public final AtomicBoolean active = new AtomicBoolean(true);
    public final AtomicBoolean offScreen = new AtomicBoolean(false);

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
         if (y >= 1170){
             this.active.set(false);
         }
         if (y >= 1200){
             this.offScreen.set(true);
         }
    }

    void setRed(){
        color = Color.RED;
    }

    void setGreen(){
        color = Color.GREEN;
    }

    void draw(Graphics2D g2D){
        g2D.setColor(color);
        g2D.fillOval(Utils.scale(x), Utils.scale(y), Utils.scale(WIDTH), Utils.scale(HEIGHT));
    }

    int calculateDistance(int newX, int newY) {
        int value = (int) Math.round(Math.sqrt( (newX - x) * (newX - x) + (newY - y) * (newY - y)));
        return value;

    }

}
