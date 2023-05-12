package com.elcheno.trues;

import com.elcheno.trues.controller.Controller;
import com.elcheno.trues.model.domain.Employee;
import com.elcheno.trues.model.domain.Line;
import com.elcheno.trues.model.domain.Product;
import com.elcheno.trues.model.dto.LineDTO;
import com.elcheno.trues.model.dto.ProductInfoDTO;
import com.elcheno.trues.model.service.EmpLineService;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.action.Action;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ProductController extends Controller implements Initializable{

    @FXML
    private Button btnExit, btnMinWindow, btnRefresh, btnHomeView, btnProductView, btnLineView, btnEmpView;
    @FXML
    private Pane navbar;
    @FXML
    private TableView<Product> table;
    @FXML
    private TableColumn colCod, colDesc;
    @FXML
    private Label txtNLine, txtProductTotal, txtProductToday;

    private double xOffset = 0, yOffset = 0;
    private Line _line; // the line that is being worked on
    private ObservableList<Product> productsList;

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

        productsList = FXCollections.observableArrayList();
        this.colCod.setCellValueFactory(new PropertyValueFactory("cod"));
        this.colDesc.setCellValueFactory(new PropertyValueFactory("description"));

        reloadInfo();

    }

    public void reloadInfo(){
        reloadLine();
        reloadTable();
        reloadNTotal();
        reloadNToday();
    }

    @FXML
    private Product selectProduct(){
        Product result = null;
        Product p = this.table.getSelectionModel().getSelectedItem();
        if(p!=null){
            result = p;
        }
        return result;
    }

    @FXML
    private void saveProduct(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("productSaved.fxml"));
            Parent root = fxmlLoader.load();

            ProductSaveController controller = fxmlLoader.getController();
            controller.initAttributtes(productsList);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.showAndWait();

            Product aux = controller.getProduct();
            if(aux!=null){
                ProductService productService = new ProductService();
                productService.save(aux);

                this.productsList.add(aux);
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
    private void updateProduct(ActionEvent event){
            try {
                ProductService productService = new ProductService();
                Product product = selectProduct();
                if(product!=null){
                    ProductInfoDTO _prodInfo = ProductInfoDTO.getInstance();
                    _prodInfo.setProduct(product);

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("productUpdate.fxml"));
                    Parent root = fxmlLoader.load();

                    ProductUpdateController controller = fxmlLoader.getController();
                    controller.initAttributtes(productsList);

                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.showAndWait();

                    if(controller.getProduct()!=null){
                        Product aux = controller.getProduct();
                        for(Product p: productsList){
                            if(p.equals(aux)){
                                p.setDescription(aux.getDescription());
                                productService.save(p);
                                this.table.refresh();
                                break;
                            }
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }

    @FXML
    private void infoProduct(ActionEvent event){
        Product aux = selectProduct();
        if(aux==null){ return; }
        try {
            ProductInfoDTO.getInstance();
            ProductInfoDTO.setProduct(aux);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("productInfo.fxml"));

            Parent root = fxmlLoader.load();

            ProductInfoController controller = fxmlLoader.getController();


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
    private void removeProduct(ActionEvent event){
        Product product = selectProduct();
        if(product!=null){
            ProductService productService = new ProductService();
            try {
                if(alertConfirmation(Alert.AlertType.CONFIRMATION, "Alert", "Are you sure you want to delete this product?")){
                    productService.remove(product);
                    this.productsList.remove(product);
                    reloadInfo();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void reloadLine(){
        _line = LineDTO.getLine();
        if(_line != null){
            txtNLine.setText(Integer.toString(_line.getId()));
        }
    }

    @FXML
    public void reloadTable(){
        ProductService productService = new ProductService();
        try {
            productsList = FXCollections.observableArrayList(productService.getByIdLine(_line.getId()));
            this.table.setItems(productsList);
            table.refresh();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void reloadNTotal(){
        ProductService productService = new ProductService();
        try {
            List<Product> aux = productService.getByIdLine(_line.getId());
            if(aux!=null){
                txtProductTotal.setText(Integer.toString(aux.size()));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);

        }

    }

    @FXML
    public void reloadNToday() {
        ProductService productService = new ProductService();
        try {
            List<Product> aux = productService.getByIdLineDateNow(_line.getId());
            if(aux!=null){
                txtProductToday.setText(Integer.toString(aux.size()));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
