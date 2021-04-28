package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.Services.SanitationServices;
import edu.wpi.MochaManticores.Services.ServiceRequest;
import edu.wpi.MochaManticores.Services.ServiceRequestType;
import edu.wpi.MochaManticores.database.DatabaseManager;
import edu.wpi.MochaManticores.database.sel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
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

public class sanitationServiceControllerEmployee extends SceneController {

    public class ss extends RecursiveTreeObject<ss>{
        edu.wpi.MochaManticores.Services.SanitationServices ref;
        StringProperty location;
        StringProperty safetyHazards;
        StringProperty sanitationType;
        StringProperty equipmentNeeded;
        StringProperty description;
        StringProperty employeeAssigned;
        boolean completed;
        LinkedList<String> fields;

        public ss(edu.wpi.MochaManticores.Services.ServiceRequest ref){
            this.ref = (edu.wpi.MochaManticores.Services.SanitationServices) ref;
            location = new SimpleStringProperty(this.ref.getLocation());
            safetyHazards = new SimpleStringProperty(this.ref.getSafetyHazards());
            sanitationType = new SimpleStringProperty(this.ref.getSanitationType());
            equipmentNeeded = new SimpleStringProperty(this.ref.getEquipmentNeeded());
            description = new SimpleStringProperty(this.ref.getDescription());
            employeeAssigned = new SimpleStringProperty(this.ref.getEmployee());
            completed = this.ref.getCompleted();
            fields = new LinkedList<>(Arrays.asList(
                    location.get(),
                    safetyHazards.get(),
                    sanitationType.get(),
                    equipmentNeeded.get(),
                    description.get()));
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        public SanitationServices getRef() {
            return ref;
        }

        public String getLocation() {
            return location.get();
        }

        public StringProperty locationProperty() {
            return location;
        }

        public String getSafetyHazards() {
            return safetyHazards.get();
        }

        public StringProperty safetyHazardsProperty() {
            return safetyHazards;
        }

        public String getSanitationType() {
            return sanitationType.get();
        }

        public StringProperty sanitationTypeProperty() {
            return sanitationType;
        }

        public String getEquipmentNeeded() {
            return equipmentNeeded.get();
        }

        public StringProperty equipmentNeededProperty() {
            return equipmentNeeded;
        }

        public String getDescription() {
            return description.get();
        }

        public StringProperty descriptionProperty() {
            return description;
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

    ObservableList<String> sanitationType = FXCollections.observableArrayList("Room Cleaning","Spill");

    @FXML
    private JFXTextField loc;

    @FXML
    private JFXTextField safetyHaz;

    @FXML
    private JFXComboBox<String> typeComboBox;

    @FXML
    private JFXCheckBox glovesNeeded;
    @FXML
    private JFXCheckBox maskNeeded;
    @FXML
    private JFXCheckBox mopNeeded;

    private List<JFXCheckBox> equipment;

    @FXML
    private JFXTextField description;

    @FXML
    public JFXButton submitButton;

    @FXML
    public JFXButton cancelButton;

    @FXML
    public JFXButton helpBtn;

    @FXML
    public JFXTextField empBox;

    @FXML
    public ImageView backgroundIMG;

    @FXML
    public ImageView badgeIMG;

    @FXML
    public GridPane contentGrid;

    @FXML
    private StackPane dialogPane;

    @FXML
    private Label empTitle;

    @FXML
    private JFXButton backBtn;

    @FXML
    private GridPane requestPage;

    @FXML
    private GridPane managerPage;

    public TableView<ss> sanitationTable;

    public TableColumn<ss, String> locationColumn;
    public TableColumn<ss, String> safetyHazardsColumn;
    public TableColumn<ss, String> sanitationTypeColumn;
    public TableColumn<ss, String> equipmentNeededColumn;
    public TableColumn<ss, String> descriptionColumn;
    public TableColumn<ss, String> employeeAssignedColumn;
    public TableColumn<ss, String> completedColumn;






    @FXML
    private void initialize(){

        locationColumn = new TableColumn<ss, String>("Location");
        locationColumn.setMinWidth(100);
        locationColumn.setCellValueFactory(new PropertyValueFactory<ss, String>("location"));


        safetyHazardsColumn = new TableColumn<ss, String>("Safety Hazards");
        safetyHazardsColumn.setMinWidth(100);
        safetyHazardsColumn.setCellValueFactory(new PropertyValueFactory<ss, String>("safetyHazards"));

        sanitationTypeColumn = new TableColumn<ss, String>("Type");
        sanitationTypeColumn.setMinWidth(100);
        sanitationTypeColumn.setCellValueFactory(new PropertyValueFactory<ss, String>("sanitationType"));

        equipmentNeededColumn = new TableColumn<ss, String>("Equipment Needed");
        equipmentNeededColumn.setMinWidth(100);
        equipmentNeededColumn.setCellValueFactory(new PropertyValueFactory<ss, String>("equipmentNeeded"));

        descriptionColumn = new TableColumn<ss, String>("Description");
        descriptionColumn.setMinWidth(100);
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<ss, String>("description"));

        employeeAssignedColumn = new TableColumn<ss, String>("Employee");
        employeeAssignedColumn.setMinWidth(100);
        employeeAssignedColumn.setCellValueFactory(new PropertyValueFactory<ss, String>("employeeAssigned"));

        completedColumn = new TableColumn<ss, String>("Status");
        completedColumn.setMinWidth(100);
        completedColumn.setCellValueFactory(new PropertyValueFactory<ss, String>("completed"));


        double height = App.getPrimaryStage().getScene().getHeight();
        double width = App.getPrimaryStage().getScene().getWidth();
        backgroundIMG.setFitHeight(height);
        backgroundIMG.setFitWidth(width);
        //contentGrid.setPrefSize(width,height);

        dialogPane.toBack();
        backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
        backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());

        typeComboBox.setItems(sanitationType);
        equipment = Arrays.asList(glovesNeeded, maskNeeded, mopNeeded);

    }

