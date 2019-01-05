package client.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * A modified KeyListener that only listens to the left and right arrow keys.
 * This is analogous to the left and right bongos.
 *
 * @author Alston
 * last updated 1/4/2019
 */
public class BongoListener implements KeyListener {

    private Controllable obj = null;

    /**
     * Sets the object that is controlled by this listener.
     * Initially, this object is set to null and only one object
     * can be controlled at a time.
     *
     * @param obj the object to be controlled by this listener
     */
    public void setControlledObj(Controllable obj) {
        this.obj = obj;
    }

    /**
     * Does nothing.
     *
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Invoked when the left or right arrow key are pressed, which
     * notifies the controlled object via {@link Controllable#notifyLeftPress()}
     * and {@link Controllable#notifyRightPress()}.
     *
     * @param e the key pressed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) { //detect left arrow press
            if (obj != null) {
                obj.notifyLeftPress();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) { //detect right arrow press
            if (obj != null) {
                obj.notifyRightPress();
            }
        }
    }


    /**
     * Invoked when the left or right arrow key are released, which
     * notifies the controlled object via {@link Controllable#notifyLeftRelease()}
     * and {@link Controllable#notifyRightRelease()}.
     *
     * @param e the key released
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) { //detect left arrow release
            if (obj != null) {
                obj.notifyLeftRelease();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) { //detect right arrow release
            if (obj != null) {
                obj.notifyRightRelease();
            }
        }
    }
}
