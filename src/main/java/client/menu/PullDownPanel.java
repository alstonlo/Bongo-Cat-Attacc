package client.menu;

import client.Window;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Graphics;
import java.util.concurrent.atomic.AtomicInteger;

abstract class PullDownPanel extends JPanel {

    static int ANIMATION_STATE = -1;
    static int DOWN_STATE = 0;
    static int UP_STATE = 1;

    Window window;

    private final long SLIDE_DURATION = 500;

    private int y;
    private AtomicInteger state = new AtomicInteger(UP_STATE);
    private Runnable pullDownAnimation = new PullDown();
    private Runnable pullUpAnimation = new PullUp();

    PullDownPanel(Window window) {
        this.window = window;
        this.y = -window.scale(1334);
        this.setLocation(0, y);
        this.setSize(window.scale(750), window.scale(1334));
        this.setOpaque(false);
        this.setVisible(true);
    }

    void relocate() {
        if (this.getLocation().getY() != y) {
            this.setLocation(0, y);
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
                y = window.scale(1334 * (deltaTime / SLIDE_DURATION - 1));
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
                y = window.scale(1334.0 * (-deltaTime / SLIDE_DURATION));
                deltaTime = System.currentTimeMillis() - startTime;
            }
            y = -window.scale(1334);
            state.set(UP_STATE);
        }
    }
}

