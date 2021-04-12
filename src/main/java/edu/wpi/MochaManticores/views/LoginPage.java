package edu.wpi.MochaManticores.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class LoginPage extends SceneController{

    @FXML
    AnchorPane emergencyPopUp;

    public void onMouseClickedContinue(ActionEvent e) {
        changeSceneTo(e, "mainMenu");
    };

    public void emergencyBtnClicked(ActionEvent e) {
        emergencyPopUp.setOpacity(1);
    }

    public void closePopUp(ActionEvent e) {
        emergencyPopUp.setOpacity(0);
    }

    public void goToEmergencyForm(ActionEvent e) {
        changeSceneTo(e, "genericForm");
    }
}
