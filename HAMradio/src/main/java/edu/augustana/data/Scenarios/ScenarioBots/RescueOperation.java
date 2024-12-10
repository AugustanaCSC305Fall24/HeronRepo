package edu.augustana.data.Scenarios.ScenarioBots;
import edu.augustana.dataModel.ScriptedMessage;
import edu.augustana.interfaces.Scenario;

import java.util.ArrayList;
import java.util.List;

public class RescueOperation implements Scenario {
    @Override
    public List<ScriptedMessage> getScriptedMessages() {
        List<ScriptedMessage> messages = new ArrayList<>();
        messages.add(new ScriptedMessage("Base, this is Rescue Team Alpha. We've deployed and are en route to target location. Do you confirm? Over.", 0));
        messages.add(new ScriptedMessage("Base, Rescue Team here. ETA to target is 10 minutes. Any further instructions before we proceed? Over.", 2));
        messages.add(new ScriptedMessage("We've reached the canyon entry point. Do you advise waiting for further backup, or should we proceed? Over.", 5));
        messages.add(new ScriptedMessage("Visual on target. Preparing extraction. Confirm target identity and proceed with recovery? Over.", 8));
        messages.add(new ScriptedMessage("Extraction complete. Should we return to base, or await further orders? Over.", 10));
        return messages;
    }
}

