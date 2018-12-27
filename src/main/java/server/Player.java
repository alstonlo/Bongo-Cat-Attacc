package server;

import com.esotericsoftware.kryonet.Connection;

/**
 * A connection to the GameServer, or player.
 *
 * @author Alston
 * last updated 12/27/2018
 */
class Player extends Connection {

    private String username;

    /**
     * Constructs a Player who has not logged in (unregistered).
     * {@link Connection#setKeepAliveTCP(int)} is disabled unless the player logs in.
     * This way, unless the player logs in within 12000ms of connecting, they
     * are automatically disconnected.
     */
    Player() {
        super();
        setKeepAliveTCP(0); //disable setKeepAliveTCP(int) by setting it to 0
    }

    /**
     * @return true, if this player has logged in
     */
    boolean loggedIn() {
        return username != null;
    }

    /**
     * @return the username this player had logged in under; or null if this player hasn't logged in
     */
    String getUsername() {
        return username;
    }

    /**
     * Registers or logs in a player under the username argument. If the player had already
     * logged in, then this does nothing.
     *
     * @param username the new username of the player
     */
    void registerUsername(String username) {
        if (!loggedIn()) {
            this.username = username;
            setKeepAliveTCP(8000);
        }
    }
}
