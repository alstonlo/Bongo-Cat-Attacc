package server;

import java.net.Socket;

class Player implements Runnable{

    private String username;

    Player(String username, Socket socket) {

    }

    @Override
    public void run() {

    }

    void stop() {

    }

    void setListener(Listener l) {

    }

    String getUsername() {
        return username;
    }

    void setUsername(String username) {
        this.username = username;
    }

    void sendMessage(Message message) {

    }
}
