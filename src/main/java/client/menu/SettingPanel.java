package client.menu;

import client.Window;
import client.utilities.Pallette;
import client.utilities.ThreadPool;
import client.utilities.Utils;
import protocol.Protocol;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class SettingPanel extends DropDownPanel {

    private BufferedImage drape = Utils.loadScaledImage("resources/menu/controls drape.png");

    private JButton backButton;

    SettingPanel(Window window) {
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

        add(backButton);
    }

    @Override
    public void notifyReceived(Protocol protocol) {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(drape, 0, 0, null);
    }
}
