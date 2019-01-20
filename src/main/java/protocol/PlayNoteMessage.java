package protocol;

/**
 *
 */
public class PlayNoteMessage extends Message {
    public final int side; // 0 is left, 1 is right

    /**
     * Constructs a PlayNoteMessage
     *
     * @param side indicates which bongo was played - 0 = left, 1 = right
     */
    public PlayNoteMessage(int side){
        this.side = side;
    }

    /**
     * Empty constructor necessary for Kyro serialization
     */
    private PlayNoteMessage(){
        this.side = -1;
    }
}
