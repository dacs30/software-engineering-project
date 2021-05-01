package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.MochaManticores.Services.*;
import edu.wpi.MochaManticores.database.DatabaseManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Arrays;
import java.util.LinkedList;


public class serviceManagerController extends SceneController {
    
    public TableView<ss> sanitationTable;
    public TableColumn<ss, String> sanitationLocationColumn;
    public TableColumn<ss, String> safetyHazardsColumn;
    public TableColumn<ss, String> sanitationTypeColumn;
    public TableColumn<ss, String> equipmentNeededColumn;
    public TableColumn<ss, String> sanitationDescriptionColumn;
    public TableColumn<ss, String> sanitationEmployeeColumn;
    public TableColumn<ss, String> sanitationCompletedColumn;

    public TableView<tl> translatorTable;
    public TableColumn<tl, String> translateRoomColumn;
    public TableColumn<tl, String> languageOneColumn;
    public TableColumn<tl, String> languageTwoColumn;
    public TableColumn<tl, String> translateEmployeeColumn;
    public TableColumn<tl, String> translateCompletedColumn
            ;
    public TableView<rr> religionTable;
    public TableColumn<rr, String> reasonVisitColumn;
    public TableColumn<rr, String> religionLocationColumn;
    public TableColumn<rr, String> typeSacredPersonColumn;
    public TableColumn<rr, String> religionEmployeeColumn;
    public TableColumn<rr, String> religionCompletedColumn;

    public TableView<fd> foodDeliveryTable;
    public TableColumn<fd, String> dietaryPrefColumn;
    public TableColumn<fd, String> FoodAllergiesColumn;
    public TableColumn<fd, String> FoodEmployeeColumn;
    public TableColumn<fd, String> FoodCompletedColumn;
    public TableColumn<fd, String> menuOptionColumn;

    public TableView<et> externalTable;
    public TableColumn<et, String> ExternalPatientRoomColumn;
    public TableColumn<et, String> currentRoomColumn;
    public TableColumn<et, String> externalRoomColumn;
    public TableColumn<et, String> transportationMethodColumn;
    public TableColumn<et, String> ExternalEmployeeColumn;
    public TableColumn<et, String> ExternalCompletedColumn;

    public TableView<md> medicineDeliveryTable;
    public TableColumn<md, String> typeMedicineColumn;
    public TableColumn<md, String> currentFeelingColumn;
    public TableColumn<md, String> MedicineAllergiesColumn;
    public TableColumn<md, String> MedicinePatientRoomColumn;
    public TableColumn<md, String> MedicineEmployeeColumn;
    public TableColumn<md, String> MedicineCompletedColumn;

    public void initialize() {
        medicineTableSetUp();
        externalTableSetUp();
        foodTableSetUp();
        religiousTableSetUp();
        translatorTableSetUp();
        sanitationTableSetUp();
    }

    private void medicineTableSetUp() {
        typeMedicineColumn = new TableColumn<md, String>("Medicine Type");
        typeMedicineColumn.setMinWidth(100);
        typeMedicineColumn.setCellValueFactory(new PropertyValueFactory<md, String>("typeMedicine"));

        currentFeelingColumn = new TableColumn<md, String>("Feeling");
        currentFeelingColumn.setMinWidth(100);
        currentFeelingColumn.setCellValueFactory(new PropertyValueFactory<md, String>("currentFeeling"));

        MedicineAllergiesColumn = new TableColumn<md, String>("Allergies");
        MedicineAllergiesColumn.setMinWidth(100);
        MedicineAllergiesColumn.setCellValueFactory(new PropertyValueFactory<md, String>("allergies"));

        MedicinePatientRoomColumn = new TableColumn<md, String>("Room");
        MedicinePatientRoomColumn.setMinWidth(100);
        MedicinePatientRoomColumn.setCellValueFactory(new PropertyValueFactory<md, String>("patientRoom"));

        MedicineEmployeeColumn = new TableColumn<md, String>("Assigned To");
        MedicineEmployeeColumn.setMinWidth(100);
        MedicineEmployeeColumn.setCellValueFactory(new PropertyValueFactory<md, String>("employeeAssigned"));

        MedicineCompletedColumn = new TableColumn<md, String>("Status");
        MedicineCompletedColumn.setMinWidth(100);
        MedicineCompletedColumn.setCellValueFactory(new PropertyValueFactory<md, String>("completed"));
        buildMedicine("");
    }

