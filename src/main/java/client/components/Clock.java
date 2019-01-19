package client.components;

import client.Drawable;
import client.utilities.Pallette;
import client.utilities.Settings;
import client.utilities.ThreadPool;
import client.utilities.Utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicBoolean;

public class Clock implements Drawable {

    private final long CLOCK_DURATION = 60000;

    private final int centerX;
    private final int centerY;
    private int radius;

    private int armX;
    private int armY;
    private int armLength;

    private BufferedImage sprite;
    private BasicStroke armOutline = new BasicStroke(Utils.scale(3));
    private BasicStroke clockOutline = new BasicStroke(Utils.scale(4));

    private AtomicBoolean timeIsOn = new AtomicBoolean(true);

    public Clock(int centerX, int centerY, int radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.armLength = radius - Utils.scale(5);
        this.armY = centerY - radius;
    }

    public void start() {
        timeIsOn.set(true);
        ThreadPool.execute(this::animate);
    }

    public void stop() {
        timeIsOn.set(false);
    }

    private void animate() {
        long startTime = System.currentTimeMillis();
        while (timeIsOn.get()) {
            double theta = 2.0 * Math.PI * (System.currentTimeMillis() - startTime) / CLOCK_DURATION;
            armX = centerX + Utils.round(armLength * Math.sin(theta));
            armY = centerY - Utils.round(armLength * Math.cos(theta));
        }
    }

    public void configureSprites() {

        int side = (int)Math.ceil((radius + clockOutline.getLineWidth()) * 2);
        sprite = new BufferedImage(side, side, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2D = (Graphics2D)sprite.getGraphics();
        g2D.setRenderingHints(Settings.QUALITY_RENDER_SETTINGS);
        g2D.setColor(new Color(255, 255, 255));
        g2D.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
        g2D.setColor(Pallette.OUTLINE_COLOR);
        g2D.setStroke(new BasicStroke(Utils.scale(4)));
        g2D.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
        g2D.dispose();
    }

    public void draw(Graphics2D g2D) {
        if (sprite == null) {
            return;
        }

        g2D.drawImage(sprite, centerX - radius, centerY - radius, null);
        g2D.setStroke(armOutline);
        g2D.drawLine(centerX, centerY, armX, armY);
    }
}
