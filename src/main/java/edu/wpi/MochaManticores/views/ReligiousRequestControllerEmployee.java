package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.Services.ReligiousRequest;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.util.Arrays;
import java.util.LinkedList;


public class ReligiousRequestControllerEmployee extends SceneController{

    public class rr extends RecursiveTreeObject<rr> {

        edu.wpi.MochaManticores.Services.ReligiousRequest ref;
        StringProperty reasonVisit;
        StringProperty location;
        StringProperty typeSacredPerson;
        StringProperty employeeAssigned;
        boolean completed;
        LinkedList<String> fields;

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        public rr(edu.wpi.MochaManticores.Services.ServiceRequest ref){
            this.ref = (edu.wpi.MochaManticores.Services.ReligiousRequest) ref;
            reasonVisit = new SimpleStringProperty(this.ref.getReasonVisit());
            location = new SimpleStringProperty(this.ref.getLocation());
            typeSacredPerson = new SimpleStringProperty(this.ref.getTypeSacredPerson());
            employeeAssigned = new SimpleStringProperty(this.ref.getEmployee());
            completed = this.ref.getCompleted();
            fields = new LinkedList<>(Arrays.asList(
                    reasonVisit.get(),
                    location.get(),
                    typeSacredPerson.get()));
        }

        public ReligiousRequest getRef() {
            return ref;
        }

        public String getReasonVisit() {
            return reasonVisit.get();
        }

        public StringProperty reasonVisitProperty() {
            return reasonVisit;
        }

        public String getLocation() {
            return location.get();
        }

        public StringProperty locationProperty() {
            return location;
        }

        public String getTypeSacredPerson() {
            return typeSacredPerson.get();
        }

        public StringProperty typeSacredPersonProperty() {
            return typeSacredPerson;
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

    public TableColumn<rr, String> reasonVisitColumn;
    public TableColumn<rr, String> locationColumn;
    public TableColumn<rr, String> typeSacredPersonColumn;
    public TableColumn<rr, String> employeeAssignedColumn;
    public TableColumn<rr, String> completedColumn;
    @FXML
    private GridPane requestPage;

    @FXML
    private GridPane managerPage;

    @FXML
    private JFXTextField empBox;

    @FXML
    private JFXTextField reasonBox;

    @FXML
    private JFXTextField roomIDbox;



    ObservableList<String> TypeOfSacredPersons = FXCollections.observableArrayList("Rabbi", "Monk", "Priest",  "Purohit", "Spiritual Person");
    @FXML
    private ComboBox<String> TypeOfSacredPerson;
    @FXML
    private GridPane backgroundGrid;
    @FXML
    private GridPane formSquare;

    @FXML
    private TableView<rr> religionTable;
    @FXML
    private JFXButton submitBTN;
    @FXML
    private JFXButton cancelBTN;

    public void initialize() {

        reasonVisitColumn = new TableColumn<rr, String>("Reason For Visit");
        reasonVisitColumn.setMinWidth(100);
        reasonVisitColumn.setCellValueFactory(new PropertyValueFactory<rr, String>("reasonVisit"));

        locationColumn = new TableColumn<rr, String>("Location");
        locationColumn.setMinWidth(100);
        locationColumn.setCellValueFactory(new PropertyValueFactory<rr, String>("location"));

        typeSacredPersonColumn = new TableColumn<rr, String>("Type of Sacred Person");
        typeSacredPersonColumn.setMinWidth(100);
        typeSacredPersonColumn.setCellValueFactory(new PropertyValueFactory<rr, String>("typeSacredPerson"));

        employeeAssignedColumn = new TableColumn<rr, String>("Employee Assigned");
        employeeAssignedColumn.setMinWidth(100);
        employeeAssignedColumn.setCellValueFactory(new PropertyValueFactory<rr, String>("employeeAssigned"));

        completedColumn = new TableColumn<rr, String>("Completed");
        completedColumn.setMinWidth(100);
        completedColumn.setCellValueFactory(new PropertyValueFactory<rr, String>("completed"));


        double height = App.getPrimaryStage().getScene().getHeight();
        double width = App.getPrimaryStage().getScene().getWidth();
        //backgroundGrid.setPrefSize(width, height);

        TypeOfSacredPerson.setItems(TypeOfSacredPersons);

        buildTable("");
    }


    public ObservableList<rr> buildTable(String searchTerm){
        ObservableList<rr> tableRow = FXCollections.observableArrayList();
        LinkedList<ServiceRequest> requests = DatabaseManager.getServiceMap().getServiceRequestsForType(ServiceRequestType.ReligiousRequest);

        for(ServiceRequest s : requests){
            rr rrToAdd = new rr(s);
            for (int i = 0; i < rrToAdd.getFields().size(); i++) {
                if(rrToAdd.getFields().get(i).toLowerCase().equals(searchTerm) || searchTerm.equals("")){
                    tableRow.add(rrToAdd);
                    break;
                }
            }
        }
        religionTable.setItems(tableRow);
        religionTable.getColumns().setAll(
                reasonVisitColumn,
                locationColumn,
                typeSacredPersonColumn,
                employeeAssignedColumn,
                completedColumn);
        return tableRow;

    }

    public void helpButton(){

    }

    public void exitPage(){
        super.back();
    }

    public void cancelEvent(ActionEvent actionEvent) {
        exitPage();
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
        rr selection = religionTable.getSelectionModel().getSelectedItem();
        selection.setCompleted(true);
        selection.getRef().setCompleted(true);
        buildTable("");
    }


    public void submitEvent(ActionEvent actionEvent) {
        sel s = sel.ReligiousRequest;
        DatabaseManager.addRequest(s,
                new ReligiousRequest("",
                        empBox.getText(),
                        false,
                        reasonBox.getText(),
                        roomIDbox.getText(),
                        TypeOfSacredPerson.getSelectionModel().getSelectedItem()));
    }
}

