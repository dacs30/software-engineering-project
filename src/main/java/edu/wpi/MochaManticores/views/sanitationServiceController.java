package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class sanitationServiceController extends SceneController {

    @FXML
    private JFXTextField loc;

    @FXML
    private JFXTextField safetyHaz;

    @FXML
    private JFXTextField type;

    @FXML
    private JFXTextField equipment;

    @FXML
    private JFXTextArea description;

    @FXML
    private JFXButton submitButton;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private void submit(ActionEvent e) {
        try {
            System.out.println(loc.getText());
            System.out.println(safetyHaz.getText());
            System.out.println(type.getText());
            System.out.println(equipment.getText());
            System.out.println(description.getText());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        returnToMain();
    }


    public void back() {
        super.back();
    }
}

