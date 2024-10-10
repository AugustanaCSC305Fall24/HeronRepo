package edu.augustana;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.util.Random;

public class EasyLevelController {

    @FXML
    private Label letterLabel;    // Label to display the random letter
    @FXML
    private Label morseCodeLabel; // Label to display the user's current morse code

    private String currentLetter;       // The current random letter
    private StringBuilder userInput = new StringBuilder(); // To collect user's Morse code input
    private long keyPressTime;          // To store the time the space bar is pressed
    private final long DOT_THRESHOLD = 300; // Threshold in milliseconds to differentiate dot and dash

    private MorseTranslator morseCodeTranslator;  // Instance of MorseCodeTranslator

    public void initialize() {
        // Define allowed characters for the Morse code translator
        String[] allowedCharacters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                "K", "L", "M", "N", "O", "P", "Q", "R", "S",
                "T", "U", "V", "W", "X", "Y", "Z"};

        // Initialize the MorseCodeTranslator with the allowed characters
        morseCodeTranslator = new MorseTranslator();

        // Start with a new random letter
        generateRandomLetter();
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
            morseCodeLabel.setText(userInput.toString());  // <-- Update the label immediately after each tap

            // Check if the user's input matches the correct Morse code for the current letter
            checkMorseCode();
        }
    }

    private void generateRandomLetter() {
        // Generate a random letter from A-Z
        Random random = new Random();
        char randomChar = (char) (random.nextInt(26) + 'A');
        currentLetter = String.valueOf(randomChar);

        // Set the random letter in the label
        letterLabel.setText(currentLetter);

        // Reset user input and update the morse code label
        userInput.setLength(0);
        morseCodeLabel.setText("");  // Clear the Morse code label for the new letter
    }

    private void checkMorseCode() {
        // Use MorseCodeTranslator to validate the user's input
        if (morseCodeTranslator.validateMorseCode(currentLetter, userInput.toString())) {
            showAlert("Correct!", "You entered the correct Morse code for " + currentLetter);
            generateRandomLetter(); // Move to the next letter
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
