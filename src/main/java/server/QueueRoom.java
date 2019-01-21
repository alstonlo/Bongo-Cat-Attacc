package server;

import protocol.MatchMadeMessage;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

class QueueRoom implements Runnable{

    private volatile boolean running = true;
    private Queue<Player> matchMaking = new ConcurrentLinkedQueue<>();

    void queue(Player player) {
        matchMaking.add(player);
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            if (matchMaking.size() >= 2) {
                Player host = matchMaking.poll();
                Player guest = matchMaking.poll();

                new Thread(new SongSelectRoom(host, guest)).start();

                MatchMadeMessage matchMade = new MatchMadeMessage(host.getUsername(), guest.getUsername());
                host.sendTCP(matchMade);
                guest.sendTCP(matchMade);
            }
        }
    }

    public void stop() {
        running = false;
    }
}
