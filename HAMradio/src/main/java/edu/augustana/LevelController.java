package edu.augustana;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert.AlertType;

import java.time.Duration;
import java.time.Instant;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.util.Random;
import javafx.event.ActionEvent;

import javax.sound.sampled.LineUnavailableException;

public class LevelController {

    @FXML
    private Label letterLabel;    // Label to display the random letter/word/phrase
    @FXML
    private Label morseCodeLabel; // Label to display the user's current morse code
    @FXML
    private Label userInputLettersLabel;
    @FXML
    private ChoiceBox<String> levelChoiceBox; // ChoiceBox for selecting difficulty level
    @FXML
    private Slider volumeSlider;
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private Runnable timerTask;
    private long TIMER_DELAY = 1000; // 1 second delay for testing purposes
    private TonePlayer tonePlayer = new TonePlayer(App.minPlayTimeSound);
    private String currentText;       // The current random letter/word/phrase
    private StringBuilder userInput = new StringBuilder(); // To collect user's Morse code input
    private StringBuilder userInputLettersString = new StringBuilder(); // To collect user's Morse code input
    private Instant keyPressTime;          // To store the time the space bar is pressed
    private int step = 0;
    private final long DOT_THRESHOLD = 150; // Threshold in milliseconds to differentiate dot and dash

    private MorseTranslator morseCodeTranslator;  // Instance of MorseCodeTranslator
    private String currentLevel = "Easy";  // Store the current level

    private String[] words = {"HELLO", "WORLD", "JAVA", "MORSE", "CODE"}; // Example words
    private String[] phrases = {"HELLO WORLD", "JAVA MORSE CODE", "LEARNING JAVA"}; // Example phrases

    public void initialize() {
        // Initialize the MorseCodeTranslator
        morseCodeTranslator = new MorseTranslator();

        

        // Populate the level choice box with levels
        levelChoiceBox.getItems().addAll("Easy", "Medium", "Hard");
        levelChoiceBox.setValue("Easy");  // Default selection


        // Add a listener for level changes
        levelChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            changeDifficultyLevel(newValue);
            letterLabel.requestFocus();
            userInputLettersString = new StringBuilder();
            userInputLettersLabel.setText("Your input will appear here");

        });

        // Start with the appropriate level (default: Easy)
        generateRandomText();

        volumeSlider.adjustValue((double) App.volume);
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            App.volume = newValue.intValue();  // Update the volume variable
        });


    }


    // Adjust the difficulty level and update the display text accordingly
    private void changeDifficultyLevel(String level) {
        currentLevel = level;
        generateRandomText();  // Generate the appropriate text based on the selected level
    }

    @FXML
    public void handleKeyPress(KeyEvent event) {
        // Only respond to the space bar being pressed
        if (event.getCode() == KeyCode.SPACE && keyPressTime==null) {
            try{
                tonePlayer.startAudio();

            } catch(LineUnavailableException e){
                System.out.println(e);
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
            morseCodeLabel.setText(userInput.toString());

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
                    userInputLettersLabel.setText(userInputLettersString.toString());
                    for (int i = 0; i < userInputLettersString.length(); i++){
                        if(userInputLettersString.charAt(i) != currentText.charAt(i)){
                            throw new InputMismatchException("Morse code is different than the text");
                        }
                    }
                    userInput = new StringBuilder();
                    morseCodeLabel.setText("");
                } catch (InputMismatchException e) {
                    System.out.println(e.getMessage());
                    userInputLettersString = new StringBuilder();
                    userInputLettersLabel.setText("Try Again");
                    userInput = new StringBuilder();
                    morseCodeLabel.setText("");

                }
                });

            };

            // Schedule the task after the specified delay
            scheduler.schedule(timerTask, TIMER_DELAY, TimeUnit.MILLISECONDS);


        }
    }

    private void generateRandomText() {
        // Generate text based on the selected difficulty level
        switch (currentLevel) {
            case "Easy":
                generateRandomLetter();
                break;
            case "Medium":
                generateRandomWord();
                break;
            case "Hard":
                generateRandomPhrase();
                break;
        }
    }

    private void generateRandomLetter() {
        // Generate a random letter from A-Z
        Random random = new Random();
        char randomChar = (char) (random.nextInt(26) + 'A');
        currentText = String.valueOf(randomChar);

        // Set the random letter in the label
        letterLabel.setText(currentText);

        // Reset user input and update the morse code label
        userInput.setLength(0);

    }

    private void generateRandomWord() {
        // Generate a random word from the predefined list
        Random random = new Random();
        currentText = words[random.nextInt(words.length)];

        // Set the random word in the label
        letterLabel.setText(currentText);

        // Reset user input and update the morse code label
        userInput.setLength(0);
        morseCodeLabel.setText("");  // Clear the Morse code label for the new word
    }

    private void generateRandomPhrase() {
        // Generate a random phrase from the predefined list
        Random random = new Random();
        currentText = phrases[random.nextInt(phrases.length)];

        // Set the random phrase in the label
        letterLabel.setText(currentText);

        // Reset user input and update the morse code label
        userInput.setLength(0);
        morseCodeLabel.setText("");  // Clear the Morse code label for the new phrase
    }

    private String checkMorseCode() {
        return morseCodeTranslator.getLetter(userInput.toString());
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void SwitchMenuButton(ActionEvent event) throws IOException {
        App.setRoot("Menu");
    }
}
