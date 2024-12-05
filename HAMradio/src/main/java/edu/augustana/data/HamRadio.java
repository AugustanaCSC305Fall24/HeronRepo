package edu.augustana.data;

import edu.augustana.ui.App;

public class HamRadio {
    public static HamRadio theRadio = new HamRadio();

    private double frequency;
    private int filter;
    private int volume = 50; // Static volume variable
    //TODO: filter information?  more?

    private NewMessageListener newMessageListener = null;

    private HamRadio() {
    }
    public int getFilter(){ return filter;}

    public void setFilter(int filter) {
        this.filter = filter;
    }

    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void setNewMessageListener(NewMessageListener listener) {
        this.newMessageListener = listener;
    }

    public void receiveMessage(CWMessage msg) {
        // could filter out messages based on our frequency,
        // and include code in here to call playing the message sounds?
        System.out.println("DEBUG: HamRadio received " + msg);
        if (newMessageListener != null) {
            newMessageListener.onNewMessage(msg);
        }
    }

    public void sendMessage(String msgText) {
        App.sendMessageToServer(new CWMessage(msgText, frequency));
    }
}
