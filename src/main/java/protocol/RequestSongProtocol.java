package protocol;

/**
 * Protocol for the client to request a song if they are the one choosing
 */
public class RequestSongProtocol extends Protocol{
    public final String songRequest;

    /**
     * Constructs a RequestSongProtocol message
     *
     * @param songRequest the name of the song requested
     */
    public RequestSongProtocol(String songRequest){
        this.songRequest = songRequest;
    }

    /**
     * Empty constructor necessary for Kyro serialization
     */
    private RequestSongProtocol(){
        this.songRequest = null;
    }
}
