package client;

import protocol.Protocol;

/**
 * Abstract representation of an object that is controlled by
 * the server's inputs
 *
 * @author Alston
 * last updated 1/9/2019
 */
public interface Messagable {

    /**
     * Notify to this object that a message was received from the server.
     *
     * @param protocol the protocol that was received
     */
    void notifyReceived(Protocol protocol);

}
