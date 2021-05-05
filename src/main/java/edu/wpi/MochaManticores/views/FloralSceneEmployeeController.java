package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.Services.FloralDelivery;
import edu.wpi.MochaManticores.Services.ServiceRequest;
import edu.wpi.MochaManticores.Services.ServiceRequestType;
import edu.wpi.MochaManticores.database.DatabaseManager;
import edu.wpi.MochaManticores.database.sel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FloralSceneEmployeeController extends SceneController {


    @FXML
    private JFXTextField roomNumber;
    @FXML
    private JFXDatePicker deliveryDate;
    @FXML
    private JFXTextField personalNote;

    @FXML
    private JFXRadioButton tulip;
    @FXML
    private JFXRadioButton rose;
    @FXML
    private JFXRadioButton lilie;

    @FXML
    private JFXRadioButton blueVase;
    @FXML
    private JFXRadioButton yellowVase;
    @FXML
    private JFXRadioButton orangeVase;

    private List<JFXRadioButton> vases;

    private List<JFXRadioButton> flowers;

    @FXML
    private GridPane contentGrid;
    @FXML
    private ImageView backgroundIMG;
    @FXML
    private StackPane dialogPane;

    @FXML
    private GridPane requestPage;

    @FXML
    private GridPane managerPage;

       @FXML
        JFXComboBox employeeAssigned;





        @FXML
        private void goBack(ActionEvent e) {
            back();
        }

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
            ObservableList<String> items = FXCollections.observableArrayList();
            items.addAll(DatabaseManager.getEmpManager().getEmployeeNames());
            employeeAssigned.setItems(items);
            createFilterListener(employeeAssigned);

            double height = App.getPrimaryStage().getScene().getHeight();
            double width = App.getPrimaryStage().getScene().getWidth();
            backgroundIMG.setFitHeight(height);
            backgroundIMG.setFitWidth(width);
            contentGrid.setPrefSize(width, height);

            dialogPane.toBack();

            backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
            backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());

            vases = Arrays.asList(blueVase, yellowVase, orangeVase);

            flowers = Arrays.asList(tulip,rose,lilie);

            if (App.getClearenceLevel() <= 0) {
                employeeAssigned.setVisible(false);
            }
        }

        public void submitForm(ActionEvent actionEvent) {
            submitEvent(actionEvent);
            loadSubmitDialog();
        }

        public void loadSubmitDialog() {
            //TODO Center the text of it.
            dialogPane.toFront();
            dialogPane.setDisable(false);
            JFXDialogLayout message = new JFXDialogLayout();
            message.setMaxHeight(Region.USE_PREF_SIZE);
            message.setMaxHeight(Region.USE_PREF_SIZE);

            final Text hearder = new Text("Your request was submited");
            hearder.setStyle("-fx-font-weight: bold");
            hearder.setStyle("-fx-font-size: 30");
            hearder.setStyle("-fx-font-family: Roboto");
            hearder.setStyle("-fx-alignment: center");
            message.setHeading(hearder);

            final Text body = new Text("Estimated time for arrival: ");
            body.setStyle("-fx-font-size: 15");
            body.setStyle("-fx-font-family: Roboto");
            body.setStyle("-fx-alignment: center");
            message.setHeading(hearder);

            message.setBody(body);
            JFXDialog dialog = new JFXDialog(dialogPane, message, JFXDialog.DialogTransition.CENTER);
            JFXButton ok = new JFXButton("OK");
            ok.setOnAction(event -> {
                goBack(null);
            });

            dialog.setOnDialogClosed(event -> {
                dialogPane.toBack();
                dialog.close();
            });

            message.setActions(ok);
            dialog.show();
        }

        public void helpButton(ActionEvent actionEvent) {
            loadHelpDialogue();
        }

        private void loadDialog() {
            JFXDialogLayout message = new JFXDialogLayout();
            message.setMaxHeight(Region.USE_COMPUTED_SIZE);
            message.setMaxHeight(Region.USE_COMPUTED_SIZE);

            final Text hearder = new Text("Help Page");
            hearder.setStyle("-fx-font-weight: bold");
            hearder.setStyle("-fx-font-size: 60");
            hearder.setStyle("-fx-font-family: Roboto");
            hearder.setStyle("-fx-alignment: center");
            message.setHeading(hearder);

            final Text body = new Text("Room number: This is the room number of the patient you are delivering flowers to.\n" +
                    "Delivery choice is how you want the flowers delivered. \n" +
                    "There are three options for the flowers and three options for the vase color.\n" +
                    "Click on the option you would like.\n" +
                    "Personalized note is not necessary to get flowers delivered, but is an option for if you want to leave a message.");

            body.setStyle("-fx-font-size: 40");
            body.setStyle("-fx-font-family: Roboto");
            body.setStyle("-fx-alignment: center");

            message.setBody(body);


            JFXDialog dialog = new JFXDialog(dialogPane, message, JFXDialog.DialogTransition.CENTER);

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

        private void loadHelpDialogue() {
            dialogPane.toFront();
            loadDialog();
        }

        StringBuilder flowerSelected = new StringBuilder();

        public void checkFlowers(ActionEvent e) {
            JFXRadioButton source = (JFXRadioButton) e.getSource();
            for (JFXRadioButton button : vases) {
                if (!button.equals(source)) {
                    button.setSelected(false);
                }
            }
            if (blueVase.isSelected()) {
                flowerSelected.append("Rose,");
            }
            if (orangeVase.isSelected()) {
                flowerSelected.append("Tulip,");
            }
            if (yellowVase.isSelected()) {
                flowerSelected.append("Lilie,");
            }
        }


    StringBuilder vaseSelected = new StringBuilder();

        public void checkVase(ActionEvent e) {
            JFXRadioButton source = (JFXRadioButton) e.getSource();
            vaseSelected = new StringBuilder();
            for (JFXRadioButton button : vases) {
                if (!button.equals(source)) {
                    button.setSelected(false);
                }
            }
            if (blueVase.isSelected()) {
                vaseSelected.append("blueVase,");
            }
            if (orangeVase.isSelected()) {
                vaseSelected.append("orangeVase,");
            }
            if (yellowVase.isSelected()) {
                vaseSelected.append("yellowVase,");
            }
        }
        public void changeManagerTable(ActionEvent actionEvent) {
            requestPage.setVisible(false);
            managerPage.setVisible(true);
            managerPage.toFront();
        }



        public void submitEvent(ActionEvent actionEvent) {
            if (!roomNumber.getText().isEmpty() && !deliveryDate.equals("") &&
                    (tulip.isSelected() || rose.isSelected() || lilie.isSelected()) &&
                    (blueVase.isSelected() || orangeVase.isSelected() || yellowVase.isSelected()) &&
                    !employeeAssigned.getValue().toString().isEmpty()) {
                sel s = sel.FloralDelivery;
                DatabaseManager.addRequest(s,
                        new FloralDelivery(
                                "", "", false, roomNumber.getText(),
                                deliveryDate.getValue().toString(), flowerSelected.toString(),
                                vaseSelected.toString(),
                                employeeAssigned.getValue().toString()));

            } else if (roomNumber.getText().isEmpty()) {
                RequiredFieldValidator missingInput = new RequiredFieldValidator();
                //roomNumber.getValue().getValidators().add(missingInput);
                //missingInput.setMessage("Patient room is required");
               // roomNumber.validate();
            } else if (deliveryDate.getValue()==null) {
                RequiredFieldValidator missingInput = new RequiredFieldValidator();
                //deliveryDate.getValidators().add(missingInput);
                //missingInput.setMessage("Delivery date is required");
                //deliveryDate.validate();
            } else if (employeeAssigned.getValue().toString().isEmpty()) {
                //RequiredFieldValidator missingInput = new RequiredFieldValidator();
                //empBox.getValidators().add(missingInput);
                //missingInput.setMessage("Employee must be assigned");
                //empBox.validate();
                System.out.println("Adds to database");
            }
        }
    }