package client.menu;

import client.Window;
import client.utilities.Pallette;
import client.utilities.ThreadPool;
import client.utilities.Utils;
import exceptions.GameException;
import protocol.AuthenticateMessage;
import protocol.ExceptionMessage;
import protocol.Message;
import protocol.RegisterMessage;
import protocol.ResponseMessage;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.util.UUID;

/**
 * Login panel where players can log into their accounts or
 * register new accounts.
 *
 * @author Alston
 * last updated 1/12/2018
 */
class LoginPanel extends DropDownPanel {

    private BufferedImage drape = Utils.loadScaledImage("resources/menu/login drape.png");

    private JTextField usernameField;
    private JTextField passwordField;
    private JRadioButton loginButton;
    private JRadioButton registerButton;
    private JButton submitButton;
    private JButton backButton;
    private JTextField errorMessageArea;

    private Message lastSent;

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
        usernameField.setLocation(Utils.scale(175), Utils.scale(400));
        stylize(usernameField);

        //text field for password
        passwordField = new JTextField("Password");
        passwordField.setLocation(Utils.scale(175), Utils.scale(520));
        stylize(passwordField);

        //radio buttons (to toggle between registering and logging in)
        loginButton = new JRadioButton("Login", true);
        loginButton.setLocation(Utils.scale(205), Utils.scale(615));
        stylize(loginButton);

        registerButton = new JRadioButton("Register");
        registerButton.setLocation(Utils.scale(360), Utils.scale(615));
        stylize(registerButton);

        ButtonGroup group = new ButtonGroup();
        group.add(loginButton);
        group.add(registerButton);

        //buttons
        submitButton = new JButton("Submit");
        submitButton.setFont(Pallette.getScaledFont(Pallette.TEXT_FONT, 33));
        submitButton.setSize(Utils.scale(250), Utils.scale(90));
        submitButton.setLocation(Utils.scale(250), Utils.scale(750));
        submitButton.addActionListener(e -> {
            submitButton.setEnabled(false); //lock the submit button
            ThreadPool.execute(this::submit);
        });
        submitButton.setBorder(BorderFactory.createLineBorder(Pallette.OUTLINE_COLOR, Utils.scale(3), true));
        submitButton.setBackground(new Color(255, 221, 216));
        submitButton.setForeground(Pallette.OUTLINE_COLOR);
        submitButton.setFocusPainted(false);

        backButton = new JButton("Back");
        backButton.setFont(Pallette.getScaledFont(Pallette.TEXT_FONT, 25));
        backButton.setSize(Utils.scale(100), Utils.scale(70));
        backButton.setLocation(Utils.scale(90), Utils.scale(260));
        backButton.addActionListener(e -> ThreadPool.execute(() -> retract()));
        backButton.setBorder(null);
        backButton.setBackground(new Color(255, 221, 216));
        backButton.setForeground(Pallette.OUTLINE_COLOR);
        backButton.setFocusPainted(false);

        //text area for error messaging
        errorMessageArea = new JTextField();
        errorMessageArea.setEditable(false);
        errorMessageArea.setFont(Pallette.getScaledFont(Pallette.TEXT_FONT, 20));
        errorMessageArea.setHorizontalAlignment(JTextField.CENTER);
        errorMessageArea.setBorder(null);
        errorMessageArea.setBackground(Color.WHITE);
        errorMessageArea.setForeground(Pallette.OUTLINE_COLOR);
        errorMessageArea.setSize(Utils.scale(600), Utils.scale(80));
        errorMessageArea.setLocation(Utils.scale(75), Utils.scale(330));

