package edu.augustana;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class TonePlayer {

    private SourceDataLine line;
    private final AudioFormat audioFormat;

    // Constructor to set up the audio format
    public TonePlayer() {
        audioFormat = new AudioFormat(Note.SAMPLE_RATE, 8, 1, true, true);
    }

    // Start playing audio
    public void startAudio() throws LineUnavailableException {
        if (line == null || !line.isOpen()) {
            line = AudioSystem.getSourceDataLine(audioFormat);
            line.open(audioFormat, Note.SAMPLE_RATE);
        }
        line.start(); // Start the audio line

        // Play the tone in a separate thread to avoid blocking the main thread
        new Thread(() -> {
            byte[] toneData = Note.A4.data();
            while (line.isOpen()) {
                line.write(toneData, 0, toneData.length);
            }
        }).start();
    }

    // Stop the audio
    public void stopAudio() {
        if (line != null && line.isOpen()) {
            line.stop();  // Stop playback
            line.flush(); // Clear the buffer
            line.close(); // Close the line
        }
    }

    public static void main(String[] args) {
        TonePlayer player = new TonePlayer();

        // Simulate button press and release
        try {
            System.out.println("Playing...");
            player.startAudio();  // Start audio (simulate button press)
            Thread.sleep(1000);    // Keep playing for 1 second
            System.out.println("Stopped.");
            player.stopAudio();    // Stop audio (simulate button release)
        } catch (InterruptedException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}

enum Note {
    A4; // Single tone for testing

    public static final int SAMPLE_RATE = 16 * 1024; // ~16KHz
    public static final int SECONDS = 2;
    private final byte[] sin = new byte[SECONDS * SAMPLE_RATE];

    // Generate a sine wave for the tone
    Note() {
        double frequency = 440.0; // A4 frequency (440 Hz)
        for (int i = 0; i < sin.length; i++) {
            double period = (double) SAMPLE_RATE / frequency;
            double angle = 2.0 * Math.PI * i / period;
            sin[i] = (byte) (Math.sin(angle) * 127f);
        }
    }

    public byte[] data() {
        return sin;
    }
}