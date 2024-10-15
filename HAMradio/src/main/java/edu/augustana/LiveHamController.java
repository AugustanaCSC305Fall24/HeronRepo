package edu.augustana;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent; // Correct import
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
    @FXML
    private Label chosenFrequency;
    @FXML
    private Label userMessageMorse;
    @FXML
    private Label userMessageInEnglish;
    @FXML
    private CheckBox showEnglishText;
    @FXML
    private Slider frequencySlider;
    @FXML
    private Slider filterSlider;
    @FXML
    private Button returnMenuButton;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Slider volumeSlider;


    // things to put in new Class MorseHandler
    private String frequencyUnit = " Mhz";
    private final int minFrequency = 200;
    private final int maxFrequency = 500;
    private final int initialFrequency = 350;


    private MorseHandler morseHandler;

    public void initialize() {

        frequencySlider.setMin(minFrequency);
        frequencySlider.setMax(maxFrequency);
        frequencySlider.setValue(initialFrequency);
        chosenFrequency.setText(Double.toString(frequencySlider.getValue()) + frequencyUnit);
        userMessageMorse.setText("Your message will be shown here");
        showEnglishText.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                userMessageInEnglish.setText("");
            }
        });
        morseHandler = new MorseHandler(new CallbackPress() {
            @Override
            public void onComplete() {

            }
        }, new CallbackRelease() {
            @Override
            public void onComplete() {
                // Update the UI to show the user's Morse code input so far
                userMessageMorse.setText(morseHandler.getUserInput().toString());

            }

            @Override
            public void onTimerComplete(String letter) {
                StringBuilder userInputLettersString = morseHandler.getUserInputLetters();
                if (userInputLettersString.length() > 40) {
                    userInputLettersString.deleteCharAt(0);
                }
                userMessageMorse.setText("");
                if (showEnglishText.isSelected()) {

                    userMessageInEnglish.setText(userInputLettersString.toString());
                } else {
                    userMessageInEnglish.setText("");
                }
            }

            @Override
            public void onTimerCatch(InputMismatchException e) {
                System.out.println(e.getMessage());

            }
        }, borderPane);


        frequencySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            chosenFrequency.setText(String.format("%.2f", newValue) + frequencyUnit);
        });

        // this part is for simulating static noise
        try {

            StaticNoisePlayer.startNoise();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        userMessageMorse.requestFocus();

        volumeSlider.adjustValue((double) App.volume);
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            App.volume = newValue.intValue();  // Update the volume variable
        });

    }

    @FXML
    private void handleTranslationCheckBoxSelected() {
        userMessageMorse.requestFocus();
    }


    // this should open new window where user can input sentence that will be received
    // and enter a double that will be frequency of sender
    @FXML
    private void simulateReceiving() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MessageInput.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load(), 580, 800));

            // Pass reference to the main controller
            MessageInputController controller = loader.getController();
            controller.setLiveHamController(this);

            stage.setTitle("Input Message and Frequency");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to receive and display the message and frequency from the new screen
    public void receiveMessage(String message, double frequency) {
        userMessageMorse.setText("Received: " + message + " on frequency " + String.format("%.2f", frequency) + frequencyUnit);
    }


    @FXML
    void SwitchMenuButton(ActionEvent event) throws IOException {
        App.setRoot("Menu");// Assuming App.setRoot is a static method to change scenes
        StaticNoisePlayer.stopNoise();
    }
}
