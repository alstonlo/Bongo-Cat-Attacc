package client.songselect;

import javax.sound.sampled.Clip;
import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.util.concurrent.atomic.AtomicBoolean;

public class SongAnimationHandler {

    private static final long SLIDE_DURATION = 500;

    private Song[] songs;

    private Clip currMusic;
    private int currXPos;
    private int prevIndex;
    private int currIndex;
    private float alpha = 1f;

    private AtomicBoolean animating = new AtomicBoolean(false);

    private Runnable left = new Runnable() {
        @Override
        public void run() {
//         /   long startTime = System.currentTimeMillis();
//            long prevTime = System.currentTimeMillis();
//            do {
//                int increment = (int) Math.round((System.currentTimeMillis() - prevTime) * velocity);
//                for (SongTile button : buttons) {
//                    button.setX(button.getX() - increment);
//                }
//                prevTime = System.currentTimeMillis();
//            } while ((System.currentTimeMillis() - startTime) * velocity <= 1320);
//            buttons[currIndex].setX(50);
//            animating.set(false);

        }
    };
    private Runnable right = new Runnable() {
        @Override
        public void run() {
//            long startTime = System.currentTimeMillis();
//            long prevTime = System.currentTimeMillis();
//            do {
//                int increment = (int) Math.round((System.currentTimeMillis() - prevTime) * velocity);
//                for (SongTile button : buttons) {
//                    button.setX(button.getX() + increment);
//                }
//                prevTime = System.currentTimeMillis();
//            } while ((System.currentTimeMillis() - startTime) * velocity <= 1320);
//            buttons[currIndex].setX(50);
//            animating.set(false);
        }
    };

    private Runnable fadeIn = new Runnable() {
        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            do {
                alpha = 0 + (System.currentTimeMillis() - startTime) * 0.001f;
            } while (alpha <= 1f);
            alpha = 1f;
            animating.set(false);
            playSong(currIndex);
        }
    };


    SongAnimationHandler(Song[] songs) {
        this.songs = songs;

    }

    boolean isAnimating() {
        return animating.get();
    }

    void setCurrIndex(int index) {
        this.prevIndex = currIndex;
        this.currIndex = index;
    }

    void leftMove() {
        if (!animating.get()) {
            animating.set(true);
            new Thread(right).start();
            new Thread(fadeIn).start();
        }

    }

    void rightMove() {
        if (!animating.get()) {
            animating.set(true);
            new Thread(left).start();
            new Thread(fadeIn).start();
        }
    }

    void draw(Graphics2D g2D, JPanel panel) {
//        g2D.setComposite(AlphaComposite.SrcOver.derive(alpha));
//        g2D.drawImage(images[currIndex], currXPos, 0, panel);
//        g2D.setComposite(AlphaComposite.SrcOver.derive(1f - alpha));
//        g2D.drawImage(images[prevIndex], currXPos, 0, panel);
//
//        g2D.setComposite(AlphaComposite.SrcOver.derive(1f));
//        for (SongTile button : buttons) {
//            button.draw(g2D);
        //}//
    }

    public void playSong(int index) {
//        if (currMusic != null) {
//            currMusic.stop();
//        }
//
//        try {
//            File song = new File(songs[index]);
//            AudioInputStream stream = AudioSystem.getAudioInputStream(song);
//            currMusic = AudioSystem.getClip();
//            currMusic.open(stream);
//            currMusic.loop(Clip.LOOP_CONTINUOUSLY);
//        } catch (Exception e) {
//            e.printStackTrace();
       // }
    }

    public int length() {
        return 0;
        //return images.length;
    }

    public void stop() {
        if (currMusic != null) {
            currMusic.stop();
        }
    }

    //ANIMATION METHODS ----------------------------------------------------------------


}

