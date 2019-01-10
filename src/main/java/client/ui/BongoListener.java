package client.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A modified KeyListener that only listens to the left and right bongo keys.
 * This is analogous to the left and right bongos.
 *
 * @author Alston
 * last updated 1/9/2019
 */
public class BongoListener implements KeyListener {

    static final int LEFT_BONGO_KEY = KeyEvent.VK_A;
    static final int RIGHT_BONGO_KEY = KeyEvent.VK_L;
    static final int HOLD_BONGO_KEY = KeyEvent.VK_ENTER;

    private Controllable obj = null;
    private final AtomicBoolean leftPress = new AtomicBoolean(false);
    private final AtomicBoolean rightPress = new AtomicBoolean(false);
    private final AtomicBoolean holdPress = new AtomicBoolean(false);

    /**
     * Constructs a BongoListener.
     */
    BongoListener() {
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
     * Does nothing.
     *
     * @param e the key pressed
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Invoked when the left, right, or hold bongo key are pressed, which
     * notifies the controlled object via {@link Controllable#notifyLeftPress()},
     * {@link Controllable#notifyRightPress()}, and {@link Controllable#notifyHold()}
     * respectively. keyPressed will only trigger once, when the key is first pressed.
     *
     * @param e the key pressed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == LEFT_BONGO_KEY) { //detect left bongo key press
            if (leftPress.compareAndSet(false, true) && obj != null) {
                obj.notifyLeftPress();
            }
        }

        if (e.getKeyCode() == RIGHT_BONGO_KEY) { //detect right bongo press
            if (rightPress.compareAndSet(false, true) && obj != null) {
                obj.notifyRightPress();
            }
        }

        if (e.getKeyCode() == HOLD_BONGO_KEY) {
            if (holdPress.compareAndSet(false, true) && obj != null) {
                obj.notifyHold();
            }
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
            leftPress.set(false);
            if (obj != null) {
                obj.notifyLeftRelease();
            }
        }

        if (e.getKeyCode() == RIGHT_BONGO_KEY) { //detect right bongo release
            rightPress.set(false);
            if (obj != null) {
                obj.notifyRightRelease();
            }
        }

        if (e.getKeyCode() == HOLD_BONGO_KEY) {
            holdPress.set(false);
        }
    }
}
