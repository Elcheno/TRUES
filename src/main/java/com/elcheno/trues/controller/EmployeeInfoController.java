package com.elcheno.trues.controller;

import com.elcheno.trues.controller.Controller;
import com.elcheno.trues.controller.EmployeeController;
import com.elcheno.trues.model.domain.Employee;
import com.elcheno.trues.model.dto.EmpLineDTO;
import com.elcheno.trues.model.dto.EmployeeInfoDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class EmployeeInfoController extends Controller {
    /**
     * This class is the controller of the EmployeeInfo view(modal)
     * @see EmployeeController
     * @author Elcheno
     */

    @FXML
    private Pane navbar, state;
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

        try{
            List<EmpLineDTO> stateList = _employee.getEmpLinesDTO();

            if(stateList.size() == 0){ return;}

            EmpLineDTO lastState = stateList.get(stateList.size() - 1);
            if(lastState.getDateOut() == null){
                state.setStyle("-fx-background-color: #00ff00");
            }
        }catch (Exception e) {
            log(Logger.getLogger(String.valueOf(this.getClass())), e.getMessage());
        }
    }
}
