package edu.augustana;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javax.sound.sampled.LineUnavailableException;
import java.time.Duration;
import java.time.Instant;
import java.util.InputMismatchException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MorseHandler {

    private final TonePlayer tonePlayer = new TonePlayer(App.minPlayTimeSound);
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private Runnable timerTask;
    private static final MorseTranslator morseCodeTranslator = new MorseTranslator();  // Instance of MorseCodeTranslator

    private Instant keyPressTime;          // To store the time the space bar is pressed
    private StringBuilder userInput = new StringBuilder();
    private StringBuilder userInputLettersString = new StringBuilder();
    private final CallbackPress keypressCallback;
    private final CallbackRelease keyreleaseCallback;
    public MorseHandler(CallbackPress keypressCallback, CallbackRelease keyreleaseCallback, Node element){
        this.keypressCallback = keypressCallback;
        this.keyreleaseCallback = keyreleaseCallback;
        element.setOnKeyPressed(this::handleKeyPress);
        element.setOnKeyReleased(this::handleKeyRelease);
    }

    public void handleKeyPress(KeyEvent event) {
        // Only respond to the space bar being pressed
        if (event.getCode() == KeyCode.SPACE && keyPressTime==null) {
            try{
                tonePlayer.startAudio();
                keypressCallback.onComplete();
            } catch(LineUnavailableException e){
                System.out.println(e);
            }
            keyPressTime = Instant.now();

            if (timerTask != null) {
                scheduler.shutdownNow(); // Cancel any previously running timer
                scheduler = Executors.newSingleThreadScheduledExecutor(); // Reset scheduler
            }
        }
    }


    private void handleKeyRelease(KeyEvent event) {
        // Only respond to the space bar being released
        if (event.getCode() == KeyCode.SPACE && keyPressTime != null) {
            // Calculate how long the space bar was held down
            Instant end = Instant.now();
            Duration duration = Duration.between(keyPressTime, end);
            long elapsedMillis = duration.toMillis();
            keyPressTime = null;
            tonePlayer.stopAudio();
            // Determine if the input is a dot or dash and append it to the userInput
            if (elapsedMillis < App.DOT_THRESHOLD) {
                userInput.append(".");
            } else {
                userInput.append("-");
            }
            keyreleaseCallback.onComplete();


            // Create a new timer task that will run after the TIMER_DELAY
            timerTask = () -> {
                Platform.runLater(() -> {

                    // Try to check the Morse code after the timer finishes
                    try {
                        String letter = checkMorseCode();
                        userInputLettersString.append(letter);
                        keyreleaseCallback.onTimerComplete(letter);
                        this.clearUserInput();
                    } catch (InputMismatchException e) {
                        keyreleaseCallback.onTimerCatch(e);
                    }
                });

            };

            // Schedule the task after the specified delay
            scheduler.schedule(timerTask, App.TIMER_DELAY, TimeUnit.MILLISECONDS);


        }
    }
    private String checkMorseCode() {
        return morseCodeTranslator.getLetter(userInput.toString());
    }
    public void clearUserInput(){
        this.userInput = new StringBuilder();
    }
    public void clearUserInputLetters(){
        this.userInputLettersString = new StringBuilder();
    }

    public StringBuilder getUserInputLetters(){
        return this.userInputLettersString;
    }
    public StringBuilder getUserInput(){
        return this.userInput;
    }

}
