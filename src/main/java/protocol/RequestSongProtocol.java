package protocol;

public class RequestSongProtocol extends Protocol{
    String songRequest;

    public RequestSongProtocol(String songRequest){
        this.songRequest = songRequest;
    }

    private RequestSongProtocol(){
        this.songRequest = null;
    }
}
