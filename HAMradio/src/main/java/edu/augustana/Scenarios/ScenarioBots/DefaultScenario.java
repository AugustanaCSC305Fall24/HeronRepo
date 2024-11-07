package edu.augustana.Scenarios.ScenarioBots;

import edu.augustana.Scenarios.ScriptedMessage;

import java.util.ArrayList;
import java.util.List;

public class DefaultScenario implements Scenario {
    @Override
    public List<ScriptedMessage> getScriptedMessages() {
        List<ScriptedMessage> messages = new ArrayList<>();
        messages.add(new ScriptedMessage("This is Control. Unknown scenario selected. Standing by for instructions. Can you clarify the operation? Over.", 0));
        return messages;
    }
}
