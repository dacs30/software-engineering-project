package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Objects;

public class landingPageController extends SceneController {


  @FXML
  public ImageView backgroundIMG;

  @FXML
  public GridPane contentPane;

  @FXML
  private StackPane dialogPane;

  @FXML
  private Pane scenesPane;

  @FXML
  private Button btn;


  public void initialize(){

      double height = super.getHeight();
      double width = super.getWidth();
//      backgroundIMG.setFitWidth(width);
//      backgroundIMG.setFitHeight(height);
//      contentPane.setPrefSize(width,height);
//
//    backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
//    backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());
//
//      dialogPane.toBack();


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

  public void renderMenu(MouseEvent mouseEvent) throws IOException {
    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("edu/wpi/MochaManticores/fxml/loadingPage.fxml")));

    scenesPane.getChildren().add(root);
  }
}
