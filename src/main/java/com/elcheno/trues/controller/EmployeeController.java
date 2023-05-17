package com.elcheno.trues.controller;

import com.elcheno.trues.model.domain.Employee;
import com.elcheno.trues.model.domain.Line;
import com.elcheno.trues.model.dto.EmployeeInfoDTO;
import com.elcheno.trues.model.dto.LineDTO;
import com.elcheno.trues.model.service.EmpLineService;
import com.elcheno.trues.model.service.EmployeeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class EmployeeController extends Controller {

    @FXML
    private Button btnExit, btnMinWindow, btnRefresh, btnHomeView, btnProductView, btnLineView, btnEmpView;
    @FXML
    private Pane navbar;
    @FXML
    private TableView<Employee> table;
    @FXML
    private TableColumn colDni, colCod, colName, colLastname;
    @FXML
    private Label txtEmpTotal, txtEmpToday, txtNLine;

    private double xOffset = 0, yOffset = 0;
    private Line _line; // the line that is being worked on
    private ObservableList<Employee> employeeList;

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

        employeeList = FXCollections.observableArrayList();
        this.colDni.setCellValueFactory(new PropertyValueFactory("dni"));
        this.colCod.setCellValueFactory(new PropertyValueFactory("cod"));
        this.colName.setCellValueFactory(new PropertyValueFactory("name"));
        this.colLastname.setCellValueFactory(new PropertyValueFactory("lastName"));

        reloadInfo();
    }

    @FXML
    private Employee selectProduct(){
        Employee result = null;
        Employee aux = this.table.getSelectionModel().getSelectedItem();
        if(aux!=null){
            result = aux;
        }
        return result;
    }

    public void reloadInfo(){
        loadLine();
        loadTable();
        loadEmpTotal();
        loadEmpToday();
    }

    public void loadTable(){
        employeeList.clear();
        try {
            EmployeeService employeeService = new EmployeeService();
            List<Employee> aux = employeeService.getAll();
            if(aux==null){ return;}
            aux = employeeService.getAll();
            employeeList.addAll(aux);
            table.setItems(employeeList);
            table.refresh();

        } catch (SQLException e) {
            log(Logger.getLogger(String.valueOf(this.getClass())), e.getMessage());


        }
    }

    public void loadEmpTotal(){
        try {
            EmployeeService employeeService = new EmployeeService();
            List<Employee> aux = employeeService.getAll();
            if(aux==null){ return;}
            txtEmpTotal.setText(Integer.toString(aux.size()));

        } catch (SQLException e) {
            log(Logger.getLogger(String.valueOf(this.getClass())), e.getMessage());


        }
    }

    public void loadEmpToday(){
        try {
            EmpLineService empLineService = new EmpLineService();
            List<Employee> aux = empLineService.getByLine(_line.getId());
            if(aux==null){ return;}
            txtEmpToday.setText(Integer.toString(aux.size()));

        } catch (SQLException e) {
            log(Logger.getLogger(String.valueOf(this.getClass())), e.getMessage());


        }
    }

    @FXML
    public void loadLine(){
        _line = LineDTO.getLine();
        if(_line != null){
            txtNLine.setText(Integer.toString(_line.getId()));
        }
    }

    @FXML
    private void saveEmployee(ActionEvent event){
        String file = "employeeSaved";
        try {
            Parent root = loadFXML(file);
            EmployeeSaveController controller = (EmployeeSaveController) getController();
            controller.initAttributes(employeeList);
            createModal(root);

            Employee aux = controller.getEmployee();
            if(aux==null){ return;}

            EmployeeService employeeService = new EmployeeService();
            employeeService.save(aux);

            this.employeeList.add(aux);
            reloadInfo();

        } catch (IOException | SQLException e) {
            log(Logger.getLogger(String.valueOf(this.getClass())), e.getMessage());

        }
    }

    @FXML
    private void removeEmployee(ActionEvent event){
        EmployeeService employeeService = new EmployeeService();
        Employee employee = selectProduct();
        if(employee==null){ return;}

        try {
            if(!alertConfirmation(Alert.AlertType.CONFIRMATION, "Alert","Are you sure you want to delete this employee?")){ return;}
            employeeService.remove(employee);
            this.employeeList.remove(employee);
            reloadInfo();

        } catch (SQLException e) {
            log(Logger.getLogger(String.valueOf(this.getClass())), e.getMessage());

        }
    }

    @FXML
    private void infoEmployee(ActionEvent event){
        String file = "employeeInfo";
        Employee employee = selectProduct();
        EmployeeInfoDTO.setEmployee(employee);
        if(employee==null){ return;}

        try {
            Parent root = loadFXML(file);
            createModal(root);

        } catch (IOException e) {
            log(Logger.getLogger(String.valueOf(this.getClass())), e.getMessage());

        }
    }

    @FXML
    private void updateEmployee(ActionEvent event){
        String file = "employeeUpdate";
        Employee employee = selectProduct();
        if(employee==null){ return;}
        EmployeeInfoDTO.setEmployee(employee);
        try {
            Parent root = loadFXML(file);
            EmployeeUpdateController controller = (EmployeeUpdateController) getController();
            createModal(root);

            Employee aux = controller.getEmployee();
            if(aux!=null){
                EmployeeService employeeService = new EmployeeService();
                employeeService.save(aux);

                this.employeeList.add(aux);
                reloadInfo();
            }

        } catch (IOException | SQLException e) {
            log(Logger.getLogger(String.valueOf(this.getClass())), e.getMessage());

        }
    }

}