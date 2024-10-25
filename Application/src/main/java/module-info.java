module Application {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens edu.metrostate to javafx.fxml;
    exports edu.metrostate;
    exports Controller;
    opens Controller to javafx.fxml;
}



