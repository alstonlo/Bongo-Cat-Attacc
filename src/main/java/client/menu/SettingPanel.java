package client.menu;

import client.Window;
import client.utilities.ThreadPool;
import client.utilities.Utils;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class SettingPanel extends DropDownPanel{
    private BufferedImage settingDrape = Utils.loadScaledImage("resources/menu/controls drape.png");

    private MenuPanel menuPanel;

    private JButton backButton;

    SettingPanel(Window window, MenuPanel menuPanel){
        super(window);
        this.menuPanel = menuPanel;
        this.setLayout(null);

        backButton = new JButton("Back");
        backButton.setSize(Utils.scale(100), Utils.scale(70));
        backButton.setLocation(Utils.scale(90), Utils.scale(260));
        backButton.addActionListener(e -> ThreadPool.execute(() -> retract()));
        backButton.setBorder(null);
        backButton.setBackground(new Color(190, 207, 255));
        backButton.setForeground(new Color(60,51,28));
        backButton.setFocusPainted(false);

        add(backButton);

        setVisible(true);
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
    }

    @Override
    void retract() {
        super.retract();
    }
}
