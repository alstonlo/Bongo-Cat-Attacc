package server;

import com.esotericsoftware.kryonet.Server;
import protocol.Network;

import java.io.IOException;

public class GameServer {

    public static void main(String[] args) {
    }

    private Server server;

    private GameServer() {
        try {
            server = new Server();
            server.start();
            server.bind(Network.PORT, Network.PORT);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
