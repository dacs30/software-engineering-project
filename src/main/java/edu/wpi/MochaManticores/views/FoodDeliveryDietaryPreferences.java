package edu.wpi.MochaManticores.views;

import edu.wpi.MochaManticores.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class FoodDeliveryDietaryPreferences {

    ObservableList<String> dropdownOptions = FXCollections.observableArrayList("No restrictions", "Vegan", "Vegetarian");

    ObservableList<String> menuOptions = FXCollections.observableArrayList("Meal 1", "Meal 2", "Meal 3");

    @FXML
    public ComboBox<String> menuChoiceBox = new ComboBox<>();

    @FXML
    public ComboBox<String> dietaryOptions = new ComboBox<>();

    @FXML
    public TextField allergies;


    @FXML
    private void initialize(){
        dietaryOptions.setItems(dropdownOptions);
        menuChoiceBox.setItems(menuOptions);
    }


    public void foodDeliveryGoBackOnClickedEvent(ActionEvent e) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/MochaManticores/fxml/foodDelivery.fxml"));
            App.getPrimaryStage().getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void foodDeliverySubmitOnClickEvent(ActionEvent e) {
        System.out.println(dietaryOptions.getSelectionModel().getSelectedItem());
        System.out.println(allergies.getText());
        System.out.println(menuChoiceBox.getSelectionModel().getSelectedItem());
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/MochaManticores/fxml/confirmationFoodDelivery.fxml"));
            App.getPrimaryStage().getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
