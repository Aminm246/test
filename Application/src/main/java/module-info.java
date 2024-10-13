module Application {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens edu.metrostate to javafx.fxml;
    exports edu.metrostate;
}



