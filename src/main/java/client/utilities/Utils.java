package client.utilities;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Convenience class containing methods for graphical scaling, rounding, loading resources, etc.
 *
 * @author Alston and Katelyn
 * last updated 1/12/2018
 */
public class Utils {

    private static AffineTransformOp scale = new AffineTransformOp(
            AffineTransform.getScaleInstance(Settings.SCALE, Settings.SCALE), AffineTransformOp.TYPE_BICUBIC);

    /**
     * Rounds a number n to the nearest integer.
     *
     * @param n a number
     * @return the nearest integer to n
     */
    public static int round(double n) {
        return (int) Math.round(n);
    }

    /**
     * Scales a number n according to {@link Settings#SCALE} and rounds it to
     * the nearest integer.
     *
     * @param n a number
     * @return the scaled value of n rounded to the nearest integer
     */
    public static int scale(double n) {
        return round(n * Settings.SCALE);
    }

    /**
     * Scales and re-sizes a image according to {@link Settings#SCALE}.
     *
     * @param img an image
     * @return the scaled image; or null if the image argument is null
     */
    public static BufferedImage scale(BufferedImage img) {
        if (img == null) {
            return null;
        }

        BufferedImage res = new BufferedImage(scale(img.getWidth()), scale(img.getHeight()), BufferedImage.TYPE_INT_ARGB);
        res = createCompatibleImage(res);
        Graphics2D g2D = (Graphics2D) res.getGraphics();
        g2D.setRenderingHints(Settings.QUALITY_RENDER_SETTINGS);
        g2D.drawImage(img, scale, 0, 0);
        g2D.dispose();
        return res;
    }

    /**
     * Returns a {@link Clip} (.wav file) from the specified file path but does
     * not play it.
     *
     * @param filePath the file path of the audio
     * @return the clip at the filePath argument; or null if an Exception occurs
     */
    public static Clip loadAudio(String filePath) {
        try (AudioInputStream stream = AudioSystem.getAudioInputStream(new File(filePath))) {
            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            return clip;
        } catch (Exception e) {
            System.out.println("Failed to load audio at " + filePath);
        }
        return null;
    }

    /**
     * Retrieves a font and creates it.
     *
     * @param filePath the name of the font file
     * @param size     the desired size of the font to be made
     * @return the created Font
     */
    public static Font loadFont(String filePath, float size) {
        Font font;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File(filePath)).deriveFont(size);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.PLAIN, new File(filePath)));
        } catch (IOException | FontFormatException e) {
            font = new Font(Font.SANS_SERIF, Font.PLAIN, Math.round(size)); //if it cannot find the font, defaults to sans-serif of the same size
        }
        return font;
    }

    /**
     * Returns a {@link BufferedImage} from the specified file path but does not scale it.
     *
     * @param filePath the file path of the image
     * @return the image file at the filePath argument; or null if an IOException occurs
     */
    public static BufferedImage loadImage(String filePath) {
        try {
            return createCompatibleImage(ImageIO.read(new File(filePath)));
        } catch (IOException e) {
            System.out.println("Failed to load image at " + filePath);
            return null;
        }
    }

    /**
     * Returns a {@link BufferedImage} from the specified file path and scales it
     * according to {@link Settings#SCALE}.
     *
     * @param filePath the file path of the image
     * @return the image file at the filePath argument; or null if an IOException occurs
     */
    public static BufferedImage loadScaledImage(String filePath) {
        return scale(loadImage(filePath));
    }

    public static BufferedImage createCompatibleImage(BufferedImage image) {
        if (image == null) {
            return null;
        }

        GraphicsConfiguration config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        BufferedImage target = config.createCompatibleImage(image.getWidth(), image.getHeight(), Transparency.TRANSLUCENT);
        Graphics2D g2d = target.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return target;
    }

    /**
     * Returns a {@link BufferedImage} that is the combination of the images arguments,
     * with the bottom-most image being the first element of the array and the top-most image being
     * the last element of the array. All images are drawn at (0, 0) and the returned image has
     * width and height set to the maximum width and height (respectively) of the array's elements.
     * If the images array is empty, an empty Buffered Image is returned.
     *
     * @param images an array of images to merge, with earlier
     * @return the combined {@link BufferedImage} of the top and bottom arguments
     */
    public static BufferedImage mergeImages(BufferedImage[] images) {
        int width = 0;
        int height = 0;
        for (BufferedImage image : images) {
            width = Math.max(width, image.getWidth());
            height = Math.max(height, image.getHeight());
        }

        BufferedImage merged = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2D = (Graphics2D) merged.getGraphics();
        g2D.setRenderingHints(Settings.QUALITY_RENDER_SETTINGS);
        for (BufferedImage image : images) {
            g2D.drawImage(image, 0, 0, null);
        }
        g2D.dispose();
        return merged;
    }
}
