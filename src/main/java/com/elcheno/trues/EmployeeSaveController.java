package com.elcheno.trues;

import com.elcheno.trues.controller.Controller;
import com.elcheno.trues.model.domain.Employee;
import com.elcheno.trues.model.service.EmployeeService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeSaveController extends Controller {
    /**
     * This class is the controller of the EmployeeSaved View(modal)
     * @see EmployeeController
     * @author Elcheno
     */

    @FXML
    private Button btnExit, btnMinWindow, btnSave;
    @FXML
    private Pane navbar;
    @FXML
    private TextField dniField, codField, nameField, lastnameField;

    private double xOffset = 0, yOffset = 0;
    private ObservableList<Employee> _employeeList;
    private Employee _employee;
    private String templateDNI = "^[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKE]$";
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

    }

    public void initAttributes(ObservableList<Employee> employeeList){
        this._employeeList = employeeList;
    }

    @FXML
    private void save() {
        if (!areFieldsValid()) {
            return;
        }

        if (!isCodFieldValid()) {
            alertInformation(Alert.AlertType.INFORMATION, "Error", "Employee cannot be saved, check fields");
            resetField();
            return;
        }

        String dni = dniField.getText();
        int cod = Integer.parseInt(codField.getText());
        String name = nameField.getText();
        String lastname = lastnameField.getText();
        Employee aux = new Employee(cod, dni, name, lastname);

        if (_employeeList.contains(aux)) {
            alertInformation(Alert.AlertType.INFORMATION, "Employee already exists", "The employee already exists");
            resetField();
        } else if (!isFieldsValid(aux)) {
            alertInformation(Alert.AlertType.INFORMATION, "Error", "Employee cannot be saved, check fields");
            resetField();
        } else {
            _employee = aux;
            alertInformation(Alert.AlertType.INFORMATION, "Employee saved", "The employee has been successfully saved");

            Stage stage = (Stage) this.btnSave.getScene().getWindow();
            stage.close();
        }
    }

    private boolean areFieldsValid() {
        return !dniField.getText().isEmpty() && !codField.getText().isEmpty() && !nameField.getText().isEmpty() && !lastnameField.getText().isEmpty();
    }

    private boolean isCodFieldValid() {
        return codField.getText().matches(templateCod);
    }

    private boolean isFieldsValid(Employee emp) {
        return emp.getDni().matches(templateDNI) && emp.getCod() >= 0 && emp.getCod() <= 9999;
    }

    public Employee getEmployee(){
        return _employee;
    }

    
    private void resetField(){
        dniField.setText("");
        codField.setText("");
        nameField.setText("");
        lastnameField.setText("");
    }
}