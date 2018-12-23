package server;

/**
 * Representation of a class that can listen to a Player's messages.
 * Players notify listeners by queueing their messages to it.
 *
 * @author Alston
 * last updated 12/22/2018
 */
public interface Listener {

    /**
     * Queues a message to the listener, notifying it.
     *
     * @param message the message to be queued.
     */
    void queue(Message message);

}
