package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.*;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.Services.ReligiousRequest;
import edu.wpi.MochaManticores.database.DatabaseManager;
import edu.wpi.MochaManticores.database.sel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;


public class ReligiousRequestControllerEmployee extends SceneController{


    @FXML
    private GridPane requestPage;

    @FXML
    private GridPane managerPage;

    @FXML
    private JFXTextField reasonBox;

    @FXML
    private ImageView backgroundIMG;
    @FXML
    private JFXTextField roomIDbox;
    @FXML
    private StackPane dialogPane;

    @FXML
    private ImageView helpButton;
    @FXML
    private JFXButton submitBTN;

    ObservableList<String> TypeOfSacredPersons = FXCollections.observableArrayList("Rabbi", "Monk", "Priest",  "Purohit", "Spiritual Person");
    @FXML
    private ComboBox<String> TypeOfSacredPerson;
    @FXML
    private GridPane backgroundGrid;
    @FXML
    private GridPane formSquare;

    @FXML
    private JFXComboBox employeeAssigned;

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

        TypeOfSacredPerson.setItems(TypeOfSacredPersons);

    }


    public void exitPage(){
        super.back();
    }

    public void cancelEvent(ActionEvent actionEvent) {
        exitPage();
    }

    public void changeToRequest(ActionEvent actionEvent) {
        requestPage.setVisible(true);
        managerPage.setVisible(false);
    }

    public void submitEvent(ActionEvent actionEvent) {
        sel s = sel.ReligiousRequest;
        DatabaseManager.addRequest(s,
                new ReligiousRequest("",
                        employeeAssigned.getValue().toString(),
                        false,
                        reasonBox.getText(),
                        roomIDbox.getText(),
                        TypeOfSacredPerson.getSelectionModel().getSelectedItem()));
        loadSubmitDialogue();
    }

    public void loadSubmitDialogue(){
        dialogPane.toFront();
        JFXDialogLayout message = new JFXDialogLayout();
        message.setMaxHeight(Region.USE_COMPUTED_SIZE);
        message.setMaxHeight(Region.USE_COMPUTED_SIZE);

        final Text hearder = new Text("Submitted request.");
        hearder.setStyle("-fx-font-weight: bold");
        hearder.setStyle("-fx-font-size: 60");
        hearder.setStyle("-fx-font-family: Roboto");
        hearder.setStyle("-fx-alignment: center");
        message.setHeading(hearder);

        final Text body = new Text("Your request has been submitted for the patient.");
        body.setStyle("-fx-font-size: 40");
        body.setStyle("-fx-font-family: Roboto");
        body.setStyle("-fx-alignment: center");

        message.setBody(body);


        JFXDialog dialog = new JFXDialog(dialogPane, message,JFXDialog.DialogTransition.CENTER);

        JFXButton cont = new JFXButton("Done");
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

    public void openHelp(MouseEvent mouseEvent) { loadHelpDialogue(); }

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
                "Select a Religious Figure: Please select a religious figure from the dropdown menu.\n" +
                "Assign to Employee: Select an employee from the provided dropdown menu.");
        body.setStyle("-fx-font-size: 40");
        body.setStyle("-fx-font-family: Roboto");
        body.setStyle("-fx-alignment: center");

        message.setBody(body);


        JFXDialog dialog = new JFXDialog(dialogPane, message,JFXDialog.DialogTransition.CENTER);

        JFXButton cont = new JFXButton("Continue");
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

    public void changeManagerTable(ActionEvent actionEvent) {
        requestPage.setVisible(true);
        managerPage.setVisible(false);
    }

    public void completeService(ActionEvent actionEvent) {

    }
}

