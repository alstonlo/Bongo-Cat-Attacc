package client;

import client.menu.MenuPanel;
import client.utilities.Settings;
import client.utilities.Utils;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import protocol.Message;
import protocol.Network;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    private final Integer BASE_LAYER = 1;
    private JLayeredPane layers = new JLayeredPane();
    private Map<Integer, GamePanel> runningPanels = new HashMap<>();

    private String username = "";

    private Client client;
    private BongoListener bongoListener = new BongoListener();
    private ServerListener serverListener = new ServerListener();


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

        this.getContentPane().setPreferredSize(Settings.PANEL_SIZE);
        pack();
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

        layers.setPreferredSize(Settings.PANEL_SIZE);
        this.add(layers);

        displayBasePanel(new MenuPanel(this));

        this.setVisible(true);
        this.requestFocus();
        this.pack();
        this.setVisible(true);
    }

    /**
     * @return the username of the player
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the player, if they have not logged in.
     * If they are already logged in, does nothing.
     *
     * @param username the player's username
     */
    public void setUsername(String username) {
        if (this.username.equals("")) {
            this.username = username;
        }
    }

    /**
     * Sends a message to the server. Currently, the message defaults to being
     * sent over TCP.
     *
     * @param message the message to be sent
     */
    public void sendMessage(Message message) {
        client.sendTCP(message);
    }


    public void displayBasePanel(GamePanel basePanel) {
        if (basePanel != null) { //stop the animation running on the currently displayed panel
            basePanel.stop();
        }
        basePanel = newPanel;         //set it as the currently displayed panel
        newPanel.run();               //run its animation

        bongoListener.setControlledObj(newPanel); //make newPanel able to be controlled
        serverListener.setControllableObj(newPanel);

        SwingUtilities.invokeLater(() -> {
            layers.add(newPanel, BASE_LAYER);
            layers.revalidate();
            layers.repaint();
        });
    }

    public void displayPanel(int layer, GamePanel newPanel) {
        if (basePanel != null) { //stop the animation running on the currently displayed panel
            basePanel.stop();
        }
        basePanel = newPanel;         //set it as the currently displayed panel
        newPanel.run();               //run its animation

        bongoListener.setControlledObj(newPanel); //make newPanel able to be controlled
        serverListener.setControllableObj(newPanel);

        SwingUtilities.invokeLater(() -> {
            layers.add(newPanel, BASE_LAYER);
            layers.revalidate();
            layers.repaint();
        });
    }


    public void close() {
        client.close();
    }
}
