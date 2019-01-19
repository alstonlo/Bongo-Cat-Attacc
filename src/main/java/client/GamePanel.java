package client;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Representation of the type of JPanels used by this game.
 *
 * @author Alston
 * last updated 1/9/2019
 */
public abstract class GamePanel extends JPanel implements Animatable, Controllable {

    private final int FPS = 30; //the preferred FPS for the game

    private Timer animationTimer;
    protected final Window window;

    /**
     * Constructs a GamePanel with the Window it belongs to.
     *
     * @param window the Window this panel belongs to
     */
    public GamePanel(Window window) {
        this.setDoubleBuffered(true);

        this.window = window;
        this.animationTimer = new Timer(FPS / 30, (e) -> update());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        animationTimer.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        animationTimer.stop();
    }
}
