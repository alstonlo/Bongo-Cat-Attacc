package server;

public class ServerUtils {

    public static void close(AutoCloseable resource) {
        try {
            if (resource != null) { //check that the resource exists
                resource.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
