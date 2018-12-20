package protocol;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

/**
 * A protocol message of the method call type. Such a protocol requests
 * its recipient to execute some sort of method.
 *
 * @author Alston
 * last updated 12/19/2018
 */
public class MethodCallProtocol extends Protocol {

    /**
     * Constructs a blank MethodCallProtocol that requests the execution
     * of a specified method.
     *
     * @param method the name of the method to be executed
     */
    public MethodCallProtocol(String method) {
        super(Convention.METHOD_CALL_TYPE);
        addField("method", method);
    }


    //Below are the overloaded methods of setParameter(String name, value)

    /**
     * Adds a parameter to the protocol message. If the parameter was
     * previously added, it is overridden with the new value.
     *
     * @param name  the name of the parameter to be added
     * @param value the String representation of the value of the parameter to be added
     */
    public void setParameter(String name, boolean value) {
        addField(name, value);
    }

    /**
     * Adds a parameter to the protocol message. If the parameter was
     * previously added, it is overridden with the new value.
     *
     * @param name  the name of the parameter to be added
     * @param value the String representation of the value of the parameter to be added
     */
    public void setParameter(String name, double value) {
        addField(name, value);
    }

    /**
     * Adds a parameter to the protocol message. If the parameter was
     * previously added, it is overridden with the new value.
     *
     * @param name  the name of the parameter to be added
     * @param value the String representation of the value of the parameter to be added
     */
    public void setParameter(String name, int value) {
        addField(name, value);
    }

    /**
     * Adds a parameter to the protocol message. If the parameter was
     * previously added, it is overridden with the new value.
     *
     * @param name  the name of the parameter to be added
     * @param value the String representation of the value of the parameter to be added
     */
    public void setParameter(String name, JsonArrayBuilder value) {
        addField(name, value);
    }

    /**
     * Adds a parameter to the protocol message. If the parameter was
     * previously added, it is overridden with the new value.
     *
     * @param name  the name of the parameter to be added
     * @param value the String representation of the value of the parameter to be added
     */
    public void setParameter(String name, JsonObjectBuilder value) {
        addField(name, value);
    }

    /**
     * Adds a parameter to the protocol message. If the parameter was
     * previously added, it is overridden with the new value.
     *
     * @param name  the name of the parameter to be added
     * @param value the String representation of the value of the parameter to be added
     */
    public void setParameter(String name, long value) {
        addField(name, value);
    }

    /**
     * Adds a parameter to the protocol message. If the parameter was
     * previously added, it is overridden with the new value.
     *
     * @param name  the name of the parameter to be added
     * @param value the String representation of the value of the parameter to be added
     */
    public void setParameter(String name, String value) {
        addField(name, value);
    }

    /**
     * Adds a null parameter to the protocol message. If the parameter was
     * previously added, it is overridden with the new value.
     *
     * @param name the name of the parameter to be added
     */
    public void setParameter(String name) {
        addField(name);
    }
}
