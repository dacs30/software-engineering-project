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
            .observableArrayList("English","Spanish","Mandarin");

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

        dialogPane.setDisable(false);

    }

    public void cancelReq(ActionEvent actionEvent) {
        back();
    }

    public void submitReq(ActionEvent actionEvent) {
        sel s = sel.LanguageInterperter;
        // changeSceneTo(e, "mainMenu");
        DatabaseManager.addRequest(s,
                new edu.wpi.MochaManticores.Services.LanguageInterpreterRequest(
                        "", "", false, roomNumber.getText(),
                        languageOne.getSelectionModel().getSelectedItem().toString(),
                        languageTwo.getSelectionModel().getSelectedItem().toString()));
        dialogPane.setVisible(true);
        loadDialog();
        back();
    }

    private void loadHelpDialog(){
        dialogPane.toFront();
        dialogPane.setDisable(false);
        JFXDialogLayout message = new JFXDialogLayout();
        message.setMaxHeight(Region.USE_COMPUTED_SIZE);
        message.setMaxHeight(Region.USE_COMPUTED_SIZE);

        final Text hearder = new Text("Help Page");
        hearder.setStyle("-fx-font-weight: bold");
        hearder.setStyle("-fx-font-size: 60");
        hearder.setStyle("-fx-font-family: Roboto");
        hearder.setStyle("-fx-alignment: center");
        message.setHeading(hearder);

        final Text body = new Text("Room Number: Room that you are currently in.\n" +
                "Language One: Language that you speak\n" +
                "Language Two: Language you need translated\n");

        body.setStyle("-fx-font-size: 40");
        body.setStyle("-fx-font-family: Roboto");
        body.setStyle("-fx-alignment: center");

        message.setBody(body);


        JFXDialog dialog = new JFXDialog(dialogPane, message,JFXDialog.DialogTransition.CENTER);

        JFXButton cont = new JFXButton("CONTINUE");
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

    public void loadDialog() {
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
        JFXDialog dialog = new JFXDialog(dialogPane, message, JFXDialog.DialogTransition.CENTER);
        JFXButton exit = new JFXButton("OK!");
        exit.setOnAction(event -> {
            back();
        });
        dialog.setOnDialogClosed(event -> {
            dialogPane.setDisable(true);
            dialogPane.toBack();
        });
        message.setActions(exit);
        dialog.show();

    }

    public void helpButton(MouseEvent mouseEvent) {
        loadHelpDialog();
    }

}
