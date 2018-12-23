package server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

/**
 * Helper class for the server package. Contains convenience methods that
 * prevent the redundant instantiations of various resources (e.g. JsonParser).
 *
 * @author Alston
 * last updated 12/21/2018
 */
class ServerUtils {

    private static final JsonParser parser = new JsonParser();

    /**
     * Parses and deserializes a String representation of a JsonObject.
     *
     * @param json Json text
     * @return the JsonObject represented by the json argument
     * @throws JsonParseException    thrown if the json argument is not valid Json
     * @throws IllegalStateException thrown if the json argument is not a serialization of a JsonObject
     */
    static JsonObject deserialize(String json) {
        return parser.parse(json).getAsJsonObject();
    }

    /**
     * Attempts to close the resource, if it is not null. If the attempt
     * fails, the error stack is printed to console.
     *
     * @param resource the resource to be closed
     */
    static void close(AutoCloseable resource) {
        try {
            if (resource != null) { //check that the resource exists
                resource.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
