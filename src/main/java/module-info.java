module com.example.lavanya {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires java.sql;

    opens com.example.lavanya to javafx.fxml;
    exports com.example.lavanya;
}