package edu.augustana.ui;

import edu.augustana.data.Scenarios.AiBotDetails;
import edu.augustana.data.Scenarios.AiScenarioFileHandler;
import edu.augustana.data.Scenarios.ScenarioBots.DataManager;
import edu.augustana.data.Scenarios.ScenarioData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ScenarioAiBuilderController {

    @FXML
    private TextField scenarioNameField;
    @FXML
    private TextArea scenarioDescriptionArea;
    @FXML
    private TextArea scenarioNotesArea;

    @FXML
    private ListView<String> botListView;
    @FXML
    private Button addBotButton;
    @FXML
    private Button kickBotButton;

    @FXML
    private VBox botDetailsMenu;
    @FXML
    private TextField botTypeField;
    @FXML
    private TextField botNameField;
    @FXML
    private TextField botObjectiveField;
    @FXML
    private CheckBox isStartingBotCheckBox;

    @FXML
    private Button saveButton;
    @FXML
    private Button saveJsonButton;
    @FXML
    private Button openJsonButton;
    @FXML
    private Button playButton;
    @FXML
    private Button backButton;

    private final ObservableList<String> bots = FXCollections.observableArrayList();
    private final Map<String, AiBotDetails> botDetailsMap = new HashMap<>();
    private String currentlyEditingBot = null;

    private AiScenarioFileHandler fileHandler;

    @FXML
    public void initialize() {
        fileHandler = new AiScenarioFileHandler();

        botListView.setItems(bots);

        // Handle bot selection
        botListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                loadBotDetails(newSelection);
            }
        });

        // Add bot button action
        addBotButton.setOnAction(event -> showBotDetailsMenu(null));

        // Kick bot button action
        kickBotButton.setOnAction(event -> removeSelectedBot());

        // Save bot details action
        saveButton.setOnAction(event -> saveBotDetails());



        botDetailsMenu.setVisible(false);

        // Save to JSON button action
        saveJsonButton.setOnAction(event -> fileHandler.exportToJson(botDetailsMap, scenarioNameField, scenarioDescriptionArea, scenarioNotesArea));

        // Open JSON button action
        openJsonButton.setOnAction(event -> fileHandler.importFromJson(botDetailsMap, bots, scenarioNameField, scenarioDescriptionArea, scenarioNotesArea));
    }

    private void showBotDetailsMenu(String botName) {
        botDetailsMenu.setVisible(true);

        if (botName == null) {
            // Adding a new bot
            currentlyEditingBot = null;
            botTypeField.clear();
            botNameField.clear();
            botObjectiveField.clear();
            isStartingBotCheckBox.setSelected(false);
        } else {
            // Editing an existing bot
            currentlyEditingBot = botName;
            AiBotDetails details = botDetailsMap.get(botName);
            if (details != null) {
                botTypeField.setText(details.getType());
                botNameField.setText(details.getName());
                botObjectiveField.setText(details.getObjective());
                isStartingBotCheckBox.setSelected(details.isStartingBot());
            }
        }
    }

    private void loadBotDetails(String botName) {
        showBotDetailsMenu(botName);
    }

    private void removeSelectedBot() {
        String selectedBot = botListView.getSelectionModel().getSelectedItem();
        if (selectedBot != null) {
            bots.remove(selectedBot);
            botDetailsMap.remove(selectedBot);
        }
    }
    @FXML
    private void pressBackButton() throws IOException {
        App.setRoot("Menu");
    }
    @FXML
    private void pressPlayButton(ActionEvent event) throws IOException {

        // Switch to the AiScenarioHamRadio scene
        App.setRoot("AiScenarioHamRadio");
    }
    private void saveBotDetails() {
        // Collect data from bot details fields
        String botType = botTypeField.getText();
        String botName = botNameField.getText();
        String botObjective = botObjectiveField.getText();
        boolean isStartingBot = isStartingBotCheckBox.isSelected();

        if (botName.isEmpty()) {
            showAlert("Validation Error", "Bot Name cannot be empty.");
            return;
        }

        // Ensure only one starting bot
        if (isStartingBot) {
            for (Map.Entry<String, AiBotDetails> entry : botDetailsMap.entrySet()) {
                if (entry.getValue().isStartingBot() && !entry.getKey().equals(currentlyEditingBot)) {
                    showAlert("Validation Error", "Only one bot can be the starting bot.");
                    return;
                }
            }
        }

        // Update or add the bot
        AiBotDetails details = new AiBotDetails(botType, botName, botObjective, isStartingBot);

        if (currentlyEditingBot != null && !currentlyEditingBot.equals(botName)) {
            bots.remove(currentlyEditingBot);
            botDetailsMap.remove(currentlyEditingBot);
        }

        if (!bots.contains(botName)) {
            bots.add(botName);
        }
        botDetailsMap.put(botName, details);

        // Hide the bot details menu
        botDetailsMenu.setVisible(false);

        // Update the ListView selection
        botListView.getSelectionModel().select(botName);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
