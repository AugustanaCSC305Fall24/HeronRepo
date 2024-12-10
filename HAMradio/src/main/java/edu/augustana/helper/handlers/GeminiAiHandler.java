package edu.augustana.helper.handlers;
import swiss.ameri.gemini.api.*;
import swiss.ameri.gemini.gson.GsonJsonParser;
import swiss.ameri.gemini.spi.JsonParser;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Handles interactions with Gemini AI to generate content based on scenarios.
 */
public class GeminiAiHandler {

    private static final String API_KEY = "AIzaSyDrIDkyTx5hgbAFw4bOPxoWxXAvYeEj0qc"; // Replace with actual API key
    private final GenAi genAi;

    public GeminiAiHandler() {
        JsonParser parser = new GsonJsonParser();
        this.genAi = new GenAi(getGeminiApiKey(), parser);
    }

    public String generateScenarioResponse(String scenarioTitle, String scenarioDetails) {
        String prompt = createPrompt(scenarioTitle, scenarioDetails);
        GenerativeModel model = createGenerativeModel(prompt);

        try {
            return genAi.generateContent(model)
                    .thenApply(response -> extractResponseText(response)) // Correct response extraction
                    .get(20, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
            return "Error generating content: " + e.getMessage();
        }
    }

    // Helper method to create prompt for Gemini AI
    private String createPrompt(String title, String details) {
        return String.format(
                "Scenario Title: %s\nDetails: %s\n" +
                        "Please generate a response to simulate this scenario realistically, making sure that you only generate very short answers that are morse code freindly ready to get translated.",
                title, details
        );
    }

    // Helper method to extract the text content from the response
    private String extractResponseText(Object response) {
        // Here, adjust based on the actual type of response you receive from the API
        if (response instanceof String) {
            return (String) response; // If it's directly a String, return it
        }
        // If response is some complex object, extract the text from it (this will depend on API response format)
        // For example:
        // If not, you can fallback to toString() or handle the case accordingly
        return response.toString();
    }

    private GenerativeModel createGenerativeModel(String prompt) {
        return GenerativeModel.builder()
                .modelName(ModelVariant.GEMINI_1_5_FLASH)
                .addContent(Content.textContent(Content.Role.USER, prompt))
                .addSafetySetting(SafetySetting.of(
                        SafetySetting.HarmCategory.HARM_CATEGORY_DANGEROUS_CONTENT,
                        SafetySetting.HarmBlockThreshold.BLOCK_ONLY_HIGH
                ))
                .generationConfig(new GenerationConfig(null, null, null, null, null, null, null))
                .build();
    }

    private static String getGeminiApiKey() {
        if (API_KEY == null || API_KEY.isBlank()) {
            throw new IllegalStateException("API_KEY is not set. Please provide a valid API key.");
        }
        return API_KEY;
    }

    public void close() {
        genAi.close();
    }
}
