package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.Services.MedicineRequest;
import edu.wpi.MochaManticores.Services.ServiceRequest;
import edu.wpi.MochaManticores.Services.ServiceRequestType;
import edu.wpi.MochaManticores.database.DatabaseManager;
import edu.wpi.MochaManticores.database.sel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.Arrays;
import java.util.LinkedList;

public class MedicineDeliveryEmployee {

    public class md extends RecursiveTreeObject<md>{
        edu.wpi.MochaManticores.Services.MedicineRequest ref;
        StringProperty typeMedicine;
        StringProperty currentFeeling;
        StringProperty allergies;
        StringProperty patientRoom;
        @FXML
        JFXComboBox employeeAssigned;
        boolean completed;
        LinkedList<String> fields;

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        public md(edu.wpi.MochaManticores.Services.ServiceRequest ref){
            this.ref = (edu.wpi.MochaManticores.Services.MedicineRequest) ref;
            typeMedicine = new SimpleStringProperty(this.ref.getTypeMedicine());
            currentFeeling = new SimpleStringProperty(this.ref.getCurrentFeeling());
            allergies = new SimpleStringProperty(this.ref.getAllergies());
            patientRoom = new SimpleStringProperty(this.ref.getPatientRoom());
            completed = this.ref.getCompleted();
            fields = new LinkedList<>(Arrays.asList(
                    typeMedicine.get(),
                    currentFeeling.get(),
                    allergies.get(),
                    patientRoom.get()));
        }

        public JFXComboBox getEmployeeAssigned() {
            return (JFXComboBox)employeeAssigned.getValue();
        }

        public JFXComboBox employeeAssignedProperty() {
            return employeeAssigned;
        }

        public String isCompleted() {
            if(completed){
                return "Completed";
            }else{
                return "Open";
            }
        }

        public MedicineRequest getRef() {
            return ref;
        }

        public String getTypeMedicine() {
            return typeMedicine.get();
        }

        public StringProperty typeMedicineProperty() {
            return typeMedicine;
        }

        public String getCurrentFeeling() {
            return currentFeeling.get();
        }

        public StringProperty currentFeelingProperty() {
            return currentFeeling;
        }

        public String getAllergies() {
            return allergies.get();
        }

        public StringProperty allergiesProperty() {
            return allergies;
        }

        public String getPatientRoom() {
            return patientRoom.get();
        }

        public StringProperty patientRoomProperty() {
            return patientRoom;
        }

        public LinkedList<String> getFields() {
            return fields;
        }

    }

    public TableColumn<md, String> typeMedicineColumn;
    public TableColumn<md, String> currentFeelingColumn;
    public TableColumn<md, String> allergiesColumn;
    public TableColumn<md, String> patientRoomColumn;
    public TableColumn<md, JFXComboBox> employeeColumn;
    public TableColumn<md, String> completedColumn;

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
    private TableView<MedicineDeliveryEmployee.md> medicineDeliveryTable;

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

        currentFeelingColumn = new TableColumn<md, String>("Feeling");
        currentFeelingColumn.setMinWidth(100);
        currentFeelingColumn.setCellValueFactory(new PropertyValueFactory<md, String>("currentFeeling"));

        allergiesColumn = new TableColumn<md, String>("Allergies");
        allergiesColumn.setMinWidth(100);
        allergiesColumn.setCellValueFactory(new PropertyValueFactory<md, String>("allergies"));

        patientRoomColumn = new TableColumn<md, String>("Room");
        patientRoomColumn.setMinWidth(100);
        patientRoomColumn.setCellValueFactory(new PropertyValueFactory<md, String>("patientRoom"));

        employeeColumn = new TableColumn<md, JFXComboBox>("Assigned To");
        employeeColumn.setMinWidth(100);
        employeeColumn.setCellValueFactory(new PropertyValueFactory<>("employeeAssigned"));

        completedColumn = new TableColumn<md, String>("Status");
        completedColumn.setMinWidth(100);
        completedColumn.setCellValueFactory(new PropertyValueFactory<md, String>("completed"));

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

    private ObservableList<md> buildTable(String searchTerm){
        ObservableList<md> tableRow = FXCollections.observableArrayList();
        LinkedList<ServiceRequest> requests = DatabaseManager.getServiceMap().getServiceRequestsForType(ServiceRequestType.Medicine);

        for(ServiceRequest s : requests){
            md mdToAdd = new md(s);
            for (int i = 0; i < mdToAdd.getFields().size(); i++) {
                if(mdToAdd.getFields().get(i).toLowerCase().equals(searchTerm) || searchTerm.equals("")){
                    tableRow.add(mdToAdd);
                    break;
                }
            }
        }
        medicineDeliveryTable.setItems(tableRow);
        medicineDeliveryTable.getColumns().setAll(
                typeMedicineColumn,
                currentFeelingColumn,
                allergiesColumn,
                patientRoomColumn,
                employeeColumn,
                completedColumn);
        return tableRow;

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
        DatabaseManager.addRequest(s, new edu.wpi.MochaManticores.Services.MedicineRequest("",empBox.getText(),false,medicineCombo.getSelectionModel().getSelectedItem(),feel.toString(),allergies.getText(),patientRoom.getText()));


    }

    public void changeToRequest(ActionEvent actionEvent) {
        requestPage.setVisible(true);
        managerPage.setVisible(false);
    }

    public void changeManagerTable(ActionEvent actionEvent) {
        buildTable("");
        requestPage.setVisible(false);
        managerPage.setVisible(true);
    }

    public void completeService(ActionEvent e){
        md selection = medicineDeliveryTable.getSelectionModel().getSelectedItem();
        selection.setCompleted(true);
        selection.getRef().setCompleted(true);
        buildTable("");
    }

    public void loadHelpDialogue(MouseEvent mouseEvent) {
    }

    public void helpButton(MouseEvent mouseEvent) {
    }
}
