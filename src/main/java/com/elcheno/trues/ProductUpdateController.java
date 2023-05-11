package com.elcheno.trues;

import com.elcheno.trues.model.domain.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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



    private Product _product;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void update(){

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
}
