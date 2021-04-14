package edu.wpi.MochaManticores.views;

import edu.wpi.MochaManticores.App;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class successController extends SceneController{

  @FXML
  private void advanceScene(ActionEvent e) {
    super.backMulti(2);
  }
}
