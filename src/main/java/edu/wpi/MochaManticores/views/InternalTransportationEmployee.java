package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.Services.ServiceRequest;
import edu.wpi.MochaManticores.Services.ServiceRequestType;
import edu.wpi.MochaManticores.database.DatabaseManager;
import edu.wpi.MochaManticores.database.sel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.LinkedList;

public class InternalTransportationEmployee extends SceneController{

    public class it extends RecursiveTreeObject<it> {

        edu.wpi.MochaManticores.Services.InternalTransportation ref;
        StringProperty patientIDTable;
        IntegerProperty numStaffNeededTable;
        StringProperty destinationTable;
        StringProperty transportationMethodsTable;
        @FXML
        JFXComboBox employeeAssigned;
        LinkedList<String> fields;


        public it(edu.wpi.MochaManticores.Services.ServiceRequest ref){
            this.ref = (edu.wpi.MochaManticores.Services.InternalTransportation) ref;
            patientIDTable = new SimpleStringProperty(this.ref.getPatientID());
            numStaffNeededTable = new SimpleIntegerProperty(this.ref.getNumStaffNeeded());
            destinationTable = new SimpleStringProperty(this.ref.getDestination());
            transportationMethodsTable = new SimpleStringProperty(this.ref.getTransportationMethod());
            fields = new LinkedList<>(Arrays.asList(patientIDTable.get(), Integer.toString(numStaffNeededTable.get()),
                    destinationTable.get(),transportationMethodsTable.get()));
        }

        public LinkedList<String> getFields() {
            return fields;
        }

        public String getTransportationMethod(){
            return transportationMethodsTable.get();
        }

        public edu.wpi.MochaManticores.Services.InternalTransportation getRef() {
            return ref;
        }

        public String getPatientID(){
            return patientIDTable.get();
        }

        public int getNumStaffNeeded(){
            return numStaffNeededTable.get();
        }

        public String getDestination(){
            return destinationTable.get();
        }
    }

    ObservableList<String> typeOfTransportList = FXCollections
            .observableArrayList("Wheelchair","Walker","Medical Bed");

    @FXML
    private ImageView backgroundIMG;

    @FXML
    private GridPane contentGrid;

    @FXML
    private StackPane dialogPane;

    @FXML
    private JFXTextField patientID, numberOfStaff, destination, empBox;

    @FXML
    private JFXComboBox<String> transportComboBox;

    @FXML
    private TableView<it> internalTransportationTable;

    @FXML
    private TableView<it> externalTable;

    @FXML
    private JFXComboBox employeeAssigned;

    @FXML
    private GridPane requestPage;

    @FXML
    private GridPane managerPage;

    @FXML
    private JFXButton submitButton;

    @FXML
    private ImageView helpButton;

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
        contentGrid.setPrefSize(width, height);

        backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
        backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());

        //externalTable.setEditable(true);

        this.employeeAssigned.setEditable(true);
        //fromLocation.setOnKeyTyped(new AutoCompleteComboBoxListener<>(fromLocation));
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(DatabaseManager.getEmpManager().getEmployeeNames());
        employeeAssigned.setItems(items);
        createFilterListener(employeeAssigned);

        transportComboBox.setItems(typeOfTransportList);

        dialogPane.toBack();

        if(App.getClearenceLevel()<=0){
            empBox.setVisible(false);
        }

        // Initializing the tables for the manager
//        patientIdColumn = new TableColumn<it, String>("Patient ID");
//        patientIdColumn.setPrefWidth(100);
//        patientIdColumn.setCellValueFactory(new PropertyValueFactory<it, String>("patientID"));
//
//        numStaffNeededColumn = new TableColumn<it, Integer>("Number of Staff Needed");
//        numStaffNeededColumn.setPrefWidth(100);
//        numStaffNeededColumn.setCellValueFactory(new PropertyValueFactory<it, Integer>("numStaffNeeded"));
//
//        destinationColumn = new TableColumn<it, String>("Destination Place");
//        destinationColumn.setPrefWidth(100);
//        destinationColumn.setCellValueFactory(new PropertyValueFactory<it, String>("transportationMethod"));
//
       // managerPage.setVisible(false);
       // requestPage.setVisible(true);
      //  requestPage.toFront();
