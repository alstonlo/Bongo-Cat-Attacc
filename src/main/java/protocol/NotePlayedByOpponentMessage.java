package protocol;

public class NotePlayedByOpponentMessage extends Message {
    public final int side;

    /**
     * Constructs a NotePlayedByOpponentMessage
     *
     * @param side indicate which bongo was played (0 = left, 1 = right)
     */
    NotePlayedByOpponentMessage(int side){
        this.side = side;
    }

    /**
     * Empty constructor necessary for Kyro serialization
     */
    private NotePlayedByOpponentMessage(){
        side = -1;
    }
}
