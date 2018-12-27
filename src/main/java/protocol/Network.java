package protocol;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

/**
 * Helper class that contains information and methods that are shared between the client
 * and server.
 *
 * @author Alston
 * last updated 12/24/2018
 */
public class Network {

    /**
     * The port the server and client should connect to
     */
    public static final int PORT = 5000;

    /*
     * Hard-coded list of classes within the protocol package (excluding this one).
     * Although less dynamic than reflection, I did this to avoid downloading a reflection
     * library and using up more space.
     */
    private static Class[] protocolClasses = {
            Protocol.class, ErrorProtocol.class, ResponseProtocol.class,
            AuthenticateProtocol.class, RegisterProtocol.class,
            JoinQueueProtocol.class
    };

    /**
     * Registers the classes that will be transmitted across the network. These classes
     * are all the classes in the protocol package (excluding this one).
     *
     * @param endPoint the server or client that expects to receive the registered classes
     */
    public static void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        for (Class protocolClass : protocolClasses) {
            kryo.register(protocolClass);
        }
    }
}
