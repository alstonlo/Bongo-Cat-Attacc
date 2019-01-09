package client.ui;

import client.utilities.Utils;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import protocol.Network;
import protocol.Protocol;

import javax.swing.JFrame;
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
        new Window();
    }

    private static final int LOGIN_STATE = -1;
    static final int MENU_STATE = 0;
    static final int QUEUE_STATE = 1;
    static final int SONG_SELECT_STATE = 2;

    private double scale;

    private Client client;
    private BongoListener bongoListener = new BongoListener();
    private ServerListener serverListener = new ServerListener();

    private GamePanel currPanel;
    private LoginPanel loginPanel = new LoginPanel(this);
    private MenuPanel menuPanel = new MenuPanel(this);
    private QueuePanel queuePanel = new QueuePanel(this);
    private SongSelectionPanel songPanel = new SongSelectionPanel(this);

    /**
     * Constructs a new Window, scaling it according to the screen size.
     */
    Window() {
        super("Bongo Cat Attacc");

        //create and start the client
        client = new Client();
        client.start();
        Network.register(client); //register objects sent over the network so they can be serialized
        client.addListener(serverListener);
        client.addListener(new Listener() { //add a listener exclusively to check if the client disconnects
            @Override
            public void disconnected(Connection connection) {
                //do something (e.g. notify and bring back to log in)
            }
        });

        try {
            client.connect(5000, "127.0.0.1", Network.PORT); //connect to the server
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                close();
            }
        });
        this.pack();
        this.setVisible(true);

        switchState(LOGIN_STATE);
    }

    /**
     * Releases all resources used by this window.
     */
    void close() {
        client.close();
        bongoListener.stop();
    }

    /**
     * @return the scale factor of this Window
     */
    double getScale() {
        return scale;
    }

    /**
     * Sends a message to the server. Currently, the message defaults to being
     * sent over TCP.
     *
     * @param protocol the message to be sent
     */
    void sendMessage(Protocol protocol) {
        client.sendTCP(protocol);
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
        currPanel = newPanel;                     //set it as the currently displayed panel

        bongoListener.setControlledObj(newPanel); //make newPanel able to be controlled
        serverListener.setControllableObj(newPanel);

        getContentPane().removeAll(); //removes previous panel and add new one
        getContentPane().add(newPanel);
        getContentPane().revalidate();
        getContentPane().repaint();   //repaint

        newPanel.run();                           //run its animation
    }
}
