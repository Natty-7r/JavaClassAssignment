module com.example.learningjavfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.learningjavfx to javafx.fxml;
    exports com.example.learningjavfx;
}