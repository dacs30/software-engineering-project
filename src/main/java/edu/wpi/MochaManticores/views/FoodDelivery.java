package edu.wpi.MochaManticores.views;

import javafx.event.ActionEvent;

public class FoodDelivery extends SceneController {

    // Go back trigger makes the user to go back
    /*
    * TODO
    * Get a logic that access the stack of scenes
    */
    public void onGoBackClickEvent(ActionEvent e) {
        super.back();
    }

    public void makeAnOrderOnClickEvent(ActionEvent e) {
        changeSceneTo("FoodDeliveryDietaryPreferences");
//        try {
//            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/MochaManticores/fxml/FoodDeliveryDietaryPreferences.fxml"));
//            App.getPrimaryStage().getScene().setRoot(root);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
    }
}
