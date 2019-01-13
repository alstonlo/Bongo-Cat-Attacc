package client.utilities;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

    private static ExecutorService pool = Executors.newCachedThreadPool();

    public static ExecutorService getPool() {
        return pool;
    }

    public static void execute(Runnable command) {
        pool.execute(command);
    }
}
