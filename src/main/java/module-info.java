module com.example.willherofinal {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.willherofinal to javafx.fxml;
    exports com.example.willherofinal;
}