package edu.augustana.ui;

import edu.augustana.data.HamRadio;
import edu.augustana.dataModel.AiBotDetails;
import edu.augustana.dataModel.AiScenarioData;
import edu.augustana.dataModel.CWMessage;
import edu.augustana.helper.handlers.GeminiAiHandler;
import edu.augustana.data.AiScenarioPlayed;
import edu.augustana.helper.handlers.MorseTranslator;
import edu.augustana.interfaces.HamControllerCallback;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class AiScenarioHamController extends HamController implements HamControllerCallback {
    @FXML
    private VBox rootVBox;  // Matches the fx:id for the root VBox in LiveHamController's FXML
    private HamController hamController;
    private double frequency;
    private AiScenarioData scenarioData = AiScenarioPlayed.instance.getData();
    private GeminiAiHandler geminiAiHandler = new GeminiAiHandler();
    private ArrayList<String> botFrequencies = new ArrayList<>();
    private static Random randGen = new Random();
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


            if (scenarioData != null) {
                System.out.println("Received scenario data: " + scenarioData);
            } else {
                System.out.println("Scenario data is null!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private String generateFrequency(){
        int frequencyDecimal = AiScenarioHamController.randGen.nextInt(68);
        if (botFrequencies.contains("7.0"+frequencyDecimal)){
            return generateFrequency();
        }
        return "7.0"+frequencyDecimal;
    }
    @Override
    public void onInitialize() {
        // Configure "Start" button functionality here
        hamController.gridPane.getChildren().remove(hamController.simulateReceivingBtn);
        ArrayList<AiBotDetails> botsDetails = scenarioData.getBotsDetails();
        for (int i = 0; i < botsDetails.size(); i++) {
            AiBotDetails botDetails = botsDetails.get(i);
            boolean isStartingFirst = botDetails.isStartingBot();
            HBox hbox = new HBox();
            hbox.setSpacing(10);
            // Create UI components for bot details
            Text botNameTextEl = new Text();
            Text botFrequencyTextEl = new Text();
            botNameTextEl.setText(botDetails.getName());
            String botFrequency = generateFrequency();

            botFrequencies.add(botFrequency);
            botFrequencyTextEl.setText(botFrequency);


            hbox.getChildren().add(botNameTextEl);
            hbox.getChildren().add(botFrequencyTextEl);
            if (isStartingFirst){
//                Use Gemini AI to generate a scenario response
            String scenarioResponse = geminiAiHandler.generateScenarioResponse(
                    botDetails.getName(),
                    botDetails.getObjective()
            );
//            hamController.receiveMessage(new CWMessage(MorseTranslator.instance.getMorseCodeForText(scenarioResponse),Double.parseDouble(botFrequency) ));
            System.out.println(scenarioResponse);
                HamRadio.theRadio.setFrequency(Double.parseDouble(botFrequency));
            }
            hamController.leftBottomSection.getChildren().add(hbox);

        }
    }
    @Override
    public void onDitDahProcessed(char signalUnit) {
        System.out.println("Dit/Dah processed: " + signalUnit);
    }

    @Override
    public void onCharacterProcessed(char character) {
        System.out.println("Character processed: " + character);
    }

    @Override
    public void onMessageCompleted(String message) {
        System.out.println("Complete message received: " + message);

        // Process sending msg to AI
        // Label self as sentMessage
        // Get a response from AI
        // If: user sends another message after this one, stop processing sending msg to AI and combine the two messages

    }

    @Override
    public void onFrequencyChanged(double newFrequency) {
        System.out.println("Frequency changed to: " + newFrequency);
        frequency = newFrequency;
    }
}