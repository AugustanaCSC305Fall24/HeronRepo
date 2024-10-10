package edu.augustana;

import javafx.fxml.FXML;

import java.io.IOException;

public class LevelController {

    @FXML
    private void switchToEasyLevel() throws IOException{
        App.setRoot("easyLevel");
    }
    @FXML
    private void switchToMediumLevel() throws IOException{
        App.setRoot("mediumLevel");
    }
    @FXML
    private void switchToHardLevel() throws IOException{
        App.setRoot("hardLevel");
    }



}
