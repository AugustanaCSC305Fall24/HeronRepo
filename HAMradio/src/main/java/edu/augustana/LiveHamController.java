package edu.augustana;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;

public class LiveHamController {
    @FXML private Label chosenFrequency;
    @FXML private Label userMessage;
    @FXML private RadioButton showEnglishText;
    @FXML private Slider frequencySlider;
    @FXML private Slider filterSlider;

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



}
