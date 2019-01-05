package client.ui;

import client.utilities.Utils;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Panel for the main menu.
 *
 * @author Katelyn Wang and Alston
 * last updated 2019/1/4
 */
class MenuPanel extends GamePanel {

    private Window window;
    private BongoCat cat;

    private BufferedImage title;
    private BufferedImage background;

    /**
     * Constructs a MenuPanel.
     *
     * @param window the Window that this MenuPanel is displayed on
     */
    MenuPanel(Window window) {
        super();
        this.window = window;
        this.cat = new BongoCat();
        this.title = Utils.loadImage("resources/menu/title.png");
        this.background = Utils.loadImage("resources/menu/yellow.png");
    }


    @Override
    public void notifyLeftPress() {
        cat.leftPawDown();
        repaint(); //repaint panel to ensure that the state change is animated (even if it violates fps)
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyLeftRelease() {
        cat.leftPawUp();
        repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyRightPress() {
        cat.rightPawDown();
        repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyRightRelease() {
        cat.rightPawUp();
        repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyHold() {
        repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        g2D.scale(window.getScale(), window.getScale()); //we set the scaling

        g2D.drawImage(background, 0, 0, this);
        g2D.drawImage(cat.getImage(), 0, 0, this);
        g2D.drawImage(title, 0, 0, this);
    }
}

