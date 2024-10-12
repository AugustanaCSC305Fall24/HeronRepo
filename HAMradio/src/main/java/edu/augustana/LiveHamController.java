package edu.augustana;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.Button;
import javafx.event.ActionEvent; // Correct import
import java.io.IOException;

public class LiveHamController {
    @FXML private Label chosenFrequency;
    @FXML private Label userMessage;
    @FXML private RadioButton showEnglishText;
    @FXML private Slider frequencySlider;
    @FXML private Slider filterSlider;
    @FXML private Button returnMenuButton;

    private StaticNoisePlayer staticNoisePlayer = new StaticNoisePlayer();



    private String frequencyUnit = " Mhz";
    private final int minFrequency = 200;
    private final int maxFrequency = 500;
    private final int initialFrequency = 350;



    public void initialize() {
        frequencySlider.setMin(minFrequency);
        frequencySlider.setMax(maxFrequency);
        frequencySlider.setValue(initialFrequency);
       chosenFrequency.setText(Double.toString(frequencySlider.getValue()) + frequencyUnit);
       userMessage.setText("Your message will be shown here");

        frequencySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            chosenFrequency.setText(String.format("%.2f", newValue) + frequencyUnit);
        });





    }


    // this should open new window where user can input sentence that will be received
    // and enter a double that will be frequency of sender
    @FXML
    private void simulateReceiving(){

    }



    @FXML
    void SwitchMenuButton(ActionEvent event) throws IOException {
        App.setRoot("Menu");  // Assuming App.setRoot is a static method to change scenes
    }
}
