package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.Services.ExternalTransportation;
import edu.wpi.MochaManticores.Services.ServiceRequest;
import edu.wpi.MochaManticores.Services.ServiceRequestType;
import edu.wpi.MochaManticores.database.DatabaseManager;
import edu.wpi.MochaManticores.database.sel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.LinkedList;

public class extTransportationControllerEmployee extends SceneController {

    public class et extends RecursiveTreeObject<et> {
        edu.wpi.MochaManticores.Services.ExternalTransportation ref;
        StringProperty patientRoom;
        StringProperty currentRoom;
        StringProperty externalRoom;
        StringProperty transportationMethod;
        StringProperty employeeAssigned;
        boolean completed;
        LinkedList<String> fields;

        public void setPatientRoom(String patientRoom) {
            this.patientRoom.set(patientRoom);
            generateFields();
        }

        public void setCurrentRoom(String currentRoom) {
            this.currentRoom.set(currentRoom);
            generateFields();

        }

        public void setExternalRoom(String externalRoom) {
            this.externalRoom.set(externalRoom);
            generateFields();

        }

        public void setTransportationMethod(String transportationMethod) {
            this.transportationMethod.set(transportationMethod);
            generateFields();

        }

        public void setEmployeeAssigned(String employeeAssigned) {
            this.employeeAssigned.set(employeeAssigned);
            generateFields();

        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
            generateFields();

        }

        public et(edu.wpi.MochaManticores.Services.ServiceRequest ref){
            this.ref = (edu.wpi.MochaManticores.Services.ExternalTransportation) ref;
            patientRoom = new SimpleStringProperty(this.ref.getPatientRoom());
            currentRoom = new SimpleStringProperty(this.ref.getCurrentRoom());
            externalRoom = new SimpleStringProperty(this.ref.getExternalRoom());
            transportationMethod = new SimpleStringProperty(this.ref.getTransportationMethod());
            employeeAssigned = new SimpleStringProperty(this.ref.getEmployee());
            completed = this.ref.getCompleted();
            fields = new LinkedList<>(Arrays.asList(
                    patientRoom.get(),
                    currentRoom.get(),
                    externalRoom.get(),
                    transportationMethod.get(),
                    employeeAssigned.get()));
        }

        public void generateFields(){
            fields = new LinkedList<>(Arrays.asList(
                    patientRoom.get(),
                    currentRoom.get(),
                    externalRoom.get(),
                    transportationMethod.get(),
                    employeeAssigned.get()));
        }

        public ExternalTransportation getRef() {
            return ref;
        }

        public String getPatientRoom() {
            return patientRoom.get();
        }

        public StringProperty patientRoomProperty() {
            return patientRoom;
        }

        public String getCurrentRoom() {
            return currentRoom.get();
        }

        public StringProperty currentRoomProperty() {
            return currentRoom;
        }

        public String getExternalRoom() {
            return externalRoom.get();
        }

        public StringProperty externalRoomProperty() {
            return externalRoom;
        }

        public String getTransportationMethod() {
            return transportationMethod.get();
        }

        public StringProperty transportationMethodProperty() {
            return transportationMethod;
        }

        public String getEmployeeAssigned() {
            return employeeAssigned.get();
        }

        public StringProperty employeeAssignedProperty() {
            return employeeAssigned;
        }

        public String isCompleted() {
            if(completed){
                return "Completed";
            }else{
                return "Open";
            }
        }

        public LinkedList<String> getFields() {
            return fields;
        }
    }

    ObservableList<String> transportationMethod = FXCollections.observableArrayList("Ambulance", "Helicopter", "Plane");


    public TableColumn<et, String> patientRoomColumn;
    public TableColumn<et, String> currentRoomColumn;
    public TableColumn<et, String> externalRoomColumn;
    public TableColumn<et, String> transportationMethodColumn;
    public TableColumn<et, String> employeeColumn;
    public TableColumn<et, String> completedColumn;

    @FXML
    private GridPane contentGrid;

    @FXML
    private GridPane requestPage;

    @FXML
    private GridPane managerPage;

    @FXML
    private ImageView backgroundIMG;
    @FXML
    private StackPane dialogPane;
    @FXML
    private JFXTextField empBox;
    @FXML
    private JFXTextField patientRoom, currentRoom, externalRoom;
    @FXML
    private ComboBox<String> transportationMethods;

