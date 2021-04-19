package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.MochaManticores.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.Arrays;
import java.util.List;

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
  private JFXRadioButton blueVase;
  @FXML
  private JFXRadioButton yellowVase;
  @FXML
  private JFXRadioButton orangeVase;

  private List<JFXRadioButton> vases;


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

    backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
    backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());

    vases = Arrays.asList(blueVase, yellowVase, orangeVase);
    //dialogPane.setDisable(false);
  }

  public void submitEvent(ActionEvent actionEvent) {
    System.out.println("hey");
  }

  public void setSingleVase(ActionEvent e){
    JFXRadioButton source = (JFXRadioButton) e.getSource();
    String pressed = source.getId();
    for (JFXRadioButton button : vases) {
      if (!button.getId().equals(pressed)) {
        button.setSelected(false);
      }
    }

  }
}
