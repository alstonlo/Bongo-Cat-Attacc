package client;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
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
public class StartFrame extends JFrame {
    private static Image bongoCat; //retrieves the image
    private static Image[] left = new Image[2];
    private static Image[] right = new Image[2];

    static {
        try {
            bongoCat = ImageIO.read(new File("resources/bongo cat/body.png"));

            left[0] = ImageIO.read(new File("resources/bongo cat/left paw down.png"));
            left[1] = ImageIO.read(new File("resources/bongo cat/left paw up.png"));
            right[0] = ImageIO.read(new File("resources/bongo cat/right paw down.png"));
            right[1] = ImageIO.read(new File("resources/bongo cat/right paw up.png"));
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    private int state = 0;
    JFrame frame;

    public static void main(String[] args) {
        new StartFrame();
    }

    /**
     * Constructor
     */
    public StartFrame() {
        super();
        this.frame = this;
        this.setAlwaysOnTop(true); //sets it above all the other panels
        this.setSize(600, 500); //hardcoded to our assets (our image we use is 500x500 so it won't ever need to be changed)
        this.setLocationRelativeTo(null); //centers it
        this.getContentPane().setBackground(Color.WHITE);
        this.setUndecorated(true);
        this.setResizable(false); //doesn't allow the screen to be resized
        this.setVisible(true);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                state = (state == 0) ? 1 : 0;
                repaint();
            }
        }, 0, 1000);

    }

    /**
     * Draws the background image
     *
     * @param g The graphic object to draw images
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.drawImage(bongoCat, -200, -100, this);

        if (state == 0) {
            g.drawImage(left[0], -200, 0, frame);
            g.drawImage(right[0], -200, 0, frame);
        } else {
            g.drawImage(left[1], -200, 0, frame);
            g.drawImage(right[1], -200, 0, frame);
        }
    }
}

