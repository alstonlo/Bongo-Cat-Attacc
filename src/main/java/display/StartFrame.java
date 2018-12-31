package display;

import utilities.Utils;

import javax.swing.JFrame;
import java.util.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;

/**
 * The window which pops up upon launching before the program starts up
 * Displays our logo
 *
 * @author Katelyn Wang
 * last updated 2018/12/11
 */
public class StartFrame extends JFrame {
    private static final Image bongoCat = Toolkit.getDefaultToolkit().getImage("res/bongo.png"); //retrieves the image
    private static final Image mouth = Toolkit.getDefaultToolkit().getImage("res/m1.png"); //retrieves the image
    private static final Image[] left = {Toolkit.getDefaultToolkit().getImage("res/l1.png"), Toolkit.getDefaultToolkit().getImage("res/l2.png")};
    private static final Image[] right = {Toolkit.getDefaultToolkit().getImage("res/r2.png"), Toolkit.getDefaultToolkit().getImage("res/r1.png")};
    private int state = 0;
    private Graphics gr;
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
        this.setUndecorated(true);
        this.setResizable(false); //doesn't allow the screen to be resized
        this.setAlwaysOnTop(true); //sets it above all the other panels
        this.setSize(600, 500); //hardcoded to our assets (our image we use is 500x500 so it won't ever need to be changed)
        this.setLocationRelativeTo(null); //centers it
        this.setBackground(new Color(255,255,255));
        this.setVisible(true);

    }

    /**
     * Draws the background image
     *
     * @param g The graphic object to draw images
     */
    @Override
    public void paint(Graphics g) {
        this.gr = g;
        super.paint(g);

        g.drawImage(bongoCat, -200,0, this);
        g.drawImage(mouth, -200,100, this);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (state == 0){
                    state = 1;
                    gr.drawImage(left[0], -200,100, frame);
                    gr.drawImage(right[0], -200,100, frame);
                } else {
                    state = 0;
                    gr.drawImage(left[1], -200,100, frame);
                    gr.drawImage(right[1], -200,100, frame);
                }
                repaint();
            }
        }, 0, 1000);


    }
}



    /*@Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(new Color(198, 245, 248));
        g.drawRect(0,0,1000,1000);
        g.drawImage(bongoCat, 0,0, this);
        if (state == 0){
            state = 1;
            g.drawImage(left[0], 0,0, this);
            g.drawImage(right[0], 0,0, this);
        } else {
            state = 0;
            g.drawImage(left[1], 0,0, this);
            g.drawImage(right[1], 0,0, this);
        }
*/
       /* try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        super.repaint();*/
