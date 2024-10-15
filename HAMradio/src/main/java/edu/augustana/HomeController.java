package edu.augustana;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;

public class HomeController {

    @FXML
    private Slider volumeSlider;


    public void initialize(){
        volumeSlider.adjustValue((double) App.volume);
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            App.volume = newValue.intValue();  // Update the volume variable
        });
    }
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("Menu");
    }

}
