package edu.augustana;

import javax.sound.sampled.LineUnavailableException;

public class MorseSoundGenerator {
    private static final int BASE_DIT_DURATION = 1200; // Base value for 1 WPM

    public static void playMorseCode(String morseCode, int wpm, int effectiveSpeed, double frequencyHz)
            throws LineUnavailableException {

        // Calculate durations for dit and dash based on character WPM
        int ditDuration = BASE_DIT_DURATION / wpm; // Dit duration in milliseconds
        int dashDuration = ditDuration * 3;       // Dash is 3 times the length of a dit

        // Adjust pauses based on effective WPM
        int intraCharPause = ditDuration;                 // Short pause between dits/dashes within a character
        int interCharPause = BASE_DIT_DURATION / effectiveSpeed * 3; // Longer pause between characters
        int wordPause = BASE_DIT_DURATION / effectiveSpeed * 7;      // Longest pause between words

        // Set frequency for the tone
        Note.TONE.setFrequency(frequencyHz);

        TonePlayer tonePlayer = new TonePlayer(ditDuration);

        try {
            for (char symbol : morseCode.toCharArray()) {
                switch (symbol) {
                    case '.':
                        tonePlayer.startAudio();
                        Thread.sleep(ditDuration); // Play "dit"
                        tonePlayer.stopAudio();
                        Thread.sleep(intraCharPause); // Short pause within a character
                        break;
                    case '-':
                        tonePlayer.startAudio();
                        Thread.sleep(dashDuration); // Play "dash"
                        tonePlayer.stopAudio();
                        Thread.sleep(intraCharPause); // Short pause within a character
                        break;
                    case ' ':
                        Thread.sleep(wordPause); // Pause for a space (between words)
                        break;
                    default:
                        System.out.println("Invalid character in Morse code input: " + symbol);
                        break;
                }
                // Pause between characters if it’s not a word space
                if (symbol != ' ') {
                    Thread.sleep(interCharPause);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Morse code playback interrupted.");
        } finally {
            tonePlayer.close(); // Release resources
        }
    }
}
