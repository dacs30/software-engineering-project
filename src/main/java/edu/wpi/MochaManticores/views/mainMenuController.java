package edu.wpi.MochaManticores.views;

import javafx.event.ActionEvent;

public class mainMenuController extends SceneController{

    public void gotoService(ActionEvent e){
        super.changeSceneTo(e, "landingPage");
    }

    public void gotoMap(ActionEvent e){
        super.changeSceneTo(e, "mapPage");
    }

    public void back(ActionEvent e){
        super.back(e);
    }

}
