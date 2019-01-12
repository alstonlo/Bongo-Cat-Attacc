package client.menu;

import client.Utils;
import client.Window;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Graphics;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A panel that can be animated into and out of visibility
 * through a drop down animation.
 *
 * @author Alston
 * last updated 1/10/2018
 */
abstract class DropDownPanel extends JPanel {

    //the states the panel can be in
    static int ANIMATION_STATE = -1; //is currently in a drop down animation
    static int DOWN_STATE = 0;       //is fully down and visible
    static int UP_STATE = 1;         //is up and not visible

    //how long the drop down animation should be in ms.
    private static final long SLIDE_DURATION = 500;

    Window window;

    private double y;
    private AtomicInteger state = new AtomicInteger(UP_STATE);
    private Runnable pullDownAnimation = new PullDown();
    private Runnable pullUpAnimation = new PullUp();

    /**
     * Constructs a DropDownPanel.
     *
     * @param window the window the panel belongs to
     */
    DropDownPanel(Window window) {
        this.window = window;
        this.y = -window.scaledHeight;
        relocate();
        this.setSize(window.scaledWidth, window.scaledHeight);
        this.setOpaque(false);
        this.setVisible(true);
    }

    void relocate() {
        if (this.getLocation().getY() != Utils.round(y)) {
            this.setLocation(0, Utils.round(y));
        }
    }

    void pullDown() {
        if (state.compareAndSet(UP_STATE, ANIMATION_STATE)) {
            SwingUtilities.invokeLater(() -> requestFocus());
            new Thread(pullDownAnimation).start();
        }
    }

    void pullUp() {
        if (state.compareAndSet(DOWN_STATE, ANIMATION_STATE)) {
            SwingUtilities.invokeLater(() -> window.requestFocus());
            new Thread(pullUpAnimation).start();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.fillRect(0, 0, 750, 1334);
    }

    private class PullDown implements Runnable {
        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            double deltaTime = System.currentTimeMillis() - startTime;
            while (deltaTime < SLIDE_DURATION) {
                y = window.scaledHeight * (deltaTime / SLIDE_DURATION - 1);
                deltaTime = System.currentTimeMillis() - startTime;
            }
            y = 0;
            state.set(DOWN_STATE);
        }
    }

    private class PullUp implements Runnable {
        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            double deltaTime = System.currentTimeMillis() - startTime;
            while (deltaTime < SLIDE_DURATION) {
                y = window.scaledHeight * (-deltaTime / SLIDE_DURATION);
                deltaTime = System.currentTimeMillis() - startTime;
            }
            y = -window.scaledHeight;
            state.set(UP_STATE);
        }
    }
}

