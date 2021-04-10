package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.scene.paint.Paint;

import javafx.event.ActionEvent;

public class GenericForm extends SceneController{

    @FXML
    private JFXTextField fieldOne;

    @FXML
    private JFXTextArea fieldThree;

    @FXML
    private JFXComboBox box;

    @FXML
    private JFXRadioButton rbOne;

    @FXML
    private JFXRadioButton rbTwo;

    @FXML
    private JFXRadioButton rbThree;

    public void initialize() {
        box.getItems().clear();
        box.getItems().addAll("1", "2", "3");
    }

    public void submit(ActionEvent e){
        System.out.println(fieldOne.getText());
        System.out.println(box.getSelectionModel().getSelectedItem());
        System.out.println(fieldThree.getText());
        System.out.println(rbOne.isSelected());

        returnToMain(e);
    }

    public void cancel(ActionEvent e) {
        System.out.println("Form cancelled");
        returnToMain(e);
    }

    public void back(ActionEvent e){
        super.back(e);
    }
}
