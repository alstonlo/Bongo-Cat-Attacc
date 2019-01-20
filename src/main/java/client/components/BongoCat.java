package client.components;

import client.Drawable;
import client.utilities.Utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Graphically represents the standard BongoCat. This class is used to store
 * the state of the bongo cat and then return the appropriate images based
 * on that state.
 *
 * @author Alston
 * last updated 1/12/2019
 */
public class BongoCat implements Drawable {

    /*
     * A 4 x 4 matrix of the sprites representing all the possible sprites
     * that the bongo cat can take on.
     * The row represents the configuration of the paws.
     * 0 = up, up | 1 = up, down | 2 = down, up | 3 = down, down
     *
     * The column represents the configuration of the hit marks.
     * 0 = none, none | 1 = none, mark | 2 = mark, none | 3 = mark, mark
     */
    private BufferedImage[][] sprites;

    /*
     * Represents the state of the bongo cat.
     * state[0] = last time in ms that leftPawDown() was called, and -1 if leftPawUp() was called most recently
     * state[1] = last time in ms that rightPawDown() was called, and -1 if rightPawUp() was called most recently
     */
    private long[] state = new long[2];

    //duration that the hit lines will stay (in ms)
    private long hitMarkDuration = 150;

    /**
     * Constructs a BongoCat. The sprites are loaded and made.
     */
    public BongoCat() {
        leftPawUp();
        rightPawUp();
    }

    /**
     * Moves the left paw of the bongo cat down.
     */
    public void leftPawDown() {
        if (state[0] == -1) {
            state[0] = System.currentTimeMillis();
        }
    }

    /**
     * Moves the right paw of the bongo cat down.
     */
    public void rightPawDown() {
        if (state[1] == -1) {
            state[1] = System.currentTimeMillis();
        }
    }

    /**
     * Moves the left paw of the bongo cat up.
     */
    public void leftPawUp() {
        state[0] = -1;
    }

    /**
     * Moves the right paw of the bongo cat up.
     */
    public void rightPawUp() {
        state[1] = -1;
    }

    public void configureSprites() {
        BufferedImage body = Utils.loadScaledImage("resources/bongo cat/body.png");
        BufferedImage leftPawDown = Utils.loadScaledImage("resources/bongo cat/left paw down.png");
        BufferedImage leftPawHit = Utils.loadScaledImage("resources/bongo cat/left paw hit.png");
        BufferedImage leftPawUp = Utils.loadScaledImage("resources/bongo cat/left paw up.png");
        BufferedImage rightPawDown = Utils.loadScaledImage("resources/bongo cat/right paw down.png");
        BufferedImage rightPawHit = Utils.loadScaledImage("resources/bongo cat/right paw hit.png");
        BufferedImage rightPawUp = Utils.loadScaledImage("resources/bongo cat/right paw up.png");

        //build sprite table
        sprites = new BufferedImage[4][4];

        sprites[0][0] = Utils.mergeImages(new BufferedImage[]{body, leftPawUp, rightPawUp});

        sprites[1][0] = Utils.mergeImages(new BufferedImage[]{body, leftPawUp, rightPawDown});
        sprites[1][1] = Utils.mergeImages(new BufferedImage[]{body, leftPawUp, rightPawDown, rightPawHit});

        sprites[2][0] = Utils.mergeImages(new BufferedImage[]{body, leftPawDown, rightPawUp});
        sprites[2][2] = Utils.mergeImages(new BufferedImage[]{body, leftPawDown, leftPawHit, rightPawUp});

        sprites[3][0] = Utils.mergeImages(new BufferedImage[]{body, leftPawDown, rightPawDown});
        sprites[3][1] = Utils.mergeImages(new BufferedImage[]{body, leftPawDown, rightPawDown, rightPawHit});
        sprites[3][2] = Utils.mergeImages(new BufferedImage[]{body, leftPawDown, leftPawHit, rightPawDown});
        sprites[3][3] = Utils.mergeImages(new BufferedImage[]{body, leftPawDown, leftPawHit, rightPawDown, rightPawHit});
    }

    public void draw(Graphics2D g2D) {
        long[] drawState = {state[0], state[1]};

        //retrieve the row index of the sprite table
        int row;
        if (drawState[0] == -1 && drawState[1] == -1) {
            row = 0;
        } else if (drawState[0] == -1) {
            row = 1;
        } else if (drawState[1] == -1) {
            row = 2;
        } else {
            row = 3;
        }

        //retrieve the column index of the sprite table
        int col;
        long time = System.currentTimeMillis();
        long leftDelta = time - drawState[0];
        long rightDelta = time - drawState[1];
        if (leftDelta > hitMarkDuration && rightDelta > hitMarkDuration) {
            col = 0;
        } else if (leftDelta > hitMarkDuration) {
            col = 1;
        } else if (rightDelta > hitMarkDuration) {
            col = 2;
        } else {
            col = 3;
        }

        g2D.drawImage(sprites[row][col], 0, 0, null);
    }
}
