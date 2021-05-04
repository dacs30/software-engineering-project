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

    private JFXComboBox<String> emps = new JFXComboBox<>();
    
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
    public TableColumn<tl, JFXButton> translateCompletedColumn
            ;
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
    public TableColumn<it, String> internalEmployeeColumn;
    public TableColumn<it, String> internalCompletedColumn;

    private EventHandler<MouseEvent> showBox = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            contextBox.setVisible(true);
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

        emps.setEditable(true);

        ObservableList<String> people = FXCollections.observableArrayList();
        DatabaseManager.getEmployeeNames().forEach(s -> {
            people.add(s.substring(s.indexOf(" ")));
        });
        emps.setItems(people);

        medicineTableSetUp();
        externalTableSetUp();
        foodTableSetUp();
        religiousTableSetUp();
        translatorTableSetUp();
        sanitationTableSetUp();
        //internalTableSetUp();
    }

    private void internalTableSetUp(){
        internalPatientIDTableColumn = new TableColumn<it, String>("Patient");
        internalPatientIDTableColumn.setMinWidth(100);
        internalPatientIDTableColumn.setCellValueFactory(new PropertyValueFactory<it, String>("internalPatientIDTable"));

        internalNumStaffNeededTableColumn = new TableColumn<it, String>("Number of Staff");
        internalNumStaffNeededTableColumn.setMinWidth(100);
        internalNumStaffNeededTableColumn.setCellValueFactory(new PropertyValueFactory<it, String>("internalNumStaffNeededTable"));

        internalDestinationTableColumn = new TableColumn<it, String>("Destination");
        internalDestinationTableColumn.setMinWidth(100);
        internalDestinationTableColumn.setCellValueFactory(new PropertyValueFactory<it, String>("internalDestinationTable"));

        internalTransportationMethodsTableColumn = new TableColumn<it, String>("Transportation Method");
        internalTransportationMethodsTableColumn.setMinWidth(100);
        internalTransportationMethodsTableColumn.setCellValueFactory(new PropertyValueFactory<it, String>("internalTransportationMethodsTable"));

        internalEmployeeColumn = new TableColumn<it, String>("Employee");
        internalEmployeeColumn.setMinWidth(100);
        internalEmployeeColumn.setCellValueFactory(new PropertyValueFactory<it, String>("employee"));

        internalCompletedColumn = new TableColumn<it, String>("Status");
        internalCompletedColumn.setMinWidth(100);
        internalCompletedColumn.setCellValueFactory(new PropertyValueFactory<it, String>("completed"));
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

            serviceStatus stat;

            md user = arg0.getValue();

            CheckBox checkBox = new CheckBox();
            JFXButton state = new JFXButton();

            emps.setValue(user.employeeAssigned.get());

            emps.getSelectionModel().selectedItemProperty().addListener((ov, old_val, new_val) -> {
                String newEmp = emps.getSelectionModel().getSelectedItem();
                user.getRef().setEmployee(newEmp);
                DatabaseManager.modRequest(sel.Medicine,user.getRef().getRequestID(),user.getRef());
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



            if(user.getEmployeeAssigned().equals("")){
                stat = serviceStatus.UNASSIGNED;
                state.setStyle("-fx-background-color: #FF0000;");
            }
            if(user.checkCompleted()){
                stat = serviceStatus.COMPLETED;
                state.setStyle("-fx-background-color: #00FF00;");
            }else{
                stat = serviceStatus.PROGRESS;
                state.setStyle("-fx-background-color: #0000FF;");
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
                    DatabaseManager.delElement(sel.Medicine,user.getRef().getRequestID());
                    buildMedicine("");
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

//        MedicineCompletedColumn.setCellFactory(tc -> new CheckBoxTableCell<>());
//        MedicineCompletedColumn.setOnEditStart(this::edit);
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


        ExternalEmployeeColumn = new TableColumn<et, JFXComboBox>("Employee");
        ExternalEmployeeColumn.setMinWidth(100);
        ExternalEmployeeColumn.setCellValueFactory(arg0 -> {

            serviceStatus stat;

            et user = arg0.getValue();

            CheckBox checkBox = new CheckBox();
            JFXButton state = new JFXButton();

            emps.setValue(user.employeeAssigned.get());

            emps.getSelectionModel().selectedItemProperty().addListener((ov, old_val, new_val) -> {
                String newEmp = emps.getSelectionModel().getSelectedItem();
                user.getRef().setEmployee(newEmp);
                DatabaseManager.modRequest(sel.Medicine,user.getRef().getRequestID(),user.getRef());
            });

//            checkBox.selectedProperty().addListener((ov, old_val, new_val) -> {
//
//                setCompleteService(new_val, user);
//                //user.setCompleted(new_val);
//
//            });


            return new SimpleObjectProperty<JFXComboBox>(emps);


        });
        //ExternalEmployeeColumn.setOnEditCommit(this::changeEmployee);


        ExternalCompletedColumn = new TableColumn<et, JFXButton>("Completed");
        ExternalCompletedColumn.setMinWidth(100);
        //ExternalCompletedColumn.setCellValueFactory(new PropertyValueFactory<et, String>("completed"));
//        ExternalCompletedColumn.setCellValueFactory(arg0 -> {
//            et user = arg0.getValue();
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
        ExternalCompletedColumn.setCellValueFactory(arg0 -> {

            serviceStatus stat;

            et user = arg0.getValue();

            CheckBox checkBox = new CheckBox();
            JFXButton state = new JFXButton();



            if(user.getEmployeeAssigned().equals("")){
                stat = serviceStatus.UNASSIGNED;
                state.setStyle("-fx-background-color: #FF0000;");
            }
            if(user.checkCompleted()){
                stat = serviceStatus.COMPLETED;
                state.setStyle("-fx-background-color: #00FF00;");
            }else{
                stat = serviceStatus.PROGRESS;
                state.setStyle("-fx-background-color: #0000FF;");
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
                    DatabaseManager.delElement(sel.ExternalTransportation,user.getRef().getRequestID());
                    buildMedicine("");
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

        FoodEmployeeColumn = new TableColumn<fd, JFXComboBox>("Employee");
        FoodEmployeeColumn.setMinWidth(100);
        FoodEmployeeColumn.setCellValueFactory(arg0 -> {

            serviceStatus stat;

            fd user = arg0.getValue();

            CheckBox checkBox = new CheckBox();
            JFXButton state = new JFXButton();

            emps.setValue(user.employeeAssigned.get());

            emps.getSelectionModel().selectedItemProperty().addListener((ov, old_val, new_val) -> {
                String newEmp = emps.getSelectionModel().getSelectedItem();
                user.getRef().setEmployee(newEmp);
                DatabaseManager.modRequest(sel.FoodDelivery,user.getRef().getRequestID(),user.getRef());
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
            }
            if(user.checkCompleted()){
                stat = serviceStatus.COMPLETED;
                state.setStyle("-fx-background-color: #00FF00;");
            }else{
                stat = serviceStatus.PROGRESS;
                state.setStyle("-fx-background-color: #0000FF;");
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
                    buildMedicine("");
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

            serviceStatus stat;

            rr user = arg0.getValue();

            CheckBox checkBox = new CheckBox();
            JFXButton state = new JFXButton();

            emps.setValue(user.employeeAssigned.get());

            emps.getSelectionModel().selectedItemProperty().addListener((ov, old_val, new_val) -> {
                String newEmp = emps.getSelectionModel().getSelectedItem();
                user.getRef().setEmployee(newEmp);
                DatabaseManager.modRequest(sel.Medicine,user.getRef().getRequestID(),user.getRef());
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
            }
            if(user.checkCompleted()){
                stat = serviceStatus.COMPLETED;
                state.setStyle("-fx-background-color: #00FF00;");
            }else{
                stat = serviceStatus.PROGRESS;
                state.setStyle("-fx-background-color: #0000FF;");
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
                    buildMedicine("");
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

            serviceStatus stat;

            tl user = arg0.getValue();

            CheckBox checkBox = new CheckBox();
            JFXButton state = new JFXButton();

            emps.setValue(user.employeeAssigned.get());

            emps.getSelectionModel().selectedItemProperty().addListener((ov, old_val, new_val) -> {
                String newEmp = emps.getSelectionModel().getSelectedItem();
                user.getRef().setEmployee(newEmp);
                DatabaseManager.modRequest(sel.Medicine,user.getRef().getRequestID(),user.getRef());
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
            }
            if(user.checkCompleted()){
                stat = serviceStatus.COMPLETED;
                state.setStyle("-fx-background-color: #00FF00;");
            }else{
                stat = serviceStatus.PROGRESS;
                state.setStyle("-fx-background-color: #0000FF;");
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
                    buildMedicine("");
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

            serviceStatus stat;

            ss user = arg0.getValue();

            CheckBox checkBox = new CheckBox();
            JFXButton state = new JFXButton();

            emps.setValue(user.employeeAssigned.get());

            emps.getSelectionModel().selectedItemProperty().addListener((ov, old_val, new_val) -> {
                String newEmp = emps.getSelectionModel().getSelectedItem();
                user.getRef().setEmployee(newEmp);
                DatabaseManager.modRequest(sel.Medicine,user.getRef().getRequestID(),user.getRef());
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
            }
            if(user.checkCompleted()){
                stat = serviceStatus.COMPLETED;
                state.setStyle("-fx-background-color: #00FF00;");
            }else{
                stat = serviceStatus.PROGRESS;
                state.setStyle("-fx-background-color: #0000FF;");
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
                    buildMedicine("");
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

    public void completeService(ActionEvent e){
        String tabName;
        tabName = serviceTabPane.getSelectionModel().selectedItemProperty().get().getText();

        try{
            service selected;
            switch(tabName){
                case "Medicine Delivery":
                    selected = medicineDeliveryTable.getSelectionModel().getSelectedItem();
                    ((md) selected).setCompleted(true);
                    ((md) selected).getRef().setCompleted(true);
                    buildMedicine("");
                    break;
                case "External Transport":
                    selected = externalTable.getSelectionModel().getSelectedItem();
                    ((et) selected).setCompleted(true);
                    ((et) selected).getRef().setCompleted(true);
                    buildExternal("");
                    break;
                case "Food Delivery":
                    selected = foodDeliveryTable.getSelectionModel().getSelectedItem();
                    ((fd) selected).setCompleted(true);
                    ((fd) selected).getRef().setCompleted(true);
                    buildFood("");
                    break;
                case "Sanitation":
                    selected = sanitationTable.getSelectionModel().getSelectedItem();
                    ((ss) selected).setCompleted(true);
                    ((ss) selected).getRef().setCompleted(true);
                    buildSanitation("");
                    break;
                case "Religious":
                    selected = religionTable.getSelectionModel().getSelectedItem();
                    ((rr) selected).setCompleted(true);
                    ((rr) selected).getRef().setCompleted(true);
                    buildReligion("");
                    break;
                case "Translator":
                    selected = translatorTable.getSelectionModel().getSelectedItem();
                    ((tl) selected).setCompleted(true);
                    ((tl) selected).getRef().setCompleted(true);
                    buildTranslate("");
                case "COVID Survey":
                    //TODO: add to DB
                    break;
                case "Internal Transport":
                    //TODO: fix table issue
                    break;
                default:
                    break;
            }
        }catch(NullPointerException ignored){}
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
                    //TODO: add to DB
                    break;
                case "Internal Transport":
                    //TODO: fix table issue
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

    public interface service{

    }

    public enum serviceStatus{
        COMPLETED,
        PROGRESS,
        UNASSIGNED;
    }


}