    private void externalTableSetUp() {
        ExternalPatientRoomColumn = new TableColumn<et, String>("Patient Room");
        ExternalPatientRoomColumn.setMinWidth(100);
        ExternalPatientRoomColumn.setCellValueFactory(new PropertyValueFactory<et, String>("patientRoom"));
        //ExternalPatientRoomColumn.setOnEditCommit(this::changePatientRoom);


        currentRoomColumn = new TableColumn<et, String>("Current Room");
        currentRoomColumn.setMinWidth(100);
        currentRoomColumn.setCellValueFactory(new PropertyValueFactory<et, String>("currentRoom"));
        //currentRoomColumn.setOnEditCommit(this::changeCurrentRoom);


        externalRoomColumn = new TableColumn<et, String>("External Room");
        externalRoomColumn.setMinWidth(100);
        externalRoomColumn.setCellValueFactory(new PropertyValueFactory<et, String>("externalRoom"));
        //externalRoomColumn.setOnEditCommit(this::changeExternalRoom);


        transportationMethodColumn = new TableColumn<et, String>("Transportation Method");
        transportationMethodColumn.setMinWidth(100);
        transportationMethodColumn.setCellValueFactory(new PropertyValueFactory<et, String>("transportationMethod"));
        //transportationMethodColumn.setOnEditCommit(this::changeTransport);


        ExternalEmployeeColumn = new TableColumn<et, String>("Employee");
        ExternalEmployeeColumn.setMinWidth(100);
        ExternalEmployeeColumn.setCellValueFactory(new PropertyValueFactory<et, String>("employeeAssigned"));
        //ExternalEmployeeColumn.setOnEditCommit(this::changeEmployee);


        ExternalCompletedColumn = new TableColumn<et, String>("Completed");
        ExternalCompletedColumn.setMinWidth(100);
        ExternalCompletedColumn.setCellValueFactory(new PropertyValueFactory<et, String>("completed"));
        //ExternalCompletedColumn.setOnEditCommit(this::changeCompleted);
        buildExternal("");
    }

    private void foodTableSetUp() {
        dietaryPrefColumn = new TableColumn<fd, String>("Dietary Preferences");
        dietaryPrefColumn.setMinWidth(100);
        dietaryPrefColumn.setCellValueFactory(new PropertyValueFactory<fd, String>("dietaryPref"));
        //dietaryPref.setPrefWidth(foodDeliveryTable.getPrefWidth()/3);

        FoodAllergiesColumn = new TableColumn<fd, String>("Allergies");
        FoodAllergiesColumn.setMinWidth(100);
        FoodAllergiesColumn.setCellValueFactory(new PropertyValueFactory<fd, String>("Allergies"));
        //allergies.setPrefWidth(foodDeliveryTable.getPrefWidth()/3);

        menuOptionColumn = new TableColumn<fd, String>("Menu");
        menuOptionColumn.setMinWidth(100);
        menuOptionColumn.setCellValueFactory(new PropertyValueFactory<fd, String>("Menu"));
        //menuOption.setPrefWidth(foodDeliveryTable.getPrefWidth()/3);

        FoodEmployeeColumn = new TableColumn<fd, String>("Employee");
        FoodEmployeeColumn.setMinWidth(100);
        FoodEmployeeColumn.setCellValueFactory(new PropertyValueFactory<fd, String>("employeeAssigned"));

        FoodCompletedColumn = new TableColumn<fd, String>("Status");
        FoodCompletedColumn.setMinWidth(100);
        FoodCompletedColumn.setCellValueFactory(new PropertyValueFactory<fd, String>("completed"));
        buildFood("");
    }

    private void religiousTableSetUp() {
        reasonVisitColumn = new TableColumn<rr, String>("Reason For Visit");
        reasonVisitColumn.setMinWidth(100);
        reasonVisitColumn.setCellValueFactory(new PropertyValueFactory<rr, String>("reasonVisit"));

        religionLocationColumn = new TableColumn<rr, String>("Location");
        religionLocationColumn.setMinWidth(100);
        religionLocationColumn.setCellValueFactory(new PropertyValueFactory<rr, String>("location"));

        typeSacredPersonColumn = new TableColumn<rr, String>("Type of Sacred Person");
        typeSacredPersonColumn.setMinWidth(100);
        typeSacredPersonColumn.setCellValueFactory(new PropertyValueFactory<rr, String>("typeSacredPerson"));

        religionEmployeeColumn = new TableColumn<rr, String>("Employee Assigned");
        religionEmployeeColumn.setMinWidth(100);
        religionEmployeeColumn.setCellValueFactory(new PropertyValueFactory<rr, String>("employeeAssigned"));

        religionCompletedColumn = new TableColumn<rr, String>("Completed");
        religionCompletedColumn.setMinWidth(100);
        religionCompletedColumn.setCellValueFactory(new PropertyValueFactory<rr, String>("completed"));
        buildReligion("");
    }

