package edu.wpi.MochaManticores.views;

import edu.wpi.MochaManticores.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;

import javax.xml.soap.Text;
import java.io.IOException;

public class FloralSceneController {

  @FXML
  private TextField rmNum;
  @FXML
  private TextField delivDate;
  @FXML
  private TextArea personalNote;

  @FXML
  private CheckBox tulip;
  @FXML
  private CheckBox rose;
  @FXML
  private CheckBox lilie;

  @FXML
  private CheckBox blue;
  @FXML
  private CheckBox yellow;
  @FXML
  private CheckBox orange;


  @FXML
  private void goBack(ActionEvent e) {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/MochaManticores/fxml/landingPage.fxml"));
      App.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
  @FXML
  private void submitForm(ActionEvent e) {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/MochaManticores/fxml/landingPage.fxml"));
      App.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  @FXML
  private void helpButton(ActionEvent e) {
    Scene scene = App.getPrimaryStage().getScene();
    scene.lookup("#helpPopup").setVisible(true);
  }

  @FXML
  private void closePopup(ActionEvent e) {
    Scene scene = App.getPrimaryStage().getScene();
    scene.lookup("#helpPopup").setVisible(false);
  }

}
