package edu.augustana;

import edu.augustana.Scenarios.ScenarioBot;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import javafx.scene.control.Slider;

public class MenuController {

    @FXML
    private Label welcomeLabel;

    // Method to update the welcome message
    public void setUsername(String username) {
        if (welcomeLabel != null) {
            welcomeLabel.setText("Welcome, " + username + "!");
        } else {
            System.out.println("welcomeLabel is null. Check your FXML for correct fx:id.");
        }
    }
    @FXML
    public void initialize() {
        // Retrieve the username from the session and set it when the scene is loaded
        String sessionUsername = UserSession.getInstance().getUsername();
        if (sessionUsername != null && welcomeLabel != null) {
            welcomeLabel.setText("Welcome, " + sessionUsername + "!");
        }
    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("Home");
    }

    @FXML
    private void switchToLevels() throws IOException {
        App.setRoot("Levels");
    }

    @FXML
    private void switchToHamRadio() throws IOException {
        App.setRoot("LiveHamRadio");
    }
    @FXML
    private void switchToScenario() throws IOException {
        App.setRoot("ScenarioMenu");
    }
}