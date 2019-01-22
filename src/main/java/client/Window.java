package client;

import client.menu.MenuPanel;
import client.utilities.Settings;
import client.utilities.ThreadPool;
import client.utilities.Utils;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import protocol.Message;
import protocol.Network;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import java.awt.Component;
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


    private String username = "";

    private Client client;
    private BongoListener bongoListener = new BongoListener();
    private ServerListener serverListener = new ServerListener();

    private JLayeredPane layeredPane = new JLayeredPane();
    private final int FPS = 60;
    private Timer animator;

    /**
     * Constructs a new Window, scaling it according to the screen size.
     */
    Window() {
        super("Bongo Cat Attacc");

        //Client code ------------------------------------------------------------------------------
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

        //Frame code -----------------------------------------------------------------------------

        this.getContentPane().setPreferredSize(Settings.PANEL_SIZE);
        this.pack();
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
        this.layeredPane.setPreferredSize(Settings.PANEL_SIZE);
        this.add(layeredPane);
        this.addBasePanel(new MenuPanel(this));
        this.setVisible(true);
        this.requestFocus();
        this.pack();
        this.setVisible(true);

        //continuously update the layered pane
        this.animator = new Timer(1000 / FPS, (e) -> layeredPane.repaint());
        animator.start();
    }

    /**
     * @return the username of the player; or an empty string if they have not logged in
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

    /**
     * Adds a base game panel to this window. Base panels are the core animation panels
     * of the game, and only one can be playing at a time. Thus, all other base panels
     * are stopped and removed.
     *
     * @param basePanel the base panel to be displayed
     */
    public void addBasePanel(GamePanel basePanel) {

        //stop and remove all other base panels (they exist in layer 0)
        for (Component comp : layeredPane.getComponentsInLayer(0)) {
            if (comp instanceof GamePanel) {
                ((GamePanel) comp).stop();
                layeredPane.remove(comp);
            }
        }

        addPanel(0, basePanel);
    }

    /**
     * Adds a panel to the specified layer on the window's pane.
     * The layer 0 should be avoided as that is the base panel layer.
     * The new panel is able to be controlled by both the bongo and server
     * listener.
     *
     * @param layer the layer the panel should be added to
     * @param panel the panel that is added
     */
    public void addPanel(Integer layer, GamePanel panel) {
        SwingUtilities.invokeLater(() -> {
            layeredPane.add(panel, layer);
            layeredPane.revalidate();
            layeredPane.repaint();
        });

        //make newPanel able to be controlled
        bongoListener.addControlledObj(panel);
        serverListener.addControllableObj(panel);

        panel.run(); //run its animation
    }

    /**
     * Stops, then removes a panel from this window.
     *
     * @param panel the panel to be removed
     */
    public void removePanel(GamePanel panel) {
        panel.stop();
        bongoListener.removeControlledObj(panel);
        serverListener.removeControllableObj(panel);
        SwingUtilities.invokeLater(() -> layeredPane.remove(panel));
    }

    /**
     * Closes the window and releases all resources.
     */
    private void close() {
        client.close();
        animator.stop();
        ThreadPool.getPool().shutdown();
    }
}
