package client.songselect;

import javax.swing.JPanel;
import java.awt.Graphics2D;

class SongAnimationHandler {

    private static final long SLIDE_DURATION = 500;

    private SongTile center;
    private SongTile side;

    private void animateMoveLeft() {

    }

    private void animateMoveRight() {
//        SongTile left = getTile(currIndex - 1);
//        left.setX(-panel.getWidth());
//        toRender[0] = left;
//
//        SongTile center = getTile(currIndex);
//        center.setX(0);
//        toRender[1] = center;
//
//        long startTime = System.currentTimeMillis();
//        long deltaTime = System.currentTimeMillis() - startTime;
//        while (deltaTime < SLIDE_DURATION) {
//            double shift = ((double) panel.getWidth()) * deltaTime / SLIDE_DURATION;
//            left.setX(-panel.getWidth() + shift);
//            center.setX(shift);
//        }
//
//        left.setX(0);
//        center.setX(panel.getWidth());
    }



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


    private Runnable fadeIn = new Runnable() {
        @Override
        public void run() {

        }
        //        @Override
//        public void run() {
//            long startTime = System.currentTimeMillis();
//            do {
//                alpha = 0 + (System.currentTimeMillis() - startTime) * 0.001f;
//            } while (alpha <= 1f);
//            alpha = 1f;
//            animating.set(false);
//            playSong(currIndex);
//        }
    };


    SongAnimationHandler() {
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
//            File song = new File(tiles[index]);
//            AudioInputStream stream = AudioSystem.getAudioInputStream(song);
//            currMusic = AudioSystem.getClip();
//            currMusic.open(stream);
//            currMusic.loop(Clip.LOOP_CONTINUOUSLY);
//        } catch (Exception e) {
//            e.printStackTrace();
        // }
    }
}

