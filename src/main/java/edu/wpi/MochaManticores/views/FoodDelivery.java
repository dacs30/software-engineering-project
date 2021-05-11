package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.database.DatabaseManager;
import edu.wpi.MochaManticores.database.sel;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class FoodDelivery extends SceneController {

    @FXML
    private JFXComboBox<String> dietaryPreferences;

    @FXML
    private JFXTextField allergyText;

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
    private JFXComboBox employeeAssigned;


    @FXML
    private void goBack() {
        back();
    }

    private void loadHelpDialog(){
        dialogPane.toFront();
        dialogPane.setDisable(false);
        JFXDialogLayout message = new JFXDialogLayout();
        message.setMaxHeight(Region.USE_COMPUTED_SIZE);
        message.setMaxHeight(Region.USE_COMPUTED_SIZE);

        final Text hearder = new Text("Help Page");
        hearder.setStyle("-fx-font-weight: bold");
        hearder.setStyle("-fx-font-size: 60");
        hearder.setStyle("-fx-font-family: Roboto");
        hearder.setStyle("-fx-alignment: center");
        message.setHeading(hearder);

        final Text body = new Text("Dietary preferences: Select an option from the dropdown of the food preferences.\n" +
                "Menu: Select from the provided dropdown one of the menus.\n" +
                "Allergies: Indicate any allergies you may have with food or beverages.");

        body.setStyle("-fx-font-size: 40");
        body.setStyle("-fx-font-family: Roboto");
        body.setStyle("-fx-alignment: center");

        message.setBody(body);


        JFXDialog dialog = new JFXDialog(dialogPane, message,JFXDialog.DialogTransition.CENTER);

        JFXButton cont = new JFXButton("Continue");
        cont.setStyle("-fx-font-size: 15");
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



    public void initialize() {
        double height = App.getPrimaryStage().getScene().getHeight();
        double width = App.getPrimaryStage().getScene().getWidth();
        backgroundIMG.setFitHeight(height);
        backgroundIMG.setFitWidth(width);
        contentGrid.setPrefSize(width, height);

        backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
        backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());

        //System.out.println(width);

        dietaryPreferences.getItems().clear();
        dietaryPreferences.getItems().addAll("Vegan", "Vegetarian", "Gluten Free", "No Dietary Preferences");

        // TODO Condition of menus depending of the dietary preference
        foodMenu.getItems().clear();
        foodMenu.getItems().addAll("Menu 0", "Menu 1", "Menu 2", "Menu 3");


        //dialogPane.setDisable(false);
    }

    public void submitForm() {
        if(!dietaryPreferences.getItems().isEmpty() && !allergyText.getText().isEmpty() &&
                !foodMenu.getItems().isEmpty()) {
            sel s = sel.FoodDelivery;
            // changeSceneTo(e, "mainMenu");
            DatabaseManager.addRequest(s,
                    new edu.wpi.MochaManticores.Services.FoodDelivery(
                            "", "", false, dietaryPreferences.getSelectionModel().getSelectedItem(),
                            allergyText.getText(), foodMenu.getSelectionModel().getSelectedItem()));

        } else if (dietaryPreferences.getItems().isEmpty()){
            RequiredFieldValidator missingInput = new RequiredFieldValidator();
            dietaryPreferences.getValidators().add(missingInput);
            missingInput.setMessage("Dietary Preference requires an input");
            dietaryPreferences.validate();
        } else if (allergyText.getText().isEmpty()){
            RequiredFieldValidator missingInput = new RequiredFieldValidator();
            allergyText.getValidators().add(missingInput);
            missingInput.setMessage("Allergies field requires an input");
            allergyText.validate();
        } else if (foodMenu.getItems().isEmpty()) {
            RequiredFieldValidator missingInput = new RequiredFieldValidator();
            foodMenu.getValidators().add(missingInput);
            missingInput.setMessage("Food menu requires an input");
            foodMenu.validate();
        }
        //if we want a validator for assign to employee

        /*else if(employeeAssigned.getItems().isEmpty()){
            RequiredFieldValidator missingInput = new RequiredFieldValidator();
            employeeAssigned.getValidators().add(missingInput);
            missingInput.setMessage("Employee must be assigned");

        }*/
        dialogPane.setVisible(true);
        loadDialog();
    }

    public void loadDialog() {
        //TODO Center the text of it.

        dialogPane.toFront();
        dialogPane.setDisable(false);
        JFXDialogLayout message = new JFXDialogLayout();
        message.setMaxHeight(Region.USE_PREF_SIZE);
        message.setMaxHeight(Region.USE_PREF_SIZE);

        final Text hearder = new Text("Submitted request");
        hearder.setStyle("-fx-font-weight: bold");
        hearder.setStyle("-fx-font-size: 30");
        hearder.setStyle("-fx-font-family: Roboto");
        hearder.setStyle("-fx-alignment: center");
        message.setHeading(hearder);

        final Text body = new Text("Your request has been submitted and will be reviewed by a staff member.");
        body.setStyle("-fx-font-size: 15");
        body.setStyle("-fx-font-family: Roboto");
        body.setStyle("-fx-alignment: center");
        message.setHeading(hearder);

        message.setBody(body);
        JFXDialog dialog = new JFXDialog(dialogPane, message, JFXDialog.DialogTransition.CENTER);
        JFXButton exit = new JFXButton("Done");
        exit.setStyle("-fx-font-size: 15");
        exit.setOnAction(event -> {
            changeSceneTo("landingPage");
        });
        dialog.setOnDialogClosed(event -> {
            dialogPane.setDisable(true);
            dialogPane.toBack();
        });
        message.setActions(exit);
        dialog.show();

    }

    public void helpButton() {
        loadHelpDialog();
    }
}
