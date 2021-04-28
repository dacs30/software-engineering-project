package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;

public class LaundryFormController {

    @FXML
    private JFXComboBox<String> soil;

    @FXML
    private JFXComboBox<String> wTemp;

    @FXML
    private JFXComboBox<String> dTemp;

    public void init(){
        soil.getItems().clear();
        soil.getItems().addAll("Light", "Medium", "Heavy");

        wTemp.getItems().clear();
        wTemp.getItems().addAll("Hot", "Warm", "Cold");

        dTemp.getItems().clear();
        dTemp.getItems().addAll("High", "Medium", "Low", "Delicate", "No Heat");
    }
}
