package edu.wpi.MochaManticores.views;

import edu.wpi.MochaManticores.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

public class landingPageController extends SceneController {



  @FXML private GridPane contentPane;


  @FXML private void initialize(){
      double height = super.getHeight();
      double width = super.getWidth();
      contentPane.setPrefSize(width,height);
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
  }

  @FXML
  private void confirmEmergency(ActionEvent e){
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

  public void back(ActionEvent e){
    super.back(e);
  }

  public void exitApp(ActionEvent e){
    super.exitApp(e);
  }

}
