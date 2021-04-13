package edu.wpi.MochaManticores.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class LoginPage extends SceneController{

    @FXML
    private ImageView backgroundIMG;

    @FXML
    private GridPane contentPane;

    public void initialize(){
        double height = super.getHeight();
        double width = super.getWidth();
        backgroundIMG.setFitHeight(height);
        backgroundIMG.setFitWidth(width);
        contentPane.setPrefSize(width,height);
    }

    @FXML
    AnchorPane emergencyPopUp;

    public void onMouseClickedContinue(ActionEvent e) {
        changeSceneTo("mainMenu");
    }

    public void emergencyBtnClicked(ActionEvent e) {

    }

    public void staffMenu(ActionEvent e) {
        super.changeSceneTo("staffMainMenu");
    }
    public void closePopUp(ActionEvent e) {
        emergencyPopUp.setOpacity(0);
    }

    public void goToEmergencyForm(ActionEvent e) {
        changeSceneTo("genericForm");
    }

    public void exitApp(ActionEvent e){
super.exitApp(e);
    }
}
