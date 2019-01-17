package server;

import com.esotericsoftware.kryonet.Connection;

/**
 * A connection to the GameServer - a player.
 *
 * @author Alston
 * last updated 1/9/2018
 */
class Player extends Connection {

    private String username;

    /**
     * Constructs a Player who has not logged in (unregistered).
     */
    Player() {
        super();
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
    synchronized void registerUsername(String username) {
        if (!loggedIn()) {
            this.username = username;
        }
    }
}
