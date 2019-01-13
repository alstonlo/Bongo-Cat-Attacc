package client.menu;

import client.Window;
import client.utilities.ThreadPool;
import protocol.AuthenticateProtocol;
import protocol.RegisterProtocol;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.Graphics;

/**
 * Login panel where players can log into their accounts or
 * register new accounts.
 *
 * @author Alston
 * last updated 1/12/2018
 */
class LoginPanel extends DropDownPanel {

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

        //text fields (for username and password)
        usernameField = new JTextField("Username");
        passwordField = new JTextField("Password");

        //radio buttons (to toggle between registering and logging in)
        loginButton = new JRadioButton("Login", true);
        registerButton = new JRadioButton("Register");

        ButtonGroup group = new ButtonGroup();
        group.add(loginButton);
        group.add(registerButton);

        //buttons
        submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> submit());

        backButton = new JButton("Back");
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
