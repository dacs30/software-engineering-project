package edu.wpi.MochaManticores.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;

public class FoodDelivery extends SceneController{

    @FXML
    private JFXComboBox<String> dietaryPreferences;

    @FXML
    private JFXComboBox<String> foodMenu;

    @FXML
    private StackPane dialogPane;

    @FXML
    private GridPane contentGrid;

    @FXML
    private ImageView backgroundIMG;

    public void initialize() {
        double height = App.getPrimaryStage().getScene().getHeight();
        double width = App.getPrimaryStage().getScene().getWidth();
        backgroundIMG.setFitHeight(height);
        backgroundIMG.setFitWidth(width);
        contentGrid.setPrefSize(width,height);
        System.out.println(width);

        dietaryPreferences.getItems().clear();
        dietaryPreferences.getItems().addAll("Vegan", "Vegetarian", "Gluten Free");

        // TODO Condition of menus depending of the dietary preference
        foodMenu.getItems().clear();
        foodMenu.getItems().addAll("Menu 0","Menu 1", "Menu 2", "Menu 3");

        dialogPane.setDisable(false);
    }

    public void submitForm(ActionEvent e) {
        // TODO Submit action
        // changeSceneTo(e, "mainMenu");
        dialogPane.setVisible(true);
        loadDialog();
    // Go back trigger makes the user to go back
    /*
    * TODO
    * Get a logic that access the stack of scenes
    */
    public void onGoBackClickEvent(ActionEvent e) {
        super.back();
    }

    public void loadDialog(){
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
        JFXDialog dialog = new JFXDialog(dialogPane, message,JFXDialog.DialogTransition.CENTER);
        JFXButton exit = new JFXButton("OK!");
        exit.setOnAction(event -> {
            back(event);
        });
        dialog.setOnDialogClosed(event -> {
            dialogPane.setDisable(true);
            dialogPane.toBack();
        });
        message.setActions(exit);
        dialog.show();
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
