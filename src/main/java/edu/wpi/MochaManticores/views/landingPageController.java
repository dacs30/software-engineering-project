package edu.wpi.MochaManticores.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class landingPageController extends SceneController {


  @FXML private AnchorPane confirmEmergencyDialog;

  @FXML
  private void advanceScene(ActionEvent e) {
    changeSceneTo(e,"Scene2");
  }

  @FXML
  private void advanceInternalTransportScene(ActionEvent e) {
    changeSceneTo(e,"internalTransportation");
  }

  @FXML
  private void dismissEmergencyDialog(ActionEvent e){
    confirmEmergencyDialog.setVisible(false);
  }

  @FXML
  private void confirmEmergency(ActionEvent e){
    confirmEmergencyDialog.setVisible(true);
  }

  @FXML
  private void emergencyForm(ActionEvent e){
    changeSceneTo(e, "EmergencyForm");
  }

  @FXML
  private void exitMethod(ActionEvent e){
    System.exit(0);
  }

  public void advanceFoodService(ActionEvent e) {
    changeSceneTo(e, "foodDelivery");
  }

  @FXML
  private void advanceToExtTransportation(ActionEvent e) {
    changeSceneTo(e,"extTransportation");
  }

  @FXML
  private void advanceToFloralScene(ActionEvent e) {
    changeSceneTo(e,"floralScene");
  }

  @FXML
  private void grabSanitation(ActionEvent e) {
    changeSceneTo(e,"sanitationService");
  }
}
