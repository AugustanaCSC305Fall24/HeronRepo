package edu.augustana.Scenarios;

public class ScriptedMessage {

    private String text;
    private int sendingTime;
    private int intervalBetweenRepetitions;

    public ScriptedMessage(String message, int time) {
        text = message;
        sendingTime=time;
    }

    public int getTime(){
        return  sendingTime;
    }
}
