package client;

import client.utilities.ThreadPool;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import protocol.Message;

import java.util.HashSet;
import java.util.Set;

/**
 * An implementation of {@link Listener} from the KyroNet library.
 *
 * @author Alston
 * last updated 1/9/2019
 * @see Listener
 */
public class ServerListener extends Listener {

    private Set<Messagable> objects = new HashSet<>();

    /**
     * Adds an object that is messaged by this listener. Each
     * object will be notified on a separate thread.
     *
     * @param obj the object to be messaged by this listener
     */
    void addControllableObj(Messagable obj) {
        objects.add(obj);
    }

    /**
     * Removes an object that is messaged by this listener.
     *
     * @param obj the object to be messaged by this listener
     */
    void removeControllableObj(Messagable obj) {
        objects.remove(obj);
    }

    @Override
    public void received(Connection connection, Object o) {
        if (o instanceof Message) { //only notify if the Object is actually a Message

            //notify all objects
            objects.forEach(obj -> ThreadPool.execute(() -> obj.notifyReceived((Message) o)));
        }
    }
}
