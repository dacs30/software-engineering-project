package edu.wpi.MochaManticores.views;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.control.TextField;
public class internalTransportationController extends SceneController{


    ObservableList<String> typeOfTransportList = FXCollections
            .observableArrayList("Wheelchair","Walker","Medical Bed");

    // Information of Patient/One Being Transported
    @FXML
        private JFXTextField patientID;
    @FXML
        private JFXTextField numberOfStaff;
    @FXML
        private JFXTextField destination;
    @FXML
        private JFXComboBox transportComboBox;

    // Buttons for Form
    @FXML
        private JFXButton cancelButton;
    @FXML
        private JFXButton submitButton;
    @FXML
        private void initialize() {
        transportComboBox.setItems(typeOfTransportList);
        transportComboBox.setValue("Wheelchair");
    }
    @FXML
    private void cancelScene(ActionEvent e) {
super.back();
    }
    @FXML
        private void advanceSuccessScene(ActionEvent e) {
        System.out.println(patientID.getText());
        System.out.println(numberOfStaff.getText());
        System.out.println(destination.getText());
        System.out.println(transportComboBox.getSelectionModel().getSelectedItem());
        super.changeSceneTo("successPage");
//            try {
//                Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/MochaManticores/fxml/successPage.fxml"));
//                App.getPrimaryStage().getScene().setRoot(root);
//            } catch (IOException ex) {
//            ex.printStackTrace();
//            }
    }
}
