package com.elcheno.trues;

import com.elcheno.trues.controller.Controller;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeController extends Controller implements Initializable {

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
            System.err.println(e.getMessage());
            throw new RuntimeException(e);

        }
    }

    public void loadEmpTotal(){
        try {
            EmployeeService employeeService = new EmployeeService();
            List<Employee> aux = employeeService.getAll();
            if(aux==null){ return;}
            txtEmpTotal.setText(Integer.toString(aux.size()));

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);

        }
    }

    public void loadEmpToday(){
        try {
            EmpLineService empLineService = new EmpLineService();
            List<Employee> aux = empLineService.getByLine(_line.getId());
            if(aux==null){ return;}
            txtEmpToday.setText(Integer.toString(aux.size()));

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);

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
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("employeeSaved.fxml"));
            Parent root = fxmlLoader.load();

            EmployeeSaveController controller = fxmlLoader.getController();
            controller.initAttributtes(employeeList);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.showAndWait();

            Employee aux = controller.getEmployee();
            if(aux!=null){
                EmployeeService employeeService = new EmployeeService();
                employeeService.save(aux);

                this.employeeList.add(aux);
                reloadInfo();
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void removeEmployee(ActionEvent event){
        Employee employee = selectProduct();
        if(employee!=null){
            EmployeeService employeeService = new EmployeeService();
            try {
                if(alertConfirmation(Alert.AlertType.CONFIRMATION, "Alert","Are you sure you want to delete this employee?")){
                    employeeService.remove(employee);
                    this.employeeList.remove(employee);
                    reloadInfo();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                throw new RuntimeException(e);

            }
        }
    }

    @FXML
    private void infoEmployee(ActionEvent event){
        Employee employee = selectProduct();
        EmployeeInfoDTO.getInstance().setEmployee(employee);
        if(employee==null){ return;}
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("employeeInfo.fxml"));
            Parent root = fxmlLoader.load();

            EmployeeInfoController controller = fxmlLoader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.showAndWait();

        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @FXML
    private void updateEmployee(ActionEvent event){
        Employee employee = selectProduct();
        if(employee==null){ return;}
        EmployeeInfoDTO.getInstance().setEmployee(employee);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("employeeUpdate.fxml"));
            Parent root = fxmlLoader.load();

            EmployeeUpdateController controller = fxmlLoader.getController();
            controller.initAttributes(employeeList);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.showAndWait();

            Employee aux = controller.getEmployee();
            if(aux!=null){
                EmployeeService employeeService = new EmployeeService();
                employeeService.save(aux);

                this.employeeList.add(aux);
                reloadInfo();
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);

        } catch(SQLException e){
                System.err.println(e.getMessage());
                throw new RuntimeException(e);

        }

    }

}