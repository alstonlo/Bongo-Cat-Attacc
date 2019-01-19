package protocol;

/**
 *
 */
public class PlayNoteProtocol extends Protocol {
    public final int side; // 0 is left, 1 is right

    /**
     * Constructs a PlayNoteProtocol
     *
     * @param side indicates which bongo was played - 0 = left, 1 = right
     */
    public PlayNoteProtocol(int side){
        this.side = side;
    }

    /**
     * Empty constructor necessary for Kyro serialization
     */
    private PlayNoteProtocol(){
        this.side = -1;
    }
}
