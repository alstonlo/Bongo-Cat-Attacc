package client;

import client.utilities.Settings;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Representation of the type of JPanels used by this game.
 *
 * @author Alston
 * last updated 1/9/2019
 */
public abstract class GamePanel extends JPanel implements Animatable, Controllable, Messagable {

    private final int FPS = 60; //the preferred FPS for the game

    private Timer animationTimer;
    protected final Window window;

    /**
     * Constructs a GamePanel with the Window it belongs to.
     *
     * @param window the Window this panel belongs to
     */
    public GamePanel(Window window) {
        this.setSize(Settings.PANEL_SIZE);
        this.setLocation(0, 0);
        this.setDoubleBuffered(true);

        this.window = window;
        this.animationTimer = new Timer(1000 / FPS, (e) -> update());
    }

    // Animatable methods ---------------------------------------------------------------------

    @Override
    public void run() {
        animationTimer.start();
    }

    @Override
    public void update() {
        repaint();
    }

    @Override
    public void stop() {
        animationTimer.stop();
    }
}
