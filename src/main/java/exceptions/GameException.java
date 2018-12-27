package exceptions;

/**
 * A custom {@link Exception} used in this game. Contains many states, each denoting an
 * exception of a different type and reason. The states and their meanings are as follows.
 * <p>
 * State:
 * 1 - database access error
 * 2 - player attempted to register with an invalid username or password
 * 3 - player who is already logged in tried to log into a another account (without logging out first)
 * 4 - player tried to log in with an incorrect username and password
 *
 * @author Alston
 * last updated 12/26/2018
 */
public class GameException extends Exception {

    private int state;

    /**
     * Constructs a GameException with a specified state
     *
     * @param state an integer denoting the type of GameException (as listed above)
     */
    public GameException(int state) {
        this.state = state;
    }

    /**
     * @return the state of this GameException
     */
    public int getState() {
        return state;
    }
}
