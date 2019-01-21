package protocol;


public class MatchMadeMessage extends Message {

    public final String host;
    public final String guest;

    public MatchMadeMessage(String host, String guest){
        this.host = host;
        this.guest = guest;
    }

    /**
     * Empty constructor necessary for Kyro serialization
     */
    private MatchMadeMessage(){
        this.host = null;
        this.guest = null;
    }
}
