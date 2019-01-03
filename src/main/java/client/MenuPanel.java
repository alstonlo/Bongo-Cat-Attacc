package client;

import client.utilities.Utils;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The window which pops up upon launching before the program starts up
 * Displays our logo
 *
 * @author Katelyn Wang
 * last updated 2018/12/11
 */
public class MenuPanel extends JPanel {
    private static Image bongoCat; //retrieves the image
    private static Image mouth; //retrieves the image
    private static Image[] left = new Image[2];
    private static Image[] right = new Image[2];
    private Window window;
    private JPanel panel = this;
    private Button playButton;
    static {
        try {
            bongoCat = ImageIO.read(new File("resources/bongo.png"));
            mouth = ImageIO.read(new File("resources/m1.png"));
            left[0] = ImageIO.read(new File("resources/l1.png"));
            left[1] = ImageIO.read(new File("resources/l2.png"));
            right[0] = ImageIO.read(new File("resources/r2.png"));
            right[1] = ImageIO.read(new File("resources/r1.png"));
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    private int state = 0;

    public static void main(String[] args) {
        new StartFrame();
    }

    /**
     * Constructor
     */
    public MenuPanel(Window window) {
        super();
        this.setBackground(Color.WHITE);
        this.window = window;
        this.addMouseListener(new MyMouseListener());
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                state = (state == 0) ? 1 : 0;
            }
        }, 0, 1000);

    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        int x = Utils.MAX_X/2-bongoCat.getWidth(this)/2-Utils.scale(198);
        g.drawImage(bongoCat, x+Utils.scale(198), Utils.scale(50), this);
        g.drawImage(mouth, x, Utils.scale(150), this);

        if (state == 0) {
            g.drawImage(left[0], x, Utils.scale(150), this);
            g.drawImage(right[0], x, Utils.scale(150), this);
        } else {
            g.drawImage(left[1], x, Utils.scale(150), this);
            g.drawImage(right[1], x, Utils.scale(150), this);
        }

        playButton = new Button("play", Utils.MAX_X/2-Utils.scale(100)/2, Utils.scale(500),
                Utils.scale(150), Utils.scale(80), new Color(150, 173, 255),
                new Color(250,250,250), window, 1);
        repaint();
        playButton.draw(g, this);
    }

    private class MyMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (playButton.isMouseOnButton(panel)){
                window.switchState(1);
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}

