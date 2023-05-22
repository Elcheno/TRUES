package com.elcheno.trues.controller;

import com.elcheno.trues.App;
import com.elcheno.trues.model.domain.Employee;
import com.elcheno.trues.model.domain.Line;
import com.elcheno.trues.model.domain.Product;
import com.elcheno.trues.model.dto.LineDTO;
import com.elcheno.trues.model.service.EmpLineService;
import com.elcheno.trues.model.service.EmployeeService;
import com.elcheno.trues.model.service.LineService;
import com.elcheno.trues.model.service.ProductService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeController extends Controller {
    /**
     * This is the controller for the home page
     * @author Elcheno
     */

    private Line _line; // the line that is being worked on
    private LineService lineService = new LineService(); // the service to the Line Class
    private EmpLineService empLineService = new EmpLineService(); // the service to the EmpLine Class
    private ProductService productService = new ProductService(); // the service to the Product Class

    @FXML
    private Pane navbar;
    @FXML
    private Button btnExit, btnMinWindow, btnRefresh, btnLineView;
    @FXML
    private Label txtNLine, txtEmployee, txtLine, txtProduct, txtEmpName, txtEmpDni, txtEmpLastname, txtProdCod, txtProdDesc;

    private double xOffset = 0, yOffset = 0;

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

        reloadInfo();
    }

    public void reloadInfo(){
        reloadLine();
        reloadNLine();
        reloadNEmployee();
        reloadNProduct();
        loadLastEmployee();
        loadLastProduct();
    }

    @FXML
    private void reloadLine(){
        _line = LineDTO.getLine();
        if(_line != null){
            txtNLine.setText(Integer.toString(_line.getId()));
        }
    }

    @FXML
    private void reloadNLine(){
        if(_line !=null){
            try {
                txtLine.setText(Integer.toString(lineService.getAll().size()));

            } catch (SQLException e) {
                log(Logger.getLogger(String.valueOf(this.getClass())), e.getMessage());

            }
        }
    }

    @FXML
    private void reloadNEmployee(){
        if(_line != null){
            try {
                txtEmployee.setText(Integer.toString(empLineService.getByLine(_line.getId()).size()));

            } catch (SQLException e) {
                log(Logger.getLogger(String.valueOf(this.getClass())), e.getMessage());

            }
        }
    }

    @FXML
    private void reloadNProduct(){
        if(_line != null){
            txtProduct.setText(Integer.toString(productService.getByIdLine(_line.getId()).size()));
        }
    }

    @FXML
    private void loadLastProduct(){
        if(_line != null){
            try {
                List<Product> productList = productService.getByIdLineDateNow(_line.getId());
                if(!productList.isEmpty()){
                    txtProdCod.setText(Integer.toString(productList.get(productList.size()-1).getCod()));
                    txtProdDesc.setText(productList.get(productList.size()-1).getDescription());
                }

            } catch (SQLException e) {
                log(Logger.getLogger(String.valueOf(this.getClass())), e.getMessage());

            }
        }
    }

    @FXML
    private void loadLastEmployee(){
        if(_line != null){
            try {
                List<Employee> employeeList = empLineService.getByLine(_line.getId());
                if(!employeeList.isEmpty()){
                    txtEmpDni.setText(employeeList.get(employeeList.size()-1).getDni());
                    txtEmpName.setText(employeeList.get(employeeList.size()-1).getName());
                    txtEmpLastname.setText(employeeList.get(employeeList.size()-1).getLastName());
                }
            } catch (SQLException e) {
                log(Logger.getLogger(String.valueOf(this.getClass())), e.getMessage());

            }
        }
    }

    @FXML
    private void config(){
        String file = "appConfig";
        try {
            Parent root = loadFXML(file);
            createModal(root);

            reloadInfo();

        } catch (IOException e) {
            log(Logger.getLogger(String.valueOf(this.getClass())), e.getMessage());

        }
    }
}
