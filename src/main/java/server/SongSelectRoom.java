package server;

import com.esotericsoftware.kryonet.Listener;

import java.util.Timer;
import java.util.TimerTask;

class SongSelectRoom implements Runnable {

    private Player host;
    private HostListener hostListener = new HostListener();

    private Player guest;
    private GuestListener guestListener = new GuestListener();

    private Timer timer = new Timer();

    SongSelectRoom(Player host, Player guest) {
        this.host = host;
        host.addListener(hostListener);

        this.guest = guest;
        guest.addListener(guestListener);
    }

    @Override
    public void run() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                host.removeListener(hostListener);
                guest.removeListener(guestListener);
            }
        }, 60000);
    }

    private class HostListener extends Listener {

    }

    private class GuestListener extends Listener {

    }
}
