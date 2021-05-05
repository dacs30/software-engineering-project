package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.MochaManticores.Services.*;
import edu.wpi.MochaManticores.database.DatabaseManager;
import edu.wpi.MochaManticores.database.sel;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.util.Arrays;
import java.util.LinkedList;


public class serviceManagerController extends SceneController {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private VBox contextBox;

    @FXML
    private Group completeEntry;

    @FXML
    private Group progressEntry;

    @FXML
    private Group deleteEntry;

    @FXML
    private VBox covidContext;

    @FXML
    private Group admitted;

    @FXML
    private Group restricted;


    
    public TableView<ss> sanitationTable;
    public TableColumn<ss, String> sanitationLocationColumn;
    public TableColumn<ss, String> safetyHazardsColumn;
    public TableColumn<ss, String> sanitationTypeColumn;
    public TableColumn<ss, String> equipmentNeededColumn;
    public TableColumn<ss, String> sanitationDescriptionColumn;
    public TableColumn<ss, JFXComboBox> sanitationEmployeeColumn;
    public TableColumn<ss, JFXButton> sanitationCompletedColumn;

    public TableView<tl> translatorTable;
    public TableColumn<tl, String> translateRoomColumn;
    public TableColumn<tl, String> languageOneColumn;
    public TableColumn<tl, String> languageTwoColumn;
    public TableColumn<tl, JFXComboBox> translateEmployeeColumn;
    public TableColumn<tl, JFXButton> translateCompletedColumn;

    public TableView<rr> religionTable;
    public TableColumn<rr, String> reasonVisitColumn;
    public TableColumn<rr, String> religionLocationColumn;
    public TableColumn<rr, String> typeSacredPersonColumn;
    public TableColumn<rr, JFXComboBox> religionEmployeeColumn;
    public TableColumn<rr, JFXButton> religionCompletedColumn;

    public TableView<fd> foodDeliveryTable;
    public TableColumn<fd, String> dietaryPrefColumn;
    public TableColumn<fd, String> FoodAllergiesColumn;
    public TableColumn<fd, JFXComboBox> FoodEmployeeColumn;
    public TableColumn<fd, JFXButton> FoodCompletedColumn;
    public TableColumn<fd, String> menuOptionColumn;

    public TableView<et> externalTable;
    public TableColumn<et, String> ExternalPatientRoomColumn;
    public TableColumn<et, String> currentRoomColumn;
    public TableColumn<et, String> externalRoomColumn;
    public TableColumn<et, String> transportationMethodColumn;
    public TableColumn<et, JFXComboBox> ExternalEmployeeColumn;
    public TableColumn<et, JFXButton> ExternalCompletedColumn;

    public TableView<md> medicineDeliveryTable;
    public TableColumn<md, String> typeMedicineColumn;
    public TableColumn<md, String> currentFeelingColumn;
    public TableColumn<md, String> MedicineAllergiesColumn;
    public TableColumn<md, String> MedicinePatientRoomColumn;
    public TableColumn<md, JFXComboBox> MedicineEmployeeColumn;
    public TableColumn<md, JFXButton> MedicineCompletedColumn;

    public TableView<it> internalTable;
    public TableColumn<it, String> internalPatientIDTableColumn;
    public TableColumn<it, String> internalNumStaffNeededTableColumn;
    public TableColumn<it, String> internalDestinationTableColumn;
    public TableColumn<it, String> internalTransportationMethodsTableColumn;
    public TableColumn<it, JFXComboBox> internalEmployeeColumn;
    public TableColumn<it, JFXButton> internalCompletedColumn;

    public TableView<cs> covidTable;
    public TableColumn<cs, String> nameColumn;
    public TableColumn<cs, String> DOBColumn;
    public TableColumn<cs, String> sickColumn;
    public TableColumn<cs, String> vaxxColumn;
    public TableColumn<cs, String> travelColumn;
    public TableColumn<cs, String> contactColumn;
    public TableColumn<cs, JFXButton> admitColumn;
    public TableColumn<cs, String> testColumn;
    public TableColumn<cs, JFXButton> CovidCompleteColumn;

    public TableView<er> emergencyTable;
    public TableColumn<er,String>EmergencyNumPeopleNeededColumn;
    public TableColumn<er,String>EmergencyLocationColumn;
    public TableColumn<er,String>EmergencyGurneyColumn;
    public TableColumn<er, JFXComboBox> EmergencyEmployeeColumn;
    public TableColumn<er, JFXButton> EmergencyCompletedColumn;


    private EventHandler<MouseEvent> showBox = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            contextBox.setVisible(true);
            contextBox.setDisable(false);
            contextBox.toFront();
            completeEntry.setVisible(true);
            progressEntry.setVisible(true);
            deleteEntry.setVisible(true);


            Point2D p = new Point2D(event.getSceneX(), event.getSceneY());
            p = mainPane.sceneToLocal(p);
            contextBox.relocate(p.getX(), p.getY());
        }
    };


    public JFXTabPane serviceTabPane;

    public void initialize() {
        contextBox.setVisible(false);
        contextBox.toBack();

        covidContext.setVisible(false);
        covidContext.toBack();



        medicineTableSetUp();
        externalTableSetUp();
        foodTableSetUp();
        religiousTableSetUp();
        translatorTableSetUp();
        sanitationTableSetUp();
        internalTableSetUp();
        covidTableSetUp();
        emergencyTableSetUp();
    }

    private void emergencyTableSetUp(){

        EmergencyNumPeopleNeededColumn = new TableColumn<er, String>("Staff needed");
        EmergencyNumPeopleNeededColumn.setMinWidth(100);
        EmergencyNumPeopleNeededColumn.setCellValueFactory(new PropertyValueFactory<er, String>("numPeopleNeeded"));

        EmergencyLocationColumn = new TableColumn<er, String>("Location");
        EmergencyLocationColumn.setMinWidth(100);
        EmergencyLocationColumn.setCellValueFactory(new PropertyValueFactory<er, String>("location"));

        EmergencyGurneyColumn = new TableColumn<er, String>("Gurney");
        EmergencyGurneyColumn.setMinWidth(100);
        EmergencyGurneyColumn.setCellValueFactory(new PropertyValueFactory<er, String>("gurney"));

        EmergencyEmployeeColumn = new TableColumn<er, JFXComboBox>("Assigned To");
        EmergencyEmployeeColumn.setMinWidth(100);
        EmergencyEmployeeColumn.setCellValueFactory(arg0 -> {

            JFXComboBox<String> emps = new JFXComboBox<>();
            emps.setEditable(true);

            ObservableList<String> people = FXCollections.observableArrayList();
            DatabaseManager.getEmployeeNames().forEach(s -> {
                people.add(s.substring(s.indexOf(" ")));
            });
            people.add("");
            emps.setItems(people);

            serviceStatus stat;

            er user = arg0.getValue();

            CheckBox checkBox = new CheckBox();
            JFXButton state = new JFXButton();



            emps.setValue(user.getEmployee());

            emps.getSelectionModel().selectedItemProperty().addListener((ov, old_val, new_val) -> {
                String newEmp = emps.getSelectionModel().getSelectedItem();
                user.getRef().setEmployee(newEmp);
                DatabaseManager.modRequest(sel.Medicine,user.getRef().getRequestID(),user.getRef());
                buildEmergency("");
            });

//            checkBox.selectedProperty().addListener((ov, old_val, new_val) -> {
//
//                setCompleteService(new_val, user);
//                //user.setCompleted(new_val);
//
//            });


            return new SimpleObjectProperty<JFXComboBox>(emps);


        });

        EmergencyCompletedColumn = new TableColumn<er, JFXButton>("Status");
        EmergencyCompletedColumn.setMinWidth(100);
        EmergencyCompletedColumn.setCellValueFactory(arg0 -> {

            serviceStatus stat;

            er user = arg0.getValue();

            CheckBox checkBox = new CheckBox();
            JFXButton state = new JFXButton();



            if(user.getEmployee().equals("")){
                stat = serviceStatus.UNASSIGNED;
                state.setStyle("-fx-background-color: #FF0000;");
            }else{
                if(user.isCompleted()){
                    stat = serviceStatus.COMPLETED;
                    state.setStyle("-fx-background-color: #00FF00;");
                }else{
                    stat = serviceStatus.PROGRESS;
                    state.setStyle("-fx-background-color: #0f4b91;");
                }
            }
            state.setText(stat.name());
            completeEntry.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    contextBox.setVisible(false);
                    contextBox.toBack();
                    completeEntry.setVisible(false);
                    progressEntry.setVisible(false);
                    deleteEntry.setVisible(false);
                    setCompleteService(true,user);
                }
            });

            progressEntry.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    setCompleteService(false,user);
                    contextBox.setVisible(false);
                    contextBox.toBack();
                    completeEntry.setVisible(false);
                    progressEntry.setVisible(false);
                    deleteEntry.setVisible(false);
                    setCompleteService(false,user);
                }
            });

            deleteEntry.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    DatabaseManager.delElement(sel.Emergency,user.getRef().getRequestID());
                    buildEmergency("");
                    contextBox.setVisible(false);
                    contextBox.toBack();
                    completeEntry.setVisible(false);
                    progressEntry.setVisible(false);
                    deleteEntry.setVisible(false);
                }
            });

            checkBox.selectedProperty().setValue(user.isCompleted());
            state.setOnMouseClicked(showBox);



