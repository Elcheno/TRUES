package com.elcheno.trues;

import com.elcheno.trues.controller.Controller;
import com.elcheno.trues.model.domain.Employee;
import com.elcheno.trues.model.dto.EmployeeInfoDTO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeUpdateController extends Controller implements Initializable {
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
    private ObservableList<Employee> _employeeList; // the employee that is being worked on
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

    public void initAttributes(ObservableList<Employee> employees){
        this._employeeList = employees;
    }

    @FXML
    private void update() {
        if (!areFieldsValid()) {
            return;
        }

        if (!isCodFieldValid()) {
            alertInfo("Error", "Employee cannot be saved, check fields");
            loadField();
            return;
        }

        Employee aux = new Employee(Integer.parseInt(codField.getText()), _employee.getDni(), nameField.getText(), lastnameField.getText());

        _employee.setDni(aux.getDni());
        _employee.setName(aux.getName());
        _employee.setLastName(aux.getLastName());

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

    private void alertInfo(String title, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void resetField(){
        codField.setText("");
        nameField.setText("");
        lastnameField.setText("");
    }
}
