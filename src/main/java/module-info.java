module com.juanite {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.xml.bind;
    requires jlayer;
    requires java.persistence;

    opens com.juanite to javafx.fxml;
    exports com.juanite;
    exports com.juanite.controller;
    exports com.juanite.model.domain;
    opens com.juanite.controller to javafx.fxml;
    opens com.juanite.connection to java.xml.bind;
}