//            checkBox.selectedProperty().addListener((ov, old_val, new_val) -> {
//
//                setCompleteService(new_val, user);
//                //user.setCompleted(new_val);
//
//            });

            return new SimpleObjectProperty<JFXButton>(state);


        });


        /*
       EmergencyNumPeopleNeededColumn
EmergencyLocationColumn
EmergencyGurneyColumn
EmergencyEmployeeColumn
EmergencyCompletedColumn
         */
        buildEmergency("");
    }

    private void internalTableSetUp(){
        internalPatientIDTableColumn = new TableColumn<it, String>("Patient");
        internalPatientIDTableColumn.setMinWidth(100);
        internalPatientIDTableColumn.setCellValueFactory(new PropertyValueFactory<it, String>("PatientIDTable"));

        internalNumStaffNeededTableColumn = new TableColumn<it, String>("Number of Staff");
        internalNumStaffNeededTableColumn.setMinWidth(100);
        internalNumStaffNeededTableColumn.setCellValueFactory(new PropertyValueFactory<it, String>("NumStaffNeededTable"));

        internalDestinationTableColumn = new TableColumn<it, String>("Destination");
        internalDestinationTableColumn.setMinWidth(100);
        internalDestinationTableColumn.setCellValueFactory(new PropertyValueFactory<it, String>("DestinationTable"));

        internalTransportationMethodsTableColumn = new TableColumn<it, String>("Transportation Method");
        internalTransportationMethodsTableColumn.setMinWidth(100);
        internalTransportationMethodsTableColumn.setCellValueFactory(new PropertyValueFactory<it, String>("TransportationMethodsTable"));

        internalEmployeeColumn = new TableColumn<it, JFXComboBox>("Employee");
        internalEmployeeColumn.setMinWidth(100);
        internalEmployeeColumn.setCellValueFactory(arg0 -> {
            JFXComboBox<String> emps = new JFXComboBox<>();
            emps.setEditable(true);

            ObservableList<String> people = FXCollections.observableArrayList();
            DatabaseManager.getEmployeeNames().forEach(s -> {
                people.add(s.substring(s.indexOf(" ")));
            });
            people.add("");
            emps.setItems(people);
            serviceStatus stat;

            it user = arg0.getValue();

            CheckBox checkBox = new CheckBox();
            JFXButton state = new JFXButton();

            emps.setValue(user.getEmployee());

            emps.getSelectionModel().selectedItemProperty().addListener((ov, old_val, new_val) -> {
                String newEmp = emps.getSelectionModel().getSelectedItem();
                user.getRef().setEmployee(newEmp);
                DatabaseManager.modRequest(sel.InternalTransportation,user.getRef().getRequestID(),user.getRef());
                buildInternal("");
            });

//            checkBox.selectedProperty().addListener((ov, old_val, new_val) -> {
//
//                setCompleteService(new_val, user);
//                //user.setCompleted(new_val);
//
//            });


            return new SimpleObjectProperty<JFXComboBox>(emps);


        });

        internalCompletedColumn = new TableColumn<it, JFXButton>("Status");
        internalCompletedColumn.setMinWidth(100);
        internalCompletedColumn.setCellValueFactory(arg0 -> {

            serviceStatus stat;

            it user = arg0.getValue();

            CheckBox checkBox = new CheckBox();
            JFXButton state = new JFXButton();



            if(user.getEmployee().equals("")){
                stat = serviceStatus.UNASSIGNED;
                state.setStyle("-fx-background-color: #FF0000;");
            }else{
                if(user.checkCompleted()){
                    stat = serviceStatus.COMPLETED;
                    state.setStyle("-fx-background-color: #00FF00;");
                }else{
                    stat = serviceStatus.PROGRESS;
                    state.setStyle("-fx-background-color: #0f4b91;");
                }
            }
            state.setText(stat.name());
            completeEntry.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    System.out.println("Complete");
                    contextBox.setVisible(false);
                    contextBox.toBack();
                    completeEntry.setVisible(false);
                    progressEntry.setVisible(false);
                    deleteEntry.setVisible(false);
                    setCompleteService(true,user);
                }
            });

            progressEntry.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    contextBox.setVisible(false);
                    contextBox.toBack();
                    completeEntry.setVisible(false);
                    progressEntry.setVisible(false);
                    deleteEntry.setVisible(false);
                    setCompleteService(false,user);
                }
            });

            deleteEntry.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    DatabaseManager.delElement(sel.InternalTransportation,user.getRef().getRequestID());
                    buildInternal("");
                    contextBox.setVisible(false);
                    contextBox.toBack();
                    completeEntry.setVisible(false);
                    progressEntry.setVisible(false);
                    deleteEntry.setVisible(false);
                }
            });

            checkBox.selectedProperty().setValue(user.checkCompleted());
            state.setOnMouseClicked(showBox);



