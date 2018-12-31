package client.utilities;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Utils {
    public static final int MAX_X = 600;
    public static final int MAX_Y = 400;
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static double scale = screenSize.height / 1200.0;

    public static int scale(int measure){
        return (int) Math.round(scale*measure);
    }
}
