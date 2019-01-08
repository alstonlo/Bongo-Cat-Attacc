package client.ui;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.Graphics;
import java.awt.Graphics2D;

class LoginPanel extends GamePanel {

    private Window window;
    private JTextField usernameField;
    private JTextField passwordField;
    private JRadioButton loginButton;
    private JRadioButton registerButton;
    private JButton submitButton;

    private String errorMessage;

    LoginPanel(Window window) {
        this.window = window;

        usernameField = new JTextField("Username");
        passwordField = new JTextField("Password");

        submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> submit());

        loginButton = new JRadioButton("Login", true);
        registerButton = new JRadioButton("Register");

        ButtonGroup group = new ButtonGroup();
        group.add(loginButton);
        group.add(registerButton);

        add(usernameField);
        add(passwordField);
        add(loginButton);
        add(registerButton);
        add(submitButton);
    }

    public void submit() {
        errorMessage = null;

        if (registerButton.isSelected()) {
            window.switchState(Window.MENU_STATE);
        } else {
            window.switchState(Window.MENU_STATE);
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D)g;

        if (errorMessage != null) {
            g2D.drawString(errorMessage, 100, 500);
        }
    }

    @Override
    public void run() {
    }

    @Override
    public void stop() {
    }


    //WE DISABLE THE BONGOLISTENER ------------------------------------------

    @Override
    public void notifyLeftPress() {
    }

    @Override
    public void notifyLeftRelease() {
    }

    @Override
    public void notifyRightPress() {
    }

    @Override
    public void notifyRightRelease() {
    }

    @Override
    public void notifyHold() {
    }
}
