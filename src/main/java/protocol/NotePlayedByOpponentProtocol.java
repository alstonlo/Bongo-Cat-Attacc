package protocol;

public class NotePlayedByOpponentProtocol extends Protocol {
    public final int side;

    /**
     * Constructs a NotePlayedByOpponentProtocol
     *
     * @param side indicate which bongo was played (0 = left, 1 = right)
     */
    NotePlayedByOpponentProtocol(int side){
        this.side = side;
    }

    /**
     * Empty constructor necessary for Kyro serialization
     */
    private NotePlayedByOpponentProtocol(){
        side = -1;
    }
}
