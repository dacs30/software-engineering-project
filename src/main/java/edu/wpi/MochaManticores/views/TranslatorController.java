package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.*;
import edu.wpi.MochaManticores.App;
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

public class TranslatorController extends SceneController{

    ObservableList<String> availableLanguages = FXCollections
            .observableArrayList("English","Spanish","Mandarin","Russian","Vietnamese",
                    "Arabic","French","Indonesian","Swahili","Korean","Hindi","Portuguese","Japanese");

    @FXML
    private JFXTextField roomNumber;
    @FXML
    private JFXComboBox languageOne;
    @FXML
    private JFXComboBox languageTwo;
    @FXML
    private StackPane dialogPane;
    @FXML
    private ImageView backgroundIMG;
    @FXML
    private GridPane contentGrid;
    @FXML
    private JFXButton submitButton;

    @FXML
    private ImageView helpButton;

    @FXML
    private void initialize() {
        languageOne.setItems(availableLanguages);
        languageTwo.setItems(availableLanguages);

        double height = App.getPrimaryStage().getScene().getHeight();
        double width = App.getPrimaryStage().getScene().getWidth();
        backgroundIMG.setFitHeight(height);
        backgroundIMG.setFitWidth(width);
        contentGrid.setPrefSize(width, height);

        backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
        backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());

        languageOne.setVisibleRowCount(5);
        languageTwo.setVisibleRowCount(5);
    }

    public void cancelReq(ActionEvent actionEvent) {
        back();
    }

    public void submitEvent() {
        sel s = sel.LanguageInterperter;
        // changeSceneTo(e, "mainMenu");
        DatabaseManager.addRequest(s,
                new edu.wpi.MochaManticores.Services.LanguageInterpreterRequest(
                        "", "", false, roomNumber.getText(),
                        languageOne.getSelectionModel().getSelectedItem().toString(),
                        languageTwo.getSelectionModel().getSelectedItem().toString()));
        dialogPane.setVisible(true);
        loadDialog();
    }


    public void loadDialog() {

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
        JFXDialog dialog = new JFXDialog(dialogPane, message, JFXDialog.DialogTransition.CENTER);
        JFXButton exit = new JFXButton("Done");
        exit.setStyle("-fx-font-size: 15");
        exit.setOnAction(event -> {
            dialog.close();
            dialogPane.toBack();
        });
        dialog.setOnDialogClosed(event -> {
            dialogPane.toBack();
        });
        message.setActions(exit);
        dialog.show();

    }

    public void helpEvent(MouseEvent mouseEvent) {
        loadHelpDialogue();
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

        final Text body = new Text("Patient Name: Please put the name of the patient.\n" +
                "Language 1: Please select the spoken language you want to translate from.\n" +
                "Language 2: Please select the desired language you want to translate to.");
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

}
