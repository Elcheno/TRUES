package com.elcheno.trues;

import com.elcheno.trues.controller.Controller;
import com.elcheno.trues.model.domain.Employee;
import com.elcheno.trues.model.dto.EmployeeInfoDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeInfoController extends Controller implements Initializable {
    /**
     * This class is the controller of the EmployeeInfo view(modal)
     * @see EmployeeController
     * @author Elcheno
     */

    @FXML
    private Pane navbar;
    @FXML
    private Label empDni, empCod, empName, empLastname;


    private double xOffset = 0, yOffset = 0;
    private Employee _employee; // the employee that is being worked on


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navbar.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        navbar.setOnMouseDragged(event -> {
            Stage stage = (Stage) navbar.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        loadField();
    }

    private void loadField(){
        _employee = EmployeeInfoDTO.getEmployee();
        if(_employee == null){ return; }
        empDni.setText(_employee.getDni());
        empCod.setText(Integer.toString(_employee.getCod()));
        empName.setText(_employee.getName());
        empLastname.setText(_employee.getLastName());
    }
}
