package edu.augustana;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent; // Correct import
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.util.InputMismatchException;


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
    private final double minFrequency = 7.000;
    private final double maxFrequency = 7.067;
    private final double initialFrequency = 7.035;


    private MorseHandler morseHandler;

    private double transmittedFrequency;

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
            userMessageMorse.requestFocus();
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
            public void onTimerWordComplete() {
                StringBuilder userInputLetters = morseHandler.getUserInputLetters();
                userMessageInEnglish.setText(userInputLetters.toString());
            }
            @Override
            public void onTimerCatch(InputMismatchException e) {
                System.out.println(e.getMessage());
                morseHandler.clearUserInput();
                morseHandler.clearUserInputLetters();
                userMessageMorse.setText("");

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


        volumeSlider.adjustValue((double) App.volume);
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            App.volume = newValue.intValue();  // Update the volume variable
        });
        userMessageMorse.requestFocus();

    }

    @FXML
    private void handleTranslationCheckBoxSelected() {

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
    // Method to check if slider frequency matches transmitted frequency and play sound
    private void checkFrequencyAndPlaySound(double currentFrequency) {
        if (currentFrequency == transmittedFrequency) {
            playCWsound();  // Method to play the CW sound
        } else {
            stopCWsound();  // Stop the sound if the frequencies don't match
        }
    }


    // Method to play the CW sound
    private void playCWsound() {
        // logic for generating and playing the CW (Morse) sound


        System.out.println("Playing CW sound at the correct frequency!");
    }

    // Method to stop the CW sound
    private void stopCWsound() {
        // Logic to stop sound playback
        System.out.println("Stopping CW sound since frequency does not match.");
    }

    // Method to receive and display the message and frequency from the new screen
    public void receiveMessage(String message, double frequency, int WPM, int toneValue, int effectiveSpeedValue) {
        transmittedFrequency = frequency;  // Store the frequency
        userMessageMorse.setText("Received: " + message + " on frequency " + String.format("%.2f", frequency) + frequencyUnit);
        MorseTranslator translator = new MorseTranslator();
        StringBuilder morseMessage = new StringBuilder();
        for(int i =0; i < message.length(); i++){
            if(i != 0 && message.charAt(i) == ' ' && morseMessage.charAt(i-1) == ' '){
                morseMessage.deleteCharAt(morseMessage.toString().length() - 1);
                morseMessage.append("%");
            }else{
                morseMessage.append(translator.getMorseCode(String.valueOf(message.charAt(i))));
                morseMessage.append(" ");
            }

        }
        System.out.print(morseMessage);
        try{
            System.out.println(WPM);
            MorseSoundGenerator.playMorseCode(morseMessage.toString(), WPM, effectiveSpeedValue, toneValue);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

    }


    @FXML
    void SwitchMenuButton(ActionEvent event) throws IOException {
        App.setRoot("Menu");// Assuming App.setRoot is a static method to change scenes
        StaticNoisePlayer.stopNoise();
    }
}
