package edu.augustana.Scenarios.ScenarioBots;

import edu.augustana.Scenarios.ScenarioBot;
import edu.augustana.Scenarios.ScriptedMessage;

import java.util.List;

public class ScriptedBot extends ScenarioBot {


    private List<ScriptedMessage> scriptedMessages;




    @Override
    public ScriptedMessage getNewMessage(int time) {
        return null;
    }

    @Override
    public ScriptedMessage getResponse(String message, double frequency, int time) {
        return null;
    }

    @Override
    public String toString(){
        return "Regular Bot";
    }
}
