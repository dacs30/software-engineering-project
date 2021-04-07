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

public class FloralSceneController extends form{

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
