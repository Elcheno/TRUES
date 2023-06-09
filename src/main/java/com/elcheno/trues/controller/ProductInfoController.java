package com.elcheno.trues.controller;

import com.elcheno.trues.controller.Controller;
import com.elcheno.trues.controller.ProductController;
import com.elcheno.trues.model.domain.Product;
import com.elcheno.trues.model.dto.ProductInfoDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductInfoController extends Controller {
    /**
     * This is the controller for the information view(modal) of the product
     * @see Product
     * @see ProductController
     * @Author Elcheno
     */

    @FXML
    private Pane navbar;
    @FXML
    private Label productCod, productDesc, productLine, productDate;

    private double xOffset = 0, yOffset = 0;
    private Product _product; // the product to load


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
        loadProduct();
    }

    @FXML
    private void loadProduct(){
        if(ProductInfoDTO.getProduct()!=null){
            _product = ProductInfoDTO.getProduct();
        }
        productCod.setText(Integer.toString(_product.getCod()));
        productDesc.setText(_product.getDescription());
        productLine.setText(Integer.toString(_product.getLine().getId()));
        productDate.setText(_product.getDate().toString());
    }

}
