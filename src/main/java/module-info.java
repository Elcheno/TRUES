module com.elcheno.trues {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.xml.bind;
    requires java.sql;

    opens com.elcheno.trues to javafx.fxml;
    exports com.elcheno.trues;
    opens com.elcheno.trues.model.connections to java.xml.bind;
    exports com.elcheno.trues.controller;
    opens com.elcheno.trues.controller to javafx.fxml;
}