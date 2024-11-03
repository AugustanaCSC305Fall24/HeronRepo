package edu.augustana.Scenarios;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;

import javax.swing.text.html.ImageView;
import java.util.ArrayList;
import java.util.List;

public class ScenarioMenuController {
    @FXML
    private ComboBox<Integer> scenarioDuration;

    @FXML
    private ComboBox<String> synopsis;

    @FXML
    private ComboBox<String> botType;

    @FXML
    private Slider transmissionSpeed;

    @FXML
    private ImageView saveScenario;

    @FXML
    private ImageView openScenario;



    private List<ScenarioBot> listOfBots = new ArrayList<>();




    @FXML
    void initialize() {
        listOfBots.add(new ScriptedBot());

    }

}
