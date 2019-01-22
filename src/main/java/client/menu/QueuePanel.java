package client.menu;

import client.Window;
import client.components.Clock;
import client.songselect.SongSelectPanel;
import client.utilities.Pallette;
import client.utilities.Settings;
import client.utilities.ThreadPool;
import client.utilities.Utils;
import protocol.JoinQueueMessage;
import protocol.MatchMadeMessage;
import protocol.Message;

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


    private AtomicBoolean lock = new AtomicBoolean(false);

    private AtomicBoolean animating = new AtomicBoolean(false);

    private BufferedImage drape = Utils.loadScaledImage("resources/menu/queue drape.png");

    private Clock clock;
    private int messageState = 0;
    private final long DOT_DURATION = 800;
    private String[] message = {"Finding Matches",
                                "Finding Matches.",
                                "Finding Matches..",
                                "Finding Matches..."};

    private float opacity = 0f;
    private final long SLIDE_DURATION = 500;
    private final long VS_ANIMATION_DURATION = 500;

    private QueueRectangle leftPanel;
    private QueueRectangle rightPanel;

    private Font vsFont = Pallette.getScaledFont(Pallette.TITLE_FONT, 80);
    private Font messageFont = Pallette.getScaledFont(Pallette.TEXT_FONT, 40);

    QueuePanel(Window window) {
        super(window);

        this.clock = new Clock(Utils.scale(375), Utils.scale(500), 80);
        this.leftPanel = new QueueRectangle(
                0, Settings.PANEL_SIZE.width / 2, Settings.PANEL_SIZE.height,
                new Color(245, 132, 148), "resources/menu/left bongo cat.png");
        this.leftPanel.setY(-Settings.PANEL_SIZE.height);
        this.rightPanel = new QueueRectangle(
                Settings.PANEL_SIZE.width / 2, Settings.PANEL_SIZE.width / 2, Settings.PANEL_SIZE.height,
                new Color(125, 151, 230), "resources/menu/right bongo cat.png");
        this.rightPanel.setY(Settings.PANEL_SIZE.height);

        this.setLayout(null);
        JButton matchMadeButton = new JButton("Match Made");
        matchMadeButton.setFont(Pallette.getScaledFont(Pallette.TITLE_FONT, 25));
        matchMadeButton.setSize(Utils.scale(200), Utils.scale(70));
        matchMadeButton.setLocation(Utils.scale(275), Utils.scale(900));
        matchMadeButton.addActionListener(e -> ThreadPool.execute(() -> {
            leftPanel.setUsername("Player 1");
            leftPanel.configureSprites();
            rightPanel.setUsername("Player 2");
            rightPanel.configureSprites();
        }));
        matchMadeButton.setBorder(null);
        matchMadeButton.setBackground(new Color(255, 221, 216));
        matchMadeButton.setForeground(Pallette.OUTLINE_COLOR);
        matchMadeButton.setFocusPainted(false);

        this.add(matchMadeButton);
    }

    @Override
    void pullDown() {
        /*if (window.getUsername().equals("")) {
            return; //don't do anything if the player hasn't logged in
        }*/

        if (lock.compareAndSet(false, true)) {
            ThreadPool.execute(this::animate);
            super.pullDown();

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            window.sendMessage(new JoinQueueMessage());
        }
    }

    @Override
    public void notifyReceived(Message message) {
        if (message instanceof MatchMadeMessage && getState() == DOWN_STATE) {
            MatchMadeMessage match = (MatchMadeMessage) message;
            transition(match.host, match.guest);
        }
    }

    private void animate() {
        animating.set(true);

        clock.configureSprites();
        clock.start();

        long startTime = System.currentTimeMillis();
        while (animating.get()) {
            messageState = (int) ((System.currentTimeMillis() - startTime) / DOT_DURATION) % 4;
        }

        clock.stop();
    }

    private void stop() {
        animating.set(false);
    }

    private void transition(String host, String guest) {

        long startTime = System.currentTimeMillis();
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

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        window.displayBasePanel(new SongSelectPanel(window, "user1", "user2"));
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
        g2D.drawImage(drape, 0, 0, null);

        clock.draw(g2D);

        g2D.setFont(messageFont);
        FontMetrics fontMetrics = g2D.getFontMetrics();
        g2D.drawString(message[messageState], Utils.scale(375) - fontMetrics.stringWidth("Finding Match") / 2, Utils.scale(690));

        leftPanel.draw(g2D);
        rightPanel.draw(g2D);

        if (opacity != 0f) {
            g2D.setComposite(AlphaComposite.SrcOver.derive(opacity)); //drawing the "vs."
            g2D.setRenderingHints(Settings.QUALITY_RENDER_SETTINGS);
            g2D.setFont(vsFont);
            g2D.setColor(Pallette.OUTLINE_COLOR);
            fontMetrics = g2D.getFontMetrics();
            g2D.drawString("vs.", Utils.scale(375) - fontMetrics.stringWidth("vs.") / 2, Utils.scale(690));
            g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER)); //resetting opacity
        }
    }
}
