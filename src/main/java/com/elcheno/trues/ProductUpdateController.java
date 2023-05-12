package com.elcheno.trues;

import com.elcheno.trues.controller.Controller;
import com.elcheno.trues.model.domain.Product;
import com.elcheno.trues.model.dto.ProductInfoDTO;
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

public class ProductUpdateController extends Controller implements Initializable {
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
    private TextField descField;
    @FXML
    private Label txtCod;

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
    private void update() {
        String desc = descField.getText();
        if (desc.isEmpty() || desc.equals(_product.getDescription())) {
            alertInformation(Alert.AlertType.INFORMATION, "Error", "The product has not changed");
            loadTextField();
            return;
        }
        Product aux = new Product(_product.getCod(), desc, _product.getLine(), _product.getDate());
        aux.setId(_product.getId());
        _product = aux;
        alertInformation(Alert.AlertType.INFORMATION, "Updated product", "Product updated successfully");
        Stage stage = (Stage) this.btnUpdate.getScene().getWindow();
        stage.close();
    }

    private void loadTextField(){
        if(ProductInfoDTO.getProduct() != null){
            this._product = ProductInfoDTO.getProduct();
            descField.setText(_product.getDescription());
            txtCod.setText(Integer.toString(_product.getCod()));
        }
    }

    public Product getProduct(){
        return _product;
    }

}
