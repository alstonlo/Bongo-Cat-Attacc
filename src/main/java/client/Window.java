package client;

import client.menu.MenuPanel;
import client.songselect.SongSelectionPanel;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import protocol.Network;
import protocol.Protocol;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * JFrame on which all panels are displayed. We wanted to
 * make a frame that approximated the size of a phone, so the
 * content pane is set to a 750 x 1334 ratio.
 *
 * @author Katelyn Wang and Alston
 * last updated 2019/1/8
 */
public class Window extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Window());
    }

    public static final int MENU_STATE = 0;
    public static final int SONG_SELECT_STATE = 1;

    private double scale;

    private Client client;
    private BongoListener bongoListener = new BongoListener();
    private ServerListener serverListener = new ServerListener();

    private GamePanel currPanel;
    private MenuPanel menuPanel;
    private SongSelectionPanel songPanel;

    /**
     * Constructs a new Window, scaling it according to the screen size.
     */
    Window() {
        super("Bongo Cat Attacc");

        //CLIENT CODE ------------------------------------------------------------------------------
        client = new Client();
        client.start();
        Network.register(client); //register objects sent over the network so they can be serialized

        //add listeners to the client
        client.addListener(serverListener);
        client.addListener(new Listener() { //add a listener to check if the client disconnects
            @Override
            public void disconnected(Connection connection) {
                //do something (e.g. notify and bring back to log in)
            }
        });

        try {
            client.connect(5000, "127.0.0.1", Network.PORT); //connect to the server
        } catch (IOException e) {
            System.out.println("Failed to connect to server.");
        }

        //FRAME CODE -----------------------------------------------------------------------------
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
        this.setIconImage(Utils.loadImage("resources/icons/app icon.png"));
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                close();
            }
        });

        //create JPanels
        this.menuPanel = new MenuPanel(this);
        this.songPanel = new SongSelectionPanel(this);
        switchState(MENU_STATE);

        this.setVisible(true);
        this.requestFocus();
        this.pack();
        this.setVisible(true);
    }

    /**
     * Releases all resources used by this window.
     */
    public void close() {
        client.close();
    }

    /**
     * @return the scale factor of this Window
     */
    public double getScale() {
        return scale;
    }

    /**
     * @param x a number
     * @return the scaled value of x based on {@link Window#getScale()}, rounded to the nearest integer
     */
    public int scale(double x) {
        return (int) Math.round(scale * x);
    }

    /**
     * Sends a message to the server. Currently, the message defaults to being
     * sent over TCP.
     *
     * @param protocol the message to be sent
     */
    public void sendMessage(Protocol protocol) {
        client.sendTCP(protocol);
    }

    /**
     * Switches this Window's state based on the state argument. Valid states
     * are listed in this class's static fields.
     *
     * @param state an integer representing the state of this Window
     * @throws IndexOutOfBoundsException if the state argument is invalid
     */
    public void switchState(int state) {
        switch (state) {
            case MENU_STATE:
                switchPanel(menuPanel);
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
        currPanel = newPanel;                     //set it as the currently displayed panel

        bongoListener.setControlledObj(newPanel); //make newPanel able to be controlled
        serverListener.setControllableObj(newPanel);

        SwingUtilities.invokeLater(() -> {
            getContentPane().removeAll(); //removes previous panel and add new one
            getContentPane().add(newPanel);
            getContentPane().revalidate();
            getContentPane().repaint();   //repaint
        });

        newPanel.run();               //run its animation
    }
}
