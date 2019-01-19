package client.components;

import client.utilities.Utils;

import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
    private int difficulty;
    private int start, end;

    /**
     * Constructs a song that is represented by a specific folder. For a folder
     * to properly represent a song, it must have the following files in the following format.
     * </br>
     * <p>
     * config.txt - a .txt file holding information about the song
     * music.wav - a file holding the audio of the song
     * album.png - a png depicting the song's album with a square aspect ratio
     * background.png - a png depicting splash art associated with the song (750px x 1334px)
     *
     * @param dirPath the file path of the folder representing the song
     */
    private Song(String dirPath) {
        this.dirPath = dirPath;

        //read the config file and configure properties
        Map<String, String> configDetails = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(dirPath + "/config.txt")))){
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
    }

    /**
     * @return the name of the song
     */
    public String getName() {
        return name;
    }

    /**
     * @return an integer denoting the song's level difficulty
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Loads the song's album image (unscaled) and returns it.
     *
     * @return the album art of the song
     */
    public BufferedImage getAlbum() {
        return Utils.loadImage(dirPath + "/album.png");
    }

    /**
     * Loads the song's splash art, scales it, and returns it.
     *
     * @return the splash art of the song
     */
    public BufferedImage getSplash() {
        return Utils.loadScaledImage(dirPath + "/background.png");
    }

    /**
     * Loads the song's audio and returns it.
     *
     * @return the audio of the song
     */
    public Clip getAudio() {
        return Utils.loadAudio(dirPath + "/music.wav");
    }

    /**
     * Loads a specific excerpt of the song's audio, specified by its
     * configuration file and returns it.
     *
     * @return an excerpt of the audio of the song
     */
    public Clip getAudioExcerpt() {
        Clip excerpt = getAudio();
        //excerpt.setLoopPoints(start, end);
        return excerpt;
    }
}
