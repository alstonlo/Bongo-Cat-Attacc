package protocol;

import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

/**
 * Abstract representation of a protocol message between the client and server.
 * In general, protocol messages are formatted as a collection of paired
 * attributes and values.
 *
 * @author Alston
 * last updated 12/19/2018
 */
abstract class Protocol {

    private JsonObject protocol = new JsonObject();

    /**
     * Constructs a blank Protocol object of a specified type. Builtin protocol types
     * are identified in {@link Convention}'s static fields.
     *
     * @param type the type of the message
     */
    Protocol(String type) {
        addField(Convention.TYPE_FIELD, type);
        addField(Convention.ID_FIELD);
    }

    /**
     * Returns a JsonObject that represents the protocol message.
     * The keys of the Json store the attributes' names, while the values store
     * the attributes' values.
     *
     * @return a JsonObject representing the protocol message
     */
    public JsonObject getFields() {
        return protocol;
    }

    /**
     * Attaches an ID value to the protocol message. The id field is initialized as null
     * and can be accessed through passing {@link Convention#ID_FIELD} as the key.
     *
     * @param id the ID the protocol message is given
     */
    public void setID(String id) {
        addField(Convention.ID_FIELD, id);
    }



    //Below are the overloaded methods of addField(String name, value)

    /**
     * Adds an attribute or field to the protocol message. If the attribute
     * was previously added, it is overridden with the new value.
     *
     * @param name  the name of the attribute to be added
     * @param value the value of the attribute to be added
     */
    void addField(String name, Boolean value) {
        protocol.addProperty(name, value);
    }

    /**
     * Adds an attribute or field to the protocol message. If the attribute
     * was previously added, it is overridden with the new value.
     *
     * @param name  the name of the attribute to be added
     * @param value the value of the attribute to be added
     */
    void addField(String name, Number value) {
        protocol.addProperty(name, value);
    }

    /**
     * Adds an attribute or field to the protocol message. If the attribute
     * was previously added, it is overridden with the new value.
     *
     * @param name  the name of the attribute to be added
     * @param value the value of the attribute to be added
     */
    void addField(String name, String value) {
        protocol.addProperty(name, value);
    }

    /**
     * Adds an attribute or field to the protocol message with a null value. If the attribute
     * was previously added, it is overridden with the new value.
     *
     * @param name  the name of the attribute to be added
     */
    void addField(String name) {
        protocol.add(name, JsonNull.INSTANCE);
    }

    /**
     * Adds an attribute or field to the protocol message. If the attribute
     * was previously added, it is overridden with the new value.
     *
     * @param name  the name of the attribute to be added
     * @param value the value of the attribute to be added
     */
    void addField(String name, JsonObject value) {
        protocol.add(name, value);
    }

    /**
     * Adds an attribute or field to the protocol message. If the attribute
     * was previously added, it is overridden with the new value.
     *
     * @param name  the name of the attribute to be added
     * @param value the value of the attribute to be added
     */
    void addField(String name, JsonArray value) {
        protocol.add(name, value);
    }
}
