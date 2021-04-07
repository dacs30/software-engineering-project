package edu.wpi.MochaManticores.views;

import edu.wpi.MochaManticores.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class FoodDelivery extends form{

    // Go back trigger makes the user to go back
    /*
    * TODO
    * Get a logic that access the stack of scenes
    */
    public void onGoBackClickEvent(ActionEvent e) {
        returnToMain(e);
    }

    public void makeAnOrderOnClickEvent(ActionEvent e) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/MochaManticores/fxml/FoodDeliveryDietaryPreferences.fxml"));
            App.getPrimaryStage().getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
