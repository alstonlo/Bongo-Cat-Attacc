package client.songselect;

import client.Window;

public class HostSongSelectPanel extends SongSelectPanel {

    public HostSongSelectPanel(Window window, String opponent) {
        super(window, opponent);
    }

    @Override
    void moveLeft() {
        super.moveLeft();
    }

    @Override
    void moveRight() {
        super.moveRight();

    }
}
