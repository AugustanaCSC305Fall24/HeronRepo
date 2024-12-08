package edu.augustana.data.Scenarios;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class AiScenarioFileHandler {

    public void exportToJson(Map<String, AiBotDetails> botDetailsMap, TextField scenarioNameField,
                             TextArea scenarioDescriptionArea, TextArea scenarioNotesArea) {
        Gson gson = new Gson();

        // Create a file chooser to save the JSON file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Scenario as JSON");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                // Create a container for scenario data
                AiScenarioData scenarioData = new AiScenarioData(
                        scenarioNameField.getText(),
                        scenarioDescriptionArea.getText(),
                        scenarioNotesArea.getText(),
                        new HashMap<>(botDetailsMap)
                );

                gson.toJson(scenarioData, writer);
                showAlert("Success", "Scenario saved successfully!");
            } catch (IOException e) {
                showAlert("Error", "Failed to save scenario: " + e.getMessage());
            }
        }
    }

    public void importFromJson(Map<String, AiBotDetails> botDetailsMap, ObservableList<String> bots,
                               TextField scenarioNameField, TextArea scenarioDescriptionArea, TextArea scenarioNotesArea) {
        Gson gson = new Gson();

        // Create a file chooser to open a JSON file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Scenario JSON");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try (FileReader reader = new FileReader(file)) {
                // Define the type of the data to deserialize
                Type scenarioDataType = new TypeToken<AiScenarioData>() {}.getType();
                AiScenarioData scenarioData = gson.fromJson(reader, scenarioDataType);

                // Populate the UI fields with the loaded data
                scenarioNameField.setText(scenarioData.getName());
                scenarioDescriptionArea.setText(scenarioData.getDescription());
                scenarioNotesArea.setText(scenarioData.getNotes());

                botDetailsMap.clear();
                botDetailsMap.putAll(scenarioData.getBotDetails());
                bots.setAll(botDetailsMap.keySet());

                showAlert("Success", "Scenario loaded successfully!");
            } catch (IOException e) {
                showAlert("Error", "Failed to load scenario: " + e.getMessage());
            }
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
