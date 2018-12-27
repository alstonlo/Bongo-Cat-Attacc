package exceptions;

/**
 *
 */
public class GameException extends Exception {

    /**
     * List of states
     *
     * 1 - database error
     * 2 - attempt to register a user with invalid username or password (duplicate, null, too long)
     * 3 - already logged in player tries to log into a another account (they need to log off first)
     * 4 - player logs in with incorrect combination of username and password
     *
     */
    private int state;

    public GameException(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }
}
