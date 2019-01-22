package server;

import java.util.Timer;

class SongSelectRoom implements Runnable {

    private Player host;
    private Player guest;

    private Timer timer = new Timer();

    SongSelectRoom(Player host, Player guest) {
        this.host = host;
        this.guest = guest;
    }

    @Override
    public void run() {
    }

}
