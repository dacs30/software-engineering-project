package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import edu.wpi.MochaManticores.App;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import javafx.scene.input.MouseEvent;


import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Objects;

public class landingPageController extends SceneController {

  @FXML
  private GridPane sidePanel;

  @FXML
  private StackPane dialogPane;

  @FXML
  private AnchorPane scenesPane;

  @FXML
  private HBox menuSidePane;

  @FXML
  private HBox foodDeliverySidePanel;

  @FXML
  private HBox medicineDeliverySidePanel;

  @FXML
  private HBox internalTransportationSidePanel;

  @FXML
  private  HBox externalTransportationSidePanel;

  @FXML
  private HBox shoppingSideMenu;

  @FXML
  private HBox sanitationSideMenu;

  @FXML
  private HBox mapSidePane;

  @FXML
  private HBox emergencySidePanel;

  @FXML
  private Label greetingLabel;

  HBox currentVbox;

  public void initialize() throws IOException {

      sidePanel.toFront();

      double height = super.getHeight();
      double width = super.getWidth();
      //contentPane.setPrefSize(width,height);

      Parent root = null;
      greetingLabel.setText("Hello, " + App.getCurrentUsername().toUpperCase());

      if(App.getClearenceLevel() == 1){
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/wpi/MochaManticores/fxml/employeeHomePage.fxml")));
      } else {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/wpi/MochaManticores/fxml/homePage2.fxml")));
      }

      scenesPane.getChildren().add(root);

      currentVbox = menuSidePane;

      menuSidePane.setStyle("-fx-background-radius: 20;");
      menuSidePane.setStyle("-fx-background-color: rgba(15,75,145,0.29);");

      dialogPane.toBack();

  }

