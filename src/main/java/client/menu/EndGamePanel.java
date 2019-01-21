package client.menu;


import client.GamePanel;
import client.utilities.Pallette;
import client.utilities.ThreadPool;
import client.utilities.Utils;
import protocol.Message;
import client.Window;

import javax.swing.JButton;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class EndGamePanel extends GamePanel {
    private double accuracy;
    public EndGamePanel(Window window, double accuracy){
        super(window);
        this.accuracy = accuracy;
        setLayout(null);

        JButton menuButton = new JButton("Main Menu");
        menuButton.setFont(Pallette.getScaledFont(Pallette.TEXT_FONT, 25));
        menuButton.setSize(Utils.scale(200), Utils.scale(70));
        menuButton.setLocation(Utils.scale(90), Utils.scale(260));
        menuButton.addActionListener(e -> ThreadPool.execute(() -> returnToMenu()));
        menuButton.setBorder(null);
        menuButton.setBackground(new Color(255, 221, 216));
        menuButton.setForeground(Pallette.OUTLINE_COLOR);
        menuButton.setFocusPainted(false);

        add(menuButton);
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
        g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        g2D.setFont(Pallette.getScaledFont(Pallette.TITLE_FONT,30));
        g2D.drawString("Game Over!", Utils.scale(150), Utils.scale(600));
        g2D.drawString(String.format("Your accuracy was: %.2f",accuracy)+"%", Utils.scale(150), Utils.scale(800) );
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
