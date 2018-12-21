package server;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

class Player implements Runnable{

    private String username;
    private Listener listener;
    private volatile boolean running = true;

    private Socket socket;
    private InputStream input;
    private OutputStream output;

    Player(Socket socket) throws IOException {
        try {
            this.socket = socket;
            this.input = socket.getInputStream();
            this.output = socket.getOutputStream();

        } catch (IOException e) {
            close();
            throw new IOException();
        }
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

    private void close() {
        ServerUtils.close(socket);
    }
}