//            checkBox.selectedProperty().addListener((ov, old_val, new_val) -> {
//
//                setCompleteService(new_val, user);
//                //user.setCompleted(new_val);
//
//            });

            return new SimpleObjectProperty<JFXButton>(state);


        });
        buildInternal("");
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

        MedicineEmployeeColumn = new TableColumn<md, JFXComboBox>("Assigned To");
        MedicineEmployeeColumn.setMinWidth(100);
        MedicineEmployeeColumn.setCellValueFactory(arg0 -> {
            JFXComboBox<String> emps = new JFXComboBox<>();
            emps.setEditable(true);

            ObservableList<String> people = FXCollections.observableArrayList();
            DatabaseManager.getEmployeeNames().forEach(s -> {
                people.add(s.substring(s.indexOf(" ")));
            });
            people.add("");
            emps.setItems(people);
            serviceStatus stat;

            md user = arg0.getValue();

            CheckBox checkBox = new CheckBox();
            JFXButton state = new JFXButton();

//            if(user.getEmployeeAssigned().equals("")){
//                emps.setValue("Unassigned");
//            }else{
                emps.setValue(user.employeeAssigned.get());
            //}


            emps.getSelectionModel().selectedItemProperty().addListener((ov, old_val, new_val) -> {
                String newEmp = emps.getSelectionModel().getSelectedItem();
                user.getRef().setEmployee(newEmp);
                user.getRef().setCompleted(false);
                DatabaseManager.modRequest(sel.Medicine,user.getRef().getRequestID(),user.getRef());
                buildMedicine("");
            });

//            checkBox.selectedProperty().addListener((ov, old_val, new_val) -> {
//
//                setCompleteService(new_val, user);
//                //user.setCompleted(new_val);
//
//            });


            return new SimpleObjectProperty<JFXComboBox>(emps);


        });

        MedicineCompletedColumn = new TableColumn<md, JFXButton>("Status");
        MedicineCompletedColumn.setMinWidth(100);
        MedicineCompletedColumn.setCellValueFactory(arg0 -> {

            serviceStatus stat;

            md user = arg0.getValue();

            CheckBox checkBox = new CheckBox();
            JFXButton state = new JFXButton();

            serviceTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
                if(newValue.getText().equals("Medicine Delivery")){
                    completeEntry.setOnMouseClicked(event -> {
                        contextBox.setVisible(false);
                        contextBox.toBack();
                        completeEntry.setVisible(false);
                        progressEntry.setVisible(false);
                        deleteEntry.setVisible(false);
                        setCompleteService(true,user);
                    });

                    progressEntry.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            setCompleteService(false,user);
                            contextBox.setVisible(false);
                            contextBox.toBack();
                            completeEntry.setVisible(false);
                            progressEntry.setVisible(false);
                            deleteEntry.setVisible(false);
                            setCompleteService(false,user);
                        }
                    });

                    deleteEntry.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            DatabaseManager.delElement(sel.Medicine,user.getRef().getRequestID());
                            buildMedicine("");
                            contextBox.setVisible(false);
                            contextBox.toBack();
                            completeEntry.setVisible(false);
                            progressEntry.setVisible(false);
                            deleteEntry.setVisible(false);
                        }
                    });
                }
            });

            if(user.getEmployeeAssigned().equals("")){
                stat = serviceStatus.UNASSIGNED;
                state.setStyle("-fx-background-color: #FF0000;");
            }else{
                if(user.checkCompleted()){
                    stat = serviceStatus.COMPLETED;
                    state.setStyle("-fx-background-color: #00FF00;");
                }else{
                    stat = serviceStatus.PROGRESS;
                    state.setStyle("-fx-background-color: #0f4b91;");
                }
            }
            state.setText(stat.name());


            checkBox.selectedProperty().setValue(user.checkCompleted());
            state.setOnMouseClicked(showBox);


//            checkBox.selectedProperty().addListener((ov, old_val, new_val) -> {
//
//                setCompleteService(new_val, user);
//                //user.setCompleted(new_val);
//
//            });

            return new SimpleObjectProperty<JFXButton>(state);


        });

//        MedicineCompletedColumn.setCellFactory(tc -> new CheckBoxTableCell<>());
//        MedicineCompletedColumn.setOnEditStart(this::edit);
        buildMedicine("");
    }

    private void externalTableSetUp() {
        ExternalPatientRoomColumn = new TableColumn<et, String>("Patient Room");
        ExternalPatientRoomColumn.setMinWidth(100);
        ExternalPatientRoomColumn.setCellValueFactory(new PropertyValueFactory<et, String>("patientRoom"));


        currentRoomColumn = new TableColumn<et, String>("Current Room");
        currentRoomColumn.setMinWidth(100);
        currentRoomColumn.setCellValueFactory(new PropertyValueFactory<et, String>("currentRoom"));


        externalRoomColumn = new TableColumn<et, String>("External Room");
        externalRoomColumn.setMinWidth(100);
        externalRoomColumn.setCellValueFactory(new PropertyValueFactory<et, String>("externalRoom"));


        transportationMethodColumn = new TableColumn<et, String>("Transportation Method");
        transportationMethodColumn.setMinWidth(100);
        transportationMethodColumn.setCellValueFactory(new PropertyValueFactory<et, String>("transportationMethod"));


        ExternalEmployeeColumn = new TableColumn<et, JFXComboBox>("Employee");
        ExternalEmployeeColumn.setMinWidth(100);
        ExternalEmployeeColumn.setCellValueFactory(arg0 -> {
            JFXComboBox<String> emps = new JFXComboBox<>();
            emps.setEditable(true);

            ObservableList<String> people = FXCollections.observableArrayList();
            DatabaseManager.getEmployeeNames().forEach(s -> {
                people.add(s.substring(s.indexOf(" ")));
            });
            people.add("");
            emps.setItems(people);
            serviceStatus stat;

            et user = arg0.getValue();

            CheckBox checkBox = new CheckBox();
            JFXButton state = new JFXButton();

            emps.setValue(user.employeeAssigned.get());

            emps.getSelectionModel().selectedItemProperty().addListener((ov, old_val, new_val) -> {
                String newEmp = emps.getSelectionModel().getSelectedItem();
                user.getRef().setEmployee(newEmp);
                DatabaseManager.modRequest(sel.ExternalTransportation,user.getRef().getRequestID(),user.getRef());
                buildExternal("");
            });

//            checkBox.selectedProperty().addListener((ov, old_val, new_val) -> {
//
//                setCompleteService(new_val, user);
//                //user.setCompleted(new_val);
//
//            });


            return new SimpleObjectProperty<JFXComboBox>(emps);


        });


        ExternalCompletedColumn = new TableColumn<et, JFXButton>("Completed");
        ExternalCompletedColumn.setMinWidth(100);
        ExternalCompletedColumn.setCellValueFactory(arg0 -> {

            serviceStatus stat;

            et user = arg0.getValue();

            CheckBox checkBox = new CheckBox();
            JFXButton state = new JFXButton();


            serviceTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
                if(newValue.getText().equals("External Transport")){
                    completeEntry.setOnMouseClicked(event -> {
                        contextBox.setVisible(false);
                        contextBox.toBack();
                        completeEntry.setVisible(false);
                        progressEntry.setVisible(false);
                        deleteEntry.setVisible(false);
                        setCompleteService(true,user);
                    });

                    progressEntry.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            contextBox.setVisible(false);
                            contextBox.toBack();
                            completeEntry.setVisible(false);
                            progressEntry.setVisible(false);
                            deleteEntry.setVisible(false);
                            setCompleteService(false,user);
                        }
                    });

                    deleteEntry.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            DatabaseManager.delElement(sel.Medicine,user.getRef().getRequestID());
                            buildMedicine("");
                            contextBox.setVisible(false);
                            contextBox.toBack();
                            completeEntry.setVisible(false);
                            progressEntry.setVisible(false);
                            deleteEntry.setVisible(false);
                        }
                    });
                }
            });
            if(user.getEmployeeAssigned().equals("")){
                stat = serviceStatus.UNASSIGNED;
                state.setStyle("-fx-background-color: #FF0000;");
            }else{
                if(user.checkCompleted()){
                    stat = serviceStatus.COMPLETED;
                    state.setStyle("-fx-background-color: #00FF00;");
                }else{
                    stat = serviceStatus.PROGRESS;
                    state.setStyle("-fx-background-color: #0f4b91;");
                }
            }
            state.setText(stat.name());

            checkBox.selectedProperty().setValue(user.checkCompleted());
            state.setOnMouseClicked(showBox);


            return new SimpleObjectProperty<JFXButton>(state);


        });
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

        FoodEmployeeColumn = new TableColumn<fd, JFXComboBox>("Employee");
        FoodEmployeeColumn.setMinWidth(100);
        FoodEmployeeColumn.setCellValueFactory(arg0 -> {
            JFXComboBox<String> emps = new JFXComboBox<>();
            emps.setEditable(true);

            ObservableList<String> people = FXCollections.observableArrayList();
            DatabaseManager.getEmployeeNames().forEach(s -> {
                people.add(s.substring(s.indexOf(" ")));
            });
            people.add("");
            emps.setItems(people);
            serviceStatus stat;

            fd user = arg0.getValue();

            CheckBox checkBox = new CheckBox();
            JFXButton state = new JFXButton();

            emps.setValue(user.employeeAssigned.get());

            emps.getSelectionModel().selectedItemProperty().addListener((ov, old_val, new_val) -> {
                String newEmp = emps.getSelectionModel().getSelectedItem();
                user.getRef().setEmployee(newEmp);
                DatabaseManager.modRequest(sel.FoodDelivery,user.getRef().getRequestID(),user.getRef());
                buildFood("");
            });

//            checkBox.selectedProperty().addListener((ov, old_val, new_val) -> {
//
//                setCompleteService(new_val, user);
//                //user.setCompleted(new_val);
//
//            });


            return new SimpleObjectProperty<JFXComboBox>(emps);


        });

        FoodCompletedColumn = new TableColumn<fd, JFXButton>("Status");
        FoodCompletedColumn.setMinWidth(100);
        //FoodCompletedColumn.setCellValueFactory(new PropertyValueFactory<fd, String>("completed"));
