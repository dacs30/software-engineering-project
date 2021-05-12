package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.database.DatabaseManager;
import edu.wpi.MochaManticores.database.sel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class FoodDeliveryEmployee extends SceneController{


    @FXML
    private GridPane contentGrid;

    @FXML
    private JFXComboBox<String> dietaryPreferences;

    @FXML
    private JFXTextField allergyText;

    @FXML
    private JFXTextField empBox;

    @FXML
    private StackPane dialogPane;

    @FXML
    private ImageView backgroundIMG;

    @FXML
    private GridPane requestPage;

    @FXML
    private GridPane managerPage;


    @FXML
    private JFXComboBox employeeAssigned;

    private void createFilterListener(JFXComboBox comboBox) {

        // Create the listener to filter the list as user enters search terms
        FilteredList<String> filteredList = new FilteredList<>(comboBox.getItems());

        // Add listener to our ComboBox textfield to filter the list
        comboBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            comboBox.show();
            filteredList.setPredicate(item -> {


                // If the TextField is empty, return all items in the original list
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Check if the search term is contained anywhere in our list
                return item.toLowerCase().contains(newValue.toLowerCase().trim());

            });
        });

        // Finally, let's add the filtered list to our ComboBox
        comboBox.setItems(filteredList);

    }
    @FXML
    private JFXComboBox<String> foodMenu;


    public Boolean isSetToCreateRequest;

    @FXML
    public void initialize(){
        double height = App.getPrimaryStage().getScene().getHeight();
        double width = App.getPrimaryStage().getScene().getWidth();
        backgroundIMG.setFitHeight(height);
        backgroundIMG.setFitWidth(width);

        backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
        backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());

        this.employeeAssigned.setEditable(true);
        //fromLocation.setOnKeyTyped(new AutoCompleteComboBoxListener<>(fromLocation));
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(DatabaseManager.getEmpManager().getEmployeeNames());
        employeeAssigned.setItems(items);
        createFilterListener(employeeAssigned);


        dietaryPreferences.getItems().clear();
        dietaryPreferences.getItems().addAll("Vegan", "Vegetarian", "Gluten Free", "No Dietary Preferences");

        foodMenu.getItems().clear();
        foodMenu.getItems().addAll("Menu 0", "Menu 1", "Menu 2", "Menu 3");


        managerPage.setVisible(false);
        requestPage.setVisible(true);
        isSetToCreateRequest = false;
    }

    public void changeToRequest(ActionEvent actionEvent) {
        requestPage.setVisible(true);
        managerPage.setVisible(false);
        requestPage.toFront();
    }



    public void submitForm() {
        if(!dietaryPreferences.getSelectionModel().isEmpty() && !allergyText.getText().isEmpty() &&
                !foodMenu.getSelectionModel().isEmpty() && !employeeAssigned.getSelectionModel().isEmpty()) {
            sel s = sel.FoodDelivery;
            // changeSceneTo(e, "mainMenu");
            DatabaseManager.addRequest(s,
                    new edu.wpi.MochaManticores.Services.FoodDelivery(
                            "", employeeAssigned.getEditor().getText(), false, dietaryPreferences.getSelectionModel().getSelectedItem(),
                            allergyText.getText(), foodMenu.getSelectionModel().getSelectedItem()));
            dialogPane.setVisible(true);
            loadDialog();
        } else if (dietaryPreferences.getSelectionModel().isEmpty()){
            RequiredFieldValidator missingInput = new RequiredFieldValidator();
            dietaryPreferences.getValidators().add(missingInput);
            missingInput.setMessage("Dietary Preference requires an input");
            dietaryPreferences.validate();
        } else if (foodMenu.getSelectionModel().isEmpty()) {
            RequiredFieldValidator missingInput = new RequiredFieldValidator();
            foodMenu.getValidators().add(missingInput);
            missingInput.setMessage("Food menu requires an input");
            foodMenu.validate();
        }else if (allergyText.getText().isEmpty()){
            RequiredFieldValidator missingInput = new RequiredFieldValidator();
            allergyText.getValidators().add(missingInput);
            missingInput.setMessage("Allergies field requires an input");
            allergyText.validate();
        } else if(employeeAssigned.getSelectionModel().isEmpty()){
            RequiredFieldValidator missingInput = new RequiredFieldValidator();
            employeeAssigned.getValidators().add(missingInput);
            missingInput.setMessage("Please assign an employee");
            employeeAssigned.validate();
        }

    }

    public void helpButton(MouseEvent mouseEvent) {
        loadHelpDialog();
    }
    public void loadHelpDialog() {


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
                "Allergies: Indicate any allergies you may have with food or beverages.\n" +
                "Assign to Employee: Select an employee from the provided dropdown menu.");

        body.setStyle("-fx-font-size: 40");
        body.setStyle("-fx-font-family: Roboto");
        body.setStyle("-fx-alignment: center");

        message.setBody(body);


        JFXDialog dialog = new JFXDialog(dialogPane, message, JFXDialog.DialogTransition.CENTER);

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

        final Text body = new Text("Your request has been submitted for the patient.");
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

    public void changeManagerTable(ActionEvent actionEvent) {
        requestPage.setVisible(false);
        managerPage.setVisible(true);
        managerPage.toFront();
    }
}