    private ObservableList<ss> buildTable(String searchTerm){
        ObservableList<ss> tableRow = FXCollections.observableArrayList();
        LinkedList<ServiceRequest> requests = DatabaseManager.getServiceMap().getServiceRequestsForType(ServiceRequestType.SanitationServices);

        for(ServiceRequest s : requests){
            ss ssToAdd = new ss(s);
            for (int i = 0; i < ssToAdd.getFields().size(); i++) {
                if(ssToAdd.getFields().get(i).toLowerCase().equals(searchTerm) || searchTerm.equals("")){
                    tableRow.add(ssToAdd);
                    break;
                }
            }
        }
        sanitationTable.setItems(tableRow);
        sanitationTable.getColumns().setAll(
                locationColumn,
                safetyHazardsColumn,
                sanitationTypeColumn,
                equipmentNeededColumn,
                descriptionColumn,
                employeeAssignedColumn,
                completedColumn);
        return tableRow;

    }

    public void helpButton(ActionEvent actionEvent){loadHelpDialogue();}

    public void goBack(ActionEvent e) {
        back();
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
            dialogPane.toBack();
            dialog.close();
        });

        dialog.setOnDialogClosed(event -> {
            dialogPane.toBack();
            dialog.close();
        });

        message.setActions(ok);
        dialog.show();
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

        final Text body = new Text("Location: The room number, hallway, waiting area, etc. of the sanitation request.\n" +
                "Safety Hazards: This is where you indicate any safety hazards such as biohazards or densely populated area near the location. \n" +
                "Sanitation Type: This is where you indicate what the request is for such as room cleaning or spill.\n" +
                "Equipment Needed: This is where you indicate what equipment may be needed to complete the request.\n " +
                "Description: This is where you indicate the rest of the necesary information to complete the request.");
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
        ss selection = sanitationTable.getSelectionModel().getSelectedItem();
        selection.setCompleted(true);
        selection.getRef().setCompleted(true);
        buildTable("");
    }

    @FXML
    private void submit(ActionEvent e) {
        //JFXCheckBox source = (JFXCheckBox) e.getSource();
        StringBuilder equipmentNeeded = new StringBuilder();
        for(JFXCheckBox button : equipment) {
//             if(!button.equals(source)) {
//                 button.setSelected(false);
//             }
            }
        if(maskNeeded.isSelected()) {
            equipmentNeeded.append("maskNeeded,");
        }
        if(glovesNeeded.isSelected()) {
            equipmentNeeded.append("gloveNeeded,");
        }
        if(mopNeeded.isSelected()) {
            equipmentNeeded.append("mopNeeded,");
        }
            if(!loc.getText().isEmpty() && !safetyHaz.getText().isEmpty() &&
                 !typeComboBox.getSelectionModel().getSelectedItem().isEmpty() && (glovesNeeded.isSelected() || maskNeeded.isSelected() || mopNeeded.isSelected()) && !description.getText().isEmpty()){
            sel s = sel.SanitationServices;
                DatabaseManager.addRequest(s, new edu.wpi.MochaManticores.Services.SanitationServices(
                    "",
                    "",
                    false,
                    loc.getText(),
                    safetyHaz.getText(),
                    typeComboBox.getValue(),
                    equipmentNeeded.toString(),
                    description.getText()
                    ));
            System.out.println("Adds to database");
        } else if (loc.getText().isEmpty()){
            RequiredFieldValidator missingInput = new RequiredFieldValidator();
            loc.getValidators().add(missingInput);
            missingInput.setMessage("Location is required.");
            loc.validate();
        } else if (safetyHaz.getText().isEmpty()){
            RequiredFieldValidator missingInput = new RequiredFieldValidator();
            safetyHaz.getValidators().add(missingInput);
            missingInput.setMessage("Safety Hazards are required.");
            safetyHaz.validate();
        } else if (typeComboBox.getSelectionModel().getSelectedItem().isEmpty()){
            RequiredFieldValidator missingInput = new RequiredFieldValidator();
            typeComboBox.getValidators().add(missingInput);
            missingInput.setMessage("Sanitation Type is required.");
            typeComboBox.validate();
        } else if (equipmentNeeded.toString().isEmpty()){
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.show();
            } else if (description.getText().isEmpty()){
                RequiredFieldValidator missingInput = new RequiredFieldValidator();
                description.getValidators().add(missingInput);
                missingInput.setMessage("Description is required.");
                description.validate();
            }
        loadSubmitDialog();
    }

    public void back() {
        super.back();
    }
}

