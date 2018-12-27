package exceptions;

/**
 * A list of error codes and their meaning
 * 0 - unknown error
 * 1 - somebody tried to register an account with a duplicate username
 * 2 - somebody tried to register an account with an invalid username
 * 3 -
 */

/**
 *
 */
public class GameException extends Exception {

    private int state;

    public GameException(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }
}
