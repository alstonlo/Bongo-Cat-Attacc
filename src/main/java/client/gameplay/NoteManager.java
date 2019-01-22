package client.gameplay;

import client.components.Song;
import client.utilities.Pallette;
import client.utilities.ThreadPool;
import client.utilities.Utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The NoteManager object manages the game mechanic of creating notes and removing them:
 * 1) It creates notes at the proper bps rate, reading from the ArrayList configured by the Song object
 * 2) Stores and iterates through all the current notes on the screen to animate them and draw them
 * 3) Calculates accuracy given the timing of the players keystrokes
 * 4) Discards notes when they leave the screen, and set notes' colours if accurately hit/not
 *
 * @Author Katelyn Wang
 * last updated 01/21/2019
 */
public class NoteManager {

    private static final int[] X_REF = {279, 471}; //the coordinates of the reference points where the notes should be played
    private static final int Y_REF = 1150;
    private static final Font MAIN_FONT = Pallette.getScaledFont(Pallette.TITLE_FONT, 30);

    private ArrayList<int[]> notes;
    private ConcurrentLinkedQueue<Note> screenNotes = new ConcurrentLinkedQueue<>(); //thread safe linked queue of notes
    private int currentBeat = 1;

    public AtomicBoolean gameInPlay = new AtomicBoolean(false);

    private int bps;
    private int duration; //in seconds

    private double accuracy = 0;
    private double accuracySum = 0;
    private int totalNotes = 0;
    private static int barHeight = 0;

    private GamePlayPanel panel;
    private Timer timer;

    /**
     * Constructs a NoteManager for the selected song
     *
     * @param song  the song which it will be reading from
     * @param panel the panel which it needs to notify when the song is over
     */
    NoteManager(Song song, GamePlayPanel panel) {
        this.notes = song.getNotes();
        this.bps = song.getBps();
        this.duration = song.getDuration();
        this.panel = panel;
        this.timer = new Timer();
    }

    /**
     * Starts the game by starting the thread to update the current notes on the screen
     */
    public void run() {
        gameInPlay.set(true);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameInPlay.set(false);
                panel.closeGame(accuracy);
            }
        }, duration * 1000);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                currentBeat += 1;
                if (notes.get(currentBeat)[0] == 1) {
                    screenNotes.add(new Note(Note.LEFT_TYPE));
                    totalNotes++;
                }
                if (notes.get(currentBeat)[1] == 1) {
                    screenNotes.add(new Note(Note.RIGHT_TYPE));
                    totalNotes++;
                }
                accuracy = accuracySum / totalNotes;
                barHeight = (int) Math.round(700 * accuracy / 100);
            }
        }, 0, 1000/bps);


        ThreadPool.execute(this::update);
    }

    public void close() {
        timer.cancel();
    }

    /**
     * Updates and manages all the notes
     */
    private void update() {
        while (gameInPlay.get()) {
            for (Note note : screenNotes) {
                note.reposition();
            }
            removeOldNotes();
        }
    }

    /**
     * Removes old notes (ones that have been marked off screen)
     */
    private synchronized void removeOldNotes() {
        for (Note note : screenNotes) {
            if (note.offScreen.get()) {
                screenNotes.remove(note);
            }
        }
    }

    /**
     * Draws all the notes, current accuracy and accuracy bar
     *
     * @param g2D the Graphics2D object to draw all the graphics
     */
    public void draw(Graphics2D g2D) {
        //drawing the notes
        for (Note note : screenNotes) {
            note.draw(g2D);
        }

        //drawing the accuracy bar fill-in
        g2D.setColor(new Color(200, 32, 110));
        g2D.fillRect(Utils.scale(50), Utils.scale(300 + (700 - barHeight)), Utils.scale(20), Utils.scale(barHeight));

        //drawing the accuracy bar outline
        g2D.setColor(Pallette.OUTLINE_COLOR);
        g2D.setStroke(new BasicStroke(5));
        g2D.drawRect(Utils.scale(50), Utils.scale(300), Utils.scale(20), Utils.scale(700));

        //drawing the current accuracy
        g2D.setFont(MAIN_FONT);
        g2D.drawString(String.format("%.2f", accuracy) + "%", Utils.scale(25), Utils.scale(280));
    }

    /**
     * Calls on the note played method, indicates a left note was played
     */
    void notifyLeftPress() {
        notePlayed(0);
    }

    /**
     * Calls on teh note played method, indicates a right not was played
     */
    void notifyRightPress() {
        notePlayed(1);
    }

    /**
     * Determines next note which was played and calculates accuracy
     *
     * @param type whether a left or right note was played
     */
    private void notePlayed(int type) {
        Note pressedNote = getNextNote(type);//checks for which note is next of the specified type
        if (pressedNote != null) { //checks that such a note exists
            double distance = pressedNote.calculateDistance(X_REF[type], Y_REF); //calculates the distance between the line and the note's current position
            if (distance <= 100) { //if it is within 100 units distance, adds it to the accuracy calculation
                accuracySum += 100 * Math.sqrt((-distance / 100.0) + 1); //sqrt function to determine accuracy
                pressedNote.setHit(); //since the play was sufficiently close, turns the note green to indicate success
            } else {
                pressedNote.setMissed(); //turns the note red if the note was missed
            }
            pressedNote.active.set(false); //deactivates the note (no longer can be played)
        }
    }

    /**
     * Retrieves the next note of the specified type which should be played in the list of existing notes
     *
     * @param type what type of note it is looking for (0=left,1=right)
     * @return the next Note to be played of the specified type
     */
    private Note getNextNote(int type) {
        Note nextNote = null;
        for (Note note : screenNotes) {
            if ((note.active.get()) && (note.type == type)) {
                nextNote = note;
                break;
            }
        }
        return nextNote;
    }

}
