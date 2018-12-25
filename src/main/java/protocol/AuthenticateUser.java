package protocol;

/**
 * Protocol for authenticating a player. The player attempts to log in
 * with a specified username and password.
 *
 * @author Alston
 * last updated 12/25/2018
 */
public class AuthenticateUser extends Protocol {

    public final String username;
    public final String password;

    /**
     * Generates an AuthenticationUser protocol by setting the {@link AuthenticateUser#username}
     * and {@link AuthenticateUser#password} to the username and password arguments respectively.
     *
     * @param username the username that the player is attempting to log in with
     * @param password the password that the player is attempting to log in with
     */
    public AuthenticateUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
