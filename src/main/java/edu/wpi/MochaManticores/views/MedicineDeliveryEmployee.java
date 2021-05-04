package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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
        DatabaseManager.getEmployeeNames().forEach(s -> {
            items.add(s.substring(s.indexOf(" ")));
        });
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


    public void loadHelpDialogue(MouseEvent mouseEvent) {
    }

    public void helpButton(MouseEvent mouseEvent) {
    }
}
