package client;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Utils {

    /**
     * Plays a {@link Clip} (.wav file) from the specified file path and returns it.
     *
     * @param filePath the file path of the audio
     * @return the clip at the filePath argument; or null if an Exception occurs
     */
    public static Clip loadAudio(String filePath) {
        try (AudioInputStream stream = AudioSystem.getAudioInputStream(new File(filePath))) {
            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            clip.start();
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
     * Returns a {@link BufferedImage} from the specified file path.
     *
     * @param filePath the file path of the image
     * @return the image file at the filePath argument; or null if an IOException occurs
     */
    public static BufferedImage loadImage(String filePath) {
        try {
            return ImageIO.read(new File(filePath));
        } catch (IOException e) {
            System.out.println("Failed to load image at " + filePath);
            return null;
        }
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
        Graphics g = merged.getGraphics();
        for (BufferedImage image : images) {
            g.drawImage(image, 0, 0, null);
        }
        return merged;
    }

//    public static BufferedImage joinImages(BufferedImage[] images) {
//
//    }
}