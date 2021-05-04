package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.Services.FloralDelivery;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FloralSceneEmployeeController extends SceneController {


    @FXML
    private JFXTextField roomNumber;
    @FXML
    private JFXDatePicker deliveryDate;
    @FXML
    private JFXTextField personalNote;

    @FXML
    private JFXCheckBox tulip;
    @FXML
    private JFXCheckBox rose;
    @FXML
    private JFXCheckBox lilie;

    @FXML
    private JFXRadioButton blueVase;
    @FXML
    private JFXRadioButton yellowVase;
    @FXML
    private JFXRadioButton orangeVase;

    private List<JFXRadioButton> vases;

    private List<JFXRadioButton> flowers;

    @FXML
    private GridPane contentGrid;
    @FXML
    private ImageView backgroundIMG;
    @FXML
    private StackPane dialogPane;

    @FXML
    private GridPane requestPage;

    @FXML
    private GridPane managerPage;

    @FXML
    private JFXComboBox employeeAssigned;

    public class fs extends RecursiveTreeObject<fs> {
        edu.wpi.MochaManticores.Services.FloralDelivery ref;
        StringProperty roomNumber;
        StringProperty deliveryDate;
        StringProperty typeFlower;
        StringProperty typeVase;
        boolean completed;
        LinkedList<String> fields;



        public FloralDelivery getRef() {
            return ref;
        }

        public LinkedList<String> getFields() {
            return fields;
        }

        public String getPatientRoom() {
            return roomNumber.get();
        }

        public StringProperty getDeliveryDate() {
            return deliveryDate;
        }

        public String getTypeFlower() {
            return typeFlower.get();
        }

        public String getTypeVase() {
            return typeFlower.get();
        }

        public String getEmployeeAssigned() {
            return employeeAssigned.getValue().toString();
        }


        public void setPatientRoom(String patientRoom) {
            this.roomNumber.set(patientRoom);
            generateFields();
        }

        public void setDeliveryDate(String currentDate) {
            this.deliveryDate.set(currentDate);
            generateFields();

        }

        public void setTypeFlower(String flower) {
            this.flowerSelected.append(flower);
            generateFields();

        }

        public void setTypeVase(String vase) {
            this.vaseSelected.append(vase);
            generateFields();

        }

        public void setEmployeeAssigned(JFXComboBox employeeAssigned) {
            this.employeeAssigned.setValue(employeeAssigned);
            generateFields();

        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
            generateFields();

        }
        @FXML
        private TableView<FloralSceneEmployeeController.fs> externalTable;

        public void changePatientRoom(TableColumn.CellEditEvent editEvent){
            FloralSceneEmployeeController.fs selectedRow = externalTable.getSelectionModel().getSelectedItem();
            selectedRow.setPatientRoom(editEvent.getNewValue().toString());
        }

        public void changeDeliveryDate(TableColumn.CellEditEvent editEvent){
            FloralSceneEmployeeController.fs selectedRow = externalTable.getSelectionModel().getSelectedItem();
            selectedRow.setDeliveryDate(editEvent.getNewValue().toString());
        }
        public void changeTypeFlower(TableColumn.CellEditEvent editEvent){
            FloralSceneEmployeeController.fs selectedRow = externalTable.getSelectionModel().getSelectedItem();
            selectedRow.setTypeFlower(editEvent.getNewValue().toString());
        }
        public void changeTypeVase(TableColumn.CellEditEvent editEvent){
            FloralSceneEmployeeController.fs selectedRow = externalTable.getSelectionModel().getSelectedItem();
            selectedRow.setTypeVase(editEvent.getNewValue().toString());
        }
        public void changeEmployee(TableColumn.CellEditEvent editEvent){
            FloralSceneEmployeeController.fs selectedRow = externalTable.getSelectionModel().getSelectedItem();
            selectedRow.setEmployeeAssigned((JFXComboBox) editEvent.getNewValue());
        }
        public void changeCompleted(TableColumn.CellEditEvent editEvent){
            FloralSceneEmployeeController.fs selectedRow = externalTable.getSelectionModel().getSelectedItem();
            if((editEvent.getNewValue().toString()).equals("Open")){
                selectedRow.setCompleted(false);
            }else if((editEvent.getNewValue().toString()).equals("Closed")){
                selectedRow.setCompleted(true);
            }

        }


        private ObservableList<fs> buildTable(String searchTerm) {
            ObservableList<FloralSceneEmployeeController.fs> tableRow = FXCollections.observableArrayList();
            LinkedList<ServiceRequest> requests = DatabaseManager.getServiceMap().getServiceRequestsForType(ServiceRequestType.Medicine);

            for (ServiceRequest s : requests) {
                FloralSceneEmployeeController.fs fsToAdd = new FloralSceneEmployeeController.fs(s);
                for (int i = 0; i < fsToAdd.getFields().size(); i++) {
                    if (fsToAdd.getFields().get(i).toLowerCase().equals(searchTerm) || searchTerm.equals("")) {
                        tableRow.add(fsToAdd);
                        break;
                    }
                }
            }

            floralDeliveryTable.setItems(tableRow);
            floralDeliveryTable.getColumns().setAll(
            //        roomNumber,
              //      deliveryDate,
                //    flowerSelected,
                  //  vaseSelected,
                    //empBox,
                    completedColumn);
            return tableRow;

        }

        public void generateFields(){
            fields = new LinkedList<>(Arrays.asList(
                    roomNumber.get(),
                    deliveryDate.get(),
                    flowerSelected.toString(),
                    vaseSelected.toString(),
                    employeeAssigned.getValue().toString()));
        }


        @FXML
        public TableView<fs> floralDeliveryTable;
        @FXML
        public TableColumn<fs, String> patientRoomColumn;
        @FXML
        public TableColumn<FloralSceneEmployeeController.fs, String> deliveryDateColumn;
        @FXML
        public TableColumn<FloralSceneEmployeeController.fs, String> personalNoteColumn;
        @FXML
        public TableColumn<FloralSceneEmployeeController.fs, String> typeFlowerColumn;
        @FXML
        public TableColumn<FloralSceneEmployeeController.fs, String> vaseTypeColumn;
        @FXML
        public TableColumn<FloralSceneEmployeeController.fs, JFXComboBox> employeeColumn;
        @FXML
        public TableColumn<FloralSceneEmployeeController.fs, String> completedColumn;
        @FXML
        JFXComboBox employeeAssigned;


        public fs(edu.wpi.MochaManticores.Services.ServiceRequest ref) {
            this.ref = (edu.wpi.MochaManticores.Services.FloralDelivery) ref;
            roomNumber = new SimpleStringProperty(this.ref.getRoomNumber());
            deliveryDate = new SimpleStringProperty(this.ref.getDeliveryChoice());
            typeFlower = new SimpleStringProperty(this.ref.getTypeOfFlowers());
            typeVase = new SimpleStringProperty(this.ref.getVaseOptions());
            fields = new LinkedList<>(Arrays.asList(
                    roomNumber.get(),
                    deliveryDate.get(),
                    typeFlower.get(),
                    typeVase.get(),
                    employeeAssigned.getValue().toString()));
        }


        @FXML
        private void goBack(ActionEvent e) {
            back();
        }

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
            patientRoomColumn = new TableColumn<FloralSceneEmployeeController.fs, String>("Medicine Type");
            patientRoomColumn.setMinWidth(100);
            patientRoomColumn.setCellValueFactory(new PropertyValueFactory<FloralSceneEmployeeController.fs, String>("typeMedicine"));

            deliveryDateColumn = new TableColumn<FloralSceneEmployeeController.fs, String>("Feeling");
            deliveryDateColumn.setMinWidth(100);
            deliveryDateColumn.setCellValueFactory(new PropertyValueFactory<FloralSceneEmployeeController.fs, String>("currentFeeling"));

            personalNoteColumn = new TableColumn<FloralSceneEmployeeController.fs, String>("Allergies");
            personalNoteColumn.setMinWidth(100);
            personalNoteColumn.setCellValueFactory(new PropertyValueFactory<FloralSceneEmployeeController.fs, String>("allergies"));

            typeFlowerColumn = new TableColumn<FloralSceneEmployeeController.fs, String>("Room");
            typeFlowerColumn.setMinWidth(100);
            typeFlowerColumn.setCellValueFactory(new PropertyValueFactory<FloralSceneEmployeeController.fs, String>("patientRoom"));

            employeeAssigned.setEditable(true);
            //fromLocation.setOnKeyTyped(new AutoCompleteComboBoxListener<>(fromLocation));
            ObservableList<String> items = FXCollections.observableArrayList();
            DatabaseManager.getEmployeeNames().forEach(s -> {
                items.add(s.substring(s.indexOf(" ")));
            });
            employeeAssigned.setItems(items);
            createFilterListener(employeeAssigned);

            employeeColumn = new TableColumn<FloralSceneEmployeeController.fs, String>("Assigned To");
            employeeColumn.setMinWidth(100);
            employeeColumn.setCellValueFactory(new PropertyValueFactory<FloralSceneEmployeeController.fs, String>("employeeAssigned"));

            completedColumn = new TableColumn<FloralSceneEmployeeController.fs, String>("Status");
            completedColumn.setMinWidth(100);
            completedColumn.setCellValueFactory(new PropertyValueFactory<FloralSceneEmployeeController.fs, String>("completed"));

            double height = App.getPrimaryStage().getScene().getHeight();
            double width = App.getPrimaryStage().getScene().getWidth();
            backgroundIMG.setFitHeight(height);
            backgroundIMG.setFitWidth(width);
            contentGrid.setPrefSize(width, height);

            dialogPane.toBack();

            backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
            backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());

            vases = Arrays.asList(blueVase, yellowVase, orangeVase);

            if (App.getClearenceLevel() <= 0) {
                empBox.setVisible(false);
            }
        }

        public void submitForm(ActionEvent actionEvent) {
            submitEvent(actionEvent);
            loadSubmitDialog();
        }

        public void loadSubmitDialog() {
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
            JFXDialog dialog = new JFXDialog(dialogPane, message, JFXDialog.DialogTransition.CENTER);
            JFXButton ok = new JFXButton("OK");
            ok.setOnAction(event -> {
                goBack(null);
            });

            dialog.setOnDialogClosed(event -> {
                dialogPane.toBack();
                dialog.close();
            });

            message.setActions(ok);
            dialog.show();
        }

        public void helpButton(ActionEvent actionEvent) {
            loadHelpDialogue();
        }

        private void loadDialog() {
            JFXDialogLayout message = new JFXDialogLayout();
            message.setMaxHeight(Region.USE_COMPUTED_SIZE);
            message.setMaxHeight(Region.USE_COMPUTED_SIZE);

            final Text hearder = new Text("Help Page");
            hearder.setStyle("-fx-font-weight: bold");
            hearder.setStyle("-fx-font-size: 60");
            hearder.setStyle("-fx-font-family: Roboto");
            hearder.setStyle("-fx-alignment: center");
            message.setHeading(hearder);

            final Text body = new Text("Room number: This is the room number of the patient you are delivering flowers to.\n" +
                    "Delivery choice is how you want the flowers delivered. \n" +
                    "There are three options for the flowers and three options for the vase color.\n" +
                    "Click on the option you would like.\n" +
                    "Personalized note is not necessary to get flowers delivered, but is an option for if you want to leave a message.");

            body.setStyle("-fx-font-size: 40");
            body.setStyle("-fx-font-family: Roboto");
            body.setStyle("-fx-alignment: center");

            message.setBody(body);


            JFXDialog dialog = new JFXDialog(dialogPane, message, JFXDialog.DialogTransition.CENTER);

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

        StringBuilder flowerSelected = new StringBuilder();

        public void checkFlowers(ActionEvent e) {
            JFXRadioButton source = (JFXRadioButton) e.getSource();
            for (JFXRadioButton button : vases) {
                if (!button.equals(source)) {
                    button.setSelected(false);
                }
            }
            if (blueVase.isSelected()) {
                flowerSelected.append("Rose,");
            }
            if (orangeVase.isSelected()) {
                flowerSelected.append("Tulip,");
            }
            if (yellowVase.isSelected()) {
                flowerSelected.append("Lilie,");
            }
        }

        StringBuilder vaseSelected = new StringBuilder();


        public void checkVase(ActionEvent e) {
            JFXRadioButton source = (JFXRadioButton) e.getSource();
            for (JFXRadioButton button : vases) {
                if (!button.equals(source)) {
                    button.setSelected(false);
                }
            }
            if (blueVase.isSelected()) {
                vaseSelected.append("blueVase,");
            }
            if (orangeVase.isSelected()) {
                vaseSelected.append("orangeVase,");
            }
            if (yellowVase.isSelected()) {
                vaseSelected.append("yellowVase,");
            }
        }
        public void changeManagerTable(ActionEvent actionEvent) {
            requestPage.setVisible(false);
            managerPage.setVisible(true);
            managerPage.toFront();
        }



        public void submitEvent(ActionEvent actionEvent) {
            if (!roomNumber.getValue().isEmpty() && !deliveryDate.equals("") &&
                    (tulip.isSelected() || rose.isSelected() | lilie.isSelected()) &&
                    (blueVase.isSelected() || orangeVase.isSelected() || yellowVase.isSelected()) &&
                    !employeeAssigned.getValue().toString().isEmpty()) {
                sel s = sel.FloralDelivery;
                DatabaseManager.addRequest(s,
                        new FloralDelivery(
                                "", "", false, roomNumber.getValue(),
                                deliveryDate.getValue(), flowerSelected.toString(),
                                vaseSelected.toString(),
                                employeeAssigned.getValue().toString()));

            } else if (roomNumber.getValue().isEmpty()) {
                RequiredFieldValidator missingInput = new RequiredFieldValidator();
                //roomNumber.getValue().getValidators().add(missingInput);
                //missingInput.setMessage("Patient room is required");
               // roomNumber.validate();
            } else if (deliveryDate.getValue().isEmpty()) {
                RequiredFieldValidator missingInput = new RequiredFieldValidator();
                //deliveryDate.getValidators().add(missingInput);
                //missingInput.setMessage("Delivery date is required");
                //deliveryDate.validate();
            } else if (empBox.getText().isEmpty()) {
                //RequiredFieldValidator missingInput = new RequiredFieldValidator();
                //empBox.getValidators().add(missingInput);
                //missingInput.setMessage("Employee must be assigned");
                //empBox.validate();
                System.out.println("Adds to database");
            }
        }
    }

}