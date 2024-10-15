package edu.augustana;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;

public class HomeController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("Menu");
    }

}
