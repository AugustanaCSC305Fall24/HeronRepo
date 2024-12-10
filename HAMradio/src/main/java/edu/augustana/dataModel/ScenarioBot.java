package edu.augustana.dataModel;

public abstract class ScenarioBot {
    public String name;

    public abstract ScriptedMessage getNewMessage(int time);
    public abstract ScriptedMessage getResponse(String message, double frequency,int time);


}