    private void translatorTableSetUp() {
        translateRoomColumn = new TableColumn<tl, String>("Room");
        translateRoomColumn.setMinWidth(100);
        translateRoomColumn.setCellValueFactory(new PropertyValueFactory<tl, String>("room"));

        languageOneColumn = new TableColumn<tl, String>("Room");
        languageOneColumn.setMinWidth(100);
        languageOneColumn.setCellValueFactory(new PropertyValueFactory<tl, String>("languageOne"));

        languageTwoColumn = new TableColumn<tl, String>("Room");
        languageTwoColumn.setMinWidth(100);
        languageTwoColumn.setCellValueFactory(new PropertyValueFactory<tl, String>("languageTwo"));

        translateEmployeeColumn = new TableColumn<tl, String>("Assigned To");
        translateEmployeeColumn.setMinWidth(100);
        translateEmployeeColumn.setCellValueFactory(new PropertyValueFactory<tl, String>("employeeAssigned"));

        translateCompletedColumn = new TableColumn<tl, String>("Status");
        translateCompletedColumn.setMinWidth(100);
        translateCompletedColumn.setCellValueFactory(new PropertyValueFactory<tl, String>("completed"));
        buildTranslate("");
    }

    private void sanitationTableSetUp() {
        sanitationLocationColumn = new TableColumn<ss, String>("Location");
        sanitationLocationColumn.setMinWidth(100);
        sanitationLocationColumn.setCellValueFactory(new PropertyValueFactory<ss, String>("location"));


        safetyHazardsColumn = new TableColumn<ss, String>("Safety Hazards");
        safetyHazardsColumn.setMinWidth(100);
        safetyHazardsColumn.setCellValueFactory(new PropertyValueFactory<ss, String>("safetyHazards"));

        sanitationTypeColumn = new TableColumn<ss, String>("Type");
        sanitationTypeColumn.setMinWidth(100);
        sanitationTypeColumn.setCellValueFactory(new PropertyValueFactory<ss, String>("sanitationType"));

        equipmentNeededColumn = new TableColumn<ss, String>("Equipment Needed");
        equipmentNeededColumn.setMinWidth(100);
        equipmentNeededColumn.setCellValueFactory(new PropertyValueFactory<ss, String>("equipmentNeeded"));

        sanitationDescriptionColumn = new TableColumn<ss, String>("Description");
        sanitationDescriptionColumn.setMinWidth(100);
        sanitationDescriptionColumn.setCellValueFactory(new PropertyValueFactory<ss, String>("description"));

        sanitationEmployeeColumn = new TableColumn<ss, String>("Employee");
        sanitationEmployeeColumn.setMinWidth(100);
        sanitationEmployeeColumn.setCellValueFactory(new PropertyValueFactory<ss, String>("employeeAssigned"));

        sanitationCompletedColumn = new TableColumn<ss, String>("Status");
        sanitationCompletedColumn.setMinWidth(100);
        sanitationCompletedColumn.setCellValueFactory(new PropertyValueFactory<ss, String>("completed"));
        buildSanitation("");
    }

    public ObservableList<ss> buildSanitation(String searchTerm) {
        ObservableList<ss> tableRow = FXCollections.observableArrayList();
        LinkedList<ServiceRequest> requests = DatabaseManager.getServiceMap().getServiceRequestsForType(ServiceRequestType.SanitationServices);

        for (ServiceRequest s : requests) {
            ss ssToAdd = new ss(s);
            for (int i = 0; i < ssToAdd.getFields().size(); i++) {
                if (ssToAdd.getFields().get(i).toLowerCase().equals(searchTerm) || searchTerm.equals("")) {
                    tableRow.add(ssToAdd);
                    break;
                }
            }
        }
        sanitationTable.setItems(tableRow);
        sanitationTable.getColumns().setAll(
                sanitationLocationColumn,
                safetyHazardsColumn,
                sanitationTypeColumn,
                equipmentNeededColumn,
                sanitationDescriptionColumn,
                sanitationEmployeeColumn,
                sanitationCompletedColumn);
        return tableRow;
    }

