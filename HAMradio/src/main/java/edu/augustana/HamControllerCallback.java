package edu.augustana;

public interface HamControllerCallback {
    void onDitDahProcessed(char signalUnit);  // Callback for each dit or dah
    void onCharacterProcessed(char character); // Callback for each complete character
    void onMessageCompleted(String message);     // Callback for a complete word/message
    void onFrequencyChanged(double newFrequency);
}