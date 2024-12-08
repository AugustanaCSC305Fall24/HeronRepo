package edu.augustana.data.Scenarios;

import java.util.Map;
// container to serialize/deserialize all scenario-related data.
public class AiScenarioData {
    private final String name;
    private final String description;
    private final String notes;
    private final Map<String, AiBotDetails> botDetails;

    public AiScenarioData(String name, String description, String notes, Map<String, AiBotDetails> botDetails) {
        this.name = name;
        this.description = description;
        this.notes = notes;
        this.botDetails = botDetails;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getNotes() {
        return notes;
    }

    public Map<String, AiBotDetails> getBotDetails() {
        return botDetails;
    }
}

