package edu.wpi.MochaManticores.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class successController extends SceneController{

  @FXML
  private void advanceScene(ActionEvent e) {
    super.back(2);
  }
}
