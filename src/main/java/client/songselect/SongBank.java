package client.songselect;

import java.io.File;

public class SongBank {

    private static Song[] songs = new Song[0];

    static {
        File[] directories = new File("resources/songs").listFiles(File::isDirectory);
        if (directories != null) {
            songs = new Song[directories.length];
            for (int i = 0; i < songs.length; i++) {
                songs[i] = new Song(directories[i].toString());
            }
        }
    }

    public static Song[] getSongs() {
        return songs;
    }


}
