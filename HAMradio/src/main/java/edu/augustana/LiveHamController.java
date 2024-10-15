package edu.augustana;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.Button;
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
    @FXML private Label chosenFrequency;
    @FXML private Label userMessageMorse;
    @FXML private Label userMessageInEnglish;
    @FXML private RadioButton showEnglishText;
    @FXML private Slider frequencySlider;
    @FXML private Slider filterSlider;
    @FXML private Button returnMenuButton;
    @FXML
    private BorderPane borderPane;

    private StringBuilder userInput = new StringBuilder();
    private StringBuilder userInputLettersString = new StringBuilder();


    private Instant keyPressTime;



    private TonePlayer tonePlayer = new TonePlayer(App.minPlayTimeSound);

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

    private MorseHandler morseHandler;

    public void initialize() {
        morseCodeTranslator = new MorseTranslator();

        frequencySlider.setMin(minFrequency);
        frequencySlider.setMax(maxFrequency);
        frequencySlider.setValue(initialFrequency);
       chosenFrequency.setText(Double.toString(frequencySlider.getValue()) + frequencyUnit);
       userMessageMorse.setText("Your message will be shown here");
        morseHandler = new MorseHandler(new CallbackPress() {
            @Override
            public void onComplete() {

            }
        }, new CallbackRelease() {
            @Override
            public void onComplete() {
                // Update the UI to show the user's Morse code input so far
                userMessageMorse.setText(userInput.toString());

            }

            @Override
            public void onTimerComplete(String letter) {
                StringBuilder userInputLettersString = morseHandler.getUserInputLetters();
                if (userInputLettersString.length() > 40){
                    userInputLettersString.deleteCharAt(0);
                }
                userMessageInEnglish.setText(userInputLettersString.toString());

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
    private String checkMorseCode() {
        return morseCodeTranslator.getLetter(userInput.toString());
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
