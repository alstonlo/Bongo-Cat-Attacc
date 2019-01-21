package client.menu;


import client.GamePanel;
import client.utilities.Pallette;
import client.utilities.Settings;
import client.utilities.ThreadPool;
import client.utilities.Utils;
import protocol.Message;
import client.Window;

import javax.swing.JButton;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class EndGamePanel extends GamePanel {
    private double accuracy;
    private BufferedImage background;
    public EndGamePanel(Window window, double accuracy){
        super(window);
        this.accuracy = accuracy;
        setLayout(null);

        JButton menuButton = new JButton("Main Menu");
        menuButton.setFont(Pallette.getScaledFont(Pallette.TITLE_FONT, 25));
        menuButton.setSize(Utils.scale(200), Utils.scale(70));
        menuButton.setLocation(Utils.scale(275), Utils.scale(750));
        menuButton.addActionListener(e -> ThreadPool.execute(() -> returnToMenu()));
        menuButton.setBorder(null);
        menuButton.setBackground(new Color(255, 221, 216));
        menuButton.setForeground(Pallette.OUTLINE_COLOR);
        menuButton.setFocusPainted(false);

        add(menuButton);

        configureBackground();

        setVisible(true);

    }

    private void returnToMenu(){
        window.requestFocus();
        window.switchPanel(new MenuPanel(window));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D)g;
        g2D.drawImage(background,0,0,null);
    }

    void configureBackground(){
        background = Utils.createCompatibleImage(Utils.scale(750),Utils.scale(1334));
        Graphics2D g2D = (Graphics2D) background.getGraphics();
        g2D.setComposite(AlphaComposite.SrcOver.derive(0.6f));
        g2D.drawImage(Utils.loadScaledImage("resources/game/gameoverbackground.png",750,1334),0,0,null);
        g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        g2D.setColor(new Color(255,255,255,220));
        g2D.fillRect(Utils.scale(50),Utils.scale(530),Utils.scale(650),Utils.scale(190));
        g2D.setRenderingHints(Settings.QUALITY_RENDER_SETTINGS);
        g2D.setFont(Pallette.getScaledFont(Pallette.TITLE_FONT,40));
        g2D.setStroke(new BasicStroke(3));
        g2D.setColor(Pallette.OUTLINE_COLOR);
        g2D.drawRect(Utils.scale(50),Utils.scale(530),Utils.scale(650),Utils.scale(190));
        FontMetrics fontMetrics = g2D.getFontMetrics();
        g2D.drawString("Game Over!", Utils.scale(375)-fontMetrics.stringWidth("Game Over!")/2, Utils.scale(600));
        g2D.drawString(String.format("Your accuracy was: %.2f",accuracy)+"%",
                Utils.scale( 375)-fontMetrics.stringWidth(String.format("Your accuracy was: %.2f",accuracy)+"%")/2, Utils.scale(660) );
        g2D.dispose();
    }

    @Override
    public void notifyReceived(Message message) {

    }

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
