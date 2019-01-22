package server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import protocol.RequestSongMessage;

import java.util.Timer;
import java.util.TimerTask;

class SongSelectRoom implements Runnable {

    private Player host;

    private Player guest;
    private HostListener hostListener = new HostListener();

    private Timer timer = new Timer();

    SongSelectRoom(Player host, Player guest) {
        this.host = host;
        this.guest = guest;
        host.addListener(hostListener);
    }

    @Override
    public void run() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                host.removeListener(hostListener);
            }
        }, 60000);
    }

    private class HostListener extends Listener {

        @Override
        public void received(Connection connection, Object o) {
            if (o instanceof RequestSongMessage) {
                guest.sendTCP(o);
            }
        }
    }
}
