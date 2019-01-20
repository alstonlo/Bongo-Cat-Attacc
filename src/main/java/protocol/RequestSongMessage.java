package protocol;

/**
 * Message for the client to request a song if they are the one choosing
 */
public class RequestSongMessage extends Message {
    public final String songRequest;

    /**
     * Constructs a RequestSongMessage message
     *
     * @param songRequest the name of the song requested
     */
    public RequestSongMessage(String songRequest){
        this.songRequest = songRequest;
    }

    /**
     * Empty constructor necessary for Kyro serialization
     */
    private RequestSongMessage(){
        this.songRequest = null;
    }
}
