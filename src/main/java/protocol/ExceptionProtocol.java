package protocol;

/**
 * Protocol to notify the opposing side of a {@link exceptions.GameException} that their
 * message caused.
 *
 * @author Alston
 * last updated 12/26/2018
 * @see exceptions.GameException
 */
public class ExceptionProtocol extends Protocol {

    public final String response;
    public final int errorState;

    /**
     * Constructs an ExceptionProtocol.
     *
     * @param response   the {@link Protocol} that this is in response to
     * @param errorState the state of the GameException, as detailed in its JavaDocs
     * @see exceptions.GameException
     */
    public ExceptionProtocol(Protocol response, int errorState) {
        this.response = response.id;
        this.errorState = errorState;
    }

    /**
     * Empty constructor necessary for Kyro serialization
     */
    private ExceptionProtocol() {
        response = null;
        errorState = -1;
    }

}
