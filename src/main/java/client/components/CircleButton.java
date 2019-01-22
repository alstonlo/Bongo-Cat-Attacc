package client.components;

import client.Drawable;
import client.utilities.Settings;
import client.utilities.Utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

/**
 * Custom circular game button object. Instead of text, the button has an icon.
 *
 * @author Alston
 * last updated 1/12/2019
 */
public class CircleButton implements Drawable {

    /*
     * Preloaded sprites of the button.
     * sprites[0]: sprite of unselected button
     * sprites[1]: sprite of selected button
     */
    private BufferedImage[] sprites;

    private int centerX;
    private int centerY;
    private int radius;
    private final int deltaRadius = Utils.scale(6);

    private BufferedImage icon;
    private Color color = new Color(212, 212, 212);
    private Color selectedColor = new Color(255, 246, 154);
    private BasicStroke outline = new BasicStroke(Utils.scale(5));
    private Color outlineColor = new Color(60, 59, 21);

    private boolean selected;
    private Runnable onSubmit;

    /**
     * Constructs a CircleButton based on its icon, position, and size.
     *
     * @param icon    the icon displayed on this button
     * @param centerX the center x-coordinate of this button
     * @param centerY the center y-coordinate of this button
     * @param radius  the radius of this button
     */
    public CircleButton(BufferedImage icon, int centerX, int centerY, int radius) {
        super();
        this.icon = icon;
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }

    /**
     * Selects or places focus on this button.
     */
    public void select() {
        selected = true;
    }

    /**
     * Deselects or removes focus from this button.
     */
    public void deselect() {
        selected = false;
    }

    /**
     * Submits or clicks the button.
     */
    public void submit() {
        if (onSubmit != null) {
            onSubmit.run();
        }
    }

    /**
     * Sets the Runnable action the button will run on being clicked
     * or submitted by {@link CircleButton#submit()}.
     *
     * @param onSubmit the action the button will perform on being clicked
     */
    public void setOnSubmit(Runnable onSubmit) {
        this.onSubmit = onSubmit;
    }


    //Drawable methods ------------------------------------------------------------------------------------

    @Override
    public void configureSprites() {
        sprites = new BufferedImage[2];

        int side = (int) Math.ceil((radius + outline.getLineWidth()) * 2);
        sprites[0] = new BufferedImage(side, side, BufferedImage.TYPE_INT_ARGB);

        side += (2 * deltaRadius); //the selected sprite is slightly larger than the unselected one
        sprites[1] = new BufferedImage(side, side, BufferedImage.TYPE_INT_ARGB);

        for (int i = 0; i < 2; i++) {
            Graphics2D g2D = (Graphics2D) sprites[i].getGraphics();
            g2D.setRenderingHints(Settings.QUALITY_RENDER_SETTINGS); //prioritize quality for rendering

            Ellipse2D shape = new Ellipse2D.Float( //the stroke thickness must be accounted for in the ellipse
                    outline.getLineWidth(),
                    outline.getLineWidth(),
                    sprites[i].getWidth() - 2 * outline.getLineWidth(),
                    sprites[i].getHeight() - 2 * outline.getLineWidth());
            g2D.setColor((i == 0) ? color : selectedColor);
            g2D.fill(shape);
            g2D.setColor(outlineColor);
            g2D.setStroke(outline);
            g2D.draw(shape);
            g2D.drawImage(icon, 0, 0, sprites[i].getWidth(), sprites[i].getHeight(), null);
            g2D.dispose();
        }
    }

    @Override
    public void draw(Graphics2D g2D) {
        if (sprites == null) {
            return;
        }

        BufferedImage sprite = sprites[(selected) ? 1 : 0];
        g2D.drawImage(sprite, centerX - sprite.getWidth() / 2, centerY - sprite.getWidth() / 2, null);
    }
}
