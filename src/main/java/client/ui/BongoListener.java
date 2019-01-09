package client.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
     * Invoked when the hold bongo key is typed.
     *
     * @param e the key pressed
     */
    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyCode() == HOLD_BONGO_KEY) {
            if (obj != null) {
                obj.notifyHold();
            }
        }
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
        }

        if (e.getKeyCode() == RIGHT_BONGO_KEY) { //detect right bongo press
            if (obj != null) {
                obj.notifyRightPress();
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
            if (obj != null) {
                obj.notifyLeftRelease();
            }
        }

        if (e.getKeyCode() == RIGHT_BONGO_KEY) { //detect right bongo release
            if (obj != null) {
                obj.notifyRightRelease();
            }
        }
    }
}
