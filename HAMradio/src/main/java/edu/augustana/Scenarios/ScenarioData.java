package edu.augustana.Scenarios;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;

public class ScenarioData {
    private double scenarioDuration;
    private String synopsis;
    private String botType;
    private double transmissionSpeed;

    // Constructor
    public ScenarioData(double scenarioDuration, String synopsis, String botType, double transmissionSpeed) {
        this.scenarioDuration = scenarioDuration;
        this.synopsis = synopsis;
        this.botType = botType;
        this.transmissionSpeed = transmissionSpeed;
    }

    // Method to export to JSON using Gson
    public void exportToJson(String filePath) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getters and setters
    public double getScenarioDuration() { return scenarioDuration; }
    public String getSynopsis() { return synopsis; }
    public String getBotType() { return botType; }
    public double getTransmissionSpeed() { return transmissionSpeed; }
}
