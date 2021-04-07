package edu.wpi.MochaManticores.views;

import edu.wpi.MochaManticores.App;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

public class landingPageController {


  @FXML private AnchorPane confirmEmergencyDialog;

  @FXML
  private void advanceScene(ActionEvent e) {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/MochaManticores/fxml/Scene2.fxml"));
      App.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
  @FXML
  private void advanceInternalTransportScene(ActionEvent e) {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/MochaManticores/fxml/internalTransportation.fxml"));
      App.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  @FXML
  private void returnToMain(ActionEvent e){
    confirmEmergencyDialog.setVisible(false);
  }
  @FXML
  private void confirmEmergency(ActionEvent e){
    confirmEmergencyDialog.setVisible(true);

    //    try{
//      Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/MochaManticores/fxml/EmergencyConfirm.fxml"));
//      App.getPrimaryStage().getScene().setRoot(root);
//    } catch (IOException ex){
//      ex.printStackTrace();
//    }


  }
  @FXML
  private void emergencyForm(ActionEvent e){
    try {
      Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/MochaManticores/fxml/EmergencyForm.fxml"));
      App.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  @FXML
  private void exitMethod(ActionEvent e){
    System.exit(0);
  }



  public void advanceFoodService(ActionEvent actionEvent) {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/MochaManticores/fxml/foodDelivery.fxml"));
      App.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex){
      ex.printStackTrace();
    }
  }

  @FXML
  private void advanceToExtTransportation(ActionEvent e) {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/MochaManticores/fxml/extTransportation.fxml"));
      App.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  @FXML
  private void advanceToFloralScene(ActionEvent e) {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/MochaManticores/fxml/floralScene.fxml"));
      App.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }

  }
  @FXML
  private void grabSanitation(ActionEvent e) {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/MochaManticores/fxml/sanitationService.fxml"));
      App.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
