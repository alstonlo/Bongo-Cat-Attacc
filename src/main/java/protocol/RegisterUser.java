package protocol;

/**
 * Protocol for registering a new player or account. The player
 * attempts to register a new account under a specified username and password.
 *
 * @author Alston
 * last updated 12/25/2018
 */
public class RegisterUser extends Protocol {

    public final String username;
    public final String password;

    /**
     * Generates an RegisterUser protocol by setting the {@link RegisterUser#username}
     * and {@link RegisterUser#password} to the username and password arguments respectively.
     *
     * @param username the username that the player is attempting to log in with
     * @param password the password that the player is attempting to log in with
     */
    public RegisterUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