    @FXML
    private TableView<et> externalTable;

    @FXML
    private void initialize() {




        patientRoomColumn = new TableColumn<et, String>("Patient Room");
        patientRoomColumn.setMinWidth(100);
        patientRoomColumn.setCellValueFactory(new PropertyValueFactory<et, String>("patientRoom"));
        patientRoomColumn.setOnEditCommit(this::changePatientRoom);



        currentRoomColumn = new TableColumn<et, String>("Current Room");
        currentRoomColumn.setMinWidth(100);
        currentRoomColumn.setCellValueFactory(new PropertyValueFactory<et, String>("currentRoom"));
        currentRoomColumn.setOnEditCommit(this::changeCurrentRoom);



        externalRoomColumn = new TableColumn<et, String>("External Room");
        externalRoomColumn.setMinWidth(100);
        externalRoomColumn.setCellValueFactory(new PropertyValueFactory<et, String>("externalRoom"));
        externalRoomColumn.setOnEditCommit(this::changeExternalRoom);


        transportationMethodColumn = new TableColumn<et, String>("Transportation Method");
        transportationMethodColumn.setMinWidth(100);
        transportationMethodColumn.setCellValueFactory(new PropertyValueFactory<et, String>("transportationMethod"));
        transportationMethodColumn.setOnEditCommit(this::changeTransport);


        employeeColumn = new TableColumn<et, String>("Employee");
        employeeColumn.setMinWidth(100);
        employeeColumn.setCellValueFactory(new PropertyValueFactory<et, String>("employeeAssigned"));
        employeeColumn.setOnEditCommit(this::changeEmployee);


        completedColumn = new TableColumn<et, String>("Completed");
        completedColumn.setMinWidth(100);
        completedColumn.setCellValueFactory(new PropertyValueFactory<et, String>("completed"));
        completedColumn.setOnEditCommit(this::changeCompleted);


        externalTable.setEditable(true);


        double height = App.getPrimaryStage().getScene().getHeight();
        double width = App.getPrimaryStage().getScene().getWidth();
        backgroundIMG.setFitHeight(height);
        backgroundIMG.setFitWidth(width);

        backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
        backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());

        transportationMethods.setItems(transportationMethod);

        dialogPane.setDisable(false);

        if(App.getClearenceLevel()<=0){
            empBox.setVisible(false);
        }

        changeToRequest(null);

