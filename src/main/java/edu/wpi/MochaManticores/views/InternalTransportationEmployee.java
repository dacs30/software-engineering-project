package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.Services.ServiceRequest;
import edu.wpi.MochaManticores.Services.ServiceRequestType;
import edu.wpi.MochaManticores.database.DatabaseManager;
import edu.wpi.MochaManticores.database.sel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.Arrays;
import java.util.LinkedList;

public class InternalTransportationEmployee {


    @FXML
    private ImageView backgroundIMG;

    @FXML
    private GridPane contentGrid;

    @FXML
    private StackPane dialogPane;

    @FXML
    private JFXTextField patientID, numberOfStaff, destination, empBox;

    @FXML
    private ComboBox<String> transportComboBox;


    @FXML
    private GridPane managerPage;

    @FXML
    private GridPane requestPage;


    ObservableList<String> typeOfTransportList = FXCollections
            .observableArrayList("Wheelchair","Walker","Medical Bed");

    @FXML
    private void initialize() {
        double height = App.getPrimaryStage().getScene().getHeight();
        double width = App.getPrimaryStage().getScene().getWidth();
        backgroundIMG.setFitHeight(height);
        backgroundIMG.setFitWidth(width);
        contentGrid.setPrefSize(width, height);

        backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
        backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());

        transportComboBox.setItems(typeOfTransportList);

        dialogPane.toBack();

        if(App.getClearenceLevel()<=0){
            empBox.setVisible(false);
        }

        managerPage.setVisible(false);
        requestPage.setVisible(true);
        requestPage.toFront();
//
//        buildTable("");
    }

    public void submitEvent(ActionEvent actionEvent) {
        if(!patientID.getText().isEmpty() || !numberOfStaff.getText().isEmpty() ||
                !destination.getText().isEmpty() || !transportComboBox.getSelectionModel().getSelectedItem().isEmpty()){
            sel s = sel.InternalTransportation;
            DatabaseManager.addRequest(s, new edu.wpi.MochaManticores.Services.InternalTransportation(
                    "",
                    empBox.getText(),
                    false,
                    patientID.getText(),
                    Integer.parseInt(numberOfStaff.getText()),
                    destination.getText(),
                    transportComboBox.getValue()
            ));
            System.out.println("Adds to database");
        }
    }

    public void openHelp(MouseEvent mouseEvent) {
    }

    public void changeToRequest(ActionEvent actionEvent) {
        requestPage.setVisible(true);
        managerPage.setVisible(false);
        requestPage.toFront();
    }


}
