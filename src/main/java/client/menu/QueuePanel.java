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
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The panel which queues players as the server tries to match them with an opponent
 *
 * @Author Katelyn and Alston
 * last updated 01/21/19
 */
public class QueuePanel extends DropDownPanel {


    private AtomicBoolean lock = new AtomicBoolean(false);

    private BufferedImage drape = Utils.loadScaledImage("resources/menu/queue drape.png");

    private Clock clock;

    private final long DOT_DURATION = 800;
    private String message = "Finding Matches";
    private Timer messageAnimator;
    private Font messageFont = Pallette.getScaledFont(Pallette.TEXT_FONT, 40);

    QueuePanel(Window window) {
        super(window);

        this.clock = new Clock(Utils.scale(375), Utils.scale(500), Utils.scale(80));
        this.messageAnimator = new Timer();

        this.setLayout(null);
        JButton matchMadeButton = new JButton("Match Made");
        matchMadeButton.setFont(Pallette.getScaledFont(Pallette.TITLE_FONT, 25));
        matchMadeButton.setSize(Utils.scale(200), Utils.scale(70));
        matchMadeButton.setLocation(Utils.scale(275), Utils.scale(900));
        matchMadeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ThreadPool.execute(() -> notifyReceived(new MatchMadeMessage("You", "Other")));
            }
        });
        matchMadeButton.setBorder(null);
        matchMadeButton.setBackground(new Color(255, 221, 216));
        matchMadeButton.setForeground(Pallette.OUTLINE_COLOR);
        matchMadeButton.setFocusPainted(false);

        this.add(matchMadeButton);
    }

    @Override
    void pullDown() {
        if (lock.compareAndSet(false, true)) {
            super.pullDown();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            window.sendMessage(new JoinQueueMessage());
        }
    }

    @Override
    public void run() {
        clock.configureSprites();
        clock.start();

        messageAnimator.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                message += ".";
                if (message.equals("Finding Matches....")) {
                    message = "Finding Matches";
                }
            }
        }, 0, DOT_DURATION);
    }

    @Override
    public void stop() {
        clock.stop();
        messageAnimator.cancel();
    }

    @Override
    public void notifyReceived(Message message) {
        if (message instanceof MatchMadeMessage) {
            MatchMadeMessage match = (MatchMadeMessage) message;
            String you = window.getUsername();
            boolean hosting = match.host.equals(you);
            String opponent = (hosting) ? match.guest : match.host;

            VsTransitionPanel vsTransition = new VsTransitionPanel(window, hosting, opponent);
            window.addPanel(2, vsTransition);

            SongSelectPanel songSelect = new SongSelectPanel(window, hosting, opponent);
            window.addBasePanel(songSelect);

            retract();
            stop();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            window.removePanel(vsTransition);
        }
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

        g2D.setRenderingHints(Settings.QUALITY_RENDER_SETTINGS);
        g2D.setColor(Pallette.OUTLINE_COLOR);
        g2D.setFont(messageFont);
        FontMetrics fontMetrics = g2D.getFontMetrics();
        g2D.drawString(message, Utils.scale(355) - fontMetrics.stringWidth("Finding Match") / 2, Utils.scale(690));
        g2D.setRenderingHints(Settings.DEFAULT_RENDER_SETTINGS);
    }
}
