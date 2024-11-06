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
        switch (scenarioType) {
            case "Rescue Operation":
                scriptedMessages.add(new ScriptedMessage("Base, this is Rescue Team Alpha. We've deployed and are en route to target location. Do you confirm? Over.", 0));
                scriptedMessages.add(new ScriptedMessage("Base, Rescue Team here. ETA to target is 10 minutes. Any further instructions before we proceed? Over.", 2));
                scriptedMessages.add(new ScriptedMessage("We've reached the canyon entry point. Do you advise waiting for further backup, or should we proceed? Over.", 5));
                scriptedMessages.add(new ScriptedMessage("Visual on target. Preparing extraction. Confirm target identity and proceed with recovery? Over.", 8));
                scriptedMessages.add(new ScriptedMessage("Extraction complete. Should we return to base, or await further orders? Over.", 10));
                break;

            case "Weather Report":
                scriptedMessages.add(new ScriptedMessage("This is Weather Station Delta. Beginning weather assessment for sector Bravo. Can you confirm data log status? Over.", 0));
                scriptedMessages.add(new ScriptedMessage("Base, current temperature is 75°F with clear skies. Do you need more detailed measurements? Over.", 1));
                scriptedMessages.add(new ScriptedMessage("Wind speeds steady at 5 mph. Shall we continue monitoring wind patterns? Over.", 2));
                scriptedMessages.add(new ScriptedMessage("Humidity at 60%. Do you need a secondary location report? Over.", 3));
                scriptedMessages.add(new ScriptedMessage("Weather report complete. Is there any other data needed from this station? Over and out.", 4));
                break;

            case "Mountain Expedition":
                scriptedMessages.add(new ScriptedMessage("Expedition Base, this is Summit Team Bravo. We've established base camp at 5,000 feet. Confirm location data logged? Over.", 0));
                scriptedMessages.add(new ScriptedMessage("Base, preparing to assess weather for ascent. Do we have clearance for the next phase? Over.", 2));
                scriptedMessages.add(new ScriptedMessage("Beginning ascent now. Equipment checks complete. Do you need additional status updates on equipment? Over.", 4));
                scriptedMessages.add(new ScriptedMessage("Reached halfway point. Conditions remain favorable. Do we proceed to the summit? Over.", 6));
                scriptedMessages.add(new ScriptedMessage("Summit reached. Expedition complete. Shall we begin descent, or await further instruction? Over and out.", 10));
                break;

            default:
                scriptedMessages.add(new ScriptedMessage("This is Control. Unknown scenario selected. Standing by for instructions. Can you clarify the operation? Over.", 0));
                break;
        }
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
