package edu.augustana.data.Scenarios.ScenarioBots;

import edu.augustana.dataModel.ScriptedMessage;
import edu.augustana.interfaces.Scenario;

import java.util.ArrayList;
import java.util.List;

public class MountainExpedition implements Scenario {
    @Override
    public List<ScriptedMessage> getScriptedMessages() {
        List<ScriptedMessage> messages = new ArrayList<>();
        messages.add(new ScriptedMessage("Base, Summit Team Bravo at 5,000 feet. Data logged? Over.", 0));
        messages.add(new ScriptedMessage("Base, assessing weather for ascent. Clearance for next phase? Over.", 2));
        messages.add(new ScriptedMessage("Ascent underway. Equipment checks complete. Any updates needed? Over.", 4));
        messages.add(new ScriptedMessage("Halfway point, conditions good. Proceed to summit? Over.", 6));
        messages.add(new ScriptedMessage("Summit reached, expedition complete. Begin descent or wait for further? Over and out.", 10));
        return messages;
    }
}
