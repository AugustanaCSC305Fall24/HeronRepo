package edu.augustana;

public class UserSession {
    private static UserSession instance;
    private String username;

    private UserSession() { }

    // Get the single instance of UserSession
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    // Set the username
    public void setUsername(String username) {
        this.username = username;
    }

    // Get the username
    public String getUsername() {
        return username;
    }
    // Clear session (if needed)
    public void clearSession() {
        username = null;
    }
}


