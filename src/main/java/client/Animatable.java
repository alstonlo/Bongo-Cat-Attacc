package client;

/**
 * Represents an object that can be animated.
 *
 * @author Alston
 * last updated 1/4/2019
 */
public interface Animatable {

    /**
     * @return the maximum fps of the object's animation
     */
    int getFps();

    /**
     * Sets the maximum fps of the animation (in order to keep
     * a consistent animation style).
     *
     * @param fps the upper bound of the animation's fps
     */
    void setFps(int fps);

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
