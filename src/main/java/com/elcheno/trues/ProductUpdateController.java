package com.elcheno.trues;

import com.elcheno.trues.model.domain.Product;
import com.elcheno.trues.model.dto.ProductInfoDTO;
import com.elcheno.trues.model.dto.ProductUpdateDTO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductUpdateController implements Initializable {
    /**
     * This is the controller for the update view(modal) of the product
     * @see Product
     * @see ProductController
     * @Author Elcheno
     */

    @FXML
    private Pane navbar;
    @FXML
    private Button btnUpdate;
    @FXML
    private TextField codField, descField;

    private Product _product;
    private double xOffset = 0, yOffset = 0;
    private ObservableList<Product> _products; // the list of products to return

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
        loadTextField();
    }

    public void initAttributtes(ObservableList<Product> _products){
        this._products = _products;
    }

    @FXML
    private void update(){
        if(codField.getText().isEmpty() || descField.getText().isEmpty()){ return; }
        int cod = Integer.parseInt(codField.getText());
        String desc = descField.getText();
        Product aux = new Product(cod, desc, _product.getLine(), _product.getDate());
        aux.setId(_product.getId());
        if(!_products.contains(aux)){
            _product = aux;
            alertInfo("Updated product", "Product updated successfully");
        }else{
            alertInfo("Error", "The product already exists");
        }
        Stage stage = (Stage) this.btnUpdate.getScene().getWindow();
        stage.close();
    }

    private void loadTextField(){
        if(ProductInfoDTO.getProduct() != null){
            this._product = ProductInfoDTO.getProduct();
            codField.setText(String.valueOf(_product.getCod()));
            descField.setText(_product.getDescription());
        }
    }

    public Product getProduct(){
        return _product;
    }

    @FXML
    private void closeWindows(ActionEvent event) {
        _product = null;
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

    private void alertInfo(String title, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
