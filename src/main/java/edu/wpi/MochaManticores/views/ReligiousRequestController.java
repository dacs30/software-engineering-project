package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.Services.ReligiousRequest;
import edu.wpi.MochaManticores.database.DatabaseManager;
import edu.wpi.MochaManticores.database.sel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;


public class ReligiousRequestController extends SceneController {
    ObservableList<String> TypeOfSacredPersons = FXCollections.observableArrayList("Rabbi", "Monk", "Priest", "Purohit", "Spiritual Person");
    @FXML
    private JFXComboBox<String> typeOfSacredPerson;
    @FXML
    private GridPane backgroundGrid;
    @FXML
    private GridPane formSquare;

    @FXML
    private ImageView backgroundIMG;

    @FXML
    private JFXButton submitBTN;
    @FXML
    private ImageView helpButton;

    @FXML
    private JFXTextField reasonBox;

    @FXML
    private JFXTextField roomIDBox;

    @FXML
    private StackPane dialogPane;

    public void initialize() {
        double height = App.getPrimaryStage().getScene().getHeight();
        double width = App.getPrimaryStage().getScene().getWidth();
        backgroundGrid.setPrefSize(width, height);
        backgroundIMG.setFitHeight(height);
        backgroundIMG.setFitWidth(width);

        backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
        backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());


        typeOfSacredPerson.setItems(TypeOfSacredPersons);
    }

    public void exitPage() {
        super.back();
    }

    public void cancelEvent(ActionEvent actionEvent) {
        exitPage();
    }



    public void submitEvent() {
        if(!reasonBox.getText().isEmpty() && !roomIDBox.getText().isEmpty() &&
                !typeOfSacredPerson.getSelectionModel().isEmpty() ) {
            sel s = sel.ReligiousRequest;
            ReligiousRequest toAdd = new ReligiousRequest("",
                    "",
                    false,
                    reasonBox.getText(),
                    roomIDBox.getText(),
                    typeOfSacredPerson.getSelectionModel().getSelectedItem());
            DatabaseManager.addRequest(s, toAdd);
            toAdd.send(toAdd.getRequestID());
            loadSubmitDialogue();
        }else if(roomIDBox.getText().isEmpty()){
                RequiredFieldValidator missingInput = new RequiredFieldValidator();
                roomIDBox.getValidators().add(missingInput);
                missingInput.setMessage("Location is required");
                roomIDBox.validate();
            } else if(reasonBox.getText().isEmpty()){
            RequiredFieldValidator missingInput = new RequiredFieldValidator();
            reasonBox.getValidators().add(missingInput);
            missingInput.setMessage("Reason for visit is required");
            reasonBox.validate();
        }
        else if(typeOfSacredPerson.getSelectionModel().isEmpty()){
            RequiredFieldValidator missingInput = new RequiredFieldValidator();
            typeOfSacredPerson.getValidators().add(missingInput);
            missingInput.setMessage("The type of religious figure is required");
            typeOfSacredPerson.validate();
        }
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

        final Text body = new Text("Reason for Visit: State the reason for religious request.\n" +
                "Patient Room: The current room in the hospital that the patient resides in.\n" +
                "Select a Religious Figure: Please select a religious figure from the dropdown menu.");
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

    public void loadSubmitDialogue(){
        dialogPane.toFront();
        JFXDialogLayout message = new JFXDialogLayout();
        message.setMaxHeight(Region.USE_COMPUTED_SIZE);
        message.setMaxHeight(Region.USE_COMPUTED_SIZE);

        final Text hearder = new Text("Submitted request");
        hearder.setStyle("-fx-font-weight: bold");
        hearder.setStyle("-fx-font-size: 60");
        hearder.setStyle("-fx-font-family: Roboto");
        hearder.setStyle("-fx-alignment: center");
        message.setHeading(hearder);

        final Text body = new Text("Your request has been submitted and will be reviewed by a staff member.");
        body.setStyle("-fx-font-size: 40");
        body.setStyle("-fx-font-family: Roboto");
        body.setStyle("-fx-alignment: center");

        message.setBody(body);


        JFXDialog dialog = new JFXDialog(dialogPane, message,JFXDialog.DialogTransition.CENTER);

        JFXButton cont = new JFXButton("Done");
        cont.setStyle("-fx-font-size: 15");
        cont.setOnAction(event -> {
            changeSceneTo("landingPage");
        });

        dialog.setOnDialogClosed(event -> {
            dialogPane.toBack();
        });

        message.setActions(cont);
        dialog.show();

    }

    public void openHelp(MouseEvent mouseEvent) {
    }
}

