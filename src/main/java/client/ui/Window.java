package client.ui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.Image;
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

    static final int MENU_STATE = 0;
    static final int QUEUE_STATE = 1;

    private double scale;

    private BongoListener bongoListener = new BongoListener();

    private GamePanel currPanel;
    private MenuPanel menuPanel = new MenuPanel(this);
    private QueuePanel queuePanel = new QueuePanel();

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

        Image icon = Toolkit.getDefaultToolkit().getImage("resources/icon.png");
        this.setIconImage(icon);

        //create the JFrame
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.addKeyListener(bongoListener);
        this.pack();
        this.setVisible(true);

        switchState(MENU_STATE);
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
            case MENU_STATE:
                switchPanel(menuPanel);
                break;

            case QUEUE_STATE:
                switchPanel(queuePanel);
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
    }
}
