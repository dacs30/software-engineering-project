package edu.wpi.MochaManticores.views;
import edu.wpi.MochaManticores.App;
import java.awt.*;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;

public class internalTransportationController {

    ObservableList<String> typeOfTransportList = FXCollections
            .observableArrayList("Wheelchair","Walker","Medical Bed");

    // Information of Patient/One Being Transported
    @FXML
        private TextField patientID;
    @FXML
        private TextField numberOfStaff;
    @FXML
        private TextField destination;
    @FXML
        private TextField methodOfTransport;
    @FXML
        private ComboBox transportComboBox;

    // Buttons for Form
    @FXML
        private Button cancelButton;
    @FXML
        private Button submitButton;
    @FXML
        private void initialize() {
        transportComboBox.setItems(typeOfTransportList);
        transportComboBox.setValue("Wheelchair");
    }
    @FXML
    private void cancelScene(ActionEvent e) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/MochaManticores/fxml/landingPage.fxml"));
            App.getPrimaryStage().getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
        private void advanceScene(ActionEvent e) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/MochaManticores/fxml/successPage.fxml"));
                App.getPrimaryStage().getScene().setRoot(root);
            } catch (IOException ex) {
            ex.printStackTrace();
            }
    }
}