//
//        buildTable("");
    }

    public void submitEvent() {
        if(!patientID.getText().isEmpty() || !numberOfStaff.getText().isEmpty() ||
                !destination.getText().isEmpty() || !transportComboBox.getSelectionModel().getSelectedItem().isEmpty()){
            sel s = sel.InternalTransportation;
            edu.wpi.MochaManticores.Services.InternalTransportation toAdd = new edu.wpi.MochaManticores.Services.InternalTransportation(
                    "",
                    employeeAssigned.getEditor().getText(),
                    false,
                    patientID.getText(),
                    Integer.parseInt(numberOfStaff.getText()),
                    destination.getText(),
                    transportComboBox.getValue()
            );
            DatabaseManager.addRequest(s, toAdd);
            toAdd.send(toAdd.getRequestID());
            loadSubmitDialogue();
            System.out.println("Adds to database");
        }
            else if (patientID.getText().isEmpty()){
                RequiredFieldValidator missingInput = new RequiredFieldValidator();
                patientID.getValidators().add(missingInput);
                missingInput.setMessage("Patient ID is required");
                patientID.validate();
            } else if (numberOfStaff.getText().isEmpty()){
                RequiredFieldValidator missingInput = new RequiredFieldValidator();
                numberOfStaff.getValidators().add(missingInput);
                missingInput.setMessage("The number of staff is required");
                numberOfStaff.validate();
            } else if (destination.getText().isEmpty()) {
                RequiredFieldValidator missingInput = new RequiredFieldValidator();
                destination.getValidators().add(missingInput);
                missingInput.setMessage("Destination is required");
                destination.validate();
            }
                else if (transportComboBox.getItems().isEmpty()) {
                RequiredFieldValidator missingInput = new RequiredFieldValidator();
                transportComboBox.getValidators().add(missingInput);
                missingInput.setMessage("Type of transportation is required");
                transportComboBox.validate();
        }
        else if (employeeAssigned.getItems().isEmpty()) {
            RequiredFieldValidator missingInput = new RequiredFieldValidator();
            employeeAssigned.getValidators().add(missingInput);
            missingInput.setMessage("External room is required");
            employeeAssigned.validate();
        }
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
            changeSceneTo("landingPage");
        });

        dialog.setOnDialogClosed(event -> {
            dialogPane.toBack();
        });

        message.setActions(cont);
        dialog.show();

    }

    public void openHelp(MouseEvent mouseEvent) { loadHelpDialogue();
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

        final Text body = new Text("Patient ID: This is the ID given to the patient by the hospital.\n" +
                                    "Number of Staff: The number of staff recommended to transport a patient.\n" +
                                    "Destination: Room or location where the patient is going to be transported to.\n" +
                                    "Method of Transportation: Dropdown menu with 3 options:(Wheelchair, Walker, or Medical Bed).\n" +
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

    private ObservableList<it> buildTable(String searchTerm) {
        ObservableList<it> tableRow = FXCollections.observableArrayList();

        LinkedList<ServiceRequest> requests = DatabaseManager.getServiceMap().getServiceRequestsForType(ServiceRequestType.InternalTransportation);

        for (ServiceRequest s : requests) {
            it itToAdd = new it(s);
            for (int i = 0; i < itToAdd.getFields().size(); i++){
                if(itToAdd.getFields().get(i).toLowerCase().equals(searchTerm) || searchTerm.equals("")){
                    //System.out.println(i + " " + itToAdd.getDietaryPreference());
                    tableRow.add(itToAdd);
                    break;
                }
            }
        }
        internalTransportationTable.setItems(tableRow);
        //internalTransportationTable.getColumns().setAll(patientIdColumn,numStaffNeededColumn,
          //      destinationColumn,transportationMethodColumn);

        return tableRow;
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

}
