package client.ui;

import java.io.File;

public class SongHandler {
    static Song[] initialize(){
        File[] directories = new File("resources/songs").listFiles(File::isDirectory);

        Song[] songs = new Song[directories.length];

        for (int i = 0; i < songs.length; i++){
            songs[i] = new Song(directories[i].toString());
        }
        return songs;
    }
}
