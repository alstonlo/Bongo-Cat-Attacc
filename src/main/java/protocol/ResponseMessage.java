package protocol;

/**
 * Message to notify the opposing side that their message was received and processed
 * without an error.
 *
 * @author Alston
 * last updated 12/27/2018
 */
public class ResponseMessage extends Message {

    public final String response;

    /**
     * Constructs a ResponseMessage.
     *
     * @param response the {@link Message} that this is in response to
     */
    public ResponseMessage(Message response) {
        this.response = response.id;
    }

    /**
     * Empty constructor necessary for Kyro serialization
     */
    private ResponseMessage() {
        response = null;
    }
}
