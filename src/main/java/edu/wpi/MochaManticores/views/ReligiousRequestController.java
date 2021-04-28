package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.*;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.Services.ReligiousRequest;
import edu.wpi.MochaManticores.database.DatabaseManager;
import edu.wpi.MochaManticores.database.sel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;



public class ReligiousRequestController extends SceneController{
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

    @FXML
    private JFXTextField reasonBox;

    @FXML
    private JFXTextField roomIDBox;

    public void initialize() {
        double height = App.getPrimaryStage().getScene().getHeight();
        double width = App.getPrimaryStage().getScene().getWidth();
        backgroundGrid.setPrefSize(width, height);

        TypeOfSacredPerson.setItems(TypeOfSacredPersons);
    }

    public void exitPage(){
        super.back();
    }

    public void cancelEvent(ActionEvent actionEvent) {
        exitPage();
    }

    public void submitEvent(ActionEvent actionEvent) {
        sel s = sel.ReligiousRequest;
        DatabaseManager.addRequest(s,
                new ReligiousRequest("", "",false,reasonBox.getText(),roomIDBox.getText(),TypeOfSacredPerson.getSelectionModel().getSelectedItem()));
        exitPage();
    }
}

