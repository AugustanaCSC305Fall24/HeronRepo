package edu.augustana.Scenarios;

import java.util.List;

public class ScriptedBot extends ScenarioBot{


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
