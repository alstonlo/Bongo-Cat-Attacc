package protocol;

public class TimeOverProtocol extends Protocol {
    public String songSelected;

    public TimeOverProtocol(String songSelected){
        this.songSelected = songSelected;
    }

    private TimeOverProtocol(){
        songSelected = null;
    }
}
