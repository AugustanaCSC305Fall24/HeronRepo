package edu.augustana.Scenarios;

import edu.augustana.App;
import edu.augustana.Scenarios.ScenarioBots.DataManager;
import edu.augustana.Scenarios.ScenarioBots.ScriptedBot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScenarioMenuController {
    @FXML
    private Slider scenarioDuration;

    @FXML
    private ComboBox<String> synopsis;

    @FXML
    private ComboBox<String> botType;

    @FXML
    private Slider transmissionSpeed;

    @FXML
    private ImageView saveScenario;

    @FXML
    private ImageView openScenario;



    private List<String> listOfBotTypes = new ArrayList<>();


    private List<String> listOfScenarios = new ArrayList<>();

    // Method to save scenario as JSON
    @FXML
    private void saveScenarioAsJson() {
        double duration = scenarioDuration.getValue();
        String selectedSynopsis = synopsis.getValue();
        String selectedBotType = botType.getValue();
        double speed = transmissionSpeed.getValue();

        ScenarioData scenarioData = new ScenarioData(duration, selectedSynopsis, selectedBotType, speed);
        scenarioData.exportToJson("scenarioData.json");

        System.out.println("Scenario data saved to JSON.");
    }

    // Method to load scenario from JSON
    @FXML
    private void openScenarioFromJson() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Scenario File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            ScenarioData scenarioData = ScenarioData.importFromJson(file.getAbsolutePath());

            if (scenarioData != null) {
                // Apply loaded data to UI components
                scenarioDuration.setValue(scenarioData.getDuration());
                synopsis.setValue(scenarioData.getSynopsis());
                botType.setValue(scenarioData.getBotType());
                transmissionSpeed.setValue(scenarioData.getTransmissionSpeed());

                System.out.println("Scenario data loaded from JSON.");
            }
        }
    }

    @FXML
    void initialize() {
        listOfScenarios.add("Rescue Operation");
        listOfScenarios.add("Weather Report");
        listOfScenarios.add("Mountain Expedition");

        synopsis.getItems().addAll(listOfScenarios);
        synopsis.setValue(listOfScenarios.get(0));

        listOfBotTypes.add("Regular Bot");
        listOfBotTypes.add("AI Bot");

        botType.getItems().addAll(listOfBotTypes);
        botType.setValue(listOfBotTypes.get(0));
    }

    @FXML
    private void pressBackButton(ActionEvent event) throws IOException {
        App.setRoot("Menu");
    }
    @FXML
    private void pressPlayButton(ActionEvent event) throws IOException {
        ScenarioData scenarioData = new ScenarioData(
                scenarioDuration.getValue(),
                synopsis.getValue(),
                botType.getValue(),
                transmissionSpeed.getValue()
        );

        // Store the scenario data
        DataManager.getInstance().setScenarioData(scenarioData);

        // Switch to the ScenarioHamRadio scene
        App.setRoot("ScenarioHamRadio");
    }


}