    public ObservableList<tl> buildTranslate(String searchTerm) {
        ObservableList<tl> tableRow = FXCollections.observableArrayList();
        LinkedList<ServiceRequest> requests = DatabaseManager.getServiceMap().getServiceRequestsForType(ServiceRequestType.LanguageInterperter);

        for (ServiceRequest s : requests) {
            tl tlToAdd = new tl(s);
            for (int i = 0; i < tlToAdd.getFields().size(); i++) {
                if (tlToAdd.getFields().get(i).toLowerCase().equals(searchTerm) || searchTerm.equals("")) {
                    tableRow.add(tlToAdd);
                    break;
                }
            }
        }
        translatorTable.setItems(tableRow);
        translatorTable.getColumns().setAll(
                translateRoomColumn,
                languageOneColumn,
                languageTwoColumn,
                translateEmployeeColumn,
                translateCompletedColumn);
        return tableRow;
    }

    public ObservableList<rr> buildReligion(String searchTerm) {
        ObservableList<rr> tableRow = FXCollections.observableArrayList();
        LinkedList<ServiceRequest> requests = DatabaseManager.getServiceMap().getServiceRequestsForType(ServiceRequestType.ReligiousRequest);

        for (ServiceRequest s : requests) {
            rr rrToAdd = new rr(s);
            for (int i = 0; i < rrToAdd.getFields().size(); i++) {
                if (rrToAdd.getFields().get(i).toLowerCase().equals(searchTerm) || searchTerm.equals("")) {
                    tableRow.add(rrToAdd);
                    break;
                }
            }
        }
        religionTable.setItems(tableRow);
        religionTable.getColumns().setAll(
                reasonVisitColumn,
                religionLocationColumn,
                typeSacredPersonColumn,
                religionEmployeeColumn,
                religionCompletedColumn);
        return tableRow;

    }

    public ObservableList<fd> buildFood(String searchTerm) {
        ObservableList<fd> tableRow = FXCollections.observableArrayList();

        LinkedList<ServiceRequest> requests = DatabaseManager.getServiceMap().getServiceRequestsForType(ServiceRequestType.FoodDelivery);

        for (ServiceRequest s : requests) {
            fd fdToAdd = new fd(s);
            for (int i = 0; i < fdToAdd.getFields().size(); i++) {
                if (fdToAdd.getFields().get(i).toLowerCase().equals(searchTerm) || searchTerm.equals("")) {
                    //System.out.println(i + " " + fdToAdd.getDietaryPreference());
                    tableRow.add(fdToAdd);
                    break;
                }
            }
        }
        foodDeliveryTable.setItems(tableRow);
        foodDeliveryTable.getColumns().setAll(
                dietaryPrefColumn,
                FoodAllergiesColumn,
                FoodEmployeeColumn,
                FoodCompletedColumn,
                menuOptionColumn
        );

        return tableRow;
    }

    public ObservableList<et> buildExternal(String searchTerm) {
        ObservableList<et> tableRow = FXCollections.observableArrayList();
        LinkedList<ServiceRequest> requests = DatabaseManager.getServiceMap().getServiceRequestsForType(ServiceRequestType.ExternalTransportation);

        for (ServiceRequest s : requests) {
            et etToAdd = new et(s);
            for (int i = 0; i < etToAdd.getFields().size(); i++) {
                if ((etToAdd.getFields().get(i).toLowerCase().equals(searchTerm) || searchTerm.equals(""))) {
                    tableRow.add(etToAdd);
                    break;
                }
            }
        }

        externalTable.setItems(tableRow);
        externalTable.getColumns().setAll(
                ExternalPatientRoomColumn,
                currentRoomColumn,
                externalRoomColumn,
                transportationMethodColumn,
                ExternalEmployeeColumn,
                ExternalCompletedColumn);
        return tableRow;
    }

