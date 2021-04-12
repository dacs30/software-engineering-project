package edu.wpi.MochaManticores.views;

import javafx.event.ActionEvent;

public class LoginPage extends SceneController{

    public void onMouseClickedContinue(ActionEvent e) {
        changeSceneTo(e, "mainMenu");
    };
}
