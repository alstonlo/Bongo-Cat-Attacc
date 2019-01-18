package client.components;

import client.utilities.Pallette;
import client.utilities.Settings;
import client.utilities.ThreadPool;
import client.utilities.Utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.concurrent.atomic.AtomicBoolean;

public class Clock {

    private final long CLOCK_DURATION = 60000;

    private final int x;
    private final int y;

    private int armX;
    private int armY;

    private int radius;
    private int armLength;

    private AtomicBoolean timeIsOn = new AtomicBoolean(true);

    public Clock(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.armLength = radius - Utils.scale(5);
        this.armY = y - radius;
    }

    public void start() {
        timeIsOn.set(true);
        ThreadPool.execute(this::update);
    }

    public void stop() {
        timeIsOn.set(false);
    }

    public void draw(Graphics2D g2D) {
        g2D.setRenderingHints(Settings.QUALITY_RENDER_SETTINGS);
        g2D.setColor(new Color(255, 255, 255));
        g2D.fillOval(x - radius, y - radius, radius * 2, radius * 2);
        g2D.setColor(Pallette.OUTLINE_COLOR);
        g2D.setStroke(new BasicStroke(Utils.scale(4)));
        g2D.drawOval(x - radius, y - radius, radius * 2, radius * 2);
        g2D.setStroke(new BasicStroke(Utils.scale(3)));
        g2D.drawLine(x, y, armX, armY);
    }

    private void update() {
        long startTime = System.currentTimeMillis();
        while (timeIsOn.get()) {
            double theta = 2.0 * Math.PI * (System.currentTimeMillis() - startTime) / CLOCK_DURATION;
            armX = x + Utils.round(armLength * Math.sin(theta));
            armY = y - Utils.round(armLength * Math.cos(theta));
        }
    }
}
