package com.elcheno.trues.controller;

import com.elcheno.trues.model.domain.Line;
import com.elcheno.trues.model.domain.Product;
import com.elcheno.trues.model.dto.LineDTO;
import com.elcheno.trues.model.dto.ProductInfoDTO;
import com.elcheno.trues.model.service.ProductService;
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

public class ProductController extends Controller {

    @FXML
    private Button btnExit, btnMinWindow, btnRefresh, btnHomeView, btnProductView, btnLineView, btnEmpView;
    @FXML
    private TableView<Product> table;
    @FXML
    private TableColumn colCod, colDesc;
    @FXML
    private Label txtNLine, txtProductTotal, txtProductToday;
    @FXML
    private Pane navbar;

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
        String file = "productSaved";
        try {
            Parent root = loadFXML(file);
            ProductSaveController controller = (ProductSaveController) getController();
            controller.initAttributtes(productsList);
            createModal(root);

            Product aux = controller.getProduct();
            if(aux==null){ return; }

            ProductService productService = new ProductService();
            productService.save(aux);
            this.productsList.add(aux);
            reloadInfo();

        } catch (IOException | SQLException e) {
            log(Logger.getLogger(String.valueOf(this.getClass())), e.getMessage());

        }
    }

    @FXML
    private void updateProduct(ActionEvent event){
        String file = "productUpdate";
        ProductService productService = new ProductService();
        Product product = selectProduct();
        try {
            if(product!=null){
                ProductInfoDTO.setProduct(product);

                Parent root = loadFXML(file);
                ProductUpdateController controller = (ProductUpdateController) getController();
                createModal(root);

                if(controller.getProduct()==null){ return; }
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
        } catch (IOException | SQLException e) {
            log(Logger.getLogger(String.valueOf(this.getClass())), e.getMessage());

        }
    }

    @FXML
    private void infoProduct(ActionEvent event){
        String file = "productInfo";
        Product aux = selectProduct();
        if(aux==null){ return; }
        ProductInfoDTO.setProduct(aux);
        try {
            Parent root = loadFXML(file);
            createModal(root);

        } catch (IOException e) {
            log(Logger.getLogger(String.valueOf(this.getClass())), e.getMessage());

        }
    }

    @FXML
    private void removeProduct(ActionEvent event) {
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
                log(Logger.getLogger(String.valueOf(this.getClass())), e.getMessage());

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
            productsList = FXCollections.observableArrayList(productService.getByIdLine(_line.getId()));
            this.table.setItems(productsList);
            table.refresh();
    }

    @FXML
    public void reloadNTotal(){
        ProductService productService = new ProductService();
        List<Product> aux = productService.getByIdLine(_line.getId());

        if(aux!=null){
            txtProductTotal.setText(Integer.toString(aux.size()));
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
            log(Logger.getLogger(String.valueOf(this.getClass())), e.getMessage());

        }
    }
}
