package edu.augustana.dataModel;

import java.util.ArrayList;
import java.util.List;

public class Session {
    private final String sessionId;
    private final List<String> history;

    public Session(String sessionId) {
        this.sessionId = sessionId;
        this.history = new ArrayList<>();
    }

    public String getSessionId() {
        return sessionId;
    }

    public List<String> getHistory() {
        return history;
    }

    public void addMessage(String message) {
        history.add(message);
    }

    public String getLastMessage() {
        return history.isEmpty() ? null : history.get(history.size() - 1);
    }
}
