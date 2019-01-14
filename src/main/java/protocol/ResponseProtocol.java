package protocol;

/**
 * Protocol to notify the opposing side that their message was received and processed
 * without an error.
 *
 * @author Alston
 * last updated 12/27/2018
 */
public class ResponseProtocol extends Protocol {

    public final String response;

    /**
     * Constructs a ResponseProtocol.
     *
     * @param response the {@link Protocol} that this is in response to
     */
    public ResponseProtocol(Protocol response) {
        this.response = response.id;
    }

    /**
     * Empty constructor necessary for Kyro serialization
     */
    private ResponseProtocol() {
        response = null;
    }
}
