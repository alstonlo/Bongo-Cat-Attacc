package client.menu;

import client.Window;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class InstructionPanel extends DrawerPanel {
    private Window window;

    InstructionPanel(Window window) {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        g2D.scale(window.getScale(), window.getScale());

        g2D.drawString("Instructions lmao", 0, 0);
    }
}
