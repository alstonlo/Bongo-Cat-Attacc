package protocol;

/**
 * Message to notify clients that they have been matched with another player to start their game.
 *
 * @author Katelyn Wang
 * Last Updated: 01/19/2019
 */
public class MatchMadeMessage extends Message {
    public final String username1;
    public final String username2;

    /**
     * Constructs an MatchMadeMessage
     *
     * @param username1 player one's user name
     * @param username2 player two's user name
     */
    public MatchMadeMessage(String username1, String username2){
        this.username1 = username1;
        this.username2 = username2;
    }

    /**
     * Empty constructor necessary for Kyro serialization
     */
    private MatchMadeMessage(){
        this.username1 = null;
        this.username2 = null;
    }
}
