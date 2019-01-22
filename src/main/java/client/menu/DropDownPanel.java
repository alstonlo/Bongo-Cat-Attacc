package client.menu;

import client.GamePanel;
import client.Messagable;
import client.Window;
import client.utilities.Settings;
import client.utilities.Utils;
import protocol.Message;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Graphics;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A panel that can be animated into and out of visibility
 * through a drop down animation.
 *
 * @author Alston
 * last updated 1/19/2018
 */
abstract class DropDownPanel extends GamePanel {

    //the states the panel can be in
    static final int ANIMATION_STATE = -1; //is currently in a drop down animation
    static final int DOWN_STATE = 0;       //is fully down and visible
    static final int UP_STATE = 1;         //is up and not visible

    //how long the drop down animation should be in ms.
    private static final long SLIDE_DURATION = 500;

    private static final Integer DROP_DOWN_LAYER = 2;

    private double y; //preferred y position of the panel
    private AtomicInteger state = new AtomicInteger(UP_STATE);

    /**
     * Constructs a DropDownPanel initialized in {@link DropDownPanel#UP_STATE}.
     *
     * @param window the window the panel belongs to
     */
    DropDownPanel(Window window) {
        super(window);
        this.setSize(Settings.PANEL_SIZE);
        this.y = -getHeight();
        relocate();
        this.setOpaque(true);
        this.setVisible(true);
    }

    int getState() {
        return state.get();
    }

    /**
     * Relocates this panel if it has moved from the previous call
     * of relocate() due to {@link DropDownPanel#pullDown()} or
     * {@link DropDownPanel#retract()}.
     */
    void relocate() {
        if (this.getLocation().getY() != Utils.round(y)) {
            this.setLocation(0, Utils.round(y));
        }
    }

    /**
     * Starts the pull or drop down animation of this panel on a new Thread.
     * pullDown() will only work if the panel is currently in {@link DropDownPanel#UP_STATE}.
     */
    void pullDown() {
        if (state.compareAndSet(UP_STATE, ANIMATION_STATE)) {

            try {
                SwingUtilities.invokeAndWait(() -> {
                    requestFocus();
                    window.addPanel(DROP_DOWN_LAYER, this);
                });
            } catch (InvocationTargetException | InterruptedException e) {
                e.printStackTrace();
            }

            long startTime = System.currentTimeMillis();
            double deltaTime = System.currentTimeMillis() - startTime;
            while (deltaTime < SLIDE_DURATION) {
                y = getHeight() * (deltaTime / SLIDE_DURATION - 1);
                deltaTime = System.currentTimeMillis() - startTime;
            }
            y = 0;

            state.set(DOWN_STATE);
        }
    }

    /**
     * Starts the animation that retracts the panel back to its initial state on a new Thread.
     * retract() will only work if the panel is currently in {@link DropDownPanel#DOWN_STATE}.
     */
    void retract() {
        if (state.compareAndSet(DOWN_STATE, ANIMATION_STATE)) {
            long startTime = System.currentTimeMillis();
            double deltaTime = System.currentTimeMillis() - startTime;
            while (deltaTime < SLIDE_DURATION) {
                y = getHeight() * (-deltaTime / SLIDE_DURATION);
                deltaTime = System.currentTimeMillis() - startTime;
            }
            y = -getHeight();

            state.set(UP_STATE);

            try {
                SwingUtilities.invokeAndWait(() -> {
                    window.requestFocus();
                    window.removePanel(this);
                });
            } catch (InvocationTargetException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        relocate();
    }
}

