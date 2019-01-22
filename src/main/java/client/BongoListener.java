package client;

import client.utilities.Settings;
import client.utilities.ThreadPool;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A modified KeyListener that only listens to the left, right, and hold bongo keys.
 * These keys are mapped in {@link Settings}. So that the key listener's methods are not
 * executed on the EDT, they are executed using a thread pool, implemented in {@link ThreadPool}.
 *
 * @author Alston
 * last updated 1/12/2019
 */
public class BongoListener implements KeyListener {

    private Set<Controllable> objects = new HashSet<>();

    private final AtomicBoolean leftPress = new AtomicBoolean(false);
    private final AtomicBoolean rightPress = new AtomicBoolean(false);

    private ExecutorService pool = ThreadPool.getPool();

    /**
     * Adds an object that is controlled by this listener. Each
     * object will be notified on a separate thread.
     *
     * @param obj the object to be controlled by this listener
     */
    void addControlledObj(Controllable obj) {
        objects.add(obj);
    }

    /**
     * Removes an object that is controlled by this listener.
     *
     * @param obj the object to be controlled by this listener
     */
    void removeControlledObj(Controllable obj) {
        objects.remove(obj);
    }

    /**
     * Does nothing.
     *
     * @param e the key pressed
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Invoked when the left or right bongo key are pressed, which
     * notifies the controlled object via {@link Controllable#notifyLeftPress()}
     * and {@link Controllable#notifyRightPress()} respectively. keyPressed(KeyEvent)
     * will only trigger once, when the key is first pressed.
     *
     * @param e the key pressed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == Settings.LEFT_BONGO_KEY) { //detect left bongo key press
            if (leftPress.compareAndSet(false, true)) {
                objects.forEach(obj -> pool.execute(obj::notifyLeftPress));
            }
        }

        if (e.getKeyCode() == Settings.RIGHT_BONGO_KEY) { //detect right bongo press
            if (rightPress.compareAndSet(false, true)) {
                objects.forEach(obj -> pool.execute(obj::notifyRightPress));
            }
        }
    }

    /**
     * Invoked when the left or right bongo key are released, which
     * notifies the controlled object via {@link Controllable#notifyLeftRelease()}
     * and {@link Controllable#notifyRightRelease()}.
     *
     * @param e the key released
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == Settings.LEFT_BONGO_KEY) { //detect left bongo release
            leftPress.set(false);
            objects.forEach(obj -> pool.execute(obj::notifyLeftRelease));
        }

        if (e.getKeyCode() == Settings.RIGHT_BONGO_KEY) { //detect right bongo release
            rightPress.set(false);
            objects.forEach(obj -> pool.execute(obj::notifyRightRelease));
        }

        if (e.getKeyCode() == Settings.HOLD_BONGO_KEY) {
            objects.forEach(obj -> pool.execute(obj::notifyHold));
        }
    }
}
