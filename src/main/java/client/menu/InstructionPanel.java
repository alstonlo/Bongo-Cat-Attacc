package client.menu;

import client.Window;
import client.utilities.Pallette;
import client.utilities.ThreadPool;
import client.utilities.Utils;
import protocol.Message;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class InstructionPanel extends DropDownPanel {

    private BufferedImage drape = Utils.loadScaledImage("resources/menu/controls drape.png");

    private JButton backButton;

    private BufferedImage background;

    InstructionPanel(Window window) {
        super(window);
        this.setLayout(null);

        backButton = new JButton("Back");
        backButton.setFont(Pallette.getScaledFont(Pallette.TEXT_FONT, 25));
        backButton.setSize(Utils.scale(100), Utils.scale(70));
        backButton.setLocation(Utils.scale(90), Utils.scale(260));
        backButton.addActionListener(e -> ThreadPool.execute(() -> retract()));
        backButton.setBorder(null);
        backButton.setBackground(new Color(190, 207, 255));
        backButton.setForeground(Pallette.OUTLINE_COLOR);
        backButton.setFocusPainted(false);

        configureBackground();

        add(backButton);
    }

    @Override
    public void notifyReceived(Message message) {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(drape, 0, 0, null);
        g2D.drawImage(background,0,0,null);
    }

    void configureBackground(){
        background = Utils.createCompatibleImage(Utils.scale(750),Utils.scale(1334));
        Graphics2D g2D = (Graphics2D) background.getGraphics();
        g2D.drawImage(Utils.loadScaledImage("resources/instructions/A.png",Utils.scale(100),Utils.scale(100)),Utils.scale(300),Utils.scale(400),null);
        g2D.drawImage(Utils.loadScaledImage("resources/instructions/L.png",Utils.scale(100),Utils.scale(100)),Utils.scale(450),Utils.scale(400),null);
        g2D.setColor(Pallette.OUTLINE_COLOR);
        g2D.setFont(Pallette.getScaledFont(Pallette.TEXT_FONT, 30));
        FontMetrics fontMetrics = g2D.getFontMetrics();
        g2D.drawString("Use the A and L key to press the left and right bongo.",
                Utils.scale(375)-fontMetrics.stringWidth("Use the A and L key to press the left and right bongo.")/2,Utils.scale(450));
        g2D.setFont(Pallette.getScaledFont(Pallette.TITLE_FONT, 50));
        fontMetrics = g2D.getFontMetrics();
        g2D.drawString("Instructions",Utils.scale(375)-fontMetrics.stringWidth("Instructions")/2,Utils.scale(400));
    }
}
