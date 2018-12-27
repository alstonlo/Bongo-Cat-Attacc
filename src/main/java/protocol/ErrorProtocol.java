package protocol;

/**
 * Protocol for sending an error message in response to some protocol
 * from a client or server.
 *
 * @author Alston
 * last updated 12/26/2018
 */
public class ErrorProtocol extends Protocol{

    public final String response;
    public final int errorCode;

    /**
     * Constructs a ErrorProtocol.
     *
     * @param response the ID of the protocol this is in response to
     * @param errorCode the error code of the error message that this is sending
     */
    public ErrorProtocol(String response, int errorCode) {
        this.response = response;
        this.errorCode = errorCode;
    }

}
