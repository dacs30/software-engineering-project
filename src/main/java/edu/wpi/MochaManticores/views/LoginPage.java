package edu.wpi.MochaManticores.views;

import edu.wpi.MochaManticores.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class LoginPage extends SceneController{

    @FXML
    private ImageView backgroundIMG;

    @FXML
    private GridPane contentPane;

    public void initialize(){
        double height = App.getPrimaryStage().getScene().getHeight();
        double width = App.getPrimaryStage().getScene().getWidth();
        backgroundIMG.setFitHeight(height);
        backgroundIMG.setFitWidth(width);
        contentPane.setPrefSize(width,height);
    }

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

    public void exitApp(ActionEvent e){
super.exitApp(e);
    }
}
