package client.menu;

import client.Window;
import client.utilities.Pallette;
import client.utilities.Settings;
import client.utilities.ThreadPool;
import client.utilities.Utils;
import protocol.Message;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * The panel which displays the instructions
 *
 * @Author Katelyn Wang
 * last updated 01/21/2019
 */
public class InstructionPanel extends DropDownPanel {
    private BufferedImage backgroundSprite;

    /**
     * Constructs an InstructionPanel
     *
     * @param window the Window to which it belongs and is displayed upon
     */
    InstructionPanel(Window window) {
        super(window);
        this.setLayout(null);

        JButton backButton = new JButton("Back");
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
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(backgroundSprite, 0, 0, null);
    }

    /**
     * Configures all the constant graphics for the instruction panel
     * (In the InstructionsPanel, nothing moves/animates/updates)
     * Draws it to a buffered image
     */
    void configureBackground() {
        backgroundSprite = Utils.createCompatibleImage(Utils.scale(750), Utils.scale(1334));
        Graphics2D g2D = (Graphics2D) backgroundSprite.getGraphics();
        g2D.setRenderingHints(Settings.QUALITY_RENDER_SETTINGS);

        //draws background drape
        g2D.drawImage(Utils.loadScaledImage("resources/menu/controls drape.png"), 0, 0, null);

        //draws the A and L keys
        g2D.drawImage(Utils.loadScaledImage("resources/instructions/A.png", 120, 120), Utils.scale(220), Utils.scale(530), null);
        g2D.drawImage(Utils.loadScaledImage("resources/instructions/L.png", 120, 120), Utils.scale(420), Utils.scale(530), null);
        //draws the bongo cat holding a trophy
        g2D.drawImage(Utils.loadScaledImage("resources/game/bongocat.png", 210, 100), Utils.scale(270), Utils.scale(780), null);
        g2D.drawImage(Utils.loadScaledImage("resources/instructions/trophy.png", 70, 92), Utils.scale(250), Utils.scale(800), null);

        //drawing the lines of instructions
        g2D.setColor(Pallette.OUTLINE_COLOR);
        g2D.setFont(Pallette.getScaledFont(Pallette.TEXT_FONT, 30));
        FontMetrics fontMetrics = g2D.getFontMetrics();
        g2D.drawString("Use the A and L key to",
                Utils.scale(375) - fontMetrics.stringWidth("Use the A and L key to") / 2, Utils.scale(450));
        g2D.drawString("press the left and right bongo.",
                Utils.scale(375) - fontMetrics.stringWidth("press the left and right bongo.") / 2, Utils.scale(500));

        g2D.drawString("Play the notes more accurately",
                Utils.scale(375) - fontMetrics.stringWidth("Play the notes more accurately") / 2, Utils.scale(700));
        g2D.drawString("to get a higher score!",
                Utils.scale(375) - fontMetrics.stringWidth("to get a higher score!") / 2, Utils.scale(750));

        g2D.setFont(Pallette.getScaledFont(Pallette.TITLE_FONT, 50));
        fontMetrics = g2D.getFontMetrics();
        //drawing the ampersand between the A and L keys
        g2D.drawString("&", Utils.scale(375) - fontMetrics.stringWidth("&") / 2, Utils.scale(610));
        //drawing the title
        g2D.drawString("Instructions", Utils.scale(375) - fontMetrics.stringWidth("Instructions") / 2, Utils.scale(400));

        g2D.dispose();
    }

    //messagable method ------------------------------------------------------------
    @Override
    public void notifyReceived(Message message) {

    }
}
