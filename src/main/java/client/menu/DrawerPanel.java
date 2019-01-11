package client.menu;

import client.Window;

import javax.swing.JPanel;

abstract class DrawerPanel extends JPanel {

    protected Window window;

    private final long SLIDE_DURATION = 500;

    DrawerPanel(Window window) {
        this.window = window;
        this.setLocation(0, -window.scale(1334));
        this.setSize(window.scale(750), window.scale(1334));
        this.setVisible(true);
    }

    void pullDown() {
        window.setFocusable(false);
    }

    void pullUp() {
        window.setFocusable(true);
    }
}

