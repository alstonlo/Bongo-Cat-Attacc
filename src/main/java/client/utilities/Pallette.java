package client.utilities;

import java.awt.Color;
import java.awt.Font;

public class Pallette {

    public static final Color OUTLINE_COLOR = new Color(60, 51, 28);

    public static final Font TEXT_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 0);
    public static final Font TITLE_FONT = Utils.loadFont("resources/fonts/cloud.ttf", 0);

    public static Font getScaledFont(Font original, float size) {
        return original.deriveFont((float)(Settings.SCALE * size));
    }
}
