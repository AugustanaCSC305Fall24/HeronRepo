package edu.augustana;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MessageInputController {

    @FXML
    private TextField messageInput;

    @FXML
    private TextField frequencyInput;

    private LiveHamController liveHamController; // Reference to the main controller

    public void setLiveHamController(LiveHamController liveHamController) {
        this.liveHamController = liveHamController;
    }

    @FXML
    private void saveMessage() {
        String message = messageInput.getText();
        String frequency = frequencyInput.getText();

        // Validate frequency input
        try {
            double frequencyValue = Double.parseDouble(frequency);
            if (frequencyValue < 200 || frequencyValue > 500) {
                // Handle invalid frequency
                System.out.println("Frequency out of range!");
                return;
            }
            // Save the message and frequency to the main controller
            liveHamController.receiveMessage(message, frequencyValue);
            // Close the input window
            Stage stage = (Stage) messageInput.getScene().getWindow();
            stage.close();
        } catch (NumberFormatException e) {
            System.out.println("Invalid frequency format!");
        }
    }
}
