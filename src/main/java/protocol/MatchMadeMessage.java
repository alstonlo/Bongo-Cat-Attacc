package protocol;


/**
 * Message indicating a match has been made between two players.
 *
 * @author Alston
 * last updated 12/26/2018
 */
public class MatchMadeMessage extends Message {

    public final String host;
    public final String guest;

    /**
     * Constructs a MatchMadeMessage.
     *
     * @param host  the username of the player hosting the game
     * @param guest the username of the player that is not hosting the game
     */
    public MatchMadeMessage(String host, String guest) {
        this.host = host;
        this.guest = guest;
    }

    /**
     * Empty constructor necessary for Kyro serialization
     */
    private MatchMadeMessage() {
        this.host = null;
        this.guest = null;
    }
}
