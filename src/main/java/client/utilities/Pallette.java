package client.utilities;

import java.awt.Color;
import java.awt.Font;

/**
 * Stores the colour and font theme used by the game.
 *
 * @author Alston
 * last updated 1/19/2019
 */
public class Pallette {

    // Colours used --------------------------------------------------------------------

    /**
     * The colour of any outline or text in the game. It is a unsaturated brown.
     */
    public static final Color OUTLINE_COLOR = new Color(60, 51, 28);


    // Fonts used -----------------------------------------------------------------------

    /**
     * The standard font used for any text in the game.
     */
    public static final Font TEXT_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 0);

    /**
     * The font used for any titles or headings in the game
     */
    public static final Font TITLE_FONT = Utils.loadFont("resources/fonts/cloud.ttf", 0);

    /**
     * Convenience method that returns a scaled font, scaled using {@link Settings#SCALE}.
     *
     * @param font the font to be scaled
     * @param size the unscaled size of the font
     * @return the scaled font of the indicated size
     */
    public static Font getScaledFont(Font font, float size) {
        return font.deriveFont((float) (Settings.SCALE * size));
    }
}
