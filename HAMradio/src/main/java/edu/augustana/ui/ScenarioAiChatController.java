package edu.augustana.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class ScenarioAiChatController {

    @FXML
    private VBox chatVBox;
    @FXML
    private TextField inputField;
    @FXML
    private Button sendButton;

    @FXML
    public void initialize() {
        sendButton.setOnAction(event -> sendMessage());
    }

    private void sendMessage() {
        String userInput = inputField.getText();
        if (!userInput.trim().isEmpty()) {
            addMessage("You: " + userInput, "user-message");
            inputField.clear();
            // Simulate AI response (replace with actual API call)
            String aiResponse = "Gemini AI: This is a simulated response!";
            addMessage(aiResponse, "ai-message");
        }
    }

    private void addMessage(String message, String styleClass) {
        TextFlow messageBubble = new TextFlow(new Text(message));
        messageBubble.getStyleClass().add(styleClass);
        chatVBox.getChildren().add(messageBubble);
    }
}