    public ObservableList<md> buildMedicine(String searchTerm) {
        ObservableList<md> tableRow = FXCollections.observableArrayList();
        LinkedList<ServiceRequest> requests = DatabaseManager.getServiceMap().getServiceRequestsForType(ServiceRequestType.Medicine);

        for (ServiceRequest s : requests) {
            md mdToAdd = new md(s);
            for (int i = 0; i < mdToAdd.getFields().size(); i++) {
                if (mdToAdd.getFields().get(i).toLowerCase().equals(searchTerm) || searchTerm.equals("")) {
                    tableRow.add(mdToAdd);
                    break;
                }
            }
        }
        medicineDeliveryTable.setItems(tableRow);
        medicineDeliveryTable.getColumns().setAll(
                typeMedicineColumn,
                currentFeelingColumn,
                MedicineAllergiesColumn,
                MedicinePatientRoomColumn,
                MedicineEmployeeColumn,
                MedicineCompletedColumn);
        return tableRow;

    }

    public void helpButton(ActionEvent e) {

        //TODO:make dialog

    }

    public class md extends RecursiveTreeObject<md> {
        edu.wpi.MochaManticores.Services.MedicineRequest ref;
        StringProperty typeMedicine;
        StringProperty currentFeeling;
        StringProperty allergies;
        StringProperty patientRoom;
        StringProperty employeeAssigned;
        boolean completed;
        LinkedList<String> fields;

