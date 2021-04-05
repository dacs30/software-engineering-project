package edu.wpi.MochaManticores.views;

import edu.wpi.MochaManticores.App;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Scene4 {

  @FXML
  private void advanceScene(ActionEvent e) {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/MochaManticores/fxml/landingPage.fxml"));
      App.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
