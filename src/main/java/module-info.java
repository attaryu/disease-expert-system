module com.diseaseexpertsystem {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.diseaseexpertsystem to javafx.fxml;
    exports com.diseaseexpertsystem;
}
