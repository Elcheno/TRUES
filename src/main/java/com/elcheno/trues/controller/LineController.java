package com.elcheno.trues.controller;

import com.elcheno.trues.controller.Controller;
import com.elcheno.trues.model.domain.Employee;
import com.elcheno.trues.model.domain.Line;
import com.elcheno.trues.model.dto.EmpLineDTO;
import com.elcheno.trues.model.dto.LineDTO;
import com.elcheno.trues.model.service.EmpLineService;
import com.elcheno.trues.model.service.EmployeeService;
import com.elcheno.trues.model.service.ProductService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class LineController extends Controller {
    /**
     * This is the controller for the home page
     * @author Elcheno
     */

    @FXML
    private Pane navbar;
    @FXML
    private Button btnExit, btnMinWindow, btnRefresh, btnHomeView, btnEmpLine;
    @FXML
    private TableView<Employee> table;
    @FXML
    private TableColumn colCod, colName, colLastname;
    @FXML
    private TextField fieldTxtEmp;
    @FXML
    private Label txtNLine, txtEmployee, txtProduct;

    private double xOffset = 0, yOffset = 0;
    private ObservableList<Employee> employeeWorkList;
    private Line _line; // the line that is being worked on

    private final EmpLineService empLineService = new EmpLineService(); // the service to the EmpLine Class
    private final ProductService productService = new ProductService(); // the service to the Product Class
    private final EmployeeService employeeService = new EmployeeService(); // the service to the Employee Class

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

        employeeWorkList = FXCollections.observableArrayList();
        this.colCod.setCellValueFactory(new PropertyValueFactory("cod"));
        this.colName.setCellValueFactory(new PropertyValueFactory("name"));
        this.colLastname.setCellValueFactory(new PropertyValueFactory("lastName"));

        reloadInfo();

    }

    @FXML
    public void reloadInfo(){
        reloadLine();
        reloadTable();
        reloadNEmployee();
        reloadNProduct();
    }

    @FXML
    public void reloadNEmployee(){
        if(_line != null){
            try {
                txtEmployee.setText(Integer.toString(empLineService.getByLine(_line.getId()).size()));
            } catch (SQLException e) {
                log(Logger.getLogger(String.valueOf(this.getClass())), e.getMessage());

            }
        }
    }

    @FXML
    public void reloadNProduct(){
        if(_line != null){
            txtProduct.setText(Integer.toString(productService.getByIdLine(_line.getId()).size()));
        }
    }

    @FXML
    public void addEmployeeLine(){
        try {
            if(fieldTxtEmp.getText().isEmpty() || !fieldTxtEmp.getText().matches("\\d+")){
                fieldTxtEmp.setText("");
                return;
            }
            int cod = Integer.parseInt(fieldTxtEmp.getText());
            Employee aux = employeeService.getByCod(cod);
            if(employeeWorkList.contains(aux) && aux != null){
                EmpLineDTO empLineDTO = empLineService.getByEmp(aux.getId(), _line.getId());
                empLineDTO.setDateOut(LocalTime.now());
                if(empLineService.save(empLineDTO, aux)){
                    employeeWorkList.remove(aux);
                }
                fieldTxtEmp.setText("");
                reloadInfo();
                return;
            }else if(aux == null){
                fieldTxtEmp.setText("");
                return;
            }
            EmpLineDTO empLineDTO = new EmpLineDTO(_line, LocalDate.now(), LocalTime.now(), null);
            empLineService.save(empLineDTO, aux);
            this.employeeWorkList.add(aux);
            fieldTxtEmp.setText("");
            reloadInfo();

        } catch (SQLException | NumberFormatException e) {
            log(Logger.getLogger(String.valueOf(this.getClass())), e.getMessage());

        }
    }

    @FXML
    public void reloadTable(){
        try {
            employeeWorkList = FXCollections.observableArrayList(empLineService.getByLine(_line.getId()));
            this.table.setItems(employeeWorkList);

        } catch (SQLException e) {
            log(Logger.getLogger(String.valueOf(this.getClass())), e.getMessage());

        }

    }

    @FXML
    public void reloadLine(){
        _line = LineDTO.getLine();
        if(_line != null){
            txtNLine.setText(Integer.toString(_line.getId()));
        }
    }
}
