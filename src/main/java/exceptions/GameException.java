package exceptions;

/**
 * A custom {@link Exception} used in this game. Contains many states, each denoting an
 * exception of a different type and reason. The states are listed as static fields.
 *
 * @author Alston
 * last updated 12/26/2018
 */
public class GameException extends Exception {

    public final static int DATABASE_ERROR_STATE = 1; //database access error
    public final static int INVALID_REGISTER_STATE = 2; //player attempted to register with an invalid username or password
    public final static int DOUBLE_LOGIN_STATE = 3; //player who is already logged in tried to log into a another account (without logging out first)
    public final static int INVALID_LOGIN_STATE = 4; //player tried to log in with an incorrect username or password
    public final static int NOT_LOGGED_IN_STATE = 5; //player wasn't logged in

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
