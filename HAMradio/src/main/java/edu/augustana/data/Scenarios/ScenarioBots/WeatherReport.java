package edu.augustana.data.Scenarios.ScenarioBots;

import edu.augustana.dataModel.ScriptedMessage;
import edu.augustana.interfaces.Scenario;

import java.util.ArrayList;
import java.util.List;

public class WeatherReport implements Scenario {
    @Override
    public List<ScriptedMessage> getScriptedMessages() {
        List<ScriptedMessage> messages = new ArrayList<>();
        messages.add(new ScriptedMessage("This is Weather Station Delta. Beginning weather assessment for sector Bravo. Can you confirm data log status? Over.", 0));
        messages.add(new ScriptedMessage("Base, current temperature is 75°F with clear skies. Do you need more detailed measurements? Over.", 1));
        messages.add(new ScriptedMessage("Wind speeds steady at 5 mph. Shall we continue monitoring wind patterns? Over.", 2));
        messages.add(new ScriptedMessage("Humidity at 60%. Do you need a secondary location report? Over.", 3));
        messages.add(new ScriptedMessage("Weather report complete. Is there any other data needed from this station? Over and out.", 4));
        return messages;
    }
}
