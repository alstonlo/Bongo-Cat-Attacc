package protocol;

/**
 * Message to notify the opposing side of a {@link exceptions.GameException} that their
 * message caused.
 *
 * @author Alston
 * last updated 12/26/2018
 * @see exceptions.GameException
 */
public class ExceptionMessage extends Message {

    public final String response;
    public final int errorState;

    /**
     * Constructs an ExceptionMessage.
     *
     * @param response   the {@link Message} that this is in response to
     * @param errorState the state of the GameException, as detailed in its JavaDocs
     * @see exceptions.GameException
     */
    public ExceptionMessage(Message response, int errorState) {
        this.response = response.id;
        this.errorState = errorState;
    }

    /**
     * Empty constructor necessary for Kyro serialization
     */
    private ExceptionMessage() {
        response = null;
        errorState = -1;
    }

}
