package com.elcheno.trues;

import com.elcheno.trues.model.domain.Employee;
import com.elcheno.trues.model.domain.Line;
import com.elcheno.trues.model.domain.Product;
import com.elcheno.trues.model.dto.LineDTO;
import com.elcheno.trues.model.service.EmpLineService;
import com.elcheno.trues.model.service.EmployeeService;
import com.elcheno.trues.model.service.ProductService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {

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
    private void saveEmployee(){
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
                employeeService.remove(employee);
                this.employeeList.remove(employee);
                reloadInfo();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                throw new RuntimeException(e);

            }
        }
    }

    @FXML
    private void closeWindows(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void minimizeWindows(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void homeView() throws IOException {
        App.setRoot("home");
    }

    @FXML
    private void productView() throws IOException {
        App.setRoot("product");
    }

    @FXML
    private void lineView() throws IOException {
        App.setRoot("line");
    }
}