//        FoodCompletedColumn.setCellValueFactory(arg0 -> {
//            fd user = arg0.getValue();
//
//            CheckBox checkBox = new CheckBox();
//
//            checkBox.selectedProperty().setValue(user.checkCompleted());
//
//
//
//            checkBox.selectedProperty().addListener((ov, old_val, new_val) -> {
//
//                setCompleteService(new_val, user);
//                //user.setCompleted(new_val);
//
//            });
//
//            return new SimpleObjectProperty<>(checkBox);
//
//        });
        FoodCompletedColumn.setCellValueFactory(arg0 -> {

            serviceStatus stat;

            fd user = arg0.getValue();

            CheckBox checkBox = new CheckBox();
            JFXButton state = new JFXButton();



            if(user.getEmployeeAssigned().equals("")){
                stat = serviceStatus.UNASSIGNED;
                state.setStyle("-fx-background-color: #FF0000;");
            }else{
                if(user.checkCompleted()){
                    stat = serviceStatus.COMPLETED;
                    state.setStyle("-fx-background-color: #00FF00;");
                }else{
                    stat = serviceStatus.PROGRESS;
                    state.setStyle("-fx-background-color: #0f4b91;");
                }
            }
            //state.setStyle("-fx-text-fill: #FFFFFF;");
            //state.setStyle("-fx-font-weight: bolder;");
            state.setText(stat.name());

            completeEntry.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    setCompleteService(true,user);
                    contextBox.setVisible(false);
                    contextBox.toBack();
                    completeEntry.setVisible(false);
                    progressEntry.setVisible(false);
                    deleteEntry.setVisible(false);
                    setCompleteService(true,user);
                }
            });

            progressEntry.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    setCompleteService(false,user);
                    contextBox.setVisible(false);
                    contextBox.toBack();
                    completeEntry.setVisible(false);
                    progressEntry.setVisible(false);
                    deleteEntry.setVisible(false);
                    setCompleteService(false,user);
                }
            });

            deleteEntry.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    DatabaseManager.delElement(sel.FoodDelivery,user.getRef().getRequestID());
                    buildFood("");
                    contextBox.setVisible(false);
                    contextBox.toBack();
                    completeEntry.setVisible(false);
                    progressEntry.setVisible(false);
                    deleteEntry.setVisible(false);
                    buildFood("");
                }
            });

            checkBox.selectedProperty().setValue(user.checkCompleted());
            state.setOnMouseClicked(showBox);


//            checkBox.selectedProperty().addListener((ov, old_val, new_val) -> {
//
//                setCompleteService(new_val, user);
//                //user.setCompleted(new_val);
//
//            });

            return new SimpleObjectProperty<JFXButton>(state);


        });
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

        religionEmployeeColumn = new TableColumn<rr, JFXComboBox>("Employee Assigned");
        religionEmployeeColumn.setMinWidth(100);
        religionEmployeeColumn.setCellValueFactory(arg0 -> {
            JFXComboBox<String> emps = new JFXComboBox<>();
            emps.setEditable(true);

            ObservableList<String> people = FXCollections.observableArrayList();
            DatabaseManager.getEmployeeNames().forEach(s -> {
                people.add(s.substring(s.indexOf(" ")));
            });
            people.add("");
            emps.setItems(people);
            serviceStatus stat;

            rr user = arg0.getValue();

            CheckBox checkBox = new CheckBox();
            JFXButton state = new JFXButton();

            emps.setValue(user.employeeAssigned.get());

            emps.getSelectionModel().selectedItemProperty().addListener((ov, old_val, new_val) -> {
                String newEmp = emps.getSelectionModel().getSelectedItem();
                user.getRef().setEmployee(newEmp);
                DatabaseManager.modRequest(sel.ReligiousRequest,user.getRef().getRequestID(),user.getRef());
                buildReligion("");
            });

//            checkBox.selectedProperty().addListener((ov, old_val, new_val) -> {
//
//                setCompleteService(new_val, user);
//                //user.setCompleted(new_val);
//
//            });


            return new SimpleObjectProperty<JFXComboBox>(emps);


        });

        religionCompletedColumn = new TableColumn<rr, JFXButton>("Completed");
        religionCompletedColumn.setMinWidth(100);
        //religionCompletedColumn.setCellValueFactory(new PropertyValueFactory<rr, String>("completed"));
