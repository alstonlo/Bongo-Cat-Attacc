package client.ui;

import client.utilities.Utils;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * JFrame on which all panels are displayed. We wanted to
 * make a frame that approximated the size of a phone, so the
 * content pane is set to a 750 x 1334 ratio.
 *
 * @author Katelyn Wang and Alston
 * last updated 2019/1/4
 */
public class Window extends JFrame {

    public static void main(String[] args) {
        new Window();
    }

    private static final int LOGIN_STATE = -1;
    static final int MENU_STATE = 0;
    static final int QUEUE_STATE = 1;
    static final int SONG_SELECT_STATE = 2;

    private double scale;

    private BongoListener bongoListener = new BongoListener();

    private GamePanel currPanel;
    private LoginPanel loginPanel = new LoginPanel(this);
    private MenuPanel menuPanel = new MenuPanel(this);
    private QueuePanel queuePanel = new QueuePanel();
    private SongSelectionPanel songPanel = new SongSelectionPanel(this);

    /**
     * Constructs a new Window, scaling it according to the screen size.
     */
    Window() {
        super("Bongo Cat Attacc");

        //resolve scaling and sizing
        //the height of the content pane should be 80% the height of the screen
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.scale = (screenHeight * 0.8) / 1334;

        Dimension paneSize = new Dimension();
        paneSize.setSize(750 * scale, 1334 * scale);
        this.getContentPane().setPreferredSize(paneSize);

        //create the JFrame
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.addKeyListener(bongoListener);
        this.setIconImage(Utils.loadImage("resources/icon.png"));
        this.pack();
        this.setVisible(true);

        switchState(LOGIN_STATE);
    }

    /**
     * Releases all threads and stops all resources used by the window.
     */
    void close() {
        bongoListener.stop();
    }

    /**
     * @return the scale factor of this Window
     */
    double getScale() {
        return scale;
    }

    /**
     * Switches this Window's state based on the state argument. Valid states
     * are listed in this class's static fields.
     *
     * @param state an integer representing the state of this Window
     * @throws IndexOutOfBoundsException if the state argument is invalid
     */
    void switchState(int state) {
        switch (state) {
            case LOGIN_STATE:
                switchPanel(loginPanel);
                break;

            case MENU_STATE:
                switchPanel(menuPanel);
                break;

            case QUEUE_STATE:
                switchPanel(queuePanel);
                break;

            case SONG_SELECT_STATE:
                switchPanel(songPanel);
                break;

            default:
                throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Switches the JPanel displayed on this Window's content pane.
     * The JPanel displayed must be a GamePanel.
     *
     * @param newPanel the new JPanel to be displayed
     */
    private void switchPanel(GamePanel newPanel) {
        if (currPanel != null) { //stop the animation running on the currently displayed panel
            currPanel.stop();
        }
        bongoListener.setControlledObj(newPanel); //make newPanel able to be controlled
        newPanel.run();                           //run its animation
        currPanel = newPanel;                     //set it as the currently displayed panel

        getContentPane().removeAll(); //removes previous panel and add new one
        getContentPane().add(newPanel);
        getContentPane().revalidate();
        getContentPane().repaint();   //repaint

        setFocusable(true); //set focus back to frame to keep bongoListener working
    }
}
