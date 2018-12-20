package protocol;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

/**
 * A protocol message of the return type. Such a protocol passes
 * or returns a value to its recipient.
 *
 * @author Alston
 * last updated 12/20/2018
 */
public class ReturnProtocol extends Protocol {

    /**
     * Constructs a blank ReturnProtocol.
     */
    public ReturnProtocol() {
        super(Convention.RETURN_TYPE);
        addField(Convention.RESPONSE_FIELD);
    }

    /**
     * Sets the ID of the protocol message that this message is responding to.
     * The response field is initialized with as null and can be accessed by passing
     * {@link Convention#RESPONSE_FIELD} as the key.
     *
     * @param id the ID of the message that this message is responding to
     */
    public void setMethodRespone(String id) {
        addField(Convention.RESPONSE_FIELD, id);
    }



    //Below are the overloaded methods of setReturnValue(String name, value)

    /**
     * Adds a value to be returned by the protocol. If the value was
     * previously added, it is overridden with the new value.
     *
     * @param name  the name of the returned value
     * @param value the String representation of the value of the returned value
     */
    public void setReturnValue(String name, boolean value) {
        addField(name, value);
    }

    /**
     * Adds a value to be returned by the protocol. If the value was
     * previously added, it is overridden with the new value.
     *
     * @param name  the name of the returned value
     * @param value the String representation of the value of the returned value
     */
    public void setReturnValue(String name, double value) {
        addField(name, value);
    }

    /**
     * Adds a value to be returned by the protocol. If the value was
     * previously added, it is overridden with the new value.
     *
     * @param name  the name of the returned value
     * @param value the String representation of the value of the returned value
     */
    public void setReturnValue(String name, int value) {
        addField(name, value);
    }

    /**
     * Adds a value to be returned by the protocol. If the value was
     * previously added, it is overridden with the new value.
     *
     * @param name  the name of the returned value
     * @param value the String representation of the value of the returned value
     */
    public void setReturnValue(String name, JsonArrayBuilder value) {
        addField(name, value);
    }

    /**
     * Adds a value to be returned by the protocol. If the value was
     * previously added, it is overridden with the new value.
     *
     * @param name  the name of the returned value
     * @param value the String representation of the value of the returned value
     */
    public void setReturnValue(String name, JsonObjectBuilder value) {
        addField(name, value);
    }

    /**
     * Adds a value to be returned by the protocol. If the value was
     * previously added, it is overridden with the new value.
     *
     * @param name  the name of the returned value
     * @param value the String representation of the value of the returned value
     */
    public void setReturnValue(String name, long value) {
        addField(name, value);
    }

    /**
     * Adds a value to be returned by the protocol. If the value was
     * previously added, it is overridden with the new value.
     *
     * @param name  the name of the returned value
     * @param value the String representation of the value of the returned value
     */
    public void setReturnValue(String name, String value) {
        addField(name, value);
    }

    /**
     * Adds a null value to be returned by the protocol. If the value was
     * previously added, it is overridden with the new value.
     *
     * @param name  the name of the returned value
     */
    public void setReturnValue(String name) {
        addField(name);
    }
}