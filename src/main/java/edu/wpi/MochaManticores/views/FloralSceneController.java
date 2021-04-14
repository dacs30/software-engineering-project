package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.MochaManticores.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

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
  private GridPane contentGrid;
  @FXML
  private ImageView backgroundIMG;


  @FXML
  private void goBack(ActionEvent e) {
back();
  }

  @FXML
  private void initialize() {
    double height = App.getPrimaryStage().getScene().getHeight();
    double width = App.getPrimaryStage().getScene().getWidth();
    backgroundIMG.setFitHeight(height);
    backgroundIMG.setFitWidth(width);
    contentGrid.setPrefSize(width,height);
    //dialogPane.setDisable(false);
  }

  public void submitEvent(ActionEvent actionEvent) {
    System.out.println("hey");
  }
}
