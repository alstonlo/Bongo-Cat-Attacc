package client.utilities;

import java.awt.Dimension;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Stores the settings of the game.
 *
 * @author Alston
 * last updated 1/12/2018
 */
public class Settings {

    // Scaling fields ------------------------------------------------------------------------------

    /**
     * The scale factor of the game display; the game frame is 80% the height of the computer screen.
     */
    public static final double SCALE = (Toolkit.getDefaultToolkit().getScreenSize().height * 0.8) / 1334;

    /**
     * The dimensions the game display is.
     */
    public static final Dimension PANEL_SIZE = new Dimension(Utils.scale(750), Utils.scale(1334));


    // Key mapping fields -----------------------------------------------------------------------

    /**
     * The key code that is mapped to the left bongo.
     *
     * @see KeyEvent
     * @see client.BongoListener
     */
    public static final int LEFT_BONGO_KEY = KeyEvent.VK_A;

    /**
     * The key code that is mapped to the right bongo.
     *
     * @see KeyEvent
     * @see client.BongoListener
     */
    public static final int RIGHT_BONGO_KEY = KeyEvent.VK_L;

    /**
     * The key code that is mapped to the hold bongo.
     *
     * @see KeyEvent
     * @see client.BongoListener
     */
    public static final int HOLD_BONGO_KEY = KeyEvent.VK_ENTER;


    // Rendering settings fields -------------------------------------------------------------------------------

    /**
     * Rendering hints that prioritize quality.
     */
    public static final Map<RenderingHints.Key, Object> QUALITY_RENDER_SETTINGS = getQualityRenderSettings();

    /**
     * The default rendering hints.
     */
    public static final Map<RenderingHints.Key, Object> DEFAULT_RENDER_SETTINGS = getDefaultRenderSettings();

    /**
     * @return the configured settings for {@link Settings#QUALITY_RENDER_SETTINGS}
     */
    private static Map<RenderingHints.Key, Object> getQualityRenderSettings() {
        Map<RenderingHints.Key, Object> settings = new HashMap<>();
        settings.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        settings.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        settings.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        settings.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        settings.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        return settings;
    }

    /**
     * @return the configured settings for {@link Settings#QUALITY_RENDER_SETTINGS}
     */
    private static Map<RenderingHints.Key, Object> getDefaultRenderSettings() {
        Map<RenderingHints.Key, Object> settings = new HashMap<>();
        settings.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
        settings.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_DEFAULT);
        settings.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        settings.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);
        settings.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_DEFAULT);
        return settings;
    }
}

