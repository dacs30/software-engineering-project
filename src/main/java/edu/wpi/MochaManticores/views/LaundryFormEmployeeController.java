package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.Services.LaundryRequest;
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

public class LaundryFormEmployeeController extends SceneController {

    @FXML
    private GridPane managerPage;

    @FXML
    private GridPane requestPage;


    @FXML
    private JFXTextField nameField;

    @FXML
    private JFXComboBox<String> soilLevel;

    @FXML
    private JFXComboBox<String> washTemp;

    @FXML
    private JFXComboBox<String> dryTemp;

    @FXML
    private JFXTextField dryCycles;

    @FXML
    private JFXToggleButton delicateToggle;

    @FXML
    private StackPane dialogPane;
    @FXML
    private GridPane contentGrid;

    @FXML
    private ImageView backgroundIMG;

    @FXML
    private JFXComboBox employeeAssigned;

    @FXML
    private ImageView helpButton;
    @FXML
    private JFXButton submitButton;

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
    private void initialize() {
        employeeAssigned.setEditable(true);
        //fromLocation.setOnKeyTyped(new AutoCompleteComboBoxListener<>(fromLocation));
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(DatabaseManager.getEmpManager().getEmployeeNames());
        employeeAssigned.setItems(items);
        createFilterListener(employeeAssigned);

        double height = App.getPrimaryStage().getScene().getHeight();
        double width = App.getPrimaryStage().getScene().getWidth();
        backgroundIMG.setFitHeight(height);
        backgroundIMG.setFitWidth(width);
        contentGrid.setPrefSize(width, height);

        backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
        backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());


        soilLevel.getItems().clear();
        soilLevel.getItems().addAll("Light", "Medium", "Heavy");

        washTemp.getItems().clear();
        washTemp.getItems().addAll("Hot", "Warm", "Cold");

        dryTemp.getItems().clear();
        dryTemp.getItems().addAll("High", "Medium", "Low", "Delicate", "No Heat");
    }

    public void helpEvent(MouseEvent mouseEvent){loadHelpDialogue();}

    public void changeToRequest(ActionEvent actionEvent) {
        requestPage.setVisible(true);
        managerPage.setVisible(false);
        requestPage.toFront();
    }

    public void submitEvent(){
        if (!nameField.getText().isEmpty() && !soilLevel.getSelectionModel().isEmpty()
                && !washTemp.getSelectionModel().isEmpty() && !dryTemp.getSelectionModel().isEmpty()
                && !dryCycles.getText().isEmpty() && !employeeAssigned.getSelectionModel().isEmpty()) {
            LaundryRequest toAdd = new LaundryRequest("",
                    employeeAssigned.getEditor().getText(),
                    false
                    , nameField.getText(),
                    soilLevel.getSelectionModel().getSelectedItem(),
                    delicateToggle.isSelected(),
                    washTemp.getSelectionModel().getSelectedItem(),
                    dryTemp.getSelectionModel().getSelectedItem(),
                    Integer.parseInt(dryCycles.getText()));
            DatabaseManager.addRequest(sel.Laundry, toAdd);
            toAdd.send(toAdd.getRequestID());
            loadSubmitDialog();
        }else if (nameField.getText().isEmpty()) {
            // if patient room is empty, generate error
            RequiredFieldValidator missingInput = new RequiredFieldValidator();
            nameField.getValidators().add(missingInput);
            missingInput.setMessage("Patient ID must be filled out.");
            nameField.validate();
        } else if (soilLevel.getSelectionModel().isEmpty()) {
            // if patient room is empty, generate error
            RequiredFieldValidator missingInput = new RequiredFieldValidator();
            soilLevel.getValidators().add(missingInput);
            missingInput.setMessage("Please select one");
            soilLevel.validate();
        } else if (washTemp.getSelectionModel().isEmpty()) {
            // if patient room is empty, generate error
            RequiredFieldValidator missingInput = new RequiredFieldValidator();
            washTemp.getValidators().add(missingInput);
            missingInput.setMessage("Please select one");
            washTemp.validate();
        } else if (dryTemp.getSelectionModel().isEmpty()) {
            RequiredFieldValidator missingInput = new RequiredFieldValidator();
            dryTemp.getValidators().add(missingInput);
            missingInput.setMessage("Please select one");
            dryTemp.validate();
        } else if (dryCycles.getText().isEmpty()) {
            // if patient room is empty, generate error
            RequiredFieldValidator missingInput = new RequiredFieldValidator();
            dryCycles.getValidators().add(missingInput);
            missingInput.setMessage("Put 0, if no extra");
            dryCycles.validate();
        } else if (employeeAssigned.getSelectionModel().isEmpty()) {
        RequiredFieldValidator missingInput = new RequiredFieldValidator();
        employeeAssigned.getValidators().add(missingInput);
        missingInput.setMessage("Please assign an employee");
        employeeAssigned.validate();
        }
    }


    private void loadHelpDialogue(){
        dialogPane.toFront();
        JFXDialogLayout message = new JFXDialogLayout();
        message.setMaxHeight(Region.USE_COMPUTED_SIZE);
        message.setMaxHeight(Region.USE_COMPUTED_SIZE);

        final Text hearder = new Text("Help Page");
        hearder.setStyle("-fx-font-weight: bold");
        hearder.setStyle("-fx-font-size: 60");
        hearder.setStyle("-fx-font-family: Roboto");
        hearder.setStyle("-fx-alignment: center");
        message.setHeading(hearder);

        final Text body = new Text("Patient Name: Please put the name of the patient.\n" +
                                    "Soil Level: Please select the level of soil for the wash cycle.\n" +
                                    "Delicates: Please select whether the laundry is consider delicate or not.\n" +
                                    "Temperature: Please select the temperature that you will wash and dry at.\n" +
                                    "Extra Cycles: Please indicate the number of extra cycles you need.\n" +
                                    "Assign to Employee: Select an employee from the provided dropdown menu.");

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

    public void loadSubmitDialog(){
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
        JFXDialog dialog = new JFXDialog(dialogPane, message,JFXDialog.DialogTransition.CENTER);
        JFXButton ok = new JFXButton("Done");
        ok.setStyle("-fx-font-size: 15");
        ok.setOnAction(event -> {
            changeSceneTo("landingPage");
        });

        dialog.setOnDialogClosed(event -> {
            dialogPane.toBack();
            dialog.close();
        });

        message.setActions(ok);
        dialog.show();
    }



    public void changeManagerTable(ActionEvent actionEvent) {
        requestPage.setVisible(false);
        managerPage.setVisible(true);
        managerPage.toFront();
    }


}
