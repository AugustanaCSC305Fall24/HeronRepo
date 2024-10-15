
package edu.augustana;

import javax.sound.sampled.*;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicBoolean;

public class TonePlayer {

    private SourceDataLine line;
    private final AudioFormat audioFormat;
    private final AtomicBoolean isPlaying = new AtomicBoolean(false);
    private Instant toneStartTime;
    private final long minPlayTimeMillis;  // Configurable minimum play time

    // Constructor to set up the audio format and minimum play time
    public TonePlayer(long minPlayTimeMillis) {
        this.audioFormat = new AudioFormat(Note.SAMPLE_RATE, 8, 1, true, true);
        this.minPlayTimeMillis = minPlayTimeMillis;  // Set the minimum play time (in milliseconds)
    }

    // Start playing audio
    public void startAudio() throws LineUnavailableException {
        if (!isPlaying.getAndSet(true)) {
            if (line == null || !line.isOpen()) {
                line = AudioSystem.getSourceDataLine(audioFormat);
                line.open(audioFormat, Note.SAMPLE_RATE);
            }
            line.start();
            toneStartTime = Instant.now();  // Record the start time

            byte[] toneData = Note.A4.data(App.sound);  // Volume controlled by App.sound
            new Thread(() -> {
                while (isPlaying.get()) {
                    line.write(toneData, 0, toneData.length);
                }
            }).start();
        }
    }

    // Stop the audio with a minimum play duration (configurable)
    public void stopAudio() {
        if (isPlaying.get()) {
            long elapsedMillis = Duration.between(toneStartTime, Instant.now()).toMillis();

            if (elapsedMillis < minPlayTimeMillis) {
                // Delay stopping the audio to ensure it plays at least for minPlayTimeMillis
                new Thread(() -> {
                    try {
                        Thread.sleep(minPlayTimeMillis - elapsedMillis);  // Wait the remaining time
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    stopTone();
                }).start();
            } else {
                stopTone();  // Stop audio immediately if already played >minPlayTimeMillis
            }
        }
    }

    // Helper method to stop the tone
    private void stopTone() {
        if (isPlaying.getAndSet(false) && line != null && line.isOpen()) {
            line.stop();
            line.flush();
        }
    }

    // Close the audio line (when done using)
    public void close() {
        if (line != null) {
            line.close();
        }
    }
}

enum Note {
    A4; // Single tone for testing

    public static final int SAMPLE_RATE = 16 * 1024; // ~16KHz
    private static final double FREQUENCY = 440.0; // A4 frequency (440 Hz)

    // Generate a sine wave for the tone with adjustable volume
    public byte[] data(int volume) {
        byte[] sin = new byte[SAMPLE_RATE];
        double maxAmplitude = 127.0 * (volume * 30.0 / 100.0 / 100.0); // Adjust amplitude based on volume (0 to 100)

        for (int i = 0; i < sin.length; i++) {
            double period = (double) SAMPLE_RATE / FREQUENCY;
            double angle = 2.0 * Math.PI * i / period;
            sin[i] = (byte) (Math.sin(angle) * maxAmplitude);
        }
        return sin;
    }
}