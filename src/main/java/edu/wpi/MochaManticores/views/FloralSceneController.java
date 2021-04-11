package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.MochaManticores.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FloralSceneController extends SceneController {

  @FXML
  private JFXTextField rmNum;
  @FXML
  private JFXTextField delivDate;
  @FXML
  private JFXTextArea personalNote;

  @FXML
  private JFXCheckBox tulip;
  @FXML
  private JFXCheckBox rose;
  @FXML
  private JFXCheckBox lilie;

  @FXML
  private JFXCheckBox blue;
  @FXML
  private JFXCheckBox yellow;
  @FXML
  private JFXCheckBox orange;


  @FXML
  private void goBack(ActionEvent e) {
returnToMain(e);
  }
  @FXML
  private void submitForm(ActionEvent e) {
    System.out.println(rmNum.getText());
    System.out.println(delivDate.getText());
    System.out.printf("Tulip: %s\nRose: %s\nLillie: %s\n",tulip.isSelected(),rose.isSelected(),lilie.isSelected());
    System.out.printf("Blue: %s\nYellow: %s\nOrange: %s\n",blue.isSelected(),yellow.isSelected(),orange.isSelected());
    System.out.println(personalNote.getText());

    returnToMain(e);
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
