package edu.augustana.dataModel;

public class CWMessage {
    private final String cwText;
    private final double frequency;

    public CWMessage(String cwText, double frequency) {
        this.cwText = cwText;
        this.frequency = frequency;
    }

    public String getCwText() {
        return cwText;
    }

    public double getFrequency() {
        return frequency;
    }

    @Override
    public String toString() {
        return "CWMessage{" +
                "cwText='" + cwText + '\'' +
                ", frequency=" + frequency +
                '}';
    }
}
