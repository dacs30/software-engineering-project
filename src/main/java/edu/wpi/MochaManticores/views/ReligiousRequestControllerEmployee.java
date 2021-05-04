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
    private JFXButton submitBTN;
    @FXML
    private JFXButton cancelBTN;

    public void initialize() {


        double height = App.getPrimaryStage().getScene().getHeight();
        double width = App.getPrimaryStage().getScene().getWidth();
        //backgroundGrid.setPrefSize(width, height);

        TypeOfSacredPerson.setItems(TypeOfSacredPersons);

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

