package client.gameplay;

import client.GamePanel;
import client.Window;
import client.components.Clock;
import client.components.Song;
import client.menu.EndGamePanel;
import client.utilities.Pallette;
import client.utilities.ThreadPool;
import client.utilities.Utils;

import javax.sound.sampled.Clip;
import javax.swing.SwingUtilities;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * The panel on which game play occurs
 *
 * @author Katelyn and Alston
 * last updated 01/21/2019
 */
public class GamePlayPanel extends GamePanel {

    private NoteManager noteManager;

    private Clock clock;
    private BufferedImage backgroundSprite;

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
        this.clock = new Clock(Utils.scale(100), Utils.scale(100), song.getDuration() * 1000, Utils.scale(60));
        this.clock.configureSprites();
        this.noteManager = new NoteManager(song, this);
        this.backgroundSprite = loadBackgroundSprite();
    }

    /*Animatable method, overridden to create clock and note manager objects,
    configure sprites and start the music upon running
    */
    @Override
    public void run() {
        super.run();

        clock.start();
        noteManager.run();

        if (song.getAudio() != null) {
            playingSong = song.getAudio();
            playingSong.start();
        }

        SwingUtilities.invokeLater(window::requestFocus);
    }

    /**
     * Method to close the game (needed to pass in the parameter of accuracy, and start the thread of
     * closing the game)
     *
     * @param accuracy the final accuracy of the player
     */
    void closeGame(double accuracy) {
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
        noteManager.close();
        clock.stop();
        if (playingSong != null) {
            playingSong.stop();
        }

        long startTime = System.currentTimeMillis();
        while (alpha > 0f) {
            alpha = 1f - ((System.currentTimeMillis() - startTime) / 3000f);
        }
        alpha = 0f;

        window.addBasePanel(new EndGamePanel(window, accuracy, 0.0, "Player 1", "Player 2"));
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
        noteManager.draw(g2D);
        clock.draw(g2D);
        g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
    }

    /**
     * Creates a buffered image of all the constant elements to be drawn in the background of the screen
     */
    private BufferedImage loadBackgroundSprite() {
        BufferedImage sprite = Utils.createCompatibleImage(Utils.scale(750), Utils.scale(1334));
        Graphics2D g2D = (Graphics2D) sprite.getGraphics();

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
        g2D.setFont(Pallette.getScaledFont(Pallette.TITLE_FONT, 30));
        g2D.drawString("Current", Utils.scale(25), Utils.scale(220));
        g2D.drawString("Accuracy:", Utils.scale(25), Utils.scale(250));

        g2D.dispose();
        return sprite;
    }

    //Controllable methods ------------------------------------------------------------------------------------

    @Override
    public void notifyRightPress() {
        noteManager.notifyRightPress();
    }

    @Override
    public void notifyLeftPress() {
        noteManager.notifyLeftPress();
    }

}
