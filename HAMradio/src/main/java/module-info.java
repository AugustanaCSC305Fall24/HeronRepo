module edu.augustana {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens edu.augustana to javafx.fxml;
    exports edu.augustana;
    exports edu.augustana.Scenarios;
    opens edu.augustana.Scenarios to javafx.fxml, com.google.gson;
    exports edu.augustana.Scenarios.ScenarioBots;
    opens edu.augustana.Scenarios.ScenarioBots to com.google.gson, javafx.fxml;
    exports edu.augustana.ui;
    opens edu.augustana.ui to com.google.gson, javafx.fxml;

    requires com.google.gson;

}
