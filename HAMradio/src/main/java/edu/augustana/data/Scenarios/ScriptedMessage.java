package edu.augustana.data.Scenarios;

public class ScriptedMessage {

    private String text;
    private int sendingTime;
    private int intervalBetweenRepetitions;

    public ScriptedMessage(String message, int time) {
        text = message;
        sendingTime=time;
    }

    public int getTime(){
        return sendingTime;
    }

    public String getMessage() {
        return text;
    }
}
