package server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;
import com.esotericsoftware.kryonet.Server;
import exceptions.GameException;
import protocol.AuthenticateProtocol;
import protocol.ErrorProtocol;
import protocol.JoinQueueProtocol;
import protocol.Network;
import protocol.RegisterProtocol;
import protocol.ResponseProtocol;

import java.io.IOException;

public class GameServer {

    public static void main(String[] args) {
        new GameServer();
    }


    private Server server;
    private Database database = Database.getInstance();

    private GameServer() {
        try {

            server = new Server() {

                @Override
                protected Connection newConnection() {
                    return new Player();
                }

            };

            Network.register(server);

            server.addListener(new ThreadedListener(new Listener() {

                @Override
                public void received(Connection connection, Object o) {
                    Player player = (Player) connection;
                    process(player, o);
                }

            }));

            server.bind(Network.PORT, Network.PORT);
            server.start();

        } catch (IOException e) {
            e.printStackTrace();
            server.close();
            System.exit(1);
        }
    }


    private void process(Player player, Object protocol) {
        if (protocol instanceof RegisterProtocol) {
            register(player, (RegisterProtocol) protocol);

        } else if (protocol instanceof AuthenticateProtocol) {
            authenticate(player, (AuthenticateProtocol) protocol);

        } else if (protocol instanceof JoinQueueProtocol) {
            joinQueue(player, (JoinQueueProtocol) protocol);

        }
    }

    private void register(Player player, RegisterProtocol protocol) {
        try {
            database.register(protocol.username, protocol.password);
            player.sendTCP(new ResponseProtocol(protocol.id))

        } catch (GameException e) {
            ErrorProtocol response = new ErrorProtocol(protocol.id, e.getState());
            player.sendTCP(response);
        }
    }

    private void authenticate(Player player, AuthenticateProtocol protocol) {
        if (player.loggedIn()) {
            player.sendTCP(new ErrorProtocol(protocol.id, 3));
            return;
        }

        boolean authenticated = database.authenticate(protocol.username, protocol.password);
        if (authenticated) {
            player.registerUsername(protocol.username);
            player.sendTCP(new ResponseProtocol(protocol.id));

        } else {
            player.sendTCP(new ErrorProtocol(protocol.id, 4));
        }
    }

    private void joinQueue(Player player, JoinQueueProtocol protocol) {

    }
}
