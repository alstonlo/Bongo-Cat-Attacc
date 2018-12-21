package protocol;

/**
 * Helper class to store all the conventions used in the protocol in
 * the form of public static fields.
 *
 * @author Alston
 * last updated 12/20/2018
 */
public class Convention {

    //Convention for protocol types
    public static final String METHOD_CALL_TYPE = "methodcall";
    public static final String RETURN_TYPE = "return";
    public static final String EXCEPTION_TYPE = "exception";

    //Convention for protocol fields
    public static final String TYPE_FIELD = "type";
    public static final String ID_FIELD = "id";
    public static final String METHOD_FIELD = "method";
    public static final String RESPONSE_FIELD = "response";
    public static final String ERRORCODE_FIELD = "errorcode";

}
