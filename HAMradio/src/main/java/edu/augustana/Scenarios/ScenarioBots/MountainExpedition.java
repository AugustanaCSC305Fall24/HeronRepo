package edu.augustana.Scenarios.ScenarioBots;

import edu.augustana.Scenarios.ScriptedMessage;
import java.util.ArrayList;
import java.util.List;

public class MountainExpedition implements Scenario {
    @Override
    public List<ScriptedMessage> getScriptedMessages() {
        List<ScriptedMessage> messages = new ArrayList<>();
        messages.add(new ScriptedMessage("Expedition Base, this is Summit Team Bravo. We've established base camp at 5,000 feet. Confirm location data logged? Over.", 0));
        messages.add(new ScriptedMessage("Base, preparing to assess weather for ascent. Do we have clearance for the next phase? Over.", 2));
        messages.add(new ScriptedMessage("Beginning ascent now. Equipment checks complete. Do you need additional status updates on equipment? Over.", 4));
        messages.add(new ScriptedMessage("Reached halfway point. Conditions remain favorable. Do we proceed to the summit? Over.", 6));
        messages.add(new ScriptedMessage("Summit reached. Expedition complete. Shall we begin descent, or await further instruction? Over and out.", 10));
        return messages;
    }
}
