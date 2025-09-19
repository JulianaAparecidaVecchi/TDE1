module com.example.tde1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens com.example.tde1 to javafx.fxml;
    exports com.tde1;
}