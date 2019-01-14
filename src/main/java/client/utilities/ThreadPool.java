package client.utilities;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Static wrapper class for the thread pool used by the program, so that it can
 * be used by the whole program
 *
 * @author Alston
 * last updated 1/13/2019
 */
public class ThreadPool {

    private static ExecutorService pool = Executors.newCachedThreadPool();

    /**
     * @return the thread pool being used
     */
    public static ExecutorService getPool() {
        return pool;
    }

    /**
     * Convenience method that directly calls {@link ExecutorService#execute(Runnable)}
     * that just makes the code shorted (so we don't have to write ThreadPool.getPool().execute()).
     *
     * @param command the command to be executed
     */
    public static void execute(Runnable command) {
        pool.execute(command);
    }
}
