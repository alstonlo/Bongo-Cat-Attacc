package server;

import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import java.io.PrintWriter;
import java.net.Socket;

class Player implements Runnable{

    private String username;
    private Listener listener;

    private Socket socket;
    private JsonReader reader;
    private JsonWriter writer;

    Player(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

    }

    void stop() {

    }

    void setListener(Listener l) {
        this.listener = l;
    }

    String getUsername() {
        return username;
    }

    void setUsername(String username) {
        this.username = username;
    }

    void sendMessage(JsonObject message) {

    }
}
