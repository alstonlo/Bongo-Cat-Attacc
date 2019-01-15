package server;

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

            }
        }
    }

    public void stop() {
        running = false;
    }
}
