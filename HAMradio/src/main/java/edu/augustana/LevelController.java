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
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.scene.layout.BorderPane;

import javax.sound.sampled.LineUnavailableException;

public class LevelController {

    @FXML
    private BorderPane borderPane;
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
    private String currentText;       // The current random letter/word/phrase
    private String currentLevel = "Easy";  // Store the current level
    private MorseHandler morseHandler;
    private String[] words = {"NAME", "PWR", "FB", "73", "QSY?", "DE"}; // Example words
    private String[] phrases = {"BT HW COPY?", "TNX FER CALL", "BT QTH IS"}; // Example phrases
    private Map<String, String> definitionsMap = new HashMap<>();
    private MorseTranslator morseTranslator = new MorseTranslator(); // Instance of MorseTranslator

    public void initialize() {
        // Populate the level choice box with levels
        // ... existing code

        // Populate definitions
        definitionsMap.put("NAME", "");
        definitionsMap.put("PWR", "transmission power");
        definitionsMap.put("FB", "fine business");
        definitionsMap.put("73", "best regards");
        definitionsMap.put("QSY?", "A question asking if the other party would like to change frequency.");
        definitionsMap.put("DE", "Abbreviation for 'from' in amateur radio communication.");
        definitionsMap.put("BT HW COPY?", "Asking how well the receiver can copy the message.");
        definitionsMap.put("TNX FER CALL", "Thanking the other operator for their call.");
        definitionsMap.put("BT QTH IS", "Asking for the other operator's location.");



        // Populate the level choice box with levels
        levelChoiceBox.getItems().addAll("Easy", "Medium", "Hard");
        levelChoiceBox.setValue("Easy");  // Default selection
        morseHandler = new MorseHandler(new CallbackPress() {
            @Override
            public void onComplete() {

            }
        }, new CallbackRelease() {
            @Override
            public void onComplete() {
                morseCodeLabel.setText(morseHandler.getUserInput().toString());

            }

            @Override
            public void onTimerComplete(String letter) {
                String userInputLetters = morseHandler.getUserInputLetters().toString().trim();
                userInputLettersLabel.setText(userInputLetters.toString());
                // Check if the lengths match
                if (userInputLetters.length() != currentText.length()) {
                    throw new InputMismatchException("Morse code input length does not match the expected text length.");
                }

                // Convert currentText to Morse code for comparison
                String expectedMorse = morseTranslator.getMorseCode(currentText);
                System.out.println(currentText);
                System.out.println(userInputLetters);

// Compare the expected Morse code with the user input
                if (!userInputLetters.equals(currentText)) {
                    throw new InputMismatchException("Morse code is different than the text");
                }

                generateRandomText();
                morseHandler.clearUserInputLetters();
                morseHandler.clearUserInput();

            }

            @Override
            public void onTimerWordComplete() {

            }

            @Override
            public void onTimerCatch(InputMismatchException e) {
                System.out.println(e.getMessage());
                String incorrectAnswer = morseHandler.getUserInputLetters().toString();
                userInputLettersLabel.setText("Try again (incorrect input: " + incorrectAnswer + ")");
                morseHandler.clearUserInputLetters();
                morseHandler.clearUserInput();
                morseCodeLabel.setText("");

            }
        }, borderPane);

        // Add a listener for level changes
        levelChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            changeDifficultyLevel(newValue);
            letterLabel.requestFocus();
            morseHandler.clearUserInputLetters();
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

    private void playCorrectMorse(String text) {
        String morse = morseTranslator.getMorseCode(text); // Translate text to Morse code
        try {
            int characterSpeed = 10; // Example character speed in WPM
            int spaceSpeed = 5;     // Example space speed in WPM
            double frequencyHz = 700; // Typical CW frequency for ham radio

            // Play the Morse code sound
            MorseSoundGenerator.playMorseCode(morse, characterSpeed, spaceSpeed, frequencyHz);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void generateRandomText() {
        // Generate text based on the selected difficulty level
        String beforeGenerate = currentText;
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
        // Play correct morse code
        playCorrectMorse(currentText);

        if (currentText.equals(beforeGenerate)) {
            generateRandomText();
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
        morseHandler.clearUserInput();


    }

    private void generateRandomWord() {
        // Generate a random word from the predefined list
        Random random = new Random();
        currentText = words[random.nextInt(words.length)];

        // Set the random word in the label
        letterLabel.setText(currentText);

        // Reset user input and update the morse code label
        morseHandler.clearUserInput();
        morseCodeLabel.setText("");  // Clear the Morse code label for the new word
    }

    private void generateRandomPhrase() {
        // Generate a random phrase from the predefined list
        Random random = new Random();
        currentText = phrases[random.nextInt(phrases.length)];

        // Set the random phrase in the label
        letterLabel.setText(currentText);

        // Reset user input and update the morse code label
        morseHandler.clearUserInput();
        morseCodeLabel.setText("");  // Clear the Morse code label for the new phrase
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
