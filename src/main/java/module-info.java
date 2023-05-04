module com.elcheno.trues {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
                            
    opens com.elcheno.trues to javafx.fxml;
    exports com.elcheno.trues;
}