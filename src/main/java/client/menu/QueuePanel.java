package client.menu;

import client.Window;
import client.components.Clock;
import client.utilities.Pallette;
import client.utilities.ThreadPool;
import client.utilities.Utils;

import javax.swing.JButton;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicBoolean;

public class QueuePanel extends DropDownPanel {
    private Window window;

    private BufferedImage settingDrape = Utils.loadScaledImage("resources/menu/controls drape.png");

    private Clock clock;

    private int currY = Utils.scale(1335);
    private double speed = (double) Utils.scale(4.0); //pixels per millisecond
    private String[] message = {"Finding Match", "Finding Match.", "Finding Match..", "Finding Match..."};
    private int currState = 0;
    private double secondsPerDot = (double) Utils.scale(0.8);

    AtomicBoolean running = new AtomicBoolean(true);
    private boolean showVS = false;
    private float opacity = 0f;

    private QueueRectangle leftPanel = new QueueRectangle(Utils.scale(375), Utils.scale(1334),new Color(245, 132, 148));
    private QueueRectangle rightPanel = new QueueRectangle(Utils.scale(375), Utils.scale(1334),new Color(125, 151,230));

    private Font vsFont = Utils.loadFont("resources/cloud.ttf", Utils.scale(80));

    QueuePanel(Window window){
        super(window);
        this.window = window;
        clock = new Clock(Utils.scale(375), Utils.scale(500),80);

        JButton backButton = new JButton("Back");
        backButton.setFont(Utils.loadFont("moon.otf", Utils.scale(25)));
        backButton.setSize(Utils.scale(100), Utils.scale(70));
        backButton.setLocation(Utils.scale(90), Utils.scale(260));
        backButton.addActionListener(e -> ThreadPool.execute(() -> matchMade()));
        backButton.setBorder(null);
        backButton.setBackground(new Color(255, 221, 216));
        backButton.setForeground(Pallette.OUTLINE_COLOR);
        backButton.setFocusPainted(false);
        add(backButton);

        setVisible(true);
    }

    @Override
    void pullDown() {
        ThreadPool.execute(() -> run());
        super.pullDown();
    }

    @Override
    void retract() {
        super.retract();
        clock.stop();
    }

    void matchMade(){
        ThreadPool.execute(() -> updateAnimation());
    }

    /**
     * {@inheritDoc}
     *
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(settingDrape, 0, 0, null);

        clock.draw(g2D);

        g2D.setFont(Utils.loadFont("moon.otf",Utils.scale(50)));
        FontMetrics fontMetrics = g2D.getFontMetrics();
        g2D.drawString(message[currState], Utils.scale(375)-fontMetrics.stringWidth("Finding Match")/2, Utils.scale(690));

        leftPanel.draw(g2D,0,-currY);
        rightPanel.draw(g2D, Utils.scale(375),currY);

        if (showVS){
            g2D.setComposite(AlphaComposite.SrcOver.derive(opacity));
            g2D.setFont(vsFont);
            g2D.setColor(Pallette.OUTLINE_COLOR);
            fontMetrics = g2D.getFontMetrics();
            g2D.drawString("vs.", Utils.scale(375)-fontMetrics.stringWidth("vs.")/2, Utils.scale(690));
            g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        }
    }

    private void run(){
        long startTime = System.currentTimeMillis();
        while (running.get()){
            currState = Utils.round((System.currentTimeMillis()-startTime)/(1000.0*secondsPerDot)%3);
        }
    }

    private void updateAnimation(){
        running.set(false);
        long startTime = System.currentTimeMillis();
        while (currY > 0){
            currY = Utils.scale(1335) - (int) Math.round((System.currentTimeMillis()-startTime)*speed);
        }
        currY = 0;
        showVS = true;
        startTime = System.currentTimeMillis();
        while (opacity < 1f){
            opacity = (System.currentTimeMillis()-startTime)*0.003f;
        }
        opacity = 1f;
        startTime = System.currentTimeMillis();
        while (System.currentTimeMillis()-startTime < 5000){

        }
        window.switchState(Window.SONG_SELECT_STATE);
    }
}
