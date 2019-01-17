package client.songselect;

import client.utilities.Pallette;
import client.utilities.Settings;
import client.utilities.ThreadPool;
import client.utilities.Utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.concurrent.atomic.AtomicBoolean;

public class Clock {
    private int seconds;

    private int x;
    private int y;

    private int currX = 0;
    private int currY;

    private int radius;
    private int armRadius;

    private AtomicBoolean timeIsOn = new AtomicBoolean(true);


    public Clock(int x, int y, int seconds, int radius){
        this.x = x;
        this.y = y;
        this.seconds = seconds;
        this.armRadius = radius - Utils.scale(5);
        this.radius = radius;
        this.currY = y - radius;
    }

    public void draw(Graphics2D g2D){
        g2D.setRenderingHints(Settings.QUALITY_RENDER_SETTINGS);
        g2D.setColor(new Color(255,255,255));
        g2D.fillOval(x-radius+1, y-radius+1, radius*2-1, radius*2-1);
        g2D.setColor(Pallette.OUTLINE_COLOR);
        g2D.setStroke(new BasicStroke(Utils.scale(3)));
        g2D.drawLine(x,y,currX, currY);
        g2D.setStroke(new BasicStroke(Utils.scale(4)));
        g2D.drawOval(x-radius,y-radius,radius*2,radius*2);
    }

    public void start(){
        timeIsOn.set(true);
        ThreadPool.execute(() -> updatePosition());
    }

    public void stop(){
        timeIsOn.set(false);
        currY = y - radius;
    }

    private void updatePosition(){
        long startTime = System.currentTimeMillis();
        while(timeIsOn.get()){
            double theta = 2.0 * Math.PI *(System.currentTimeMillis()-startTime)/(1000.0*seconds);
            currX = x + (int) Math.round((armRadius)*Math.sin(theta));
            currY = y - (int) Math.round((armRadius)*Math.cos(theta));
        }
    }
}
