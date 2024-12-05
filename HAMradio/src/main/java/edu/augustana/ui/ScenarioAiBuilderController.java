package edu.augustana.ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.io.FileWriter;
import java.io.IOException;

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
    private Button openButton;

    @FXML
    private Button saveButton;
    @FXML
    private Button playButton;
    @FXML
    private Button backButton;

    private final ObservableList<String> bots = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        botListView.setItems(bots);

        // Add bot button action
        addBotButton.setOnAction(event -> showBotDetailsMenu());

        // Kick bot button action
        kickBotButton.setOnAction(event -> removeSelectedBot());

        // Save button action
        saveButton.setOnAction(event -> saveScenarioToJson());

        // Play button action
        playButton.setOnAction(event -> playScenario());

        // Back button action
        backButton.setOnAction(event -> goBack());

        botDetailsMenu.setVisible(false);
    }

    private void showBotDetailsMenu() {
        botDetailsMenu.setVisible(true);
        botTypeField.clear();
        botNameField.clear();
        botObjectiveField.clear();
        isStartingBotCheckBox.setSelected(false);
    }

    private void removeSelectedBot() {
        String selectedBot = botListView.getSelectionModel().getSelectedItem();
        if (selectedBot != null) {
            bots.remove(selectedBot);
        }
    }

    private void saveScenarioToJson() {
        String scenarioName = scenarioNameField.getText();
        String description = scenarioDescriptionArea.getText();
        String notes = scenarioNotesArea.getText();

    }

    private void playScenario() {
        System.out.println("Playing scenario...");
        // Add logic to trigger scenario playback
    }

    private void goBack() {
        System.out.println("Going back to the previous screen...");
        // Add logic to navigate back to the main menu or previous UI
    }

    @FXML
    private void addBotDetails() {
        String botType = botTypeField.getText();
        String botName = botNameField.getText();
        String botObjective = botObjectiveField.getText();
        boolean isStartingBot = isStartingBotCheckBox.isSelected();

        if (!botName.isEmpty()) {
            bots.add(botName);


        }

        botDetailsMenu.setVisible(false);
    }
}
