module edu.augustana {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.net.http;
    requires swiss.ameri.gemini.api;
    requires swiss.ameri.gemini.gson;

    requires tyrus.standalone.client;

    exports edu.augustana.data.Scenarios;
    opens edu.augustana.data.Scenarios to javafx.fxml, com.google.gson;
    exports edu.augustana.data.Scenarios.ScenarioBots;
    opens edu.augustana.data.Scenarios.ScenarioBots to com.google.gson, javafx.fxml;
    exports edu.augustana.ui;
    opens edu.augustana.ui to com.google.gson, javafx.fxml;
    exports edu.augustana.helper.callbacks;
    opens edu.augustana.helper.callbacks to javafx.fxml;
    exports edu.augustana.data;
    opens edu.augustana.data to javafx.fxml, com.google.gson;
    opens edu.augustana to com.google.gson, javafx.fxml;

    requires com.google.gson;

}
