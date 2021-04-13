package edu.wpi.MochaManticores.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class landingPageController extends SceneController {



  @FXML private GridPane contentPane;


  @FXML private void initialize(){
      double height = super.getHeight();
      double width = super.getWidth();
      contentPane.setPrefSize(width,height);
  }

  @FXML
  private void advanceScene(ActionEvent e) {
    changeSceneTo("Scene2");
  }

  @FXML
  private void advanceInternalTransportScene(ActionEvent e) {
    changeSceneTo("internalTransportation");
  }

  @FXML
  private void dismissEmergencyDialog(ActionEvent e){
  }

  @FXML
  private void confirmEmergency(ActionEvent e){
  }

  @FXML
  private void emergencyForm(ActionEvent e){
    changeSceneTo("EmergencyForm");
  }


  public void advanceFoodService(ActionEvent e) {
    changeSceneTo("foodDelivery");
  }

  @FXML
  private void advanceToExtTransportation(ActionEvent e) {
    changeSceneTo("extTransportation");
  }

  @FXML
  private void advanceToFloralScene(ActionEvent e) {
    changeSceneTo("floralScene");
  }

  @FXML
  private void grabSanitation(ActionEvent e) {
    changeSceneTo("sanitationService");
  }

  public void back(){
    super.back();
  }

  public void exitApp(ActionEvent e){
    super.exitApp(e);
  }

}
