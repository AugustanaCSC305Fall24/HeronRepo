package edu.augustana.Scenarios;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ScenarioData {
    private double duration;
    private String synopsis;
    private String botType;
    private double transmissionSpeed;

    // Constructor
    public ScenarioData(double duration, String synopsis, String botType, double transmissionSpeed) {
        this.duration = duration;
        this.synopsis = synopsis;
        this.botType = botType;
        this.transmissionSpeed = transmissionSpeed;
    }

    // Method to save scenario data to JSON
    public void exportToJson(String filePath) {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load scenario data from JSON
    public static ScenarioData importFromJson(String filePath) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, ScenarioData.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public String toString() {
        return "ScenarioData{" +
                "duration=" + duration +
                ", synopsis='" + synopsis + '\'' +
                ", botType='" + botType + '\'' +
                ", transmissionSpeed=" + transmissionSpeed +
                '}';
    }


    // Getters
    public double getDuration() { return duration; }
    public String getSynopsis() { return synopsis; }
    public String getBotType() { return botType; }
    public double getTransmissionSpeed() { return transmissionSpeed; }
}

