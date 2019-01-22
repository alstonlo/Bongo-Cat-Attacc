package server;

import protocol.MatchMadeMessage;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Match making room, where players are continuously queued, popped off, and paired.
 *
 * @author Alston
 * last udpated 1/22/2019
 */
class QueueRoom implements Runnable {

    private volatile boolean running = true;
    private Queue<Player> matchMaking = new ConcurrentLinkedQueue<>();

    /**
     * Adds a player to the queue.
     *
     * @param player the player to be added
     */
    void queue(Player player) {
        matchMaking.add(player);
    }

    @Override
    public void run() {
        running = true;

        //continuously pop off characters
        while (running) {
            if (matchMaking.size() >= 2) {

                //randomly assign host and guest to the order that they are added to the queue
                Player host = matchMaking.poll();
                Player guest = matchMaking.poll();

                //start the song selection
                new Thread(new SongSelectRoom(host, guest)).start();

                //notify the users that a match has been made
                MatchMadeMessage matchMade = new MatchMadeMessage(host.getUsername(), guest.getUsername());
                host.sendTCP(matchMade);
                guest.sendTCP(matchMade);
            }
        }
    }

    /**
     * Stops this QueueRoom.
     */
    public void stop() {
        running = false;
    }
}
