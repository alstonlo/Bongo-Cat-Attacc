package client;

import client.menu.MenuPanel;
import client.songselect.SongSelectPanel;
import client.utilities.Settings;
import client.utilities.Utils;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import protocol.Network;
import protocol.Protocol;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
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

    private Client client;
    private BongoListener bongoListener = new BongoListener();
    private ServerListener serverListener = new ServerListener();

    private GamePanel currPanel;

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

        //create the JFrame
        this.getContentPane().setPreferredSize(Settings.PANEL_SIZE);
        pack();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.addKeyListener(bongoListener);
        this.setIconImage(Utils.loadScaledImage("resources/icons/app icon.png"));
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                close();
            }
        });


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

        /*
         * The reason new panels are created each time is because I want to
         * release the sprites and resources by allowing them to be collected.
         */
        switch (state) {
            case MENU_STATE:
                switchPanel(new MenuPanel(this));
                break;

            case SONG_SELECT_STATE:
                switchPanel(new SongSelectPanel(this));
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
        currPanel = newPanel;         //set it as the currently displayed panel

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
