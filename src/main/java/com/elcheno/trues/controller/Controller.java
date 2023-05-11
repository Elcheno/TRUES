package com.elcheno.trues.controller;

import com.elcheno.trues.App;
import com.elcheno.trues.model.dto.LineDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {


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
    private void homeView() {
        try {
            App.setRoot("home");
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void productView() {
        try {
            App.setRoot("product");
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void lineView() {
        try {
            App.setRoot("line");
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void employeeView() {
        try {
            App.setRoot("employee");
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