  //emergencyDialog
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
      // change the colors of the old selected page back to the default
      currentVbox.setStyle("-fx-background-radius: 0;");
      currentVbox.setStyle("-fx-background-color:  #E9E9E9");
      // send the dialog to back
      dialogPane.toBack();
      // close the dialog
      dialog.close();
      // remove all the children from the scene panes to render the emergency form
      scenesPane.getChildren().removeAll(scenesPane.getChildren());
      Parent root = null;
      try {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/wpi/MochaManticores/fxml/EmergencyForm.fxml")));
      } catch (IOException e) {
        e.printStackTrace();
      }
      scenesPane.getChildren().add(root);
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
    dialogPane.toFront();
    dialog.show();

  }

  public void renderMenu(MouseEvent mouseEvent) throws IOException {
    scenesPane.getChildren().removeAll(scenesPane.getChildren());

    Parent root = null;
    // if it is an employee the page page is different
    if(App.getClearenceLevel() == 1){
      root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/wpi/MochaManticores/fxml/employeeHomePage.fxml")));
    } else {
      root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/wpi/MochaManticores/fxml/homePage2.fxml")));
    }

    currentVbox.setStyle("-fx-background-radius: 0;");
    currentVbox.setStyle("-fx-background-color:  #E9E9E9");

    currentVbox = menuSidePane;

    menuSidePane.setStyle("-fx-background-radius: 20;");
    menuSidePane.setStyle("-fx-background-color: rgba(15,75,145,0.29);");

    scenesPane.getChildren().add(root);
  }

  public void renderFoodDelivery(MouseEvent mouseEvent) throws IOException {
    // removes the children so you don't end up with weird scenes one over the other
    scenesPane.getChildren().removeAll(scenesPane.getChildren());

    // sets parent to be the file to be loaded
    // if it is an employee the page page is different
    Parent root = null;
    if(App.getClearenceLevel() == 1){
      root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/wpi/MochaManticores/fxml/foodDeliveryEmployee.fxml")));
    } else {
      root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/wpi/MochaManticores/fxml/foodDelivery.fxml")));
    }

    // change the colors of the old selected page back to the default
    currentVbox.setStyle("-fx-background-radius: 0;");
    currentVbox.setStyle("-fx-background-color:  #E9E9E9");

    // changes the currentbox
    currentVbox = foodDeliverySidePanel;

    // gives the selected properties for the new selected page
    foodDeliverySidePanel.setStyle("-fx-background-radius: 20;");
    foodDeliverySidePanel.setStyle("-fx-background-color: rgba(15,75,145,0.29);");

    // adds the selected page to the scenesPane so it can be displayed
    scenesPane.getChildren().add(root);
  }

  public void renderMedicineDelivery(MouseEvent mouseEvent) throws IOException {
    // removes the children so you don't end up with weird scenes one over the other
    scenesPane.getChildren().removeAll(scenesPane.getChildren());

    // sets parent to be the file to be loaded
    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/wpi/MochaManticores/fxml/medicineDelivery.fxml")));

    // change the colors of the old selected page back to the default
    currentVbox.setStyle("-fx-background-radius: 0;");
    currentVbox.setStyle("-fx-background-color:  #E9E9E9");

    // changes the currentbox
    currentVbox = medicineDeliverySidePanel;

    // gives the selected properties for the new selected page
    medicineDeliverySidePanel.setStyle("-fx-background-radius: 20;");
    medicineDeliverySidePanel.setStyle("-fx-background-color: rgba(15,75,145,0.29);");

    // adds the selected page to the scenesPane so it can be displayed
    scenesPane.getChildren().add(root);
  }

  public void renderInternalTransportation(MouseEvent mouseEvent) throws IOException {
    // removes the children so you don't end up with weird scenes one over the other
    scenesPane.getChildren().removeAll(scenesPane.getChildren());

    // sets parent to be the file to be loaded
    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/wpi/MochaManticores/fxml/internalTransportation.fxml")));

    // change the colors of the old selected page back to the default
    currentVbox.setStyle("-fx-background-radius: 0;");
    currentVbox.setStyle("-fx-background-color:  #E9E9E9");

    // changes the currentbox
    currentVbox = internalTransportationSidePanel;

    // gives the selected properties for the new selected page
    internalTransportationSidePanel.setStyle("-fx-background-radius: 20;");
    internalTransportationSidePanel.setStyle("-fx-background-color: rgba(15,75,145,0.29);");

    // adds the selected page to the scenesPane so it can be displayed
    scenesPane.getChildren().add(root);

  }

  public void renderExternalTransportation(MouseEvent mouseEvent) throws IOException {
    // removes the children so you don't end up with weird scenes one over the other
    scenesPane.getChildren().removeAll(scenesPane.getChildren());

    // sets parent to be the file to be loaded
    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/wpi/MochaManticores/fxml/extTransportation.fxml")));

    // change the colors of the old selected page back to the default
    currentVbox.setStyle("-fx-background-radius: 0;");
    currentVbox.setStyle("-fx-background-color:  #E9E9E9");

    // changes the currentbox
    currentVbox = externalTransportationSidePanel;

    // gives the selected properties for the new selected page
    externalTransportationSidePanel.setStyle("-fx-background-radius: 20;");
    externalTransportationSidePanel.setStyle("-fx-background-color: rgba(15,75,145,0.29);");

    // adds the selected page to the scenesPane so it can be displayed
    scenesPane.getChildren().add(root);
  }

  //TODO Fix to actually render shopping menu
  public void renderShopping(MouseEvent mouseEvent) throws IOException {
    // removes the children so you don't end up with weird scenes one over the other
    scenesPane.getChildren().removeAll(scenesPane.getChildren());

    // sets parent to be the file to be loaded
    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/wpi/MochaManticores/fxml/floralScene.fxml")));

    // change the colors of the old selected page back to the default
    currentVbox.setStyle("-fx-background-radius: 0;");
    currentVbox.setStyle("-fx-background-color:  #E9E9E9");

    // changes the currentbox
    currentVbox = shoppingSideMenu;

    // gives the selected properties for the new selected page
    shoppingSideMenu.setStyle("-fx-background-radius: 20;");
    shoppingSideMenu.setStyle("-fx-background-color: rgba(15,75,145,0.29);");

    // adds the selected page to the scenesPane so it can be displayed
    scenesPane.getChildren().add(root);
  }

  public void renderSanitationServices(MouseEvent mouseEvent) throws IOException {

    // removes the children so you don't end up with weird scenes one over the other
    scenesPane.getChildren().removeAll(scenesPane.getChildren());

    // sets parent to be the file to be loaded
    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/wpi/MochaManticores/fxml/sanitationService.fxml")));

    // change the colors of the old selected page back to the default
    currentVbox.setStyle("-fx-background-radius: 0;");
    currentVbox.setStyle("-fx-background-color:  #E9E9E9");

    // changes the currentbox
    currentVbox = sanitationSideMenu;

    // gives the selected properties for the new selected page
    sanitationSideMenu.setStyle("-fx-background-radius: 20;");
    sanitationSideMenu.setStyle("-fx-background-color: rgba(15,75,145,0.29);");

    // adds the selected page to the scenesPane so it can be displayed
    scenesPane.getChildren().add(root);
  }

  public void openEmergencyDIalog(MouseEvent mouseEvent) {
    loadDialog();
  }

  public void renderMapEditor(MouseEvent mouseEvent) throws IOException {
    // removes the children so you don't end up with weird scenes one over the other
    scenesPane.getChildren().removeAll(scenesPane.getChildren());

    Parent root = null;

    // if it is an employee the page page is different
    if(App.getClearenceLevel() == 1){
      root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/wpi/MochaManticores/fxml/employeeMapPage.fxml")));
    } else {
      root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/wpi/MochaManticores/fxml/mapEditor.fxml")));
    }

    // change the colors of the old selected page back to the default
    currentVbox.setStyle("-fx-background-radius: 0;");
    currentVbox.setStyle("-fx-background-color:  #E9E9E9");

    // changes the currentbox
    currentVbox = mapSidePane;

    // gives the selected properties for the new selected page
    mapSidePane.setStyle("-fx-background-radius: 20;");
    mapSidePane.setStyle("-fx-background-color: rgba(15,75,145,0.29);");

    // adds the selected page to the scenesPane so it can be displayed
    scenesPane.getChildren().add(root);
  }


  public void logOut(ActionEvent actionEvent) {
    App.setCurrentUsername(null);
    changeSceneTo("loginPage");
  }

  public void userSettings(ActionEvent actionEvent) {
    loadErrorDialog(dialogPane, "Coming in a future iteration!");
  }
}
