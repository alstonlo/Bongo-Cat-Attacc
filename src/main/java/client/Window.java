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
import javax.swing.Timer;
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


    private String username = "";

    private Client client;
    private BongoListener bongoListener = new BongoListener();
    private ServerListener serverListener = new ServerListener();

    private JLayeredPane layeredPane = new JLayeredPane();
    private GamePanel currBasePanel;



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

        layeredPane.setPreferredSize(Settings.PANEL_SIZE);
        this.add(layeredPane);

        addBasePanel(new MenuPanel(this));

        this.setVisible(true);
        this.requestFocus();
        this.pack();
        this.setVisible(true);

        new Timer(1000 / 60, (e) -> layeredPane.repaint()).start();
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


    public void addBasePanel(GamePanel basePanel) {
        if (currBasePanel != null) {
            removePanel(currBasePanel);
        }
        basePanel.run();

        bongoListener.addControlledObj(basePanel); //make newPanel able to be controlled
        serverListener.addControllableObj(basePanel);

        SwingUtilities.invokeLater(() -> {
            layeredPane.add(basePanel, 0);
            layeredPane.revalidate();
            layeredPane.repaint();
        });
    }

    public void addPanel(Integer layer, GamePanel panel) {
        if (layer <= 0) {
            throw new IndexOutOfBoundsException();
        }

        panel.run();               //run its animation

        SwingUtilities.invokeLater(() -> {
            layeredPane.add(panel, layer);
            layeredPane.revalidate();
            layeredPane.repaint();
        });
    }

    public void removePanel(GamePanel panel) {
        int layer = layeredPane.getLayer(panel);
        if (layer == 0) {
            bongoListener.removeControlledObj(panel);
            serverListener.removeControllableObj(panel);
        }

        SwingUtilities.invokeLater(() -> layeredPane.remove(panel));
        panel.stop();
    }

    public void close() {
        client.close();
    }
}
