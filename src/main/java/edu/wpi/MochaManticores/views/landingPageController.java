package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import edu.wpi.MochaManticores.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class landingPageController extends SceneController {


  @FXML
  public ImageView backgroundIMG;

  @FXML
  public GridPane contentPane;

  @FXML
  private StackPane dialogPane;


  @FXML private void initialize(){
      double height = super.getHeight();
      double width = super.getWidth();
      backgroundIMG.setFitWidth(width);
      backgroundIMG.setFitHeight(height);
      contentPane.setPrefSize(width,height);

    backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
    backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());

      dialogPane.toBack();
  }

  public void loadDialog(){
    //TODO Center the text of it.


    //dialogPane.setDisable(false);
    JFXDialogLayout message = new JFXDialogLayout();
    message.setMaxHeight(Region.USE_PREF_SIZE);
    message.setMaxHeight(Region.USE_PREF_SIZE);

    final Text hearder = new Text("Are you sure it is an emergency?\n Do this");
    hearder.setStyle("-fx-font-weight: bold");
    hearder.setStyle("-fx-font-size: 30");
    hearder.setStyle("-fx-font-family: Roboto");
    hearder.setStyle("-fx-alignment: center");
    message.setHeading(hearder);

    final Text body = new Text("If it is, please, click yes to proceed with the form");
    body.setStyle("-fx-font-size: 15");
    body.setStyle("-fx-font-family: Roboto");
    body.setStyle("-fx-alignment: center");
    message.setHeading(hearder);

    message.setBody(body);
    JFXDialog dialog = new JFXDialog(dialogPane, message,JFXDialog.DialogTransition.CENTER);
    JFXButton yes = new JFXButton("YES");
    yes.setOnAction(event -> {
      dialogPane.toBack();
      dialog.close();
      changeSceneTo("EmergencyForm");
    });

    JFXButton no = new JFXButton("NO");
    no.setOnAction(event -> {
      dialog.close();
      dialogPane.toBack();
    });

    dialog.setOnDialogClosed(event -> {
      dialogPane.toBack();
    });

    message.setActions(yes, no);
    dialog.show();

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
    //dialogPane.setVisible(true);
    dialogPane.toFront();
    loadDialog();
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

  public void goBack(){
    back();
  }

  public void exitApp(ActionEvent e){
    super.exitApp(e);
  }

}
