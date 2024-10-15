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

    private static Scene scene;
    public static int sound = 100;

    public static int minPlayTimeSound = 100;
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("Home"), 580, 800);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event -> {
            StaticNoisePlayer.stopNoise();
            Platform.exit();
            // Optionally, you can add other cleanup code here
        });
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}