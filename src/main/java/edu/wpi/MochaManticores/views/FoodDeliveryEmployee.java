package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.Nodes.MapSuper;
import edu.wpi.MochaManticores.Nodes.NodeSuper;
import edu.wpi.MochaManticores.Services.*;
import edu.wpi.MochaManticores.database.DatabaseManager;
import edu.wpi.MochaManticores.database.sel;
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
        StringProperty employeeAssigned;
        boolean completed;
        LinkedList<String> fields;

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

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        public fd(edu.wpi.MochaManticores.Services.ServiceRequest ref){
                this.ref = (edu.wpi.MochaManticores.Services.FoodDelivery) ref;
                dp = new SimpleStringProperty(this.ref.getDietaryPreference());
                a = new SimpleStringProperty(this.ref.getAllergies());
                menu = new SimpleStringProperty(this.ref.getMenu());
                employeeAssigned = new SimpleStringProperty(this.ref.getEmployee());
                completed = this.ref.getCompleted();
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
    private JFXComboBox<String> dietaryPreferences;

    @FXML
    private JFXTextArea allergiesField;

    @FXML
    private JFXTextField empBox;

    @FXML
    private ImageView backgroundIMG;

    @FXML
    private GridPane requestPage;

    @FXML
    private GridPane managerPage;

    public TableColumn<fd, String> dietaryPref;

    public TableColumn<fd, String> allergies;

    public TableColumn<fd, String> employee;

    public TableColumn<fd, String> completed;

    @FXML
    private JFXComboBox<String> foodMenu;

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

        employee = new TableColumn<fd, String>("Employee");
        employee.setMinWidth(100);
        employee.setCellValueFactory(new PropertyValueFactory<fd, String>("employeeAssigned"));

        completed = new TableColumn<fd, String>("Status");
        completed.setMinWidth(100);
        completed.setCellValueFactory(new PropertyValueFactory<fd, String>("completed"));

        // TODO add combox values

        dietaryPreferences.getItems().clear();
        dietaryPreferences.getItems().addAll("Vegan", "Vegetarian", "Gluten Free");

        foodMenu.getItems().clear();
        foodMenu.getItems().addAll("Menu 0", "Menu 1", "Menu 2", "Menu 3");


        managerPage.setVisible(false);
        requestPage.setVisible(true);
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
        foodDeliveryTable.getColumns().setAll(dietaryPref,allergies,menuOption,employee,completed);

        return tableRow;
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
        fd selection = foodDeliveryTable.getSelectionModel().getSelectedItem();
        selection.setCompleted(true);
        selection.getRef().setCompleted(true);
        buildTable("");
    }

    public void submitForm(ActionEvent actionEvent) {
        sel s = sel.FoodDelivery;
        // changeSceneTo(e, "mainMenu");
        DatabaseManager.addRequest(s,
                new edu.wpi.MochaManticores.Services.FoodDelivery(
                        "", empBox.getText(), false, dietaryPreferences.getSelectionModel().getSelectedItem(),
                        allergiesField.getText(), foodMenu.getSelectionModel().getSelectedItem()));
    }

    public void helpButton(MouseEvent mouseEvent) {
    }
}
