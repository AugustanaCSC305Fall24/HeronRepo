package edu.augustana;


import javax.sound.sampled.*;
import java.util.Random;

public class StaticNoisePlayer {

    private static SourceDataLine line;
    private static Thread noiseThread;
    private static volatile boolean playing;

    public static void startNoise() throws LineUnavailableException {
        AudioFormat format = new AudioFormat(8000f, 8, 1, true, true);
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

        if (!AudioSystem.isLineSupported(info)) {
            System.err.println("Audio line not supported");
            return;
        }

        line = (SourceDataLine) AudioSystem.getLine(info);
        line.open(format);
        line.start();

        playing = true;
        noiseThread = new Thread(() -> {
            Random random = new Random();
            byte[] buffer = new byte[1024];
            while (playing) {
                random.nextBytes(buffer);
                line.write(buffer, 0, buffer.length);
            }
            line.drain();
            line.close();
        });
        noiseThread.start();
    }

    public static void stopNoise() {
        playing = false;
        if (noiseThread != null) {
            try {
                noiseThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
