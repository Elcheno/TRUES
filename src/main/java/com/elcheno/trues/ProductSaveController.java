package com.elcheno.trues;

import com.elcheno.trues.controller.Controller;
import com.elcheno.trues.model.domain.Line;
import com.elcheno.trues.model.domain.Product;
import com.elcheno.trues.model.dto.LineDTO;
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
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ProductSaveController extends Controller implements Initializable {
    /**
     * This is the controller for the save view(modal) of the product
     * @see Product
     * @see ProductController
     * @Author Elcheno
     */

    @FXML
    private Button btnExit, btnMinWindow, btnSave;
    @FXML
    private Pane navbar;
    @FXML
    private TextField codField, descField;


    private double xOffset = 0, yOffset = 0;
    private Line _line; // the line that is being worked on
    private Product _product; // the product to return
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
        reloadLine();
    }

    public void initAttributtes(ObservableList<Product> products){
        _products = products;
    }



    @FXML
    private void save(ActionEvent event) {
        String codText = codField.getText();
        String descText = descField.getText();

        if(codText.isEmpty() || descText.isEmpty() || codText.matches("[a-zA-Z]+")){
            resetField();
            alertInfo("Error creating product", "Correctly fill in the fields");
            return;
        }

        int cod = Integer.parseInt(codText);
        String description = descText;
        Product aux = new Product(cod, description, _line, LocalDate.now());

        if(_products.contains(aux)){
            alertInfo("Error creating product", "The product already exists");
            return;
        }

        _product = aux;
        alertInfo("Saved product", "The product has been saved correctly");

        Stage stage = (Stage) this.btnSave.getScene().getWindow();
        stage.close();
    }

    private void resetField() {
        codField.setText("");
        descField.setText("");
    }

    @FXML
    public void reloadLine(){
        _line = LineDTO.getLine();
    }

    public Product getProduct(){
        return _product;
    }

    private void alertInfo(String title, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
