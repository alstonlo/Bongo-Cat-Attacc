package server;

import com.esotericsoftware.kryonet.Connection;

class Player extends Connection {

    private String username;

    Player () {
        super();
        setKeepAliveTCP(0);
    }

    boolean loggedIn() {
        return username != null;
    }

    String getUsername() {
        return username;
    }

    void registerUsername(String username) {
        if (!loggedIn()) {
            this.username = username;
            setKeepAliveTCP(8000);
        }
    }
}