        buildTable("",false);

//        externalTable.getSelectionModel().cellSelectionEnabledProperty().set(true);
//        patientRoomColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        patientRoomColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        currentRoomColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        externalRoomColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        transportationMethodColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        employeeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        completedColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    public void changePatientRoom(TableColumn.CellEditEvent editEvent){
        et selectedRow = externalTable.getSelectionModel().getSelectedItem();
        selectedRow.setPatientRoom(editEvent.getNewValue().toString());
    }

    public void changeCurrentRoom(TableColumn.CellEditEvent editEvent){
        et selectedRow = externalTable.getSelectionModel().getSelectedItem();
        selectedRow.setCurrentRoom(editEvent.getNewValue().toString());
    }
    public void changeExternalRoom(TableColumn.CellEditEvent editEvent){
        et selectedRow = externalTable.getSelectionModel().getSelectedItem();
        selectedRow.setExternalRoom(editEvent.getNewValue().toString());
    }
    public void changeTransport(TableColumn.CellEditEvent editEvent){
        et selectedRow = externalTable.getSelectionModel().getSelectedItem();
        selectedRow.setTransportationMethod(editEvent.getNewValue().toString());
    }
    public void changeEmployee(TableColumn.CellEditEvent editEvent){
        et selectedRow = externalTable.getSelectionModel().getSelectedItem();
        selectedRow.setEmployeeAssigned(editEvent.getNewValue().toString());
    }
    public void changeCompleted(TableColumn.CellEditEvent editEvent){
        et selectedRow = externalTable.getSelectionModel().getSelectedItem();
        if((editEvent.getNewValue().toString()).equals("Open")){
            selectedRow.setCompleted(false);
        }else if((editEvent.getNewValue().toString()).equals("Closed")){
            selectedRow.setCompleted(true);
        }

    }



    public ObservableList<et> buildTable(String searchTerm, boolean showCompleted){



        ObservableList<et> tableRow = FXCollections.observableArrayList();
        LinkedList<ServiceRequest> requests = DatabaseManager.getServiceMap().getServiceRequestsForType(ServiceRequestType.ExternalTransportation);

        for(ServiceRequest s : requests){
            et etToAdd = new et(s);
            for (int i = 0; i < etToAdd.getFields().size(); i++) {
                if((etToAdd.getFields().get(i).toLowerCase().equals(searchTerm) || searchTerm.equals(""))){
                        tableRow.add(etToAdd);
                        break;
                }
            }
        }

        externalTable.setItems(tableRow);
        externalTable.getColumns().setAll(
                patientRoomColumn,
                currentRoomColumn,
                externalRoomColumn,
                transportationMethodColumn,
                employeeColumn,
                completedColumn);
        return  tableRow;

    }


    public void submitEvent(ActionEvent actionEvent) {
        if(!externalRoom.getText().isEmpty() && !currentRoom.getText().isEmpty() && !patientRoom.getText().isEmpty()){
            sel s = sel.ExternalTransportation;
            DatabaseManager.addRequest(s,
                    new edu.wpi.MochaManticores.Services.ExternalTransportation(
                            "",
                            empBox.getText(),
                            false,
                            patientRoom.getText(),
                            currentRoom.getText(),
                            externalRoom.getText(),
                            transportationMethods.getSelectionModel().getSelectedItem()
                    ));
            System.out.println("runned");
        } else if (patientRoom.getText().isEmpty()){
            RequiredFieldValidator missingInput = new RequiredFieldValidator();
            patientRoom.getValidators().add(missingInput);
            missingInput.setMessage("Patient room is required");
            patientRoom.validate();
        } else if (currentRoom.getText().isEmpty()){
            RequiredFieldValidator missingInput = new RequiredFieldValidator();
            currentRoom.getValidators().add(missingInput);
            missingInput.setMessage("Current room is required");
            currentRoom.validate();
        } else if (externalRoom.getText().isEmpty()){
            RequiredFieldValidator missingInput = new RequiredFieldValidator();
            externalRoom.getValidators().add(missingInput);
            missingInput.setMessage("External room is required");
            externalRoom.validate();
        }


    }

    public void helpButton(ActionEvent actionEvent){loadHelpDialogue();}

    public void changeToRequest(ActionEvent actionEvent) {
        requestPage.setVisible(true);
        managerPage.setVisible(false);
    }

    public void changeManagerTable(ActionEvent actionEvent) {
        buildTable("",true);
        requestPage.setVisible(false);
        managerPage.setVisible(true);
    }

    private void loadDialog(){
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

    private void loadHelpDialogue() {
        dialogPane.toFront();
        loadDialog();
    }

    public void completeService(ActionEvent e){
        et selection = externalTable.getSelectionModel().getSelectedItem();
        selection.setCompleted(true);
        selection.getRef().setCompleted(true);
        buildTable("",true);
    }

    public void loadSubmitDialog(){
        //TODO Center the text of it.
        dialogPane.toFront();
        dialogPane.setDisable(false);
        JFXDialogLayout message = new JFXDialogLayout();
        message.setMaxHeight(Region.USE_PREF_SIZE);
        message.setMaxHeight(Region.USE_PREF_SIZE);

        final Text hearder = new Text("Your request was submited");
        hearder.setStyle("-fx-font-weight: bold");
        hearder.setStyle("-fx-font-size: 30");
        hearder.setStyle("-fx-font-family: Roboto");
        hearder.setStyle("-fx-alignment: center");
        message.setHeading(hearder);

        final Text body = new Text("Estimated time for arrival: ");
        body.setStyle("-fx-font-size: 15");
        body.setStyle("-fx-font-family: Roboto");
        body.setStyle("-fx-alignment: center");
        message.setHeading(hearder);

        message.setBody(body);
        JFXDialog dialog = new JFXDialog(dialogPane, message,JFXDialog.DialogTransition.CENTER);
        JFXButton ok = new JFXButton("OK");
        ok.setOnAction(event -> {
            super.back();
        });

        dialog.setOnDialogClosed(event -> {
            dialogPane.toBack();
            dialog.close();
        });

        message.setActions(ok);
        dialog.show();
    }

}
