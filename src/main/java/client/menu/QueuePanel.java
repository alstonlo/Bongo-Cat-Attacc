package client.menu;

import client.Window;
import client.components.Clock;
import client.utilities.Pallette;
import client.utilities.Settings;
import client.utilities.ThreadPool;
import client.utilities.Utils;
import protocol.JoinQueueProtocol;

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
    private MenuPanel menuPanel;

    private BufferedImage settingDrape = Utils.loadScaledImage("resources/menu/controls drape.png");

    private AtomicBoolean lock = new AtomicBoolean(false);
    private AtomicBoolean matchMade = new AtomicBoolean(false);

    private Clock clock;
    private int messageState = 0;
    private double secondsPerDot = 0.8;
    private String[] message = {"Finding Matches", "Finding Matches.", "Finding Matches..", "Finding Matches..."};

    private final long SLIDE_DURATION = 500;
    private final long VS_ANIMATION_DURATION = 500;
    private float opacity = 0f;

    private QueueRectangle leftPanel;
    private QueueRectangle rightPanel;

    private String user1;
    private String user2;

    private Font vsFont = Utils.loadFont("resources/cloud.ttf", Utils.scale(80));

    QueuePanel(Window window, MenuPanel menuPanel) {
        super(window);
        this.menuPanel = menuPanel;

        JButton backButton = new JButton("Back");
        backButton.setFont(Utils.loadFont("moon.otf", Utils.scale(25)));
        backButton.setSize(Utils.scale(100), Utils.scale(70));
        backButton.setLocation(Utils.scale(90), Utils.scale(260));
        backButton.addActionListener(e -> matchMade("Username 1", "Username 2"));
        backButton.setBorder(null);
        backButton.setBackground(new Color(255, 221, 216));
        backButton.setForeground(Pallette.OUTLINE_COLOR);
        backButton.setFocusPainted(false);
        add(backButton);

        this.clock = new Clock(Utils.scale(375), Utils.scale(500), 80);
        this.leftPanel = new QueueRectangle(
                0, Settings.PANEL_SIZE.width / 2, Settings.PANEL_SIZE.height,
                new Color(245, 132, 148), "resources/menu/left bongo cat.png");
        this.leftPanel.setY(-Settings.PANEL_SIZE.height);
        this.rightPanel = new QueueRectangle(
                Settings.PANEL_SIZE.width / 2, Settings.PANEL_SIZE.width / 2, Settings.PANEL_SIZE.height,
                new Color(125, 151, 230),"resources/menu/right bongo cat.png");
        this.rightPanel.setY(Settings.PANEL_SIZE.height);
    }

    @Override
    void pullDown() {
        if (lock.compareAndSet(false, true)) {
            clock.configureSprites();
            ThreadPool.execute(this::animate);
            super.pullDown();
        }
    }

    void matchMade(String user1, String user2) {
        this.user1 = user1;
        this.user2 = user2;
        leftPanel.setUsername(user1);
        rightPanel.setUsername(user2);
        leftPanel.configureSprites();
        rightPanel.configureSprites();
        matchMade.set(true);
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
        g2D.setFont(Utils.loadFont("moon.otf", Utils.scale(50)));
        FontMetrics fontMetrics = g2D.getFontMetrics();
        g2D.drawString(message[messageState], Utils.scale(375) - fontMetrics.stringWidth("Finding Match") / 2, Utils.scale(690));

        leftPanel.draw(g2D);
        rightPanel.draw(g2D);

        if (opacity != 0f) {
            g2D.setComposite(AlphaComposite.SrcOver.derive(opacity)); //drawing the "vs."
            g2D.setFont(vsFont);
            g2D.setColor(Pallette.OUTLINE_COLOR);
            fontMetrics = g2D.getFontMetrics();
            g2D.drawString("vs.", Utils.scale(375) - fontMetrics.stringWidth("vs.") / 2, Utils.scale(690));
            g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER)); //resetting opacity
        }
    }

    private void animate() {
        clock.start();

        long startTime = System.currentTimeMillis();
        while (!matchMade.get()) {
            messageState = Utils.round((System.currentTimeMillis() - startTime) / (1000.0 * secondsPerDot)) % 4;
        }

        startTime = System.currentTimeMillis();
        double deltaTime = 0;
        while (deltaTime < SLIDE_DURATION) {
            double y = Settings.PANEL_SIZE.height * (deltaTime / SLIDE_DURATION);
            leftPanel.setY(-Settings.PANEL_SIZE.height + y);
            rightPanel.setY(Settings.PANEL_SIZE.height - y);
            deltaTime = System.currentTimeMillis() - startTime;
        }
        leftPanel.setY(0);
        rightPanel.setY(0);

        opacity = 0f;
        startTime = System.currentTimeMillis();
        deltaTime = 0;
        while (deltaTime < VS_ANIMATION_DURATION) {
            opacity = (float) (deltaTime / VS_ANIMATION_DURATION);
            deltaTime = System.currentTimeMillis() - startTime;
        }
        opacity = 1f;

        clock.stop();
        window.switchState(Window.SONG_SELECT_STATE);
    }

}
