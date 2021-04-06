package edu.wpi.MochaManticores.views;

import edu.wpi.MochaManticores.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class FoodDelivery {

    // Go back trigger makes the user to go back
    /*
    * TODO
    * Get a logic that access the stack of scenes
    */
    public void onGoBackClickEvent(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/MochaManticores/fxml/landingPage.fxml"));
            App.getPrimaryStage().getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
