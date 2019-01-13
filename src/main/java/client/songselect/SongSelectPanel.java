package client.songselect;

import client.GamePanel;
import client.Window;
import protocol.Protocol;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicBoolean;

public class SongSelectPanel extends GamePanel {

    private static final long SLIDE_DURATION = 500;

    private AtomicBoolean isAnimating = new AtomicBoolean(false);

    private int selected = 0;
    private SongTile[] songTiles;
    private final Deque<SongTile> viewFrame = new ArrayDeque<>();

    public SongSelectPanel(Window window) {
        super(window);

        Song[] songs = SongBank.getSongs();
        this.songTiles = new SongTile[songs.length];
        for (int i = 0; i < songs.length; i++) {
            songTiles[i] = new SongTile(songs[i]);
            songTiles[i].loadTile();
        }

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

//        if (center != null) {
//            center.clamp();
//            center.drawBackground(g2D);
//        }
//        if (side != null) {
//            side.clamp();
//            side.drawBackground(g2D);
//        }
//
//        if (center != null) {
//            center.drawForeground(g2D);
//        }
//        if (side != null) {
//            side.drawForeground(g2D);
//        }
    }

    private SongTile getTile(int i) {
        while (i < 0) {
            i += songTiles.length;
        }
        return songTiles[i % songTiles.length];
    }

    private void moveLeft() {
        if (isAnimating.compareAndSet(false, true)) {
            SongTile right = getTile(selected + 1);
            new Thread(() -> animateMoveLeft(right)).start();
        }
    }

    private void animateMoveLeft(SongTile right) {

//        viewFrame.addFir
//
//        long startTime = System.currentTimeMillis();
//        long deltaTime = System.currentTimeMillis() - startTime;
//        while (deltaTime < SLIDE_DURATION) {
//            double shift = Settings.PANEL_SIZE.getWidth() * deltaTime / SLIDE_DURATION;
//            center.setX(-shift);
//            side.setX(Settings.PANEL_SIZE.getWidth() - shift);
//            deltaTime = System.currentTimeMillis() - startTime;
//        }
//
//        center = side;
//        center.setX(0);
//        //side = null;
//        selected--;
//        isAnimating.set(false);
    }

    private void moveRight() {
        if (isAnimating.compareAndSet(false, true)) {
            SongTile left = getTile(selected - 1);
            new Thread(() -> animateMoveRight(left)).start();
        }
    }

    private void animateMoveRight(SongTile left) {
//        center.setX(0);
//        left.setX(-Settings.PANEL_SIZE.getWidth());
//        side = left;
//
//        long startTime = System.currentTimeMillis();
//        long deltaTime = System.currentTimeMillis() - startTime;
//        while (deltaTime < SLIDE_DURATION) {
//            double shift = Settings.PANEL_SIZE.getWidth() * deltaTime / SLIDE_DURATION;
//            center.setX(shift);
//            side.setX(-Settings.PANEL_SIZE.getWidth() + shift);
//            deltaTime = System.currentTimeMillis() - startTime;
//        }
//
//        center = side;
//        center.setX(0);
//        side = null;
//        selected++;
//        isAnimating.set(false);
    }
}
