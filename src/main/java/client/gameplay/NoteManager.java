package client.gameplay;

import client.Controllable;
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
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class NoteManager implements Drawable {
    private ArrayList<int[]> notes;
    private ConcurrentLinkedQueue<Note> screenNotes = new ConcurrentLinkedQueue<>();
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

    private double accuracy = 0;
    private double accuracySum = 0;
    private int totalNotes = 0;

    NoteManager(Song song) {
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
        long prevTime = System.currentTimeMillis();
        long startTime = System.currentTimeMillis();
        while (gameInPlay.get()) {
            if (((System.currentTimeMillis()-startTime)/1000.0)*bps > currentBeat) {
                currentBeat+=1;
                //System.out.println((System.currentTimeMillis()-startTime)/1000+" "+currentBeat);
                if (notes.get(currentBeat)[0] == 1) {
                    screenNotes.add(new Note(361,634,Note.LEFT_TYPE));
                    totalNotes++;
                }
                if (notes.get(currentBeat)[1] == 1) {
                    screenNotes.add(new Note(389,634,Note.RIGHT_TYPE));
                    totalNotes++;
                }
                accuracy = accuracySum/totalNotes;
            }

            long elapsedTime = System.currentTimeMillis() - prevTime;
            if (elapsedTime > 100){
                prevTime = System.currentTimeMillis();
                for (Note note : screenNotes) {
                    note.updatePosition((int) elapsedTime /15);
                }
            }
           removeOldNotes();

        }
    }

    public synchronized void removeOldNotes() {
            for (Note note : screenNotes) {
                if (note.offScreen.get()) {
                    screenNotes.remove(note);
                }
            }
    }

    public void draw(Graphics2D g2D) {
        for (Note note : screenNotes){
            note.draw(g2D);
        }
        g2D.drawString(String.valueOf(accuracy),Utils.scale(150), Utils.scale(150));
    }

    public int calculateLength(int x, int y, int newX, int newY) {
        return (int) Math.round(Math.sqrt((Utils.scale(newX - x))) * (Utils.scale(newX - x)) + (Utils.scale(newY - y)) * (Utils.scale(newY - y)));
    }


    public void notifyLeftPress() {
        Note pressedNote = getNextNote(0);
        if (pressedNote!=null) {
            int distance = pressedNote.calculateDistance(279, 1150);
            if (distance <= 100) {
                accuracySum += 100*Math.sqrt((-distance/100.0)+1);
                System.out.println(100*Math.sqrt((-distance/100.0)+1));
                pressedNote.setGreen();
            } else {
                pressedNote.setRed();
            }
            pressedNote.active.set(false);

        }
    }

    public void notifyRightPress() {
        Note pressedNote = getNextNote(1);
        if (pressedNote != null) {
            int distance = pressedNote.calculateDistance(471, 1150);
            if (distance <= 100) {
                accuracySum +=  100*Math.sqrt((-distance/100.0)+1);
                System.out.println(100*Math.sqrt((-distance/100.0)+1));
                pressedNote.setGreen();
            } else {
                pressedNote.setRed();
            }
            pressedNote.active.set(false);
        }
    }

    Note getNextNote(int type){
        Note nextNote = null;
        for (Note note : screenNotes){
            if ((note.active.get()) && (note.type == type)){
                nextNote = note;
                break;
            }
        }
        return nextNote;
    }

}
