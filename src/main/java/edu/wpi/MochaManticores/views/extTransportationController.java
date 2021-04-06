package edu.wpi.MochaManticores.views;

import edu.wpi.MochaManticores.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;

public class extTransportationController {

    ObservableList<String> transportationMethod = FXCollections.observableArrayList("Ambulance", "Helicopter", "Plane");

    @FXML
    private TextField patientName;
    @FXML
    private TextField roomNumber;
    @FXML
    private TextField address;
    @FXML
    private ComboBox<String> transportationDropDown;

    @FXML
    private void initialize() {
        //transportationDropDown.setValue("Ambulance");
        transportationDropDown.setItems(transportationMethod);

    }

    @FXML
    private void advanceScene(ActionEvent e) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/MochaManticores/fxml/landingPage.fxml"));
            App.getPrimaryStage().getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
