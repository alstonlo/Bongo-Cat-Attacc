package protocol;

/**
 * Message for registering a new player or account. The player
 * attempts to register a new account under a specified username and password.
 *
 * @author Alston
 * last updated 12/25/2018
 */
public class RegisterMessage extends Message {

    public final String username;
    public final String password;

    /**
     * Constructs an RegisterMessage.
     *
     * @param username the username that the player is attempting to log in with
     * @param password the password that the player is attempting to log in with
     */
    public RegisterMessage(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Empty constructor necessary for Kyro serialization
     */
    private RegisterMessage() {
        this.username = null;
        this.password = null;
    }
}
