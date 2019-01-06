package client.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * A modified KeyListener that only listens to the left and right bongo keys.
 * This is analogous to the left and right bongos.
 *
 * @author Alston
 * last updated 1/4/2019
 */
public class BongoListener implements KeyListener {

    static final int LEFT_BONGO_KEY = KeyEvent.VK_A;
    static final int RIGHT_BONGO_KEY = KeyEvent.VK_L;
    static final long HOLD_DURATION = 1500;

    private Controllable obj = null;
    private HoldListener holdListener;

    BongoListener() {
        this.holdListener = new HoldListener();
        new Thread(holdListener).start();
    }

    /**
     * Sets the object that is controlled by this listener.
     * Initially, this object is set to null and only one object
     * can be controlled at a time.
     *
     * @param obj the object to be controlled by this listener
     */
    void setControlledObj(Controllable obj) {
        this.obj = obj;
    }

    /**
     * Stops the internal thread used by this listener, which is used
     * to detect if the two bongo keys are held for {@link BongoListener#HOLD_DURATION} seconds.
     */
    void stop() {
        holdListener.running = false;
    }

    /**
     * Does nothing.
     *
     * @param e the key pressed
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Invoked when the left or right bongo key are pressed, which
     * notifies the controlled object via {@link Controllable#notifyLeftPress()}
     * and {@link Controllable#notifyRightPress()}.
     *
     * @param e the key pressed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == LEFT_BONGO_KEY) { //detect left bongo key press
            if (obj != null) {
                obj.notifyLeftPress();
            }
            holdListener.left = true;
        }

        if (e.getKeyCode() == RIGHT_BONGO_KEY) { //detect right bongo press
            if (obj != null) {
                obj.notifyRightPress();
            }
            holdListener.right = true;
        }
    }


    /**
     * Invoked when the left or right bongo key are released, which
     * notifies the controlled object via {@link Controllable#notifyLeftRelease()}
     * and {@link Controllable#notifyRightRelease()}.
     *
     * @param e the key released
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == LEFT_BONGO_KEY) { //detect left bongo release
            if (obj != null) {
                obj.notifyLeftRelease();
            }
            holdListener.left = false;
        }

        if (e.getKeyCode() == RIGHT_BONGO_KEY) { //detect right bongo release
            if (obj != null) {
                obj.notifyRightRelease();
            }
            holdListener.right = false;
        }
    }

    /**
     * Runnable object that observes whether the left and right bongo key
     * were hold for longer than {@link BongoListener#HOLD_DURATION} ms.
     */
    private class HoldListener implements Runnable {

        private volatile boolean running = false;

        private long holdStart;
        private boolean left = false;
        private boolean right = false;

        /**
         * Starts the listener.
         */
        @Override
        public void run() {
            running = true;
            holdStart = System.currentTimeMillis();

            while (running) {
                if (!(left && right)) {
                    holdStart = System.currentTimeMillis();
                }

                if (System.currentTimeMillis() - holdStart > HOLD_DURATION && obj != null) {
                    obj.notifyHold();
                    holdStart = System.currentTimeMillis(); //reset time
                }
            }
        }
    }
}
