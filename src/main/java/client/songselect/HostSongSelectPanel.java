package client.songselect;

import client.Window;

public class HostSongSelectPanel extends SongSelectPanel {

    public HostSongSelectPanel(Window window, String opponent) {
        super(window, opponent);
    }

    @Override
    void moveLeft() {
        super.moveRight();
    }

    @Override
    void moveRight() {
        super.moveLeft();

    }
}
