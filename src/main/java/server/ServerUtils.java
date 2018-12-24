package server;

/**
 * Helper class for the server package. Contains convenience methods that
 * prevent the redundant instantiations of various resources.
 *
 * @author Alston
 * last updated 12/21/2018
 */
class ServerUtils {

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
