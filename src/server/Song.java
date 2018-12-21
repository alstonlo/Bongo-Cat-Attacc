package server;

import java.io.File;

class Song implements Comparable<Song>{

    Song(File songConfigFile) {

    }

    String getName() {
        return null;
    }

    double[] getNotes() {
        return null;
    }

    @Override
    public int compareTo(Song o) {
        return 0;
    }
}
