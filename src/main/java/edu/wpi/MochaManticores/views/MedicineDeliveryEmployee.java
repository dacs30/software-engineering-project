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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class MedicineDeliveryEmployee {


    @FXML
    private GridPane contentGrid;

    @FXML
    private ImageView backgroundIMG;

    @FXML
    private GridPane requestPage;

    @FXML
    private GridPane managerPage;

    @FXML
    private JFXComboBox<String> medicineCombo;


    @FXML
    private JFXCheckBox checkBox0, checkBox1, checkBox2, checkBox3, checkBox4, checkBox5;

    @FXML
    private JFXTextField allergies;

    @FXML
    private JFXTextField patientRoom;

    @FXML
    private JFXTextField empBox;

    @FXML
    private JFXComboBox employeeAssigned;

    @FXML
    private JFXButton submitButton;

    @FXML
    private ImageView helpButton;

    @FXML
    private StackPane dialogPane;

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

        double height = App.getPrimaryStage().getScene().getHeight();
        double width = App.getPrimaryStage().getScene().getWidth();
        backgroundIMG.setFitHeight(height);
        backgroundIMG.setFitWidth(width);
        //contentGrid.setPrefSize(width, height);

        backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
        backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());

        this.employeeAssigned.setEditable(true);
        //fromLocation.setOnKeyTyped(new AutoCompleteComboBoxListener<>(fromLocation));
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(DatabaseManager.getEmpManager().getEmployeeNames());
        employeeAssigned.setItems(items);
        createFilterListener(employeeAssigned);

        medicineCombo.getItems().clear();
        medicineCombo.getItems().addAll("Advil", "Tylenol", "Aspirin");


        //buildTable("");

        managerPage.setVisible(false);
        requestPage.setVisible(true);
    }


    /**
     * Helper function to the submit funtion
     * @return true if at least one check box is marked
     */
    public boolean checkBoxesAreFilled(){
        return (checkBox0.isSelected() || checkBox1.isSelected() ||
                checkBox2.isSelected() || checkBox3.isSelected() ||
                checkBox4.isSelected() || checkBox5.isSelected());
    }

    public void submitForm(ActionEvent actionEvent) {
        StringBuilder feel = new StringBuilder();
        if(checkBox0.isSelected()){
            feel.append("Muscle pain,");
        }
        if(checkBox1.isSelected()){
            feel.append("Nausea,");
        }
        if(checkBox2.isSelected()){
            feel.append("Headache,");
        }
        if(checkBox3.isSelected()){
            feel.append("Other pain,");
        }
        if(checkBox4.isSelected()){
            feel.append("Some pain,");
        }
        if(checkBox5.isSelected()){
            feel.append("More pain");
        }
        //feel.setLength(feel.length()-1);
        // changeSceneTo(e, "mainMenu");
        if (checkBoxesAreFilled() && !medicineCombo.getSelectionModel().isEmpty() && !patientRoom.getText().isEmpty()){
//            ServiceRequest.addRequest(new edu.wpi.MochaManticores.Services.MedicineDelivery(App.getClearenceLevel()==1,
//                    false,
//                    0,
//                    typeOfMedicineComboBx.getSelectionModel().getSelectedItem(),
//                    allergies.getText(),
//                    patientRoom.getText()), ServiceMap.FoodDelivery);
            System.out.println("send to database");
        } else if (patientRoom.getText().isEmpty()){
            // if patient room is empty, generate error
            RequiredFieldValidator missingInput = new RequiredFieldValidator();
            patientRoom.getValidators().add(missingInput);
            missingInput.setMessage("Room number must be filled");
            patientRoom.validate();
        } else if (medicineCombo.getSelectionModel().isEmpty()){
            RequiredFieldValidator missingInput = new RequiredFieldValidator();
            medicineCombo.getValidators().add(missingInput);
            missingInput.setMessage("Type of medicine must be selected");
            medicineCombo.validate();
        }
        loadSubmitDialogue();
        sel s = sel.Medicine;
        DatabaseManager.addRequest(s, new edu.wpi.MochaManticores.Services.MedicineRequest("", employeeAssigned.getEditor().getText(),false,medicineCombo.getSelectionModel().getSelectedItem(),feel.toString(),allergies.getText(),patientRoom.getText()));


    }



    public void changeToRequest(ActionEvent actionEvent) {
        requestPage.setVisible(true);
        managerPage.setVisible(false);
        requestPage.toFront();
    }

    public void changeManagerTable(ActionEvent actionEvent) {
        requestPage.setVisible(false);
        managerPage.setVisible(true);
        managerPage.toFront();
    }

    public void loadHelpDialogue(){
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

        final Text body = new Text("Type of Medicine: Select a medicine from the provided dropdown.\n" +
                "Please check of one or more boxes to describe the pain you are experiencing.\n" +
                "Allergies: Type out any known allergies to any medication that you know of.\n" +
                "Patient Room: The current room in the hospital that the patient resides in.\n"+
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

    public void loadSubmitDialogue(){
        dialogPane.toFront();
        JFXDialogLayout message = new JFXDialogLayout();
        message.setMaxHeight(Region.USE_COMPUTED_SIZE);
        message.setMaxHeight(Region.USE_COMPUTED_SIZE);

        final Text hearder = new Text("Submitted request");
        hearder.setStyle("-fx-font-weight: bold");
        hearder.setStyle("-fx-font-size: 60");
        hearder.setStyle("-fx-font-family: Roboto");
        hearder.setStyle("-fx-alignment: center");
        message.setHeading(hearder);

        final Text body = new Text("Your request has been submitted for the patient.");
        body.setStyle("-fx-font-size: 40");
        body.setStyle("-fx-font-family: Roboto");
        body.setStyle("-fx-alignment: center");

        message.setBody(body);


        JFXDialog dialog = new JFXDialog(dialogPane, message,JFXDialog.DialogTransition.CENTER);

        JFXButton cont = new JFXButton("Done");
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

    public void helpButton(ActionEvent actionEvent) {
        loadHelpDialogue();
    }
}
