package client.menu;

import client.Window;
import client.utilities.ThreadPool;
import client.utilities.Utils;
import protocol.AuthenticateProtocol;
import protocol.RegisterProtocol;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Login panel where players can log into their accounts or
 * register new accounts.
 *
 * @author Alston
 * last updated 1/12/2018
 */
class LoginPanel extends DropDownPanel {

    private BufferedImage loginDrape = Utils.loadScaledImage("resources/menu/login drape.png");

    private JTextField usernameField;
    private JTextField passwordField;
    private JRadioButton loginButton;
    private JRadioButton registerButton;
    private JButton submitButton;
    private JButton backButton;

    /**
     * Constructs a LoginPanel.
     *
     * @param window the Window this panel belongs to
     */
    LoginPanel(Window window) {
        super(window);
        this.setLayout(null);

        //text field for username
        usernameField = new JTextField("Username");
        usernameField.setSize(Utils.scale(400), Utils.scale(90));
        usernameField.setLocation(Utils.scale(175), Utils.scale(400));

        //text field for password
        passwordField = new JTextField("Password");
        passwordField.setSize(Utils.scale(400), Utils.scale(90));
        passwordField.setLocation(Utils.scale(175), Utils.scale(520));

        //radio buttons (to toggle between registering and logging in)
        loginButton = new JRadioButton("Login", true);
        loginButton.setSize(Utils.scale(150), Utils.scale(90));
        loginButton.setLocation(Utils.scale(225), Utils.scale(640));

        registerButton = new JRadioButton("Register");
        registerButton.setSize(Utils.scale(150), Utils.scale(90));
        registerButton.setLocation(Utils.scale(375), Utils.scale(640));

        ButtonGroup group = new ButtonGroup();
        group.add(loginButton);
        group.add(registerButton);

        //buttons
        submitButton = new JButton("Submit");
        submitButton.setSize(Utils.scale(200), Utils.scale(70));
        submitButton.setLocation(Utils.scale(275), Utils.scale(760));
        submitButton.addActionListener(e -> submit());

        backButton = new JButton("Back");
        backButton.setSize(Utils.scale(70), Utils.scale(70));
        backButton.setLocation(Utils.scale(90), Utils.scale(260));
        backButton.addActionListener(e -> ThreadPool.execute(() -> retract()));

        add(usernameField);
        add(passwordField);
        add(loginButton);
        add(registerButton);
        add(submitButton);
        add(backButton);

        setVisible(true);
    }

    /**
     * {@inheritDoc}
     *
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D)g;
        g2D.drawImage(loginDrape, 0, 0, null);
    }

    /**
     * Submits the form and sends the appropriate message to the server
     * based on what is inputted in the fields and buttons.
     */
    private void submit() {
        String username = usernameField.getText().trim().toLowerCase();
        String password = passwordField.getText().trim().toLowerCase();

        if (registerButton.isSelected()) {      //if user is registering a new account
            window.sendMessage(new RegisterProtocol(username, password));

        } else if (loginButton.isSelected()) {   //if user is logging into an account
            window.sendMessage(new AuthenticateProtocol(username, password));
        }
    }
}
