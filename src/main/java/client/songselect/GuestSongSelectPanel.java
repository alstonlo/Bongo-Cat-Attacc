package client.songselect;

import client.Window;

/**
 * The SongSelectionPanel for the guest who does not choose the song
 *
 * @Author Alston Lo
 * last updated 01/21/2019
 */
public class GuestSongSelectPanel extends SongSelectPanel{

    /**
     * Constructs a SongSelectPanel for the guest player (the one who cannot choose the song)
     * @param window the Window object to which this panel belongs and is drawn upon
     * @param opponent the opponent's username
     */
    public GuestSongSelectPanel(Window window, String opponent) {
        super(window, opponent);
    }


}
