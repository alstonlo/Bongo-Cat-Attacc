package protocol;

public class TimeOverMessage extends Message {
    public String songSelected;

    public TimeOverMessage(String songSelected){
        this.songSelected = songSelected;
    }

    private TimeOverMessage(){
        songSelected = null;
    }
}
