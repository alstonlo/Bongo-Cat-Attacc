package client;

import client.utilities.Settings;
import protocol.Message;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Representation of the type of JPanels used by this game.
 *
 * @author Alston
 * last updated 1/9/2019
 */
public abstract class GamePanel extends JPanel implements Animatable, Controllable, Messagable {

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
    }

    // Animatable methods ---------------------------------------------------------------------

    @Override
    public void run() {
    }

    @Override
    public void stop() {
    }

    @Override
    public void notifyLeftPress() {
    }

    @Override
    public void notifyLeftRelease() {
    }

    @Override
    public void notifyRightPress() {
    }

    @Override
    public void notifyRightRelease() {
    }

    @Override
    public void notifyHold() {
    }

    @Override
    public void notifyReceived(Message message) {
    }
}