//        religionCompletedColumn.setCellValueFactory(arg0 -> {
//            rr user = arg0.getValue();
//
//            CheckBox checkBox = new CheckBox();
//
//            checkBox.selectedProperty().setValue(user.checkCompleted());
//
//
//
//            checkBox.selectedProperty().addListener((ov, old_val, new_val) -> {
//
//                setCompleteService(new_val, user);
//                //user.setCompleted(new_val);
//
//            });
//
//            return new SimpleObjectProperty<>(checkBox);
//
//        });
        religionCompletedColumn.setCellValueFactory(arg0 -> {

            serviceStatus stat;

            rr user = arg0.getValue();

            CheckBox checkBox = new CheckBox();
            JFXButton state = new JFXButton();



            if(user.getEmployeeAssigned().equals("")){
                stat = serviceStatus.UNASSIGNED;
                state.setStyle("-fx-background-color: #FF0000;");
            }else{
                if(user.checkCompleted()){
                    stat = serviceStatus.COMPLETED;
                    state.setStyle("-fx-background-color: #00FF00;");
                }else{
                    stat = serviceStatus.PROGRESS;
                    state.setStyle("-fx-background-color: #0f4b91;");
                }
            }
            state.setText(stat.name());
            completeEntry.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    setCompleteService(true,user);
                    contextBox.setVisible(false);
                    contextBox.toBack();
                    completeEntry.setVisible(false);
                    progressEntry.setVisible(false);
                    deleteEntry.setVisible(false);
                    setCompleteService(true,user);
                }
            });

            progressEntry.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    setCompleteService(false,user);
                    contextBox.setVisible(false);
                    contextBox.toBack();
                    completeEntry.setVisible(false);
                    progressEntry.setVisible(false);
                    deleteEntry.setVisible(false);
                    setCompleteService(false,user);
                }
            });

            deleteEntry.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    DatabaseManager.delElement(sel.ReligiousRequest,user.getRef().getRequestID());
                    buildReligion("");
                    contextBox.setVisible(false);
                    contextBox.toBack();
                    completeEntry.setVisible(false);
                    progressEntry.setVisible(false);
                    deleteEntry.setVisible(false);
                }
            });

            checkBox.selectedProperty().setValue(user.checkCompleted());
            state.setOnMouseClicked(showBox);


//            checkBox.selectedProperty().addListener((ov, old_val, new_val) -> {
//
//                setCompleteService(new_val, user);
//                //user.setCompleted(new_val);
//
//            });

            return new SimpleObjectProperty<JFXButton>(state);


        });


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

        translateEmployeeColumn = new TableColumn<tl, JFXComboBox>("Assigned To");
        translateEmployeeColumn.setMinWidth(100);
        translateEmployeeColumn.setCellValueFactory(arg0 -> {
            JFXComboBox<String> emps = new JFXComboBox<>();
            emps.setEditable(true);

            ObservableList<String> people = FXCollections.observableArrayList();
            DatabaseManager.getEmployeeNames().forEach(s -> {
                people.add(s.substring(s.indexOf(" ")));
            });
            people.add("");
            emps.setItems(people);
            serviceStatus stat;

            tl user = arg0.getValue();

            CheckBox checkBox = new CheckBox();
            JFXButton state = new JFXButton();

            if(user.employeeAssigned.get().equals("")){

            }
            emps.setValue(user.employeeAssigned.get());

            emps.getSelectionModel().selectedItemProperty().addListener((ov, old_val, new_val) -> {
                String newEmp = emps.getSelectionModel().getSelectedItem();
                user.getRef().setEmployee(newEmp);
                DatabaseManager.modRequest(sel.LanguageInterperter,user.getRef().getRequestID(),user.getRef());
                buildTranslate("");
            });

//            checkBox.selectedProperty().addListener((ov, old_val, new_val) -> {
//
//                setCompleteService(new_val, user);
//                //user.setCompleted(new_val);
//
//            });


            return new SimpleObjectProperty<JFXComboBox>(emps);


        });

        translateCompletedColumn = new TableColumn<tl, JFXButton>("Status");
        translateCompletedColumn.setMinWidth(100);
       //translateCompletedColumn.setCellValueFactory(new PropertyValueFactory<tl, String>("completed"));
//        translateCompletedColumn.setCellValueFactory(arg0 -> {
//            tl user = arg0.getValue();
//
//            CheckBox checkBox = new CheckBox();
//
//            checkBox.selectedProperty().setValue(user.checkCompleted());
//
//
//
//            checkBox.selectedProperty().addListener((ov, old_val, new_val) -> {
//
//                setCompleteService(new_val, user);
//                //user.setCompleted(new_val);
//
//            });
//
//            return new SimpleObjectProperty<>(checkBox);
//
//        });
        translateCompletedColumn.setCellValueFactory(arg0 -> {

            serviceStatus stat;

            tl user = arg0.getValue();

            CheckBox checkBox = new CheckBox();
            JFXButton state = new JFXButton();



            if(user.getEmployeeAssigned().equals("")){
                stat = serviceStatus.UNASSIGNED;
                state.setStyle("-fx-background-color: #FF0000;");
            }else{
                if(user.checkCompleted()){
                    stat = serviceStatus.COMPLETED;
                    state.setStyle("-fx-background-color: #00FF00;");
                }else{
                    stat = serviceStatus.PROGRESS;
                    state.setStyle("-fx-background-color: #0f4b91;");
                }
            }
            state.setText(stat.name());
            completeEntry.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    setCompleteService(true,user);
                    contextBox.setVisible(false);
                    contextBox.toBack();
                    completeEntry.setVisible(false);
                    progressEntry.setVisible(false);
                    deleteEntry.setVisible(false);
                    setCompleteService(true,user);
                }
            });

            progressEntry.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    setCompleteService(false,user);
                    contextBox.setVisible(false);
                    contextBox.toBack();
                    completeEntry.setVisible(false);
                    progressEntry.setVisible(false);
                    deleteEntry.setVisible(false);
                    setCompleteService(false,user);
                }
            });

            deleteEntry.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    DatabaseManager.delElement(sel.LanguageInterperter,user.getRef().getRequestID());
                    buildTranslate("");
                    contextBox.setVisible(false);
                    contextBox.toBack();
                    completeEntry.setVisible(false);
                    progressEntry.setVisible(false);
                    deleteEntry.setVisible(false);
                }
            });

            checkBox.selectedProperty().setValue(user.checkCompleted());
            state.setOnMouseClicked(showBox);


//            checkBox.selectedProperty().addListener((ov, old_val, new_val) -> {
//
//                setCompleteService(new_val, user);
//                //user.setCompleted(new_val);
//
//            });

            return new SimpleObjectProperty<JFXButton>(state);


        });

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

        sanitationEmployeeColumn = new TableColumn<ss, JFXComboBox>("Employee");
        sanitationEmployeeColumn.setMinWidth(100);
        sanitationEmployeeColumn.setCellValueFactory(arg0 -> {
            JFXComboBox<String> emps = new JFXComboBox<>();
            emps.setEditable(true);

            ObservableList<String> people = FXCollections.observableArrayList();
            DatabaseManager.getEmployeeNames().forEach(s -> {
                people.add(s.substring(s.indexOf(" ")));
            });
            people.add("");
            emps.setItems(people);
            serviceStatus stat;

            ss user = arg0.getValue();

            CheckBox checkBox = new CheckBox();
            JFXButton state = new JFXButton();

            emps.setValue(user.employeeAssigned.get());

            emps.getSelectionModel().selectedItemProperty().addListener((ov, old_val, new_val) -> {
                String newEmp = emps.getSelectionModel().getSelectedItem();
                user.getRef().setEmployee(newEmp);
                DatabaseManager.modRequest(sel.SanitationServices,user.getRef().getRequestID(),user.getRef());
                buildSanitation("");
            });

//            checkBox.selectedProperty().addListener((ov, old_val, new_val) -> {
//
//                setCompleteService(new_val, user);
//                //user.setCompleted(new_val);
//
//            });


            return new SimpleObjectProperty<JFXComboBox>(emps);


        });

        sanitationCompletedColumn = new TableColumn<ss, JFXButton>("Status");
        sanitationCompletedColumn.setMinWidth(100);
        //sanitationCompletedColumn.setCellValueFactory(new PropertyValueFactory<ss, String>("completed"));
