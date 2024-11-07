package edu.augustana.Scenarios.ScenarioBots;

import edu.augustana.Scenarios.ScenarioBot;
import edu.augustana.Scenarios.ScriptedMessage;

import java.util.ArrayList;
import java.util.List;

public class ScriptedBot extends ScenarioBot {

    private String scenarioType;
    private List<ScriptedMessage> scriptedMessages = new ArrayList<>();

    public ScriptedBot(String scenarioType) {
        this.scenarioType = scenarioType;
        initializeScenarioMessages();
    }

    // Initialize radio-themed messages based on the scenario type
    private void initializeScenarioMessages() {
        Scenario scenario;

        switch (scenarioType) {
            case "Rescue Operation":
                scenario = new RescueOperation();
                break;
            case "Weather Report":
                scenario = new WeatherReport();
                break;
            case "Mountain Expedition":
                scenario = new MountainExpedition();
                break;
            default:
                scenario = new DefaultScenario();
                break;
        }

        scriptedMessages.addAll(scenario.getScriptedMessages());
    }

    @Override
    public ScriptedMessage getNewMessage(int time) {
        // Return a message that corresponds to the given time, if available
        for (ScriptedMessage message : scriptedMessages) {
            if (message.getTime() == time) {
                return message;
            }
        }
        return null; // No new message if no message is scheduled for this time
    }

    @Override
    public ScriptedMessage getResponse(String message, double frequency, int time) {
        // Example responses based on keywords in the message
        if (message.toLowerCase().contains("confirm")) {
            return new ScriptedMessage("Confirmation received. Proceeding as directed. Over.", time);
        } else if (message.toLowerCase().contains("instructions")) {
            return new ScriptedMessage("Instructions acknowledged. Standing by for further details. Over.", time);
        } else if (message.toLowerCase().contains("proceed")) {
            return new ScriptedMessage("Proceeding with the operation as requested. Over.", time);
        }
        return null; // No specific response if message does not trigger a response
    }

    @Override
    public String toString() {
        return scenarioType + " Radio Bot";
    }
}
