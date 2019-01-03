package client;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Window on which all panels are displayed
 * @author Katelyn Wang
 */
public class Window extends JFrame {
    JFrame window;
    JPanel menuPanel = new MenuPanel(this);
    JPanel gamePanel = new GamePanel();


    public static void main(String[] args) {
        new Window();
    }

    /**
     * Constructor
     */
    Window(){
        this.window = this;
        this.setResizable(false);
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize().width,
                Toolkit.getDefaultToolkit().getScreenSize().height);
        this.setUndecorated(true);
        this.setVisible(true);
        this.addKeyListener(new MyKeyListener());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        switchState(0);
    }

    protected void switchState(int state){
        switch(state){
            case(0):
                switchPanel(menuPanel);
                break;
            case(1):
                switchPanel(gamePanel);
                break;

            default:
                throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Switches the panel currently being displayed to the panel indicated
     * @param newPanel the panel to be displayed instead
     */
    private void switchPanel(JPanel newPanel) {
        getContentPane().removeAll(); //removes all content currently being displayed
        getContentPane().add(newPanel); //adds the panel to be displayed
        getContentPane().revalidate();
        getContentPane().repaint(); //displays it
    }

    private class MyKeyListener implements KeyListener {
        @Override
        public void keyPressed(KeyEvent e) {
            if (KeyEvent.getKeyText(e.getKeyCode()).equals("Escape")){
                window.dispose();
            }

        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

}
