package client;

import protocol.Protocol;

/**
 * Abstract representation of an object that is controlled by
 * the game's inputs and the server's messages.
 *
 * @author Alston
 * last updated 1/9/2019
 */
public interface Controllable {

    //KEYLISTENER NOTIFY METHODS -------------------------------------------------
    //notifies this object of inputs from the key listener

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
     * Notify to this object that both the left and right bongo keys have
     * been held or pressed for {@link BongoListener#HOLD_DURATION} milliseconds.
     */
    void notifyHold();

    //SERVERLISTENER NOTIFY METHOD ---------------------------------------------------
    //notifies this object of connections and messages from the server

    /**
     * Notify to this object that the client connected to the server.
     */
    void notifyConnected();

    /**
     * Notify to this object that a message was received from the server.
     *
     * @param protocol the protocol that was received
     */
    void notifyReceived(Protocol protocol);

    /**
     * Notify to this object that the client disconnected from the server.
     */
    void notifyDisconnected();
}
