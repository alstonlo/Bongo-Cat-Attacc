package client;

import client.utilities.ThreadPool;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import protocol.Protocol;

/**
 * An implementation of {@link Listener} from the KyroNet library.
 *
 * @author Alston
 * last updated 1/9/2019
 * @see Listener
 */
public class ServerListener extends Listener {

    private Controllable obj;

    /**
     * Sets the object that is controlled by this listener.
     * Initially, this object is set to null and only one object
     * can be controlled at a time.
     *
     * @param obj the object to be controlled by this listener
     */
    void setControllableObj(Controllable obj) {
        this.obj = obj;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void connected(Connection connection) {
    }

    /**
     * {@inheritDoc}
     *
     * @param connection
     * @param o
     */
    @Override
    public void received(Connection connection, Object o) {
        if (obj != null && o instanceof Protocol) { //only notify if the Object is actually a Protocol
            ThreadPool.execute(() -> obj.notifyReceived((Protocol) o));
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param connection
     */
    @Override
    public void disconnected(Connection connection) {
    }

}
