package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.*;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.Services.EmergencyRequest;
import edu.wpi.MochaManticores.database.DatabaseManager;
import edu.wpi.MochaManticores.database.sel;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class emergencyFormEmployee extends SceneController {

    @FXML
    private JFXComboBox<String> employeeAssigned;

    @FXML
    private ComboBox numPeople;

    @FXML
    private ImageView backgroundIMG;

    @FXML
    private TextField roomNumber;

    @FXML
    private StackPane dialogPane;

    @FXML
    private JFXCheckBox gurney;

    public void initialize() {
        double height = super.getHeight();
        double width = super.getWidth();
        backgroundIMG.setFitHeight(height);
        backgroundIMG.setFitWidth(width);

        backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
        backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());

        numPeople.getItems().clear();
        numPeople.getItems().addAll("1", "2", "3");
        numPeople.getSelectionModel().select("1");
    }

    public void backBtn() {
        back();
    }

    public void submitEmergency() {

        DatabaseManager.addRequest(sel.Emergency, new EmergencyRequest("",
                employeeAssigned.getEditor().getText(),
                false,
                (Integer) numPeople.getSelectionModel().getSelectedItem(),
                roomNumber.getText(),
                gurney.isSelected()));
        loadSubmitDialog();
    }

    public void helpEvent(){ loadHelpDialogue();}

    public void loadSubmitDialog(){
        //TODO Center the text of it.
        dialogPane.toFront();
        dialogPane.setDisable(false);
        JFXDialogLayout message = new JFXDialogLayout();
        message.setMaxHeight(Region.USE_PREF_SIZE);
        message.setMaxHeight(Region.USE_PREF_SIZE);

        final Text hearder = new Text("Submitted request");
        hearder.setStyle("-fx-font-weight: bold");
        hearder.setStyle("-fx-font-size: 30");
        hearder.setStyle("-fx-font-family: Roboto");
        hearder.setStyle("-fx-alignment: center");
        message.setHeading(hearder);

        final Text body = new Text("Help is on the way!");
        body.setStyle("-fx-font-size: 15");
        body.setStyle("-fx-font-family: Roboto");
        body.setStyle("-fx-alignment: center");
        message.setHeading(hearder);

        message.setBody(body);
        JFXDialog dialog = new JFXDialog(dialogPane, message,JFXDialog.DialogTransition.CENTER);
        JFXButton ok = new JFXButton("Done");
        ok.setStyle("-fx-font-size: 15");
        ok.setOnAction(event -> {
            backBtn();
        });

        dialog.setOnDialogClosed(event -> {
            dialogPane.toBack();
            dialog.close();
        });

        message.setActions(ok);
        dialog.show();
    }

    public void loadHelpDialogue(){
        dialogPane.toFront();
        JFXDialogLayout message = new JFXDialogLayout();
        message.setMaxHeight(Region.USE_COMPUTED_SIZE);
        message.setMaxHeight(Region.USE_COMPUTED_SIZE);

        final Text hearder = new Text("Help Page");
        hearder.setStyle("-fx-font-weight: bold");
        hearder.setStyle("-fx-font-size: 60");
        hearder.setStyle("-fx-font-family: Roboto");
        hearder.setStyle("-fx-alignment: center");
        message.setHeading(hearder);

        final Text body = new Text("How many people do you need?: Please select from the dropdown menu and select the number of people needed.\n"+
                "Which room is the emergency in?: Please type in the room number or name that the emergency is happening in.\n"+
                "Need a gurney?: Please check the box off, if a gurney is necessary.\n"+
                "Assign to Employee: Select an employee from the provided dropdown menu.");
        body.setStyle("-fx-font-size: 40");
        body.setStyle("-fx-font-family: Roboto");
        body.setStyle("-fx-alignment: center");

        message.setBody(body);


        JFXDialog dialog = new JFXDialog(dialogPane, message,JFXDialog.DialogTransition.CENTER);

        JFXButton cont = new JFXButton("Continue");
        cont.setStyle("-fx-font-size: 15");
        cont.setOnAction(event -> {
            dialogPane.toBack();
            dialog.close();
        });

        dialog.setOnDialogClosed(event -> {
            dialog.close();
        });

        message.setActions(cont);
        dialog.show();

    }
}
