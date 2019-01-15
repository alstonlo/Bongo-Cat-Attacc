package client.menu;

import client.Window;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class InstructionPanel extends DropDownPanel {

    InstructionPanel(Window window, MenuPanel menuPanel) {
        super(window);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;

        g2D.drawString("Instructions lmao", 0, 0);
    }
}
