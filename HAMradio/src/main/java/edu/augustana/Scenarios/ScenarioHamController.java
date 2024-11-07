package edu.augustana.Scenarios;


import edu.augustana.HamController;
import edu.augustana.HamControllerCallback;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ScenarioHamController extends HamController implements HamControllerCallback {
    @FXML
    private VBox rootVBox;  // This should match the fx:id for the root VBox in LiveHamController's FXML

    private HamController hamController;
    private double frequency;
    public void initialize() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/augustana/HamRadio.fxml"));

            // Load HamRadio.fxml as a BorderPane
            BorderPane hamInterface = loader.load();

            // Retrieve the HamController instance
            hamController = loader.getController();

            // Set the callback or perform other configuration with hamController
            hamController.setCallback(this);

            // Add hamInterface to rootVBox
            rootVBox.getChildren().add(hamInterface);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startBtn(){
        System.out.println("Hey I started");
        hamController.receiveMessage("what up", 7.035);
    }
    @Override
    public void onInitialize(){
        hamController.simulateReceivingBtn.setText("Start");
        hamController.simulateReceivingBtn.setOnAction((e)-> {
            startBtn();
        });
    }
    @Override
    public void onDitDahProcessed(char signalUnit) {
        System.out.println("Dit/Dah processed: " + signalUnit);
    }

    @Override
    public void onCharacterProcessed(char character) {
        System.out.println("Character processed: " + character);
        // Only send to server if needed for specific cases
    }

    @Override
    public void onMessageCompleted(String message) {
        System.out.println("Complete message received: " + message);
    }

    @Override
    public void onFrequencyChanged(double newFrequency) {
        System.out.println("Frequency changed to: " + newFrequency);
        frequency = newFrequency;
        // Custom behavior after frequency change

    }

}