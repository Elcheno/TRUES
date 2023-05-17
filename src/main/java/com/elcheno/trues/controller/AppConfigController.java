package com.elcheno.trues.controller;

import com.elcheno.trues.controller.Controller;
import com.elcheno.trues.model.domain.Line;
import com.elcheno.trues.model.dto.LineDTO;
import com.elcheno.trues.model.service.LineService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class AppConfigController extends Controller {
    /**
     * This is the controller for the config page
     * @Author Elcheno
     */

    @FXML
    private Pane navbar;
    @FXML
    private ComboBox comboBoxLines;
    @FXML
    private Button btnSave;

    private Line _line; // the line that is being worked on
    private double xOffset = 0, yOffset = 0;
    private ObservableList<String> lineList; // the list of lines
    LineService lineService = new LineService(); // the service to the LineDAO Class

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

        reloadInfo();
    }

    @FXML
    private void save(){
        try {
            Line aux = lineService.getById(Integer.parseInt((String) comboBoxLines.getValue()));
            if(aux==null || _line.equals(aux)){ return;}
            if(!alertConfirmation(Alert.AlertType.CONFIRMATION, "Save", "Are you sure you want to save this line?")){ return;}
            _line = aux;
            LineDTO.getLine(_line);

            Stage stage = (Stage) this.btnSave.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            log(Logger.getLogger(String.valueOf(this.getClass())), e.getMessage());

        }


    }

    private void reloadInfo() {
        loadLine();
        loadLines();
        loadBoxLines();
    }

    private void loadLine() {
        _line = LineDTO.getLine();
    }

    private void loadLines() {
        try {
            List<Line> auxLine = lineService.getAll();
            List<String> auxLineString = new ArrayList<>();

            for(Line l : auxLine){
                auxLineString.add(Integer.toString(l.getId()));
            }

            lineList = FXCollections.observableArrayList(auxLineString);

        } catch (Exception e) {
            log(Logger.getLogger(String.valueOf(this.getClass())), e.getMessage());

        }
    }

    private void loadBoxLines() {
        if(lineList == null){ return;}
        comboBoxLines.setItems(lineList);
        comboBoxLines.setValue(Integer.toString(_line.getId()));
//        comboBoxLines.setVisibleRowCount(_line.getId());
    }
}
