package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.*;
import edu.wpi.MochaManticores.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;

public class FoodDelivery extends SceneController {

    @FXML
    private JFXComboBox<String> dietaryPreferences;

    @FXML
    private JFXComboBox<String> foodMenu;

    @FXML
    private StackPane dialogPane;

    @FXML
    private GridPane contentGrid;

    @FXML
    private ImageView backgroundIMG;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXTextField empBox;


    @FXML
    private void goBack(ActionEvent e) {
        back();
    }

    public void helpButton(ActionEvent actionEvent){loadFullHelpDialogue();}

    /**
     * Loads the help dialog for the user to complete the request
     *
     * @return void
     */

    private void loadHelpDialog(){
        JFXDialogLayout message = new JFXDialogLayout();
        message.setMaxHeight(Region.USE_COMPUTED_SIZE);
        message.setMaxHeight(Region.USE_COMPUTED_SIZE);

        final Text hearder = new Text("Help Page");
        hearder.setStyle("-fx-font-weight: bold");
        hearder.setStyle("-fx-font-size: 60");
        hearder.setStyle("-fx-font-family: Roboto");
        hearder.setStyle("-fx-alignment: center");
        message.setHeading(hearder);

        final Text body = new Text("Patient room: This is the room number given to the patient by the hospital.\n" +
                "Current Room: is where the patient is currently staying until transportation out of the hospital.\n" +
                "External Room: is the location where the patient is going to be transported to\n" +
                "Transportation Method: This is a dropdown menu that you select which type of transportation the patient will take. ");

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

    private void loadFullHelpDialogue() {
        dialogPane.toFront();
        loadHelpDialog();
    }


    /**
     * Initializes a page with an image and fits the map to screen
     * then creates starting and ending nodes using the edges
     *
     * @return void
     */
    public void initialize() {
        double height = App.getPrimaryStage().getScene().getHeight();
        double width = App.getPrimaryStage().getScene().getWidth();
        backgroundIMG.setFitHeight(height);
        backgroundIMG.setFitWidth(width);
        contentGrid.setPrefSize(width, height);

        backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
        backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());

        System.out.println(width);

        dietaryPreferences.getItems().clear();
        dietaryPreferences.getItems().addAll("Vegan", "Vegetarian", "Gluten Free");

        // TODO Condition of menus depending of the dietary preference
        foodMenu.getItems().clear();
        foodMenu.getItems().addAll("Menu 0", "Menu 1", "Menu 2", "Menu 3");

        if(App.getClearenceLevel()<=0){
            empBox.setVisible(false);
        }

        //dialogPane.setDisable(false);
    }

    /**
     * Loads the submitted dialog and confirms the request was submitted
     *
     * @return void
     */

    public void submitForm(ActionEvent e) {
        // TODO Submit action
        // changeSceneTo(e, "mainMenu");
        dialogPane.setVisible(true);
        loadDialog();
    }

    /**
     * Loads the submitted dialog and confirms the request was submitted
     *
     * @return void
     */
    public void loadDialog() {
        //TODO Center the text of it.

        dialogPane.toFront();
        dialogPane.setDisable(false);
        JFXDialogLayout message = new JFXDialogLayout();
        message.setMaxHeight(Region.USE_PREF_SIZE);
        message.setMaxHeight(Region.USE_PREF_SIZE);

        final Text hearder = new Text("Submited");
        hearder.setStyle("-fx-font-weight: bold");
        hearder.setStyle("-fx-font-size: 30");
        hearder.setStyle("-fx-font-family: Roboto");
        hearder.setStyle("-fx-alignment: center");
        message.setHeading(hearder);

        final Text body = new Text("Time estimated:");
        body.setStyle("-fx-font-size: 15");
        body.setStyle("-fx-font-family: Roboto");
        body.setStyle("-fx-alignment: center");
        message.setHeading(hearder);

        message.setBody(body);
        JFXDialog dialog = new JFXDialog(dialogPane, message, JFXDialog.DialogTransition.CENTER);
        JFXButton exit = new JFXButton("OK!");
        exit.setOnAction(event -> {
            back();
        });
        dialog.setOnDialogClosed(event -> {
            dialogPane.setDisable(true);
            dialogPane.toBack();
        });
        message.setActions(exit);
        dialog.show();

    }

}
