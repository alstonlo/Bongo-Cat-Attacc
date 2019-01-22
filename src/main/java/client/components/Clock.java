package client.components;

import client.Drawable;
import client.utilities.Pallette;
import client.utilities.Settings;
import client.utilities.ThreadPool;
import client.utilities.Utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Circular 60 second clock with only the seconds hand.
 *
 * @author Alston and Katelyn
 * last updated 1/19/2018
 */
public class Clock implements Drawable {

    private long clockDuration = 60000;

    private final int centerX;
    private final int centerY;
    private int radius;

    private int armX;
    private int armY;
    private int armLength;

    private BufferedImage clockSprite;
    private BasicStroke armOutline = new BasicStroke(Utils.scale(3));
    private BasicStroke clockOutline = new BasicStroke(Utils.scale(4));

    private AtomicBoolean timeIsOn = new AtomicBoolean(true);

    /**
     * Constructs a Clock.
     *
     * @param centerX the x-coordinate of the clock's center
     * @param centerY the y-coordinate of the clock's center
     * @param radius  the radius of the clock
     */
    public Clock(int centerX, int centerY, int radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.armLength = radius - Utils.scale(5);
        this.armY = centerY - armLength;
    }

    /**
     * Constructs a Clock.
     *
     * @param centerX the x-coordinate of the clock's center
     * @param centerY the y-coordinate of the clock's center
     * @param radius  the radius of the clock
     */
    public Clock(int centerX, int centerY, int duration, int radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.clockDuration = duration;
        this.armLength = radius - Utils.scale(5);
        this.armY = centerY - armLength;
    }

    /**
     * Starts the clock on a new thread.
     */
    public void start() {
        timeIsOn.set(true);
        ThreadPool.execute(this::animate);
    }

    /**
     * Stops the clock.
     */
    public void stop() {
        timeIsOn.set(false);
    }

    /**
     * Continuously updates the seconds hand of this clock.
     */
    private void animate() {
        long startTime = System.currentTimeMillis();
        while (timeIsOn.get()) {
            double theta = 2.0 * Math.PI * (System.currentTimeMillis() - startTime) / clockDuration;
            armX = centerX + Utils.round(armLength * Math.sin(theta));
            armY = centerY - Utils.round(armLength * Math.cos(theta));
        }
    }


    //Drawable methods ------------------------------------------------------------------------------------

    public void configureSprites() {

        //pre-load the backing of this clock
        int side = (int) Math.ceil((radius + clockOutline.getLineWidth()) * 2);
        clockSprite = new BufferedImage(side, side, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2D = (Graphics2D) clockSprite.getGraphics();
        g2D.setRenderingHints(Settings.QUALITY_RENDER_SETTINGS);
        Ellipse2D shape = new Ellipse2D.Float( //the stroke thickness must be accounted for in the ellipse
                clockOutline.getLineWidth(),
                clockOutline.getLineWidth(),
                side - 2 * clockOutline.getLineWidth(),
                side - 2 * clockOutline.getLineWidth());
        g2D.setColor(Color.WHITE);
        g2D.fill(shape);
        g2D.setColor(Pallette.OUTLINE_COLOR);
        g2D.setStroke(clockOutline);
        g2D.draw(shape);
        g2D.dispose();
    }

    public void draw(Graphics2D g2D) {
        g2D.drawImage(clockSprite, centerX - clockSprite.getWidth() / 2,
                centerY - clockSprite.getHeight() / 2, null);

        //draw the arm
        g2D.setRenderingHints(Settings.QUALITY_RENDER_SETTINGS);
        g2D.setColor(Pallette.OUTLINE_COLOR);
        g2D.setStroke(armOutline);
        g2D.drawLine(centerX, centerY, armX, armY);
        g2D.setRenderingHints(Settings.DEFAULT_RENDER_SETTINGS);
    }
}
