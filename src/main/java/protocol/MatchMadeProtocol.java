package protocol;

/**
 * Protocol to notify clients that they have been matched with another player to start their game.
 *
 * @author Katelyn Wang
 * Last Updated: 01/19/2019
 */
public class MatchMadeProtocol extends Protocol {
    public final String username1;
    public final String username2;

    /**
     * Constructs an MatchMadeProtocol
     *
     * @param username1 player one's user name
     * @param username2 player two's user name
     */
    public MatchMadeProtocol (String username1, String username2){
        this.username1 = username1;
        this.username2 = username2;
    }

    /**
     * Empty constructor necessary for Kyro serialization
     */
    private MatchMadeProtocol(){
        this.username1 = null;
        this.username2 = null;
    }
}
