package edu.augustana.helper.handlers;
import edu.augustana.dataModel.Session;
import swiss.ameri.gemini.api.*;
import swiss.ameri.gemini.gson.GsonJsonParser;
import swiss.ameri.gemini.spi.JsonParser;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import io.github.cdimascio.dotenv.Dotenv;
/**
 * Handles interactions with Gemini AI to generate content based on scenarios.
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class GeminiAiHandler {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String API_KEY = dotenv.get("API_KEY");

    private final GenAi genAi;
    private final ConcurrentHashMap<String, Session> sessions;

    public GeminiAiHandler() {
        JsonParser parser = new GsonJsonParser();
        this.genAi = new GenAi(getGeminiApiKey(), parser);
        this.sessions = new ConcurrentHashMap<>();
    }

    public Session createSession(String sessionId) {
        Session session = new Session(sessionId);
        sessions.put(sessionId, session);
        return session;
    }

    public Session getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    public String generateResponse(Session session, String userInput) {
        session.addMessage("User: " + userInput);

        String prompt = createSessionPrompt(session);
        GenerativeModel model = createGenerativeModel(prompt);

        try {
            String response = genAi.generateContent(model)
                    .thenApply(this::extractResponseText)
                    .get(20, TimeUnit.SECONDS);

            session.addMessage("Gemini: " + response);
            return response;
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
            return "Error generating response: " + e.getMessage();
        }
    }

    private String createSessionPrompt(Session session) {
        StringBuilder prompt = new StringBuilder();
        for (String message : session.getHistory()) {
            prompt.append(message).append("\n");
        }
        prompt.append("Please continue the conversation.");
        return prompt.toString();
    }

    private String extractResponseText(Object response) {
        if (response instanceof String) {
            return (String) response;
        }
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
