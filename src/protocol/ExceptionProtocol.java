package protocol;

/**
 * A protocol message of the exception type. Such a protocol passes
 * an exception to its recipient.
 *
 * @author Alston
 * last updated 12/20/2018
 */
public class ExceptionProtocol extends Protocol {

    /**
     * Constructs a blank ExceptionProtocol.
     */
    public ExceptionProtocol() {
        super(Convention.EXCEPTION_TYPE);
        addField(Convention.RESPONSE_FIELD);
        addField(Convention.ERRORCODE_FIELD);
    }

    /**
     * Sets the ID of the protocol message that this message is responding to.
     * The response field is initialized as null and can be accessed by passing
     * {@link Convention#RESPONSE_FIELD} as the key.
     *
     * @param id the ID of the message that this message is responding to
     */
    public void setMethodRespone(String id) {
        addField("response", id);
    }

    /**
     * Sets the error code of the protocol message, which gives information on the type
     * of error thrown. The error code field is initialized as null and can be accessed by
     * passing {@link Convention#ERRORCODE_FIELD} as the key. Various error codes can also
     * be found in the static fields of {@link Convention}.
     *
     * @param code a String code that represents the type of exception that is detailed in the message
     */
    public void setErrorCode(String code) {
        addField(Convention.ERRORCODE_FIELD, code);
    }

}
