package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.*;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.database.DatabaseManager;
import edu.wpi.MochaManticores.database.sel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class TranslatorControllerEmployee extends SceneController{


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
    private JFXComboBox employeeAssigned;

    @FXML
    private JFXTextField empBox;
    @FXML
    private StackPane dialogPane;

    @FXML
    private ImageView backgroundIMG;


    @FXML
    private GridPane requestPage;
    @FXML
    private GridPane managerPage;

    @FXML
    private JFXButton submitButton;

    @FXML
    private ImageView helpButton;


    private void createFilterListener(JFXComboBox comboBox) {

        // Create the listener to filter the list as user enters search terms
        FilteredList<String> filteredList = new FilteredList<>(comboBox.getItems());

        // Add listener to our ComboBox textfield to filter the list
        comboBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            comboBox.show();
            filteredList.setPredicate(item -> {


                // If the TextField is empty, return all items in the original list
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Check if the search term is contained anywhere in our list
                return item.toLowerCase().contains(newValue.toLowerCase().trim());

            });
        });

        // Finally, let's add the filtered list to our ComboBox
        comboBox.setItems(filteredList);

    }

    @FXML
    private void initialize() {
        employeeAssigned.setEditable(true);
        //fromLocation.setOnKeyTyped(new AutoCompleteComboBoxListener<>(fromLocation));
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(DatabaseManager.getEmpManager().getEmployeeNames());
        employeeAssigned.setItems(items);
        createFilterListener(employeeAssigned);


        double height = App.getPrimaryStage().getScene().getHeight();
        double width = App.getPrimaryStage().getScene().getWidth();
        backgroundIMG.setFitHeight(height);
        backgroundIMG.setFitWidth(width);

        backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
        backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());


        languageOne.setItems(availableLanguages);
        languageTwo.setItems(availableLanguages);
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
                        "", employeeAssigned.getEditor().getText(), false, roomNumber.getText(),
                        languageOne.getSelectionModel().getSelectedItem().toString(),
                        languageTwo.getSelectionModel().getSelectedItem().toString()));
        dialogPane.setVisible(true);
        loadDialog();
//        back();
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

        final Text body = new Text("Your request has been submitted for the patient.");
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
                "Language 2: Please select the desired language you want to translate to.\n" +
                "Assign to Employee: Select an employee from the provided dropdown menu.");
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

    public void changeToRequest(ActionEvent actionEvent) {
        requestPage.setVisible(true);
        managerPage.setVisible(false);
        requestPage.toFront();
    }

}
