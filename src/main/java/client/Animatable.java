package client;

/**
 * Represents an object that can be animated.
 *
 * @author Alston
 * last updated 1/10/2019
 */
public interface Animatable {

    /**
     * Runs the animation by continuously updating the panel.
     * If the animation is already running, nothing happens.
     */
    void run();

    /**
     * Updates the panel and repaints it.
     */
    void update();

    /**
     * Stops the animation. If the animation is already stopped, nothing happens.
     */
    void stop();

}
