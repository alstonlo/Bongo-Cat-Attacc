package server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * A player connection to the game server. The Player
 * class handles I/O from its socket connection.
 *
 * @author Alston
 * last updated 12/22/2018
 */
class Player implements Runnable {

    private String username;
    private Listener listener;
    private volatile boolean running = true;

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    /**
     * Constructs a Player on a specified socket. While doing so, it attempts to access
     * the socket's input and output stream.
     *
     * @param socket the socket that is the player's connection to the server socket
     * @throws IOException thrown if accessing the socket's input and output stream throws an IOException
     */
    Player(Socket socket) throws IOException {
        try {
            this.socket = socket;
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new PrintWriter(socket.getOutputStream());

        } catch (IOException e) { //getInputStream() and getOutputStream() threw an IOException
            close(); //close anything that is open
            throw new IOException(); //rethrow the IOException
        }
    }

    /**
     * Runs the player's session. During the session, input is continually read and parsed
     * from the player. run() will continue until stop() is called, upon which all opened resources are closed.
     * After stop() is called, run() will do nothing. In short, run() can only be called once.
     */
    @Override
    public void run() {
        while (running) {
            try {
                if (reader.ready()) {
                    JsonObject json = ServerUtils.deserialize(reader.readLine()); //deserialize line
                    Message message = new Message(this, json);

                    if (listener != null) { //queue onto listener if it has been set
                        listener.queue(message);
                    }
                }

            } catch (JsonParseException | IllegalStateException e) { //json cannot be deserialized
                System.out.println("Message improperly formatted");

            } catch (IOException e) { //IOException thrown when trying to access BufferedReader
                System.out.println("Failed to receive message.");
            }
        }

        close(); //close all resources
    }

    /**
     * Stops the run() method if it currently in execution. If stop() was previously called
     * and run() was already stopped, then stop() will do nothing.
     */
    void stop() {
        running = false;
    }

    /**
     * Sets the listener to which the player queues its Messages to.
     *
     * @param l the new listener of the player
     */
    void setListener(Listener l) {
        this.listener = l;
    }

    /**
     * Returns the registered username of the player. If the player has not registered a username,
     * then null is returned.
     *
     * @return the registered username of the player
     */
    String getUsername() {
        return username;
    }

    /**
     * Registers and sets the username of the player. If the player has already registered a username,
     * then nothing happens.
     *
     * @param username the username the player is to be registered under
     */
    void setUsername(String username) {
        if (this.username == null) { //this.username == null if no username has been registered
            this.username = username;
        }
    }

    /**
     * Sends the message argument to the player's socket's output stream. The message
     * is first serialized and then sent.
     *
     * @param message the message to be sent
     */
    void sendMessage(JsonObject message) {
        writer.write(message.toString());
        writer.flush();
    }

    /**
     * Attempts to close any opened resources, including the player's
     * socket, reader, and writer.
     */
    private void close() {
        ServerUtils.close(reader);
        ServerUtils.close(writer);
        ServerUtils.close(socket);
    }
}
