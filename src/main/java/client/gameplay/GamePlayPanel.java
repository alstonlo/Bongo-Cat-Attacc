package client.gameplay;

import client.GamePanel;
import client.Window;
import client.components.Clock;
import client.components.Song;
import client.menu.EndGamePanel;
import client.utilities.Pallette;
import client.utilities.ThreadPool;
import client.utilities.Utils;
import protocol.Message;

import javax.sound.sampled.Clip;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * The panel on which game play occurs
 *
 * @author Katelyn Wang
 * last updated 01/21/2019
 */
public class GamePlayPanel extends GamePanel {
    private BufferedImage backgroundSprite;
    private NoteManager noteManager;

    private Clock clock;
    private Song song;
    private Clip playingSong;
    private float alpha = 1f;
    private double accuracy;

    /**
     * Constructs a GamePlayPanel
     *
     * @param window the Window to which this panel belongs and is painted upon
     * @param song   the song selected (which the game will be played based on)
     */
    public GamePlayPanel(Window window, Song song) {
        super(window);
        this.song = song;
    }

    /*Animatable method, overridden to create clock and note manager objects,
    configure sprites and start the music upon running
    */
    @Override
    public void run() {
        super.run();
        configureSprites();
        window.requestFocus();
        clock = new Clock(Utils.scale(100), Utils.scale(100), song.getDuration() * 1000, Utils.scale(60));
        clock.configureSprites();
        clock.start();
        noteManager = new NoteManager(song, this);
        noteManager.run();
        if (song.getAudio() != null) {
            playingSong = song.getAudio();
            playingSong.start();
        }
    }

    /**
     * Paints all the elements on the screen
     *
     * @param g Graphics object with which to draw items
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setComposite(AlphaComposite.SrcOver.derive(alpha));
        g2D.drawImage(backgroundSprite, 0, 0, null);
        if (noteManager != null) {
            noteManager.draw(g2D);
        }
        if (clock != null) {
            clock.draw(g2D);
        }
    }

    /**
     * Method to close the game (needed to pass in the parameter of accuracy, and start the thread of
     * closing the game)
     *
     * @param accuracy the final accuracy of the player
     */
    protected void closeGame(double accuracy) {
        this.accuracy = accuracy;
        ThreadPool.execute(this::close);
    }

    /**
     * The method to end the game and close the panel. Does the following things:
     * 1) Stops the clock
     * 2) Stops the song (to ensure the audio doesn't continue into next panel)
     * 3) Fades the gameplaypanel to white
     * 4) Switches the panel to the endgamepanel
     */
    private void close() {
        clock.stop();
        long startTime = System.currentTimeMillis();
        if (playingSong != null) {
            playingSong.stop();
        }
        while (alpha > 0f) {
            alpha = 1f - ((System.currentTimeMillis() - startTime) / 3000f);
        }
        alpha = 0f;
        window.displayBasePanel(new EndGamePanel(window,accuracy, 0.0,"Player 1", "Player 2"));
    }

    /**
     * Creates a buffered image of all the constant elements to be drawn in the background of the screen
     */
    private void configureSprites() {
        backgroundSprite = Utils.createCompatibleImage(Utils.scale(750), Utils.scale(1334));
        Graphics2D g2D = (Graphics2D) backgroundSprite.getGraphics();

        //drawing the grass background and bongocat image
        g2D.drawImage(Utils.loadScaledImage("resources/game/grass.png", 750, 850), 0, Utils.scale(484), null);
        g2D.drawImage(Utils.loadScaledImage("resources/game/bongocat.png", 200, 95), Utils.scale(275), Utils.scale(540), null);

        g2D.setColor(Pallette.OUTLINE_COLOR);

        //drawing the horizontal line where the notes should be played
        g2D.setStroke(new BasicStroke(3));
        g2D.drawLine(Utils.scale(100), Utils.scale(1150), Utils.scale(650), Utils.scale(1150));

        //drawing the two borders of the note lane
        g2D.setStroke(new BasicStroke(5));
        g2D.drawLine(Utils.scale(300), Utils.scale(634), Utils.scale(0), Utils.scale(1334));
        g2D.drawLine(Utils.scale(450), Utils.scale(634), Utils.scale(750), Utils.scale(1334));

        //drawing the accuracy header
        g2D.setFont(Pallette.getScaledFont(Pallette.TITLE_FONT,30));
        g2D.drawString("Current",Utils.scale(25), Utils.scale(220));
        g2D.drawString("Accuracy:",Utils.scale(25), Utils.scale(250));

        g2D.dispose();
    }

    //Controllable methods ------------------------------------------------------------------------------------

    @Override
    public void notifyRightPress() {
        noteManager.notifyRightPress();
    }

    @Override
    public void notifyRightRelease() {

    }

    @Override
    public void notifyLeftPress() {
        noteManager.notifyLeftPress();
    }

    @Override
    public void notifyLeftRelease() {

    }

    @Override
    public void notifyHold() {

    }

    //Messagable methos ------------------------------------------------------------------------------------

    @Override
    public void notifyReceived(Message message) {

    }
}