//        sanitationCompletedColumn.setCellValueFactory(arg0 -> {
//            ss user = arg0.getValue();
//
//            CheckBox checkBox = new CheckBox();
//
//            checkBox.selectedProperty().setValue(user.checkCompleted());
//
//
//
//            checkBox.selectedProperty().addListener((ov, old_val, new_val) -> {
//
//                setCompleteService(new_val, user);
//                //user.setCompleted(new_val);
//
//            });
//
//            return new SimpleObjectProperty<>(checkBox);
//
//        });
        sanitationCompletedColumn.setCellValueFactory(arg0 -> {

            serviceStatus stat;

            ss user = arg0.getValue();

            CheckBox checkBox = new CheckBox();
            JFXButton state = new JFXButton();



            if(user.getEmployeeAssigned().equals("")){
                stat = serviceStatus.UNASSIGNED;
                state.setStyle("-fx-background-color: #FF0000;");
            }else{
                if(user.checkCompleted()){
                    stat = serviceStatus.COMPLETED;
                    state.setStyle("-fx-background-color: #00FF00;");
                }else{
                    stat = serviceStatus.PROGRESS;
                    state.setStyle("-fx-background-color: #0f4b91;");
                }
            }
            state.setText(stat.name());
            completeEntry.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    setCompleteService(true,user);
                    contextBox.setVisible(false);
                    contextBox.toBack();
                    completeEntry.setVisible(false);
                    progressEntry.setVisible(false);
                    deleteEntry.setVisible(false);
                    setCompleteService(true,user);
                }
            });

            progressEntry.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    setCompleteService(false,user);
                    contextBox.setVisible(false);
                    contextBox.toBack();
                    completeEntry.setVisible(false);
                    progressEntry.setVisible(false);
                    deleteEntry.setVisible(false);
                    setCompleteService(false,user);
                }
            });

            deleteEntry.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    DatabaseManager.delElement(sel.SanitationServices,user.getRef().getRequestID());
                    buildSanitation("");
                    contextBox.setVisible(false);
                    contextBox.toBack();
                    completeEntry.setVisible(false);
                    progressEntry.setVisible(false);
                    deleteEntry.setVisible(false);
                }
            });

            checkBox.selectedProperty().setValue(user.checkCompleted());
            state.setOnMouseClicked(showBox);


//            checkBox.selectedProperty().addListener((ov, old_val, new_val) -> {
//
//                setCompleteService(new_val, user);
//                //user.setCompleted(new_val);
//
//            });

            return new SimpleObjectProperty<JFXButton>(state);


        });

        buildSanitation("");
    }

    private void covidTableSetUp(){
        nameColumn = new TableColumn<cs, String>("Name");
        nameColumn.setMinWidth(100);
        nameColumn.setCellValueFactory(new PropertyValueFactory<cs, String>("name"));

        DOBColumn = new TableColumn<cs, String>("DOB");
        DOBColumn.setMinWidth(100);
        DOBColumn.setCellValueFactory(new PropertyValueFactory<cs, String>("DOB"));

        sickColumn = new TableColumn<cs, String>("Is Sick");
        sickColumn.setMinWidth(100);
        sickColumn.setCellValueFactory(new PropertyValueFactory<cs, String>("sick"));

        vaxxColumn = new TableColumn<cs, String>("Is Vaccinated");
        vaxxColumn.setMinWidth(100);
        vaxxColumn.setCellValueFactory(new PropertyValueFactory<cs, String>("vaxx"));

        travelColumn = new TableColumn<cs, String>("has Traveled");
        travelColumn.setMinWidth(100);
        travelColumn.setCellValueFactory(new PropertyValueFactory<cs, String>("travel"));

        testColumn = new TableColumn<cs, String>("Tested");
        testColumn.setMinWidth(100);
        testColumn.setCellValueFactory(new PropertyValueFactory<cs, String>("test"));

        contactColumn = new TableColumn<cs, String>("Contact");
        contactColumn.setMinWidth(100);
        contactColumn.setCellValueFactory(new PropertyValueFactory<cs, String>("contact"));

        admitColumn = new TableColumn<cs, JFXButton>("Admitted");
        admitColumn.setMaxWidth(100);
        admitColumn.setCellValueFactory(arg0 -> {

            covidStatus stat;

            cs user = arg0.getValue();

            CheckBox checkBox = new CheckBox();
            JFXButton state = new JFXButton();



            if(user.isAdmit()){
                stat = covidStatus.ADMIT;
                state.setStyle("-fx-background-color: #00FF00;");
            }else{
                stat = covidStatus.RESTRICTED;
                state.setStyle("-fx-background-color: #FF0000;");
            }
            state.setText(stat.name());
            admitted.setOnMouseClicked(event -> {
                setCompleteService(true,user);
                covidContext.setVisible(false);
                covidContext.setDisable(true);
                covidContext.toBack();

                user.getRef().setAdmit(true);
                DatabaseManager.modRequest(sel.COVID,user.getRef().getRequestID(),user.getRef());
                buildCovid("");
            });

            restricted.setOnMouseClicked(event -> {
                setCompleteService(true,user);
                covidContext.setVisible(false);
                covidContext.setDisable(true);
                covidContext.toBack();

                user.getRef().setAdmit(false);
                DatabaseManager.modRequest(sel.COVID,user.getRef().getRequestID(),user.getRef());
                buildCovid("");
            });

            checkBox.selectedProperty().setValue(user.isCompleted());
            state.setOnMouseClicked(e -> {
                covidContext.setVisible(true);
                covidContext.setDisable(false);
                covidContext.toFront();

                Point2D p = new Point2D(e.getSceneX(), e.getSceneY());
                p = mainPane.sceneToLocal(p);
                covidContext.relocate(p.getX(), p.getY());
            });


//            checkBox.selectedProperty().addListener((ov, old_val, new_val) -> {
//
//                setCompleteService(new_val, user);
//                //user.setCompleted(new_val);
//
//            });

            return new SimpleObjectProperty<JFXButton>(state);


        });

        CovidCompleteColumn = new TableColumn<cs, JFXButton>("Completed");
        CovidCompleteColumn.setMaxWidth(100);
        CovidCompleteColumn.setCellValueFactory(arg0 -> {


            serviceStatus stat;

            cs user = arg0.getValue();

            CheckBox checkBox = new CheckBox();
            JFXButton state = new JFXButton();



            if(user.isCompleted()){
                stat = serviceStatus.COMPLETED;
                state.setStyle("-fx-background-color: #00FF00;");
            }else{
                stat = serviceStatus.PROGRESS;
                state.setStyle("-fx-background-color: #0f4b91;");
            }
            state.setText(stat.name());
            completeEntry.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    contextBox.setVisible(false);
                    contextBox.setDisable(true);
                    contextBox.toBack();
                    completeEntry.setVisible(false);
                    progressEntry.setVisible(false);
                    deleteEntry.setVisible(false);
                    setCompleteService(true,user);
                }
            });

            progressEntry.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    setCompleteService(false,user);
                    contextBox.setVisible(false);
                    contextBox.setDisable(true);
                    contextBox.toBack();
                    completeEntry.setVisible(false);
                    progressEntry.setVisible(false);
                    deleteEntry.setVisible(false);
                    setCompleteService(false,user);
                }
            });

            deleteEntry.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    DatabaseManager.delElement(sel.COVID,user.ref.getRequestID());
                    buildCovid("");
                    contextBox.setVisible(false);
                    contextBox.setDisable(true);
                    contextBox.toBack();
                    completeEntry.setVisible(false);
                    progressEntry.setVisible(false);
                    deleteEntry.setVisible(false);
                }
            });

            checkBox.selectedProperty().setValue(user.isCompleted());
            state.setOnMouseClicked(showBox);


