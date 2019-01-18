package client.menu;

import client.Window;
import client.utilities.Settings;
import client.utilities.Utils;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.lang.reflect.InvocationTargetException;
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

    private double y; //preferred y position of the panel
    private AtomicInteger state = new AtomicInteger(UP_STATE);

    /**
     * Constructs a DropDownPanel initialized in {@link DropDownPanel#UP_STATE}.
     *
     * @param window the window the panel belongs to
     */
    DropDownPanel(Window window) {
        this.window = window;
        this.setSize(Settings.PANEL_SIZE);
        this.y = -getHeight();
        relocate();
        this.setOpaque(false);
        this.setVisible(true);
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

            SwingUtilities.invokeLater(() -> requestFocus()); //shift focus to the panel
            try {
                SwingUtilities.invokeAndWait(() -> setVisible(true));
            } catch (InterruptedException | InvocationTargetException e) {
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
            SwingUtilities.invokeLater(() -> window.requestFocus()); //return the focus to the window

            long startTime = System.currentTimeMillis();
            double deltaTime = System.currentTimeMillis() - startTime;
            while (deltaTime < SLIDE_DURATION) {
                y = getHeight() * (-deltaTime / SLIDE_DURATION);
                deltaTime = System.currentTimeMillis() - startTime;
            }
            y = -getHeight();

            state.set(UP_STATE);

            SwingUtilities.invokeLater(() -> setVisible(false));
        }
    }
}

