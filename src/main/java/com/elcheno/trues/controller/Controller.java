package com.elcheno.trues.controller;

import com.elcheno.trues.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public abstract class Controller implements Initializable {

    private Controller _instance;


    public abstract void initialize(URL url, ResourceBundle resourceBundle);

    @FXML
    public void closeWindows(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void minimizeWindows(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void homeView() {
        try {
            App.setRoot("home");
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void productView() {
        try {
            App.setRoot("product");
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void lineView() {
        try {
            App.setRoot("line");
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    @FXML
    public void employeeView() {
        try {
            App.setRoot("employee");
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public boolean alertConfirmation(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(content);
        Optional<ButtonType> action = alert.showAndWait();
        return action.get() == ButtonType.OK;
    }

    public void alertInformation(Alert.AlertType alertType, String title, String content){
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent root = fxmlLoader.load();
        _instance = fxmlLoader.getController();
        return root;
    }

    public Controller getController()throws IOException{
        return _instance;
    }

    public void createModal(Parent root){
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.showAndWait();
    }

    public void exception(String bar) {
        throw new RuntimeException(bar);
    }
}
