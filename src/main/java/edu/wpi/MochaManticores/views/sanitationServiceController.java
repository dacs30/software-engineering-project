package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.database.DatabaseManager;
import edu.wpi.MochaManticores.database.sel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.List;

public class sanitationServiceController extends SceneController {

    ObservableList<String> sanitationType = FXCollections.observableArrayList("Room Cleaning","Spill");

    @FXML
    private JFXTextField loc;

    @FXML
    private JFXTextField safetyHaz;

    @FXML
    private JFXComboBox<String> typeComboBox;

    @FXML
    private JFXCheckBox glovesNeeded;
    @FXML
    private JFXCheckBox maskNeeded;
    @FXML
    private JFXCheckBox mopNeeded;

    private List<JFXCheckBox> equipment;

    @FXML
    private JFXTextField description;

    @FXML
    public JFXButton submitButton;

    @FXML
    public JFXButton cancelButton;

    @FXML
    public ImageView helpBtn;

    @FXML
    public JFXTextField empBox;

    @FXML
    public ImageView backgroundIMG;

    @FXML
    public ImageView badgeIMG;



    @FXML
    public GridPane contentGrid;

    @FXML
    private StackPane dialogPane;

    @FXML
    private Label empTitle;

    @FXML
    private JFXButton backBtn;


    @FXML
    private void initialize(){
        double height = App.getPrimaryStage().getScene().getHeight();
        double width = App.getPrimaryStage().getScene().getWidth();
        backgroundIMG.setFitHeight(height);
        backgroundIMG.setFitWidth(width);
        contentGrid.setPrefSize(width,height);

        dialogPane.toBack();
        backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
        backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());

        typeComboBox.setItems(sanitationType);
        equipment = Arrays.asList(glovesNeeded, maskNeeded, mopNeeded);

    }

    public void helpButton(){loadHelpDialogue();}

    public void goBack() {
        back();
    }

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

        final Text body = new Text("Your request has been submitted and will be reviewed by a staff member.");
        body.setStyle("-fx-font-size: 15");
        body.setStyle("-fx-font-family: Roboto");
        body.setStyle("-fx-alignment: center");
        message.setHeading(hearder);

        message.setBody(body);
        JFXDialog dialog = new JFXDialog(dialogPane, message,JFXDialog.DialogTransition.CENTER);
        JFXButton ok = new JFXButton("Done");
        ok.setStyle("-fx-font-size: 15");
        ok.setOnAction(event -> {
            changeSceneTo("landingPage");
        });

        dialog.setOnDialogClosed(event -> {
            dialogPane.toBack();
            dialog.close();
        });

        message.setActions(ok);
        dialog.show();
    }

    private void loadDialog(){
        JFXDialogLayout message = new JFXDialogLayout();
        message.setMaxHeight(Region.USE_COMPUTED_SIZE);
        message.setMaxHeight(Region.USE_COMPUTED_SIZE);

        final Text hearder = new Text("Help Page");
        hearder.setStyle("-fx-font-weight: bold");
        hearder.setStyle("-fx-font-size: 60");
        hearder.setStyle("-fx-font-family: Roboto");
        hearder.setStyle("-fx-alignment: center");
        message.setHeading(hearder);

        final Text body = new Text("Location: The room number, hallway, waiting area, etc. of the sanitation request.\n" +
                "Safety Hazards: This is where you indicate any safety hazards such as biohazards or densely populated area near the location. \n" +
                "Sanitation Type: This is where you indicate what the request is for such as room cleaning or spill.\n" +
                "Equipment Needed: This is where you indicate what equipment may be needed to complete the request.\n" +
                "Description: This is where you indicate the rest of the necessary information to complete the request.");
        body.setStyle("-fx-font-size: 40");
        body.setStyle("-fx-font-family: Roboto");
        body.setStyle("-fx-alignment: center");

        message.setBody(body);


        JFXDialog dialog = new JFXDialog(dialogPane, message,JFXDialog.DialogTransition.CENTER);

        JFXButton cont = new JFXButton("Continue");
        cont.setStyle("-fx-font-size: 15");
        cont.setOnAction(event -> {
            dialog.close();
            dialogPane.toBack();
        });

        dialog.setOnDialogClosed(event -> {
            dialogPane.toBack();
        });

        message.setActions(cont);
        dialog.show();

    }

    public void loadHelpDialogue() {
        dialogPane.toFront();
        loadDialog();
    }



    @FXML
    public void submitEvent() {
        StringBuilder equipmentNeeded = new StringBuilder();
        if(maskNeeded.isSelected()) {
            equipmentNeeded.append("maskNeeded,");
        }
        if(glovesNeeded.isSelected()) {
            equipmentNeeded.append("gloveNeeded,");
        }
        if(mopNeeded.isSelected()) {
            equipmentNeeded.append("mopNeeded,");
        }
            if(!loc.getText().isEmpty() && !safetyHaz.getText().isEmpty() &&
                 !typeComboBox.getSelectionModel().isEmpty() && !equipmentNeeded.toString().isEmpty() && !description.getText().isEmpty()){
            sel s = sel.SanitationServices;
                edu.wpi.MochaManticores.Services.SanitationServices toAdd = new edu.wpi.MochaManticores.Services.SanitationServices(
                        "",
                        "",
                        false,
                        loc.getText(),
                        safetyHaz.getText(),
                        typeComboBox.getSelectionModel().getSelectedItem(),
                        equipmentNeeded.toString(),
                        description.getText()
                );
                DatabaseManager.addRequest(s,toAdd);
                toAdd.send(toAdd.getRequestID());
                loadSubmitDialog();
            System.out.println("Adds to database");
        } else if (loc.getText().isEmpty()){
            RequiredFieldValidator missingInput = new RequiredFieldValidator();
            loc.getValidators().add(missingInput);
            missingInput.setMessage("Location is required.");
            loc.validate();
        } else if (description.getText().isEmpty()){
                RequiredFieldValidator missingInput = new RequiredFieldValidator();
                description.getValidators().add(missingInput);
                missingInput.setMessage("Description is required.");
                description.validate();
            } else if (typeComboBox.getSelectionModel().isEmpty()){
                RequiredFieldValidator missingInput = new RequiredFieldValidator();
                typeComboBox.getValidators().add(missingInput);
                missingInput.setMessage("Sanitation Type is required.");
                typeComboBox.validate();
            } else if (safetyHaz.getText().isEmpty()) {
                RequiredFieldValidator missingInput = new RequiredFieldValidator();
                safetyHaz.getValidators().add(missingInput);
                missingInput.setMessage("Safety Hazards are required.");
                safetyHaz.validate();
            }
    }

}

