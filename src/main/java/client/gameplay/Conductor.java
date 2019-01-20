package client.gameplay;

import client.Drawable;
import client.components.Song;
import client.utilities.Pallette;
import client.utilities.ThreadPool;
import client.utilities.Utils;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Conductor implements Drawable {
    private ArrayList<int[]> notes;
    private List<int[]> leftNoteCoords = Collections.synchronizedList(new ArrayList<>());
    private List<int[]> rightNoteCoords = Collections.synchronizedList(new ArrayList<>());
    private int currentBeat = 1;
    private int lastBeat = 0;

    private int WIDTH = Utils.scale(30);
    private int HEIGHT = Utils.scale(20);

    private AtomicBoolean gameInPlay = new AtomicBoolean(false);
    private int baseDistance = calculateLength(361, 634, 375, 546);

    private ArrayList<Integer> leftToRemove = new ArrayList<>();
    private ArrayList<Integer> rightToRemove = new ArrayList<>();

    private int bps;

    private Song song;

    private int duration; //in seconds

    Conductor(Song song) {
        notes = song.getNotes();
        bps = song.getBps();
        duration = song.getDuration();
        this.song = song;
    }

    public void run() {
        gameInPlay.set(true);
        ThreadPool.execute(this::update);
        long startTime = System.currentTimeMillis();
    }

    @Override
    public void configureSprites() {

    }

    private void update() {
        long noteCreationPrevTime = System.currentTimeMillis();
        long animationPrevTime = System.currentTimeMillis();
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis()-startTime/1000.0 < duration) {
            long noteCreationElapsedTime = System.currentTimeMillis() - noteCreationPrevTime;
            if (noteCreationElapsedTime > 1000.0/bps) {
                noteCreationPrevTime = System.currentTimeMillis();
                currentBeat += 1;
                if (notes.get(currentBeat)[0] == 1) {
                    leftNoteCoords.add(new int[]{361, 634});
                }
                if (notes.get(currentBeat)[1] == 1) {
                    rightNoteCoords.add(new int[]{389, 634});
                }
            }

            long animationElapsedTime = System.currentTimeMillis() - animationPrevTime;
            if (animationElapsedTime > 100) {
                animationPrevTime = System.currentTimeMillis();
                for (int i = 0; i < leftNoteCoords.size(); i++) {
                    int[] currCoords = leftNoteCoords.get(i);
                    if (currCoords[0] >= 250) {
                        currCoords[0] -= (int) Math.round((animationElapsedTime) / 25.0);
                        currCoords[1] = (int) Math.round(((-700.0 / 111) * currCoords[0]) + 2910.0);
                        leftNoteCoords.set(i, currCoords);
                    } else {
                        leftToRemove.add(i);
                   }

                }
                for (int i = 0; i < rightNoteCoords.size(); i++) {
                    int[] currCoords = rightNoteCoords.get(i);
                    if (currCoords[0] <= 500) {
                        currCoords[0] += (int) Math.round((animationElapsedTime) / 25.0);
                        currCoords[1] = (int) Math.round(((700.0 / 111) * currCoords[0]) - 1819.0);
                        rightNoteCoords.set(i, currCoords);
                    } else {
                        rightToRemove.add(i);
                    }
                }
                removeOldNotes();
            }

        }
    }

    public synchronized void removeOldNotes() {
        for (int i = 0; i < leftToRemove.size(); i++) {
            leftNoteCoords.remove((int) leftToRemove.get(i));
        }
        for (int i = 0; i < rightToRemove.size(); i++) {
            rightNoteCoords.remove((int) rightToRemove.get(i));
        }
        rightToRemove.clear();
        leftToRemove.clear();
    }

    public void draw(Graphics2D g2D) {
        for (int i = 0; i < leftNoteCoords.size(); i++) {
            int x = leftNoteCoords.get(i)[0];
            int y = leftNoteCoords.get(i)[1];
            int multiplier = calculateLength(x, y, 375, 546) / baseDistance;
            int width = multiplier * WIDTH;
            int height = multiplier * HEIGHT;
            g2D.fillOval(Utils.scale(x) - WIDTH / 2, Utils.scale(y) - HEIGHT / 2, WIDTH, HEIGHT);
        }
        for (int i = 0; i < rightNoteCoords.size(); i++) {
            int x = rightNoteCoords.get(i)[0];
            int y = rightNoteCoords.get(i)[1];
            int multiplier = calculateLength(x, y, 375, 546) / baseDistance;
            int width = multiplier * WIDTH;
            int height = multiplier * HEIGHT;
            g2D.drawOval(Utils.scale(x) - WIDTH / 2, Utils.scale(y) - HEIGHT / 2, WIDTH, HEIGHT);
        }
    }

    public int calculateLength(int x, int y, int newX, int newY) {
        return (int) Math.round(Math.sqrt((Utils.scale(newX - x))) * (Utils.scale(newX - x)) + (Utils.scale(newY - y)) * (Utils.scale(newY - y)));
    }
}
