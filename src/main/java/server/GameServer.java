package server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;
import com.esotericsoftware.kryonet.Server;
import protocol.AuthenticateProtocol;
import protocol.Network;
import protocol.RegisterProtocol;

import java.io.IOException;

public class GameServer {

    public static void main(String[] args) {
        //new GameServer();
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
                register(player, (RegisterProtocol)protocol);

            } else if (protocol instanceof AuthenticateProtocol) {
                authenticate(player, (AuthenticateProtocol)protocol);
            }

        }

        private void register(Player player, RegisterProtocol protocol) {

            //boolean registered = database.register(protocol.username, protocol.password);


        }

    private void authenticate(Player player, AuthenticateProtocol protocol) {
        if (player.loggedIn()) {
            return;
        }

        //boolean authentucated = database.authenticate(protocol.username, protocol.password);

    }
}
