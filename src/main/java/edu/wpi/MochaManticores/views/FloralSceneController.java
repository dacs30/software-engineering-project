package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.*;
import edu.wpi.MochaManticores.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

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
  private StackPane dialogPane;




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

    dialogPane.toBack();

    backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
    backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());

    vases = Arrays.asList(blueVase, yellowVase, orangeVase); }

  public void submitEvent(ActionEvent actionEvent) {
    System.out.println("Submitted");
  }

  public void helpButton(ActionEvent actionEvent){loadHelpDialogue();}

  private void loadDialog(){
    JFXDialogLayout message = new JFXDialogLayout();
    message.setMaxHeight(Region.USE_COMPUTED_SIZE);
    message.setMaxHeight(Region.USE_COMPUTED_SIZE);

    final Text hearder = new Text("Help Page");
    hearder.setStyle("-fx-font-weight: bold");
    hearder.setStyle("-fx-font-size: 60");
    hearder.setStyle("-fx-font-family: Roboto");
    hearder.setStyle("-fx-alignment: center");
    message.setHeading(hearder);

    final Text body = new Text("Room number: This is the room number of the patient you are delivering flowers to.\n" +
            "Delivery choice is how you want the flowers delivered. \n" +
            "There are three options for the flowers and three options for the vase color.\n" +
            "Click on the option you would like.\n" +
            "Personalized note is not necessary to get flowers delivered, but is an option for if you want to leave a message.");

    body.setStyle("-fx-font-size: 40");
    body.setStyle("-fx-font-family: Roboto");
    body.setStyle("-fx-alignment: center");

    message.setBody(body);


    JFXDialog dialog = new JFXDialog(dialogPane, message,JFXDialog.DialogTransition.CENTER);

    JFXButton cont = new JFXButton("CONTINUE");
    cont.setOnAction(event -> {
      dialog.close();
      dialogPane.toBack();
    });

    dialog.setOnDialogClosed(event -> {
      dialogPane.toBack();
    });

    message.setActions(cont);
    dialog.show();

  }
  private void loadHelpDialogue() {
    dialogPane.toFront();
    loadDialog();
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
