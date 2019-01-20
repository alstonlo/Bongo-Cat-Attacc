package protocol;

/**
 * Message for authenticating a player. The player attempts to log in
 * with a specified username and password.
 *
 * @author Alston
 * last updated 12/25/2018
 */
public class AuthenticateMessage extends Message {

    public final String username;
    public final String password;

    /**
     * Constructs an AuthenticateMessage.
     *
     * @param username the username that the player is attempting to log in with
     * @param password the password that the player is attempting to log in with
     */
    public AuthenticateMessage(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Empty constructor necessary for Kyro serialization
     */
    private AuthenticateMessage() {
        username = null;
        password = null;
    }
}
