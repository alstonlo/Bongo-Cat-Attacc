package client.songselect;

import client.GamePanel;
import client.Song;
import client.Window;
import client.utilities.Settings;
import protocol.Protocol;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Panel where the user selects their song.
 *
 * @author Alston
 * last updated 1/13/2019
 */
public class SongSelectPanel extends GamePanel {

    private static final long SLIDE_DURATION = 500;

    private AtomicBoolean isAnimating = new AtomicBoolean(false);

    private int selected = 0; //the index of the focused song tile
    private SongTile[] songTiles;

    /*
     *
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

    }

    @Override
    public void notifyDisconnected() {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;

        SongTile[] toRender;
        synchronized (viewFrame) {
            toRender = viewFrame.toArray(new SongTile[0]);
        }

        for (SongTile tile : toRender) {
            tile.clamp();
        }
        for (SongTile tile : toRender) {
            tile.drawBackground(g2D);
        }
        for (SongTile tile : toRender) {
            tile.drawForeground(g2D);
        }
    }

    private SongTile getTile(int i) {
        while (i < 0) {
            i += songTiles.length;
        }
        return songTiles[i % songTiles.length];
    }

    private void moveLeft() {
        if (isAnimating.compareAndSet(false, true)) {

            SongTile center;
            SongTile right = getTile(selected + 1);

            synchronized (viewFrame) {
                center = viewFrame.getFirst();
                viewFrame.addLast(right);
            }

            center.setX(0);
            right.setX(Settings.PANEL_SIZE.getWidth());

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

            synchronized (viewFrame) {
                viewFrame.pollFirst();
            }

            selected--;
            isAnimating.set(false);
        }
    }


    private void moveRight() {
        if (isAnimating.compareAndSet(false, true)) {

            SongTile center;
            SongTile left = getTile(selected - 1);

            synchronized (viewFrame) {
                center = viewFrame.getFirst();
                viewFrame.addFirst(left);
            }

            center.setX(0);
            left.setX(-Settings.PANEL_SIZE.getWidth());

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

            synchronized (viewFrame) {
                viewFrame.pollLast();
            }

            selected++;
            isAnimating.set(false);
        }
    }

}
