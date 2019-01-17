package client.songselect;

import client.GamePanel;
import client.Song;
import client.Window;
import client.utilities.Settings;
import client.utilities.Utils;
import protocol.Protocol;
import protocol.TimeOverProtocol;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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

    private AtomicBoolean isAnimating = new AtomicBoolean(false);

    private int selected = 0; //the index of the focused song tile
    private SongTile[] songTiles;
    private Clock clock;

    /*
     * Contains the tiles that are in the frame of viewing, and therefore,
     * should be rendered. As, both the painting and animating threads access
     * this collection, adding and removing from it always occurs in a synchronized
     * block.
     */
    private final Deque<SongTile> viewFrame = new LinkedList<>();

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

        clock = new Clock(Utils.scale(650),Utils.scale(250),60,Utils.scale(60));
    }

    @Override
    public void run() {
        super.run();
        clock.start();
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
    public void notifyConnected() {

    }

    @Override
    public void notifyReceived(Protocol protocol) {
        if (protocol instanceof TimeOverProtocol){

        }
    }

    @Override
    public void notifyDisconnected() {

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
        g2D.setColor(new Color(182, 239, 242));
        g2D.fillRect(0,0,Utils.scale(750),Utils.scale(150));
        g2D.setColor(new Color(60, 51, 2));
        g2D.setFont(Utils.loadFont("resources/mon.otf",20));
        g2D.drawString("Select Song",40,50);

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
            isAnimating.set(false);
        }
    }

}
