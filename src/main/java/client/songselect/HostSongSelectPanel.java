package client.songselect;

import client.Window;

/**
 * The SongSelectionPanel for the host who will choose the song
 *
 * @Author Alston Lo
 * last updated 01/21/2019
 */
public class HostSongSelectPanel extends SongSelectPanel {

    /**
     * Constructs a SongSelectPanel for the host player (the one who can choose the song)
     * @param window the Window object to which this panel belongs and is drawn upon
     * @param opponent the opponent's username
     */
    public HostSongSelectPanel(Window window, String opponent) {
        super(window, opponent);
    }

}
