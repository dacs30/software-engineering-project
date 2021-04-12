package edu.wpi.MochaManticores.views;

import edu.wpi.MochaManticores.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

public class landingPageController extends SceneController {


  @FXML private AnchorPane confirmEmergencyDialog;

  @FXML private AnchorPane outerContainer;

  @FXML private AnchorPane innerContainer;

  @FXML private Rectangle titleBG;


  @FXML private void initialize(){
    double h = App.getPrimaryStage().getScene().getWindow().getHeight();
    double w = App.getPrimaryStage().getScene().getWindow().getWidth();
    double height = innerContainer.getPrefHeight();
    double width = innerContainer.getPrefWidth();
    double setX = (w/2) - (width/2);
    double setY = (h/2) - (height/2);
    System.out.println(setX);
    System.out.println(setY);
    innerContainer.setLayoutX(setX);
    innerContainer.setLayoutY(setY);
  }

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

  @FXML
  private void scaleScene(MouseEvent e){
    double h = App.getPrimaryStage().getScene().getWindow().getHeight();
    double w = App.getPrimaryStage().getScene().getWindow().getWidth();
    double height = outerContainer.getPrefHeight();
    double width = outerContainer.getPrefWidth();
    double setX = (w/2) - (width/2);
    double setY = (h/2) - (height/2);
    System.out.println(setX);
    System.out.println(setY);
    outerContainer.setLayoutX(setX);
    outerContainer.setLayoutY(setY);
  }
}
