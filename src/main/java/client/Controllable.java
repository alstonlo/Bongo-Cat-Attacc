package client;

import protocol.Protocol;

/**
 * Abstract representation of an object that is controlled by
 * the game's inputs
 *
 * @author Alston
 * last updated 1/19/2019
 */
public interface Controllable {

    /**
     * Notify to this object that the left bongo key has been pressed.
     */
    void notifyLeftPress();

    /**
     * Notify to this object that the left bongo key has been released.
     */
    void notifyLeftRelease();

    /**
     * Notify to this object that the right bongo key has been pressed.
     */
    void notifyRightPress();

    /**
     * Notify to this object that the right bongo key has been released.
     */
    void notifyRightRelease();

    /**
     * Notify to this object that both the hold bongo key has been triggered.
     */
    void notifyHold();
}
