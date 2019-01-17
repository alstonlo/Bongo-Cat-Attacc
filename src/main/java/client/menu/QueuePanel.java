package client.menu;

import client.Window;
import client.components.Clock;
import client.utilities.Utils;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class QueuePanel extends DropDownPanel{
    private BufferedImage settingDrape = Utils.loadScaledImage("resources/menu/controls drape.png");

    private Clock clock;

    QueuePanel(Window window){
        super(window);

        clock = new Clock(Utils.scale(375), Utils.scale(500),60,80);

        setVisible(true);

    }

    @Override
    void pullDown() {
        clock.start();
        super.pullDown();
    }

    @Override
    void retract() {
        super.retract();
        clock.stop();
    }


    /**
     * {@inheritDoc}
     *
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(settingDrape, 0, 0, null);

        clock.draw(g2D);

        g2D.setFont(Utils.loadFont("moon.otf",30));
        FontMetrics fontMetrics = g2D.getFontMetrics();
        g2D.drawString("Loading...", Utils.scale(375)-fontMetrics.stringWidth("Loading")/2, Utils.scale(690));
    }
}
