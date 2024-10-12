package edu.augustana;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert.AlertType;

import java.util.Random;

public class LevelController {

    @FXML
    private Label letterLabel;    // Label to display the random letter/word/phrase
    @FXML
    private Label morseCodeLabel; // Label to display the user's current morse code
    @FXML
    private ChoiceBox<String> levelChoiceBox; // ChoiceBox for selecting difficulty level

    private String currentText;       // The current random letter/word/phrase
    private StringBuilder userInput = new StringBuilder(); // To collect user's Morse code input
    private long keyPressTime;          // To store the time the space bar is pressed
    private final long DOT_THRESHOLD = 300; // Threshold in milliseconds to differentiate dot and dash

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
        });

        // Start with the appropriate level (default: Easy)
        generateRandomText();


    }

    // Adjust the difficulty level and update the display text accordingly
    private void changeDifficultyLevel(String level) {
        currentLevel = level;
        generateRandomText();  // Generate the appropriate text based on the selected level
    }

    private void handleKeyPress(KeyEvent event) {
        // Only respond to the space bar being pressed
        if (event.getCode() == KeyCode.SPACE) {
            // Start measuring the key press time when the space bar is pressed
            keyPressTime = System.currentTimeMillis();
        }
    }

    @FXML
    private void handleKeyRelease(KeyEvent event) {
        // Only respond to the space bar being released
        if (event.getCode() == KeyCode.SPACE) {
            // Calculate how long the space bar was held down
            long duration = System.currentTimeMillis() - keyPressTime;

            // Determine if the input is a dot or dash and append it to the userInput
            if (duration < DOT_THRESHOLD) {
                userInput.append(".");
            } else {
                userInput.append("-");
            }

            // Update the UI to show the user's Morse code input so far
            morseCodeLabel.setText(userInput.toString());

            // Check if the user's input matches the correct Morse code for the current text (letter, word, or phrase)
            checkMorseCode();
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
        morseCodeLabel.setText("");  // Clear the Morse code label for the new letter
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

    private void checkMorseCode() {
        // Use MorseCodeTranslator to validate the user's input
        if (morseCodeTranslator.validateMorseCode(currentText, userInput.toString())) {
            showAlert("Correct!", "You entered the correct Morse code for " + currentText);
            generateRandomText(); // Move to the next letter/word/phrase
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
