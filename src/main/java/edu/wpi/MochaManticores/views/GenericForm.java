package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.scene.paint.Paint;

import javafx.event.ActionEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GenericForm extends SceneController{

    public JFXButton backButton;

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

    private List<JFXRadioButton> buttons;

    public void initialize() {
        box.getItems().clear();
        box.getItems().addAll("1", "2", "3");
        buttons = Arrays.asList(rbOne, rbTwo, rbThree);

    }

    public void submit(ActionEvent e){
        System.out.println(fieldOne.getText());
        System.out.println(box.getSelectionModel().getSelectedItem());
        System.out.println(fieldThree.getText());
        System.out.println(rbOne.isSelected());
        System.out.println(rbTwo.isSelected());
        System.out.println(rbThree.isSelected());
        returnToMain(e);
    }

    public void cancel(ActionEvent e) {
        System.out.println("Form cancelled");
        returnToMain(e);
    }

    public void back(ActionEvent e){
        super.back(e);
    }

    public void setSingleRadio(ActionEvent e){
        JFXRadioButton source = (JFXRadioButton) e.getSource();
        String pressed = source.getId();
        for (JFXRadioButton button : buttons) {
            if (!button.getId().equals(pressed)) {
                button.setSelected(false);
            }
        }

    }
}