//            checkBox.selectedProperty().addListener((ov, old_val, new_val) -> {
//
//                setCompleteService(new_val, user);
//                //user.setCompleted(new_val);
//
//            });

            return new SimpleObjectProperty<JFXButton>(state);


        });


        buildCovid("");
    }

    public ObservableList<er> buildEmergency(String searchTerm){
        ObservableList<er> tableRow = FXCollections.observableArrayList();
        LinkedList<ServiceRequest> requests = DatabaseManager.getServiceMap().getServiceRequestsForType(ServiceRequestType.Emergency);

        for (ServiceRequest s : requests) {
            er erToAdd = new er(s);
            for (int i = 0; i < erToAdd.getFields().size(); i++) {
                if (erToAdd.getFields().get(i).toLowerCase().equals(searchTerm) || searchTerm.equals("")) {
                    tableRow.add(erToAdd);
                    break;
                }
            }
        }
        emergencyTable.setItems(tableRow);
        emergencyTable.getColumns().setAll(
                EmergencyNumPeopleNeededColumn,
                EmergencyLocationColumn,
                EmergencyGurneyColumn,
                EmergencyEmployeeColumn,
                EmergencyCompletedColumn);
        return tableRow;
    }

    public ObservableList<cs> buildCovid(String searchTerm){
        ObservableList<cs> tableRow = FXCollections.observableArrayList();
        LinkedList<ServiceRequest> requests = DatabaseManager.getServiceMap().getServiceRequestsForType(ServiceRequestType.COVID);

        for (ServiceRequest s : requests) {
            cs csToAdd = new cs(s);
            for (int i = 0; i < csToAdd.getFields().size(); i++) {
                if (csToAdd.getFields().get(i).toLowerCase().equals(searchTerm) || searchTerm.equals("")) {
                    tableRow.add(csToAdd);
                    break;
                }
            }
        }
        covidTable.setItems(tableRow);
        covidTable.getColumns().setAll(
                nameColumn,
                DOBColumn,
                sickColumn,
                vaxxColumn,
                travelColumn,
                contactColumn,
                testColumn,
                admitColumn,
                CovidCompleteColumn);
        return tableRow;
    }

    public ObservableList<it> buildInternal(String searchTerm){
        ObservableList<it> tableRow = FXCollections.observableArrayList();
        LinkedList<ServiceRequest> requests = DatabaseManager.getServiceMap().getServiceRequestsForType(ServiceRequestType.InternalTransportation);

        for (ServiceRequest s : requests) {
            it itToAdd = new it(s);
            for (int i = 0; i < itToAdd.getFields().size(); i++) {
                if (itToAdd.getFields().get(i).toLowerCase().equals(searchTerm) || searchTerm.equals("")) {
                    tableRow.add(itToAdd);
                    break;
                }
            }
        }
        internalTable.setItems(tableRow);
        internalTable.getColumns().setAll(
                internalPatientIDTableColumn,
                internalNumStaffNeededTableColumn,
                internalDestinationTableColumn,
                internalTransportationMethodsTableColumn,
                internalEmployeeColumn,
                internalCompletedColumn);
        return tableRow;
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
                if (etToAdd.getFields().get(i).toLowerCase().equals(searchTerm) || searchTerm.equals("")) {
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
        medicineDeliveryTable.setEditable(true);
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

    public void setCompleteService(boolean val, service selected){
        String tabName;
        tabName = serviceTabPane.getSelectionModel().selectedItemProperty().get().getText();

        try{
            //service selected;
            switch(tabName){
                case "Medicine Delivery":
                    //selected = medicineDeliveryTable.getSelectionModel().getSelectedItem();
                    ((md) selected).setCompleted(val);
                    ((md) selected).getRef().setCompleted(val);
                    DatabaseManager.modRequest(sel.Medicine, ((md) selected).getRef().getRequestID(), ((md) selected).getRef());
                    buildMedicine("");
                    break;
                case "External Transport":
                    //selected = externalTable.getSelectionModel().getSelectedItem();
                    ((et) selected).setCompleted(val);
                    ((et) selected).getRef().setCompleted(val);
                    DatabaseManager.modRequest(sel.ExternalTransportation, ((et) selected).getRef().getRequestID(), ((et) selected).getRef());
                    buildExternal("");
                    break;
                case "Food Delivery":
                    //selected = foodDeliveryTable.getSelectionModel().getSelectedItem();
                    ((fd) selected).setCompleted(val);
                    ((fd) selected).getRef().setCompleted(val);
                    DatabaseManager.modRequest(sel.FoodDelivery, ((fd) selected).getRef().getRequestID(), ((fd) selected).getRef());
                    buildFood("");
                    break;
                case "Sanitation":
                    //selected = sanitationTable.getSelectionModel().getSelectedItem();
                    ((ss) selected).setCompleted(val);
                    ((ss) selected).getRef().setCompleted(val);
                    DatabaseManager.modRequest(sel.SanitationServices, ((ss) selected).getRef().getRequestID(), ((ss) selected).getRef());
                    buildSanitation("");
                    break;
                case "Religious":
                    //selected = religionTable.getSelectionModel().getSelectedItem();
                    ((rr) selected).setCompleted(val);
                    ((rr) selected).getRef().setCompleted(val);
                    DatabaseManager.modRequest(sel.ReligiousRequest, ((rr) selected).getRef().getRequestID(), ((rr) selected).getRef());
                    buildReligion("");
                    break;
                case "Translator":
                    //selected = translatorTable.getSelectionModel().getSelectedItem();
                    ((tl) selected).setCompleted(val);
                    ((tl) selected).getRef().setCompleted(val);
                    DatabaseManager.modRequest(sel.LanguageInterperter, ((tl) selected).getRef().getRequestID(), ((tl) selected).getRef());
                    buildTranslate("");
                case "COVID Survey":
                    ((cs) selected).setCompleted(val);
                    ((cs) selected).getRef().setCompleted(val);
                    DatabaseManager.modRequest(sel.COVID,((cs) selected).getRef().getRequestID(), ((cs)selected).getRef());
                    buildCovid("");
                    //TODO: add to DB
                    break;
                case "Internal Transport":
                    ((it) selected).setCompleted(val);
                    ((it) selected).getRef().setCompleted(val);
                    DatabaseManager.modRequest(sel.COVID,((it) selected).getRef().getRequestID(), ((it)selected).getRef());
                    buildInternal("");
                    break;
                default:
                    break;
            }
        }catch(NullPointerException ignored){}
    }

    public class md extends RecursiveTreeObject<md> implements service {
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
        public boolean checkCompleted(){
            return completed;
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

    public class et extends RecursiveTreeObject<et> implements service {
        edu.wpi.MochaManticores.Services.ExternalTransportation ref;
        StringProperty patientRoom;
        StringProperty currentRoom;
        StringProperty externalRoom;
        StringProperty transportationMethod;
        StringProperty employeeAssigned;
        boolean completed;
        LinkedList<String> fields;
        public boolean checkCompleted(){
            return completed;
        }
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

    public class fd extends RecursiveTreeObject<fd> implements service {

        edu.wpi.MochaManticores.Services.FoodDelivery ref;
        StringProperty dp;
        StringProperty a;
        StringProperty menu;
        StringProperty employeeAssigned;
        boolean completed;
        LinkedList<String> fields;
        public boolean checkCompleted(){
            return completed;
        }
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

    public class rr extends RecursiveTreeObject<rr> implements service {

        edu.wpi.MochaManticores.Services.ReligiousRequest ref;
        StringProperty reasonVisit;
        StringProperty location;
        StringProperty typeSacredPerson;
        StringProperty employeeAssigned;
        boolean completed;
        LinkedList<String> fields;
        public boolean checkCompleted(){
            return completed;
        }
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

    public class tl extends RecursiveTreeObject<tl> implements service {
        edu.wpi.MochaManticores.Services.LanguageInterpreterRequest ref;
        StringProperty room;
        StringProperty languageOne;
        StringProperty languageTwo;
        StringProperty employeeAssigned;
        boolean completed;
        LinkedList<String> fields;
        public boolean checkCompleted(){
            return completed;
        }
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

    public class ss extends RecursiveTreeObject<ss> implements service {
        edu.wpi.MochaManticores.Services.SanitationServices ref;
        StringProperty location;
        StringProperty safetyHazards;
        StringProperty sanitationType;
        StringProperty equipmentNeeded;
        StringProperty description;
        StringProperty employeeAssigned;
        boolean completed;
        LinkedList<String> fields;
        public boolean checkCompleted(){
            return completed;
        }
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

    public class it extends RecursiveTreeObject<it> implements service{

        edu.wpi.MochaManticores.Services.InternalTransportation ref;
        StringProperty patientIDTable;
        IntegerProperty numStaffNeededTable;
        StringProperty destinationTable;
        StringProperty transportationMethodsTable;
        StringProperty employee;
        boolean completed;
        LinkedList<String> fields;
        public boolean checkCompleted(){
            return completed;
        }
        public void setCompleted(boolean completed) {
            this.completed = completed;
        }


        public String getPatientIDTable() {
            return patientIDTable.get();
        }

        public StringProperty patientIDTableProperty() {
            return patientIDTable;
        }

        public int getNumStaffNeededTable() {
            return numStaffNeededTable.get();
        }

        public IntegerProperty numStaffNeededTableProperty() {
            return numStaffNeededTable;
        }

        public String getDestinationTable() {
            return destinationTable.get();
        }

        public StringProperty destinationTableProperty() {
            return destinationTable;
        }

        public String getTransportationMethodsTable() {
            return transportationMethodsTable.get();
        }

        public StringProperty transportationMethodsTableProperty() {
            return transportationMethodsTable;
        }

        public String getEmployee() {
            return employee.get();
        }

        public StringProperty employeeProperty() {
            return employee;
        }

        public String isCompleted() {
            if (completed) {
                return "Completed";
            } else {
                return "Open";
            }
        }

        public it(edu.wpi.MochaManticores.Services.ServiceRequest ref){
            this.ref = (edu.wpi.MochaManticores.Services.InternalTransportation) ref;
            patientIDTable = new SimpleStringProperty(this.ref.getPatientID());
            numStaffNeededTable = new SimpleIntegerProperty(this.ref.getNumStaffNeeded());
            destinationTable = new SimpleStringProperty(this.ref.getDestination());
            transportationMethodsTable = new SimpleStringProperty(this.ref.getTransportationMethod());
            employee = new SimpleStringProperty(ref.getEmployee());
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

    public class cs extends RecursiveTreeObject<cs> implements service{
        public StringProperty name;
        public StringProperty DOB;
        public boolean sick;
        public boolean vaxx;
        public boolean travel;
        public boolean test;
        public boolean contact;
        public StringProperty Symptoms;
        public boolean admit;
        public boolean completed;
        public COVIDsurvey ref;
        LinkedList<String> fields;

        public COVIDsurvey getRef() {
            return ref;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        public cs(edu.wpi.MochaManticores.Services.ServiceRequest ref){
            this.ref = (COVIDsurvey) ref;
            name = new SimpleStringProperty(((COVIDsurvey) ref).getName());
            DOB = new SimpleStringProperty(((COVIDsurvey) ref).getDOB());
            sick = ((COVIDsurvey) ref).isSick();
            vaxx = ((COVIDsurvey) ref).isVaxx();
            travel = ((COVIDsurvey) ref).isTravel();
            test = ((COVIDsurvey) ref).isTest();
            contact = ((COVIDsurvey) ref).isContact();
            Symptoms = new SimpleStringProperty(((COVIDsurvey) ref).getSymptoms());
            admit = ((COVIDsurvey) ref).isAdmit();
            completed = ref.getCompleted();
            fields = new LinkedList<>(Arrays.asList(
                    name.get(),
                    DOB.get(),
                    String.valueOf(sick),
                    String.valueOf(vaxx),
                    String.valueOf(travel),
                    String.valueOf(test),
                    String.valueOf(contact),
                    Symptoms.get(),
                    String.valueOf(admit)));
        }

        public String getName() {
            return name.get();
        }

        public StringProperty nameProperty() {
            return name;
        }

        public String getDOB() {
            return DOB.get();
        }

        public StringProperty DOBProperty() {
            return DOB;
        }

        public boolean isSick() {
            return sick;
        }

        public boolean isVaxx() {
            return vaxx;
        }

        public boolean isTravel() {
            return travel;
        }

        public boolean isTest() {
            return test;
        }

        public boolean isContact() {
            return contact;
        }

        public String getSymptoms() {
            return Symptoms.get();
        }

        public StringProperty symptomsProperty() {
            return Symptoms;
        }

        public boolean isAdmit() {
            return admit;
        }

        public LinkedList<String> getFields() {
            return fields;
        }
    }

    public class er extends RecursiveTreeObject<er> implements service{
        public StringProperty numPeopleNeeded;
        public StringProperty location;
        public StringProperty gurney;
        public StringProperty employee;
        public EmergencyRequest ref;
        public boolean completed;
        public LinkedList<String> fields;

        public er(ServiceRequest ref){
            this.ref = (EmergencyRequest) ref;
            numPeopleNeeded = new SimpleStringProperty(String.valueOf(((EmergencyRequest) ref).getNumPeopleNeeded()));
            location = new SimpleStringProperty(((EmergencyRequest) ref).getLocation());
            gurney = new SimpleStringProperty(String.valueOf(((EmergencyRequest) ref).isGurney()));
            employee = new SimpleStringProperty(ref.getEmployee());
            completed = false;
            fields = new LinkedList<String>(
                    Arrays.asList(numPeopleNeeded.get(),
                            location.get(),
                            gurney.get()));
        }

        public LinkedList<String> getFields() {
            return fields;
        }

        public String getNumPeopleNeeded() {
            return numPeopleNeeded.get();
        }

        public StringProperty numPeopleNeededProperty() {
            return numPeopleNeeded;
        }

        public String getLocation() {
            return location.get();
        }

        public StringProperty locationProperty() {
            return location;
        }

        public String getGurney() {
            return gurney.get();
        }

        public StringProperty gurneyProperty() {
            return gurney;
        }

        public String getEmployee() {
            return employee.get();
        }

        public StringProperty employeeProperty() {
            return employee;
        }

        public EmergencyRequest getRef() {
            return ref;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setEmployee(String employee) {
            this.employee.set(employee);
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }
    }

    public interface service{

    }

    public enum serviceStatus{
        COMPLETED,
        PROGRESS,
        UNASSIGNED;
    }

    public enum covidStatus{
        ADMIT,
        RESTRICTED
    }


}