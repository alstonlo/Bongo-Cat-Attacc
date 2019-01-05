package client.ui;

/**
 * Abstract representation of an object that is controlled by
 * the game's inputs.
 *
 * @author Alston
 * last updated 1/4/2019
 */
public interface Controllable {

    /**
     * Notify to this object that the left arrow key has been pressed.
     */
    void notifyLeftPress();

    /**
     * Notify to this object that the left arrow key has been released.
     */
    void notifyLeftRelease();

    /**
     * Notify to this object that the right arrow key has been pressed.
     */
    void notifyRightPress();

    /**
     * Notify to this object that the right arrow key has been released.
     */
    void notifyRightRelease();

    /**
     * Notify to this object that both the left and right arrow key have
     * been held or pressed for {@link BongoListener#HOLD_DURATION} milliseconds.
     */
    void notifyHold();

}
