module com.example.searchenginegui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.searchenginegui to javafx.fxml;
    exports com.example.searchenginegui;
}