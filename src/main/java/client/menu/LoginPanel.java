package client.menu;

import client.Window;
import client.utilities.ThreadPool;
import client.utilities.Utils;
import java.awt.Color;
import protocol.AuthenticateProtocol;
import protocol.RegisterProtocol;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

    private boolean usernameClicked = false;
    private boolean passwordClicked = false;

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
        usernameField.setBackground(new Color(247, 195, 210));
        usernameField.setBorder(null);
        usernameField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if (!usernameClicked) {
                    usernameClicked = true;
                    usernameField.setText("");
                }
            }
        });
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                usernameField.getBorder(),
                BorderFactory.createEmptyBorder(Utils.scale(5), Utils.scale(10), Utils.scale(5), Utils.scale(10))));
        usernameField.setFont(Utils.loadFont("resources/mon.otf", Utils.scale(40)));
        usernameField.setForeground(new Color(60,51,28));

        //text field for password
        passwordField = new JTextField("Password");
        passwordField.setSize(Utils.scale(400), Utils.scale(90));
        passwordField.setLocation(Utils.scale(175), Utils.scale(520));
        passwordField.setBackground(new Color(247, 195, 210));
        passwordField.setBorder(null);
        passwordField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if (!passwordClicked) {
                    passwordClicked = true;
                    passwordField.setText("");
                }
            }
        });
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                passwordField.getBorder(),
                BorderFactory.createEmptyBorder(Utils.scale(5), Utils.scale(10), Utils.scale(5), Utils.scale(10))));
        passwordField.setFont(Utils.loadFont("resources/mon.otf", Utils.scale(40)));
        passwordField.setForeground(new Color(60,51,28));

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
        submitButton.addActionListener(e -> ThreadPool.execute(() -> submit()));
        submitButton.setBorder(null);
        submitButton.setBackground(new Color(255, 221, 216));
        submitButton.setForeground(new Color(60,51,28));
        submitButton.setFocusPainted(false);

        backButton = new JButton("Back");
        backButton.setSize(Utils.scale(100), Utils.scale(70));
        backButton.setLocation(Utils.scale(90), Utils.scale(260));
        backButton.addActionListener(e -> ThreadPool.execute(() -> retract()));
        backButton.setBorder(null);
        backButton.setBackground(new Color(255, 221, 216));
        backButton.setForeground(new Color(60,51,28));
        backButton.setFocusPainted(false);

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
        retract();
    }

    @Override
    void retract() {
        super.retract();
        usernameClicked = false;
        passwordClicked = false;
        usernameField.setText("Username");
        passwordField.setText("Password");
    }
}