        add(usernameField);
        add(passwordField);
        add(loginButton);
        add(registerButton);
        add(submitButton);
        add(backButton);
        add(errorMessageArea);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(drape, 0, 0, null);
    }

    /**
     * Displays an error onto the panel.
     *
     * @param errorState the state denoting the type of error, as specified by {@link GameException}
     */
    private void displayError(int errorState) {
        String errorMessage;

        switch (errorState) {
            case GameException.DATABASE_ERROR_STATE:
                errorMessage = "Our database ran into an issue. Please try again.";
                break;

            case GameException.INVALID_REGISTER_STATE:
                errorMessage = "Username taken. Please try again.";
                break;

            case GameException.DOUBLE_LOGIN_STATE:
                errorMessage = "You are already logged in.";
                break;

            case GameException.INVALID_LOGIN_STATE:
                errorMessage = "Incorrect username or password.";
                break;

            default:
                errorMessage = "";
                System.out.println("Received error with state " + errorState);
        }

        //display the message into the appropriate area
        SwingUtilities.invokeLater(() -> errorMessageArea.setText(errorMessage));
    }

    /**
     * Submits the form and sends the appropriate message to the server
     * based on what is inputted in the fields and buttons.
     */
    private void submit() {
        String username = usernameField.getText().trim().toLowerCase();
        String password = passwordField.getText().trim().toLowerCase();

        Message toSend;
        if (registerButton.isSelected()) {      //if user is registering a new account
            toSend = new RegisterMessage(username, password);

        } else {                                //if user is logging into an account
            toSend = new AuthenticateMessage(username, password);
        }

        toSend.id = UUID.randomUUID().toString();
        lastSent = toSend;
        window.sendMessage(toSend);
    }


    // DropDownPanel methods -------------------------------------------------------------------------

    @Override
    void retract() {
        super.retract();

        //reset all text fields to their default values
        SwingUtilities.invokeLater(() -> {
            usernameField.setText("Username");
            passwordField.setText("Password");
            errorMessageArea.setText("");
        });
    }

    @Override
    public void notifyReceived(Message message) {

        //use if statements to filter out the type of message
        if (message instanceof ExceptionMessage) {
            ExceptionMessage error = (ExceptionMessage) message;
            if (lastSent.id.equals(error.response)) {
                displayError(error.errorState); //display the error
                SwingUtilities.invokeLater(() -> submitButton.setEnabled(true)); //unlock the submit button
            }

        } else if (message instanceof ResponseMessage) {
            ResponseMessage response = (ResponseMessage) message;
            if (lastSent.id.equals(response.response)) {

                //retrieve what type of message the server responded to
                if (lastSent instanceof RegisterMessage) { //account was successfully registered
                    SwingUtilities.invokeLater(() -> errorMessageArea.setText("Account registered!"));

                } else if (lastSent instanceof AuthenticateMessage) { //user successfully logged in
                    window.setUsername(((AuthenticateMessage) lastSent).username);
                    ThreadPool.execute(this::retract);
                }

                SwingUtilities.invokeLater(() -> submitButton.setEnabled(true)); //unlock the submit button
            }
        }
    }


    // Stylization methods (for readability) -----------------------------------------------------------

    /**
     * Convenience method that stylizes the text fields used in this panel
     *
     * @param field the field to be stylized
     */
    private void stylize(JTextField field) {
        String defaultText = field.getText();

        field.setSize(Utils.scale(400), Utils.scale(60));
        field.setForeground(Pallette.OUTLINE_COLOR);

        field.setBackground(Color.WHITE);
        field.setFont(Pallette.getScaledFont(Pallette.TEXT_FONT, 40));

        Border outline = BorderFactory.createMatteBorder(0, 0, Utils.scale(4), 0, Pallette.OUTLINE_COLOR);
        int padSize = Utils.scale(10);
        Border padding = BorderFactory.createEmptyBorder(0, padSize, 0, padSize);
        field.setBorder(BorderFactory.createCompoundBorder(outline, padding));

        //bound field to 10 characters
        field.setDocument(new PlainDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (str == null) {
                    return;
                }
                if ((getLength() + str.length()) <= 10) {
                    super.insertString(offs, str, a);
                }
            }
        });

        //add focus listener to modify prompting
        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(defaultText)) {
                    field.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(defaultText);
                }
            }
        });

        field.setText(defaultText);
    }

    /**
     * Convenience method to stylize the radio buttons used in this panel
     *
     * @param button the button to be stylized
     */
    private void stylize(JRadioButton button) {
        button.setFont(Pallette.getScaledFont(Pallette.TEXT_FONT, 30));
        button.setFocusPainted(false);
        button.setForeground(Pallette.OUTLINE_COLOR);
        button.setSize(Utils.scale(150), Utils.scale(90));
        button.setBackground(Color.WHITE);
        button.setHorizontalAlignment(JButton.CENTER);
    }
}
