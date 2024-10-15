package edu.augustana;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.event.ActionEvent; // Correct import
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.InputMismatchException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LiveHamController {
    @FXML private Label chosenFrequency;
    @FXML private Label userMessageMorse;
    @FXML private Label userMessageInEnglish;
    @FXML private RadioButton showEnglishText;
    @FXML private Slider frequencySlider;
    @FXML private Slider filterSlider;
    @FXML private Button returnMenuButton;

    private StringBuilder userInput = new StringBuilder();
    private StringBuilder userInputLettersString = new StringBuilder();


    private Instant keyPressTime;

    private static final StaticNoisePlayer staticNoisePlayer = new StaticNoisePlayer();


    private TonePlayer tonePlayer = new TonePlayer();

    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private Runnable timerTask;


    private MorseTranslator morseCodeTranslator;  // Instance of MorseCodeTranslator

    // things to put in new Class MorseHandler
    private String frequencyUnit = " Mhz";
    private final int minFrequency = 200;
    private final int maxFrequency = 500;
    private final int initialFrequency = 350;

    private final long DOT_THRESHOLD = 150;
    private long TIMER_DELAY = 1000;



    public void initialize() {
        morseCodeTranslator = new MorseTranslator();

        frequencySlider.setMin(minFrequency);
        frequencySlider.setMax(maxFrequency);
        frequencySlider.setValue(initialFrequency);
       chosenFrequency.setText(Double.toString(frequencySlider.getValue()) + frequencyUnit);
       userMessageMorse.setText("Your message will be shown here");


        frequencySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            chosenFrequency.setText(String.format("%.2f", newValue) + frequencyUnit);
        });

        // this part is for simulating static noise
        try {

            staticNoisePlayer.startNoise();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        userMessageMorse.requestFocus();

    }

    @FXML
    public void handleKeyPress(KeyEvent event) {
        // Only respond to the space bar being pressed
        if (event.getCode() == KeyCode.SPACE && keyPressTime==null) {
            try{
                tonePlayer.startAudio();

            } catch(LineUnavailableException e){
                System.out.println(e.getMessage());
            }
            keyPressTime = Instant.now();

        }
    }

    @FXML
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
            if (elapsedMillis < DOT_THRESHOLD) {
                userInput.append(".");
            } else {
                userInput.append("-");
            }

            // Update the UI to show the user's Morse code input so far
            userMessageMorse.setText(userInput.toString());

            // Cancel and reschedule the timer
            if (timerTask != null) {
                scheduler.shutdownNow(); // Cancel any previously running timer
                scheduler = Executors.newSingleThreadScheduledExecutor(); // Reset scheduler
            }

            // Create a new timer task that will run after the TIMER_DELAY
            timerTask = () -> {
                Platform.runLater(() -> {

                    // Try to check the Morse code after the timer finishes
                    try {
                        String letter = checkMorseCode();
                        userInputLettersString.append(letter);
                        if (userInputLettersString.length() > 40){
                            userInputLettersString.deleteCharAt(0);
                        }

                        userMessageInEnglish.setText(userInputLettersString.toString());


                    } catch (InputMismatchException e) {
                        System.out.println(e.getMessage());

                    }
                });

            };

            // Schedule the task after the specified delay
            scheduler.schedule(timerTask, TIMER_DELAY, TimeUnit.MILLISECONDS);


        }
    }


    // this should open new window where user can input sentence that will be received
    // and enter a double that will be frequency of sender
    @FXML
    private void simulateReceiving(){

    }
    private String checkMorseCode() {
        return morseCodeTranslator.getLetter(userInput.toString());
    }



    @FXML
    void SwitchMenuButton(ActionEvent event) throws IOException {
        App.setRoot("Menu");  // Assuming App.setRoot is a static method to change scenes
    }
}
