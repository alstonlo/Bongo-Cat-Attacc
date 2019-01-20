package client;

import client.utilities.ThreadPool;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import protocol.Message;

/**
 * An implementation of {@link Listener} from the KyroNet library.
 *
 * @author Alston
 * last updated 1/9/2019
 * @see Listener
 */
public class ServerListener extends Listener {

    private Messagable obj;

    /**
     * Sets the object that is messaged by this listener.
     * Initially, this object is set to null and only one object
     * can be messaged at a time.
     *
     * @param obj the object to be messaged by this listener
     */
    void setControllableObj(Messagable obj) {
        this.obj = obj;
    }


    @Override
    public void received(Connection connection, Object o) {
        if (obj != null && o instanceof Message) { //only notify if the Object is actually a Message
            ThreadPool.execute(() -> obj.notifyReceived((Message) o));
        }
    }
}
