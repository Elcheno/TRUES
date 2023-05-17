package com.elcheno.trues.controller;

import com.elcheno.trues.controller.Controller;
import com.elcheno.trues.controller.EmployeeController;
import com.elcheno.trues.model.domain.Employee;
import com.elcheno.trues.model.dto.EmployeeInfoDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeUpdateController extends Controller {
    /**
     * This class is the controller of the EmployeeUpdate view(modal)
     * @see EmployeeController
     * @author Elcheno
     */

    @FXML
    private Pane navbar;
    @FXML
    private Label txtEmpDni;
    @FXML
    private TextField codField, nameField, lastnameField;
    @FXML
    private Button btnUpdate;

    private double xOffset = 0, yOffset = 0;
    private Employee _employee; // the employee that is being worked on
    private String templateCod = "[0-9]+";

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

    @FXML
    private void update() {
        if (!areFieldsValid()) {
            return;
        }

        if (!isCodFieldValid()) {
            alertInformation(Alert.AlertType.INFORMATION, "Error", "Employee cannot be updated, check fields");
            loadField();
            return;
        }

        Employee aux = new Employee(Integer.parseInt(codField.getText()), _employee.getDni(), nameField.getText(), lastnameField.getText());

        _employee.setDni(aux.getDni());
        _employee.setName(aux.getName());
        _employee.setLastName(aux.getLastName());

        alertInformation(Alert.AlertType.INFORMATION, "Update Employee", "Employee has been updated correctly");

        Stage stage = (Stage) this.btnUpdate.getScene().getWindow();
        stage.close();
    }

    private boolean areFieldsValid() {
        return !codField.getText().isEmpty() && !nameField.getText().isEmpty() && !lastnameField.getText().isEmpty();
    }

    private boolean isCodFieldValid() {
        return codField.getText().matches(templateCod);
    }

    @FXML
    private void loadField(){
        _employee = EmployeeInfoDTO.getEmployee();
        if(_employee == null){ return; }
        txtEmpDni.setText(_employee.getDni());
        codField.setText(Integer.toString(_employee.getCod()));
        nameField.setText(_employee.getName());
        lastnameField.setText(_employee.getLastName());
    }

    public Employee getEmployee(){
        return this._employee;
    }

}
