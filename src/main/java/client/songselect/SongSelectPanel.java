package client.songselect;

import client.GamePanel;
import client.components.Song;
import client.Window;
import client.components.Clock;
import client.utilities.Pallette;
import client.utilities.Settings;
import client.utilities.Utils;
import protocol.Protocol;
import protocol.TimeOverProtocol;

import javax.sound.sampled.Clip;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Panel where the user selects their song.
 *
 * @author Alston and Katelyn
 * last updated 1/13/2019
 */
public class SongSelectPanel extends GamePanel {

    private static final long SLIDE_DURATION = 500;

    private BufferedImage title;

    private AtomicBoolean isAnimating = new AtomicBoolean(false);

    private int selected = 0; //the index of the focused song tile
    private SongTile[] songTiles;

    /*
     * Contains the tiles that are in the frame of viewing, and therefore,
     * should be rendered. As, both the painting and animating threads access
     * this collection, adding and removing from it always occurs in a synchronized
     * block.
     */
    private final Deque<SongTile> viewFrame = new LinkedList<>();

    private Clock clock;

    private Clip currSong;


    /**
     * Constructs the SongSelectPanel by loading the songs stored
     * in the resources folder. If the resources fail to be loaded,
     * the program is killed.
     *
     * @param window the window that the panel belongs to
     */
    public SongSelectPanel(Window window) {
        super(window);

        Song[] songs; //load the songs
        try {
            songs = Song.getSongs();
        } catch (IOException e) {
            System.out.println("Could not load songs");
            System.exit(1);
            return;
        }

        this.songTiles = new SongTile[songs.length]; //create the tiles
        for (int i = 0; i < songs.length; i++) {
            songTiles[i] = new SongTile(songs[i]);
            songTiles[i].loadTile();
        }

        //set the view frame
        SongTile focus = songTiles[selected];
        focus.setX(0);
        synchronized (viewFrame) {
            viewFrame.addFirst(focus);
        }

        this.clock = new Clock(Utils.scale(375), Utils.scale(170), Utils.scale(60));
        clock.configureSprites();

        window.requestFocus();
    }

    @Override
    public void run() {
        super.run();
        configureTitle();
        clock.configureSprites();
        clock.start();
        switchSong(songTiles[selected].getAudio());
    }

    @Override
    public void notifyLeftPress() {
        moveRight();
    }


    @Override
    public void notifyLeftRelease() {
    }

    @Override
    public void notifyRightPress() {
        moveLeft();
    }

    @Override
    public void notifyRightRelease() {

    }

    @Override
    public void notifyHold() {
    }


    @Override
    public void notifyReceived(Protocol protocol) {
        if (protocol instanceof TimeOverProtocol) {

        }
    }

    private void switchSong(Clip song){
        if (currSong != null){
            currSong.stop();
        }
        currSong = song;
        if (currSong != null) {
            currSong.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;

        SongTile[] toRender; //the tiles that must be rendered
        synchronized (viewFrame) {
            toRender = viewFrame.toArray(new SongTile[0]);
        }

        for (SongTile tile : toRender) { //clamp all the tiles to draw them
            tile.clamp();
        }
        for (SongTile tile : toRender) { //draw all the tiles' backgrounds
            tile.drawBackground(g2D);
        }
        for (SongTile tile : toRender) { //draw all the tiles' foregrounds
            tile.drawForeground(g2D);
        }
        g2D.drawImage(title, 0,0, null);

        clock.draw(g2D);


    }

    /**
     * Retrieves from the array at index i. The array is treated as a closed loop
     * such that A[-1] = A[A.length - 1].
     *
     * @param i the index of the tile
     * @return the tile at index i
     */
    private SongTile getTile(int i) {
        while (i < 0) {
            i += songTiles.length;
        }
        return songTiles[i % songTiles.length];
    }

    /**
     * Moves the viewFrame one tile to the left over the span of {@link SongSelectPanel#SLIDE_DURATION}.
     * moveLeft() only triggers if the panel is not currently in an animating state.
     */
    private void moveLeft() {
        if (isAnimating.compareAndSet(false, true)) {

            SongTile center; //the tile at the center
            SongTile right = getTile(selected + 1); //the off-screen tile to the right
            synchronized (viewFrame) { //set the view frame
                center = viewFrame.getFirst();
                viewFrame.addLast(right);
            }

            //set the positions of the center and right tiles
            center.setX(0);
            right.setX(Settings.PANEL_SIZE.getWidth());

            //move the tiles
            long startTime = System.currentTimeMillis();
            long deltaTime = System.currentTimeMillis() - startTime;
            while (deltaTime < SLIDE_DURATION) {
                double shift = Settings.PANEL_SIZE.getWidth() * deltaTime / SLIDE_DURATION;
                center.setX(-shift);
                right.setX(Settings.PANEL_SIZE.getWidth() - shift);
                deltaTime = System.currentTimeMillis() - startTime;
            }
            center.setX(-Settings.PANEL_SIZE.getWidth());
            right.setX(0);

            synchronized (viewFrame) { //remove offscreen center tile from view frame
                viewFrame.pollFirst();
            }

            //decrement index
            selected--;
            switchSong(getTile(selected).getAudio());
            isAnimating.set(false);
        }
    }

    /**
     * Moves the viewFrame one tile to the right over the span of {@link SongSelectPanel#SLIDE_DURATION}.
     * moveRight() only triggers if the panel is not currently in an animating state.
     */
    private void moveRight() {
        if (isAnimating.compareAndSet(false, true)) {

            SongTile center;  //the tile at the center
            SongTile left = getTile(selected - 1); //the off-screen tile to the left
            synchronized (viewFrame) { //set the view frame
                center = viewFrame.getFirst();
                viewFrame.addFirst(left);
            }

            //set the positions of the center and right tiles
            center.setX(0);
            left.setX(-Settings.PANEL_SIZE.getWidth());

            //move the tiles
            long startTime = System.currentTimeMillis();
            long deltaTime = System.currentTimeMillis() - startTime;
            while (deltaTime < SLIDE_DURATION) {
                double shift = Settings.PANEL_SIZE.getWidth() * deltaTime / SLIDE_DURATION;
                center.setX(shift);
                left.setX(-Settings.PANEL_SIZE.getWidth() + shift);
                deltaTime = System.currentTimeMillis() - startTime;
            }
            center.setX(Settings.PANEL_SIZE.getWidth());
            left.setX(0);

            synchronized (viewFrame) { //remove offscreen center tile from view frame
                viewFrame.pollLast();
            }

            //increment index
            selected++;
            switchSong(getTile(selected).getAudio());
            isAnimating.set(false);
        }
    }

    void configureTitle(){
        title = Utils.createCompatibleImage(Utils.scale(750),Utils.scale(260));
        Graphics2D g2D = (Graphics2D)title.getGraphics();
        g2D.setColor(new Color(255,255,255,200));
        g2D.fillRect(0,0,Utils.scale(750),Utils.scale(260));
        g2D.setRenderingHints(Settings.QUALITY_RENDER_SETTINGS);
        g2D.setColor(Pallette.OUTLINE_COLOR);
        g2D.setStroke(new BasicStroke(Utils.scale(10)));
        g2D.drawRect(0,0,Utils.scale(750),Utils.scale(260));
        g2D.setFont(Utils.loadFont("resources/fonts/cloud.ttf", Utils.scale(50)));
        FontMetrics fontMetrics = g2D.getFontMetrics();
        g2D.drawString("Select Song", Utils.scale(375)-fontMetrics.stringWidth("Select Song")/2, Utils.scale(80));
    }

}
