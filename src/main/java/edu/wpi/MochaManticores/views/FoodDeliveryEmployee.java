package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.sun.rowset.internal.Row;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.Nodes.MapSuper;
import edu.wpi.MochaManticores.Nodes.NodeSuper;
import edu.wpi.MochaManticores.Services.*;
import edu.wpi.MochaManticores.database.DatabaseManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import edu.wpi.MochaManticores.Services.ServiceMap;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.*;

public class FoodDeliveryEmployee {

    public class fd extends RecursiveTreeObject<fd>{

        edu.wpi.MochaManticores.Services.FoodDelivery ref;
        StringProperty dp;
        StringProperty a;
        StringProperty menu;
        LinkedList<String> fields;

        public fd(edu.wpi.MochaManticores.Services.ServiceRequest ref){
                this.ref = (edu.wpi.MochaManticores.Services.FoodDelivery) ref;
                dp = new SimpleStringProperty(this.ref.getDietaryPreference());
                a = new SimpleStringProperty(this.ref.getAllergies());
                menu = new SimpleStringProperty(this.ref.getMenu());
                fields = new LinkedList<>(Arrays.asList(dp.get(),a.get(),menu.get()));
        }

        public LinkedList<String> getFields(){
            return fields;
        }

        public edu.wpi.MochaManticores.Services.FoodDelivery getRef() {
            return ref;
        }

        public String getDietaryPref(){
            return dp.get();
        }

        public String getAllergies(){
            return a.get();
        }
        public String getMenu(){
            return menu.get();
        }
    }

    @FXML
    private TableView<fd> foodDeliveryTable;

    @FXML
    private GridPane contentGrid;

    @FXML
    private ImageView backgroundIMG;

    @FXML
    private GridPane requestPage;

    public TableColumn<fd, String> dietaryPref;

    public TableColumn<fd, String> allergies;

    public TableColumn<fd, String> menuOption;

    public Boolean isSetToCreateRequest;

    @FXML
    public void initialize(){
        double height = App.getPrimaryStage().getScene().getHeight();
        double width = App.getPrimaryStage().getScene().getWidth();
        backgroundIMG.setFitHeight(height);
        backgroundIMG.setFitWidth(width);

        backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
        backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());


        dietaryPref = new TableColumn<fd, String>("Dietary Preferences");
        dietaryPref.setMinWidth(100);
        dietaryPref.setCellValueFactory(new PropertyValueFactory<fd, String>("dietaryPref"));
        //dietaryPref.setPrefWidth(foodDeliveryTable.getPrefWidth()/3);

        allergies = new TableColumn<fd, String>("Allergies");
        allergies.setMinWidth(100);
        allergies.setCellValueFactory(new PropertyValueFactory<fd, String>("Allergies"));
        //allergies.setPrefWidth(foodDeliveryTable.getPrefWidth()/3);

        menuOption = new TableColumn<fd, String>("Menu");
        menuOption.setMinWidth(100);
        menuOption.setCellValueFactory(new PropertyValueFactory<fd, String>("Menu"));
        //menuOption.setPrefWidth(foodDeliveryTable.getPrefWidth()/3);

        // TODO add combox values

        buildTable("");

        isSetToCreateRequest = false;
    }

    private ObservableList<fd> buildTable(String searchTerm) {
        ObservableList<fd> tableRow = FXCollections.observableArrayList();

        LinkedList<ServiceRequest> requests = DatabaseManager.getServiceMap().getServiceRequestsForType(ServiceRequestType.FoodDelivery);

        for (ServiceRequest s : requests) {
            fd fdToAdd = new fd(s);
            for (int i = 0; i < fdToAdd.getFields().size(); i++){
                if(fdToAdd.getFields().get(i).toLowerCase().equals(searchTerm) || searchTerm.equals("")){
                    //System.out.println(i + " " + fdToAdd.getDietaryPreference());
                    tableRow.add(fdToAdd);
                    break;
                }
            }
        }
        foodDeliveryTable.setItems(tableRow);
        foodDeliveryTable.getColumns().setAll(dietaryPref,allergies,menuOption);

        return tableRow;
    }

    public void changeToRequest(ActionEvent actionEvent) {
        requestPage.toFront();
    }

    public void changeManagerTable(ActionEvent actionEvent) {
        requestPage.toBack();
    }

    public void submitForm(ActionEvent actionEvent) {

    }

    public void helpButton(MouseEvent mouseEvent) {
    }
}
