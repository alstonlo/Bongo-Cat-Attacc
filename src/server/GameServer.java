package server;

/**
 * The main connection point for players. In the GameServer, players can create new accounts,
 * log into an existing account, or start a game by joining the match making room.
 *
 * @author Alston
 * last updated 12/23/2018
 */
public class GameServer implements Runnable, Listener {

    public static void main(String[] args) {
        GameServer server = new GameServer(5000);
        server.run();
    }

    private Database database = Database.getInstance();
    private QueueingRoom matchMakingRoom = new QueueingRoom();

    private GameServer(int port) {

    }

    @Override
    public void run() {

    }

    public void stop() {

    }

    @Override
    public void queue(Message message) {

    }
}
