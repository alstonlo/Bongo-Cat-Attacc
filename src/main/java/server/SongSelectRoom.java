package server;

class SongSelectRoom implements Runnable{

    private Player host;
    private Player guest;

    SongSelectRoom(Player host, Player guest) {
        this.host = host;
        this.guest = guest;
    }

    @Override
    public void run() {

    }

}
