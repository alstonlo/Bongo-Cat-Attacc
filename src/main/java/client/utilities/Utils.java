package client.utilities;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

public class Utils {
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int MAX_X = screenSize.width;
    public static final int MAX_Y = screenSize.height;
    private static double scale = screenSize.height / 1200.0;

    public static int scale(int measure){
        return (int) Math.round(scale*measure);
    }


    /**
     * Retrieves a font and creates it
     *
     * @param fileName the name of the font file
     * @param size     the desired size of the font to be made
     * @return the created Font
     */
    public static Font getFont(String fileName, float size) {
        Font font;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File(fileName)).deriveFont(size);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.PLAIN, new File(fileName)));
        } catch (IOException | FontFormatException e) {
            font = new Font(Font.SANS_SERIF, Font.PLAIN, Math.round(size)); //if it cannot find the font, defaults to sans-serif of the same size
        }
        return font;
    }
}