        public md(edu.wpi.MochaManticores.Services.ServiceRequest ref) {
            this.ref = (edu.wpi.MochaManticores.Services.MedicineRequest) ref;
            typeMedicine = new SimpleStringProperty(this.ref.getTypeMedicine());
            currentFeeling = new SimpleStringProperty(this.ref.getCurrentFeeling());
            allergies = new SimpleStringProperty(this.ref.getAllergies());
            patientRoom = new SimpleStringProperty(this.ref.getPatientRoom());
            employeeAssigned = new SimpleStringProperty(this.ref.getEmployee());
            completed = this.ref.getCompleted();
            fields = new LinkedList<>(Arrays.asList(
                    typeMedicine.get(),
                    currentFeeling.get(),
                    allergies.get(),
                    patientRoom.get()));
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        public String getEmployeeAssigned() {
            return employeeAssigned.get();
        }

        public StringProperty employeeAssignedProperty() {
            return employeeAssigned;
        }

        public String isCompleted() {
            if (completed) {
                return "Completed";
            } else {
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

    public class et extends RecursiveTreeObject<et> {
        edu.wpi.MochaManticores.Services.ExternalTransportation ref;
        StringProperty patientRoom;
        StringProperty currentRoom;
        StringProperty externalRoom;
        StringProperty transportationMethod;
        StringProperty employeeAssigned;
        boolean completed;
        LinkedList<String> fields;

        public et(edu.wpi.MochaManticores.Services.ServiceRequest ref) {
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

        public void setCompleted(boolean completed) {
            this.completed = completed;
            generateFields();

        }

        public void generateFields() {
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

        public void setPatientRoom(String patientRoom) {
            this.patientRoom.set(patientRoom);
            generateFields();
        }

        public StringProperty patientRoomProperty() {
            return patientRoom;
        }

        public String getCurrentRoom() {
            return currentRoom.get();
        }

        public void setCurrentRoom(String currentRoom) {
            this.currentRoom.set(currentRoom);
            generateFields();

        }

        public StringProperty currentRoomProperty() {
            return currentRoom;
        }

        public String getExternalRoom() {
            return externalRoom.get();
        }

        public void setExternalRoom(String externalRoom) {
            this.externalRoom.set(externalRoom);
            generateFields();

        }

        public StringProperty externalRoomProperty() {
            return externalRoom;
        }

        public String getTransportationMethod() {
            return transportationMethod.get();
        }

        public void setTransportationMethod(String transportationMethod) {
            this.transportationMethod.set(transportationMethod);
            generateFields();

        }

        public StringProperty transportationMethodProperty() {
            return transportationMethod;
        }

        public String getEmployeeAssigned() {
            return employeeAssigned.get();
        }

        public void setEmployeeAssigned(String employeeAssigned) {
            this.employeeAssigned.set(employeeAssigned);
            generateFields();

        }

        public StringProperty employeeAssignedProperty() {
            return employeeAssigned;
        }

        public String isCompleted() {
            if (completed) {
                return "Completed";
            } else {
                return "Open";
            }
        }

        public LinkedList<String> getFields() {
            return fields;
        }
    }

    public class fd extends RecursiveTreeObject<fd> {

        edu.wpi.MochaManticores.Services.FoodDelivery ref;
        StringProperty dp;
        StringProperty a;
        StringProperty menu;
        StringProperty employeeAssigned;
        boolean completed;
        LinkedList<String> fields;

        public fd(edu.wpi.MochaManticores.Services.ServiceRequest ref) {
            this.ref = (edu.wpi.MochaManticores.Services.FoodDelivery) ref;
            dp = new SimpleStringProperty(this.ref.getDietaryPreference());
            a = new SimpleStringProperty(this.ref.getAllergies());
            menu = new SimpleStringProperty(this.ref.getMenu());
            employeeAssigned = new SimpleStringProperty(this.ref.getEmployee());
            completed = this.ref.getCompleted();
            fields = new LinkedList<>(Arrays.asList(dp.get(), a.get(), menu.get()));
        }

        public String getEmployeeAssigned() {
            return employeeAssigned.get();
        }

        public StringProperty employeeAssignedProperty() {
            return employeeAssigned;
        }

        public String isCompleted() {
            if (completed) {
                return "Completed";
            } else {
                return "Open";
            }
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        public LinkedList<String> getFields() {
            return fields;
        }

        public edu.wpi.MochaManticores.Services.FoodDelivery getRef() {
            return ref;
        }

        public String getDietaryPref() {
            return dp.get();
        }

        public String getAllergies() {
            return a.get();
        }

        public String getMenu() {
            return menu.get();
        }
    }

    public class rr extends RecursiveTreeObject<rr> {

        edu.wpi.MochaManticores.Services.ReligiousRequest ref;
        StringProperty reasonVisit;
        StringProperty location;
        StringProperty typeSacredPerson;
        StringProperty employeeAssigned;
        boolean completed;
        LinkedList<String> fields;

        public rr(edu.wpi.MochaManticores.Services.ServiceRequest ref) {
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

        public void setCompleted(boolean completed) {
            this.completed = completed;
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
            if (completed) {
                return "Completed";
            } else {
                return "Open";
            }
        }

        public LinkedList<String> getFields() {
            return fields;
        }
    }

    public class tl extends RecursiveTreeObject<tl> {
        edu.wpi.MochaManticores.Services.LanguageInterpreterRequest ref;
        StringProperty room;
        StringProperty languageOne;
        StringProperty languageTwo;
        StringProperty employeeAssigned;
        boolean completed;
        LinkedList<String> fields;

        public tl(edu.wpi.MochaManticores.Services.ServiceRequest ref) {
            this.ref = (edu.wpi.MochaManticores.Services.LanguageInterpreterRequest) ref;
            room = new SimpleStringProperty(this.ref.getRoom());
            languageOne = new SimpleStringProperty(this.ref.getLanguageOne());
            languageTwo = new SimpleStringProperty(this.ref.getLanguageTwo());
            employeeAssigned = new SimpleStringProperty(this.ref.getEmployee());
            completed = this.ref.getCompleted();
            fields = new LinkedList<>(Arrays.asList(
                    room.get(),
                    languageOne.get(),
                    languageTwo.get()));
        }


        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        public LanguageInterpreterRequest getRef() {
            return ref;
        }

        public String getRoom() {
            return room.get();
        }

        public StringProperty roomProperty() {
            return room;
        }

        public String getLanguageOne() {
            return languageOne.get();
        }

        public StringProperty languageOneProperty() {
            return languageOne;
        }

        public String getLanguageTwo() {
            return languageTwo.get();
        }

        public StringProperty languageTwoProperty() {
            return languageTwo;
        }

        public String getEmployeeAssigned() {
            return employeeAssigned.get();
        }

        public StringProperty employeeAssignedProperty() {
            return employeeAssigned;
        }

        public String isCompleted() {
            if (completed) {
                return "Completed";
            } else {
                return "Open";
            }
        }

        public LinkedList<String> getFields() {
            return fields;
        }
    }

    public class ss extends RecursiveTreeObject<ss> {
        edu.wpi.MochaManticores.Services.SanitationServices ref;
        StringProperty location;
        StringProperty safetyHazards;
        StringProperty sanitationType;
        StringProperty equipmentNeeded;
        StringProperty description;
        StringProperty employeeAssigned;
        boolean completed;
        LinkedList<String> fields;

        public ss(edu.wpi.MochaManticores.Services.ServiceRequest ref) {
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
            if (completed) {
                return "Completed";
            } else {
                return "Open";
            }
        }

        public LinkedList<String> getFields() {
            return fields;
        }
    }


}














































































//1000