package server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;
import com.esotericsoftware.kryonet.Server;
import exceptions.GameException;
import protocol.AuthenticateProtocol;
import protocol.ExceptionProtocol;
import protocol.JoinQueueProtocol;
import protocol.Network;
import protocol.RegisterProtocol;
import protocol.ResponseProtocol;

import java.io.IOException;

/**
 * Main server/room where players connect, log in, and join games.
 *
 * @author Alston
 * last updated 1/9/2018
 */
class GameServer {

    public static void main(String[] args) {
        new GameServer();
    }

    private Server server;
    private QueueRoom matchMakingRoom = new QueueRoom();
    private Database database = Database.getInstance();

    /**
     * Constructs a GameServer.
     */
    GameServer() {
        try {
            server = new Server() {

                /*
                 * By overriding this method, whenever a client connects, a new
                 * Player is created instead of a new Connection
                 */
                @Override
                protected Connection newConnection() {
                    return new Player();
                }

            };

            Network.register(server); //registers all objects that will be sent to/from server

            //ThreadedListener executes each method on a separate thread
            server.addListener(new ThreadedListener(new Listener() {

                @Override
                public void connected(Connection connection) {
                    System.out.println("Client connected.");
                }

                /*
                 * Listens to when a Connection sends this GameServer an Object o.
                 * Since newConnection() was overridden above, we can be sure that
                 * the Connection is actually a Player and cast it accordingly.
                 */
                @Override
                public void received(Connection connection, Object o) {
                    Player player = (Player) connection;
                    process(player, o);
                }

                @Override
                public void disconnected(Connection connection) {
                    System.out.println("Client disconnected.");
                }

            }));

            server.bind(Network.PORT); //bind to TCP port 5000

            server.start();
            matchMakingRoom.run();

        } catch (IOException e) { //server fails to be started...
            e.printStackTrace();
            close();
            System.exit(1); //everything is closed and the program is killed
        }
    }

    /**
     * Closes this GameServer.
     */
    void close() {
        server.close();
        database.close();
    }

    /**
     * Resolves and executes some protocol sent by a player.
     * If the protocol argument is improperly formatted, then nothing happens.
     *
     * @param player   the player that sends the protocol
     * @param protocol the protocol
     */
    private void process(Player player, Object protocol) {

        //the class of the protocol argument is inspected,
        //casted accordingly and then directed to some method
        if (protocol instanceof JoinQueueProtocol) {
            process(player, (JoinQueueProtocol) protocol);

        } else if (protocol instanceof RegisterProtocol) {
            process(player, (RegisterProtocol) protocol);

        } else if (protocol instanceof AuthenticateProtocol) {
            process(player, (AuthenticateProtocol) protocol);

        }
    }

    /*
     * Below are the overloaded methods of process(Player, Object)
     * in the format of process(Player, E extends Object). Doing so just
     * makes it cleaner.
     */

    private void process(Player player, JoinQueueProtocol protocol) {
        if (!player.loggedIn()) { //ensure player is logged in in order to join queue room
            player.sendTCP(new ExceptionProtocol(protocol, GameException.NOT_LOGGED_IN_STATE));
        }

        matchMakingRoom.queue(player);
        player.sendTCP(new ResponseProtocol(protocol));
    }

    private void process(Player player, RegisterProtocol protocol) {
        try {
            database.register(protocol.username, protocol.password); //attempt to register account
        } catch (GameException e) {
            player.sendTCP(new ExceptionProtocol(protocol, e.getState()));
            return;
        }

        player.sendTCP(new ResponseProtocol(protocol)); //no error was thrown, so send ResponseProtocol
    }

    private void process(Player player, AuthenticateProtocol protocol) {
        if (player.loggedIn()) {
            player.sendTCP(new ExceptionProtocol(protocol, GameException.DOUBLE_LOGIN_STATE));
            return;
        }

        boolean authenticated = database.authenticate(protocol.username, protocol.password);

        if (!authenticated) { //authentication successful, so send ResponseProtocol
            player.registerUsername(protocol.username);
            player.sendTCP(new ResponseProtocol(protocol));
        } else {
            player.sendTCP(new ExceptionProtocol(protocol, GameException.INVALID_LOGIN_STATE));
        }
    }
}
