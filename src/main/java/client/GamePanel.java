package client;

import javax.swing.JPanel;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Representation of the type of JPanels used by this game.
 *
 * @author Alston
 * last updated 1/9/2019
 */
abstract class GamePanel extends JPanel implements Animatable, Controllable {

    private int fps = 30;
    private Timer animationTimer;
    final Window window;

    /**
     * Constructs a GamePanel with the Window it belongs to.
     *
     * @param window the Window this panel belongs to
     */
    GamePanel(Window window) {
        this.window = window;
    }

    /**
     * {@inheritDoc}
     *
     * @return the fps of this GamePanel, which is initialized at 30
     */
    @Override
    public int getFps() {
        return fps;
    }

    /**
     * {@inheritDoc}
     *
     * @param fps the upper bound of the animation's fps
     */
    @Override
    public void setFps(int fps) {
        this.fps = fps;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        if (animationTimer == null) { //ensure that the animation isn't running (there would be a Timer)
            animationTimer = new Timer();
            animationTimer.scheduleAtFixedRate(new TimerTask() {

                @Override
                public void run() {
                    repaint();
                }

            }, 1000 / fps, 100); //continuously repaint at a fixed fps
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        if (animationTimer != null) { //ensure that the animation isn't stopped (there wouldn't be a Timer)
            animationTimer.cancel();
            animationTimer = null;
        }
    }
}
