package server;

import client.utilities.Utils;

import javax.sound.sampled.Clip;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A class containing information and audio of a song.
 *
 * @author Alston
 * last updated 1/13/2019
 */
public class Song {

    /**
     * Returns an array of songs stored in the resource library of this game.
     *
     * @return an array of songs
     * @throws IOException thrown if an error accessing the song resources directory occurs
     */
    public static Song[] getSongs() throws IOException {
        File[] directories = new File("resources/songs").listFiles(File::isDirectory);
        if (directories == null) {
            throw new IOException();
        }

        Song[] songs = new Song[directories.length];
        for (int i = 0; i < songs.length; i++) {
            songs[i] = new Song(directories[i].toString());
        }
        return songs;
    }

    private String dirPath;
    private String name;
    private int bpm;
    private int duration;
    private ArrayList<int[]> notes = new ArrayList<>();
    private int start, end;

    /**
     * Constructs a song that is represented by a specific folder. For a folder
     * to properly represent a song, it must have the following files in the following format.
     * <p>
     * <ul>
     * <li>config.txt - a .txt file holding information about the song</li>
     * <li>music.wav - a file holding the audio of the song</li>
     * <li>album.png - a png depicting the song's album with a square aspect ratio</li>
     * <li>background.png - a png depicting splash art associated with the song (750px x 1334px)</li>
     * </ul>
     * <p>
     *
     * @param dirPath the file path of the folder representing the song
     */
    private Song(String dirPath) {
        this.dirPath = dirPath;

        //read the config file and configure properties
        Map<String, String> configDetails = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(dirPath + "/config.txt")))) {
            String line = reader.readLine();
            while (line != null) {
                String[] property = line.split(":");
                configDetails.put(property[0].trim().toLowerCase(), property[1].trim());
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println(dirPath + "/config.txt cannot be found.");
        }

        this.name = configDetails.get("name");
        this.bpm = Integer.valueOf(configDetails.get("bpm"));
        this.duration = Integer.valueOf(configDetails.get("duration"));

        try (BufferedReader reader = new BufferedReader(new FileReader(new File(dirPath + "/beats.txt")))) {
            String line = reader.readLine();
            while (line != null) {
                notes.add(new int[]{Integer.valueOf(line.substring(0, 1)), Integer.valueOf(line.substring(1))});
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println(dirPath + "/beats.txt cannot be found.");
        }
    }

    /**
     * @return the name of the song
     */
    public String getName() {
        return name;
    }

    /**
     * @return the beats per second of the song
     */
    public int getBps() {
        return this.bpm / 60;
    }

    /**
     * @return the length of the song, in seconds
     */
    public int getDuration() {
        return this.duration;
    }

    /**
     * @return the songs array of beats (1 = a note, 0 = a space)
     */
    public ArrayList<int[]> getNotes() {
        return this.notes;
    }

    /**
     * Loads the song's audio and returns it.
     *
     * @return the audio of the song
     */
    public Clip getAudio() {
        return Utils.loadAudio(dirPath + "/music.wav");
    }
}
