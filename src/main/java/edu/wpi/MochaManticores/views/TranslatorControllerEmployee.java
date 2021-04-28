package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.MochaManticores.Services.LanguageInterpreterRequest;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.LinkedList;

public class TranslatorControllerEmployee extends SceneController{

    public class tl extends RecursiveTreeObject<tl>{
        edu.wpi.MochaManticores.Services.LanguageInterpreterRequest ref;
        StringProperty room;
        StringProperty languageOne;
        StringProperty languageTwo;
        StringProperty employeeAssigned;
        boolean completed;
        LinkedList<String> fields;

        public tl(edu.wpi.MochaManticores.Services.ServiceRequest ref){
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

    ObservableList<String> availableLanguages = FXCollections
            .observableArrayList("English","Spanish","Mandarin");

    @FXML
    private JFXTextField roomNumber;
    @FXML
    private JFXComboBox languageOne;
    @FXML
    private JFXComboBox languageTwo;
    @FXML
    private StackPane dialogPane;

    public TableView<tl> translatorTable;

    public TableColumn<tl, String> roomColumn;
    public TableColumn<tl, String> languageOneColumn;
    public TableColumn<tl, String> languageTwoColumn;
    public TableColumn<tl, String> employeeAssignedColumn;
    public TableColumn<tl, String> completedColumn;

    @FXML
    private GridPane requestPage;

    @FXML
    private GridPane managerPage;


    @FXML
    private void initialize() {

        roomColumn = new TableColumn<tl,String>("Room");
        roomColumn.setMinWidth(100);
        roomColumn.setCellValueFactory(new PropertyValueFactory<tl, String>("room"));

        languageOneColumn = new TableColumn<tl,String>("Room");
        languageOneColumn.setMinWidth(100);
        languageOneColumn.setCellValueFactory(new PropertyValueFactory<tl, String>("languageOne"));

        languageOneColumn = new TableColumn<tl,String>("Room");
        languageOneColumn.setMinWidth(100);
        languageOneColumn.setCellValueFactory(new PropertyValueFactory<tl, String>("languageTwo"));

        employeeAssignedColumn = new TableColumn<tl, String>("Assigned To");
        employeeAssignedColumn.setMinWidth(100);
        employeeAssignedColumn.setCellValueFactory(new PropertyValueFactory<tl, String>("employeeAssigned"));

        completedColumn = new TableColumn<tl, String>("Status");
        completedColumn.setMinWidth(100);
        completedColumn.setCellValueFactory(new PropertyValueFactory<tl, String>("completed"));


        languageOne.setItems(availableLanguages);
        languageTwo.setItems(availableLanguages);
    }


    private ObservableList<tl> buildTable(String searchTerm){
        ObservableList<tl> tableRow = FXCollections.observableArrayList();
        LinkedList<ServiceRequest> requests = DatabaseManager.getServiceMap().getServiceRequestsForType(ServiceRequestType.LanguageInterperter);

        for(ServiceRequest s : requests){
            tl tlToAdd = new tl(s);
            for (int i = 0; i < tlToAdd.getFields().size(); i++) {
                if(tlToAdd.getFields().get(i).toLowerCase().equals(searchTerm) || searchTerm.equals("")){
                    tableRow.add(tlToAdd);
                    break;
                }
            }
        }
        translatorTable.setItems(tableRow);
        translatorTable.getColumns().setAll(
                roomColumn,
                languageOneColumn,
                languageTwoColumn,
                employeeAssignedColumn,
                completedColumn);
        return tableRow;

    }

    public void cancelReq(ActionEvent actionEvent) {
        back();
    }

    public void submitReq(ActionEvent actionEvent) {
        sel s = sel.LanguageInterperter;
        // changeSceneTo(e, "mainMenu");
        DatabaseManager.addRequest(s,
                new edu.wpi.MochaManticores.Services.LanguageInterpreterRequest(
                        "", "", false, roomNumber.getText(),
                        languageOne.getSelectionModel().getSelectedItem().toString(),
                        languageTwo.getSelectionModel().getSelectedItem().toString()));
        dialogPane.setVisible(true);
        loadDialog();
        back();
    }

    private void loadHelpDialog(){
        dialogPane.toFront();
        dialogPane.setDisable(false);
        JFXDialogLayout message = new JFXDialogLayout();
        message.setMaxHeight(Region.USE_COMPUTED_SIZE);
        message.setMaxHeight(Region.USE_COMPUTED_SIZE);

        final Text hearder = new Text("Help Page");
        hearder.setStyle("-fx-font-weight: bold");
        hearder.setStyle("-fx-font-size: 60");
        hearder.setStyle("-fx-font-family: Roboto");
        hearder.setStyle("-fx-alignment: center");
        message.setHeading(hearder);

        final Text body = new Text("Room Number: Room that you are currently in.\n" +
                "Language One: Language that you speak\n" +
                "Language Two: Language you need translated\n");

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

    public void loadDialog() {
        //TODO Center the text of it.

        dialogPane.toFront();
        dialogPane.setDisable(false);
        JFXDialogLayout message = new JFXDialogLayout();
        message.setMaxHeight(Region.USE_PREF_SIZE);
        message.setMaxHeight(Region.USE_PREF_SIZE);

        final Text hearder = new Text("Submited");
        hearder.setStyle("-fx-font-weight: bold");
        hearder.setStyle("-fx-font-size: 30");
        hearder.setStyle("-fx-font-family: Roboto");
        hearder.setStyle("-fx-alignment: center");
        message.setHeading(hearder);

        final Text body = new Text("Time estimated:");
        body.setStyle("-fx-font-size: 15");
        body.setStyle("-fx-font-family: Roboto");
        body.setStyle("-fx-alignment: center");
        message.setHeading(hearder);

        message.setBody(body);
        JFXDialog dialog = new JFXDialog(dialogPane, message, JFXDialog.DialogTransition.CENTER);
        JFXButton exit = new JFXButton("OK!");
        exit.setOnAction(event -> {
            back();
        });
        dialog.setOnDialogClosed(event -> {
            dialogPane.setDisable(true);
            dialogPane.toBack();
        });
        message.setActions(exit);
        dialog.show();

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

    public void helpButton(MouseEvent mouseEvent) {
        loadHelpDialog();
    }

}
