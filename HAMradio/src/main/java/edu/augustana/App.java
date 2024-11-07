package edu.augustana;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    public static long TIMER_DELAY = 400;
    public static long DOT_THRESHOLD = 150;
    private static Scene scene;
    private static Stage stage; // Add this line
    public static int volume = 50; // Static volume variable
    public static int wpm = 20;
    public static int minPlayTimeSound = 50;
    public static int ditFrequency = 600;

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage; // Store the primary stage reference
        scene = new Scene(loadFXML("Home"), 800, 580);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event -> {
            StaticNoisePlayer.stopNoise();
            Platform.exit();
            // Optionally, you can add other cleanup code here
        });
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Stage getStage() {
        return stage; // Return the stored stage reference
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
