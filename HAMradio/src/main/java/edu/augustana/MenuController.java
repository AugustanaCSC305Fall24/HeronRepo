package edu.augustana;

import java.io.IOException;
import javafx.fxml.FXML;

public class MenuController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("Home");
    }

    @FXML
    private void switchToLevels() throws IOException{
        App.setRoot("Levels");
    }

    @FXML
    private void switchToHamRadio() throws IOException{
        App.setRoot("LiveHamRadio");
    }
}