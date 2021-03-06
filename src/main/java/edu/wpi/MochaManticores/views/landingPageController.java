package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.*;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.Exceptions.InvalidElementException;
import edu.wpi.MochaManticores.database.DatabaseManager;
import edu.wpi.MochaManticores.database.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class landingPageController extends SceneController {

    @FXML
    private ColumnConstraints mainGrid;

    @FXML
    private GridPane gridSurvey;

    @FXML
    private GridPane sidePanel;

    @FXML
    private StackPane dialogPane;

    @FXML
    private AnchorPane scenesPane;

    @FXML
    private JFXButton userButton;

    @FXML
    private HBox menuSidePane;

    @FXML
    private HBox foodDeliverySidePanel;

    @FXML
    private HBox medicineDeliverySidePanel;

    @FXML
    private HBox floralSceneSidePanel;

    @FXML
    private HBox internalTransportationSidePanel;

    @FXML
    private HBox externalTransportationSidePanel;


    @FXML
    private HBox servicesBox;

    @FXML
    private HBox surveySideMenu;

    @FXML
    private HBox sanitationSideMenu;

    @FXML
    private HBox mapSidePane;

    @FXML
    private HBox emergencySidePanel;

    @FXML
    private HBox religionSidePane;

    @FXML
    private HBox userSettings;

    @FXML
    private HBox laundrySidePane;

    @FXML
    private HBox translatorSidePane;

    @FXML
    private Label greetingLabel;

    @FXML
    private ScrollPane servicesPane;

    @FXML
    private HBox currentVbox;

    @FXML
    private VBox textFieldGroup;

    @FXML
    private JFXTextField searchBar;

    LinkedList<HBox> listServices = new LinkedList<>();

    // services VBox
    VBox services = new VBox();

    public void initialize() throws IOException {

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            services.getChildren().clear();

            for (Object service : listServices) {
                if (service.toString().toLowerCase().contains(newValue.replaceAll("\\s+", ""))) {
                    if (!services.getChildren().contains(service)) {
                        services.getChildren().add((Node) service);
                    }
                }
            }
        });

        sidePanel.toFront();

        double height = super.getHeight();
        double width = super.getWidth();
        //contentPane.setPrefSize(width,height);

        Parent root = null;
        greetingLabel.setText("Hello, " + App.getCurrentUsername());

        if (App.getClearenceLevel() == 1) {
            userButton.setVisible(true);
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/wpi/MochaManticores/fxml/employeeHomePage.fxml")));
        } else {
            userSettings.getChildren().remove(userButton);
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/wpi/MochaManticores/fxml/homePage2.fxml")));
        }

        scenesPane.getChildren().add(root);

        currentVbox = menuSidePane;

        menuSidePane.setStyle("-fx-background-radius: 20;");
        menuSidePane.setStyle("-fx-background-color: rgba(15,75,145,0.29);");

        dialogPane.toBack();

        listServices.add(menuSidePane);
        listServices.add(mapSidePane);
        listServices.add(servicesBox);
        listServices.add(foodDeliverySidePanel);
        listServices.add(medicineDeliverySidePanel);
        listServices.add(internalTransportationSidePanel);
        listServices.add(externalTransportationSidePanel);
        listServices.add(floralSceneSidePanel);
        listServices.add(sanitationSideMenu);
        listServices.add(surveySideMenu);
        listServices.add(religionSidePane);
        listServices.add(laundrySidePane);
        listServices.add(translatorSidePane);


        for (HBox service : listServices) {
            services.getChildren().add(service);
        }


        //search
        //loop that gets the children and compates them to a string

        services.setMaxWidth(Region.USE_COMPUTED_SIZE);

        //StackPane container = new StackPane(services);
        servicesPane.setContent(services);
        servicesPane.setMaxWidth(sidePanel.getMaxWidth());

        // get rid of the horizontal scroll
        servicesPane.setFitToWidth(true);
        if (scenesPane == null) {
            System.out.println("ERROR");
        }
        super.setLandingPageWindow(scenesPane);
    }


    public void setAutoComplete(JFXTextField test, List<HBox> items) {
        JFXAutoCompletePopup<HBox> autoCompletePopup = new JFXAutoCompletePopup<>();
        autoCompletePopup.getSuggestions().addAll(items);

        autoCompletePopup.setSelectionHandler(event -> {
            test.setText(event.getObject().toString());

            // you can do other actions here when text completed

        });

        // filtering options
        test.textProperty().addListener(observable -> {
            autoCompletePopup.filter(string -> string.toString().toLowerCase().contains(test.getText().toLowerCase()));
            if (autoCompletePopup.getFilteredSuggestions().isEmpty()) {
                autoCompletePopup.hide();
                // if you remove textField.getText.isEmpty() when text field is empty it suggests all options
                // so you can choose
            } else {
                autoCompletePopup.show(test);
            }
        });
    }

    //emergencyDialog
    public void loadDialog() {
        //TODO Center the text of it.
        //dialogPane.setDisable(false);
        JFXDialogLayout message = new JFXDialogLayout();
        message.setMaxHeight(Region.USE_PREF_SIZE);
        message.setMaxHeight(Region.USE_PREF_SIZE);

        final Text hearder = new Text("Are you sure it is an emergency?\n Do this");
        hearder.setStyle("-fx-font-weight: bold");
        hearder.setStyle("-fx-font-size: 30");
        hearder.setStyle("-fx-font-family: Roboto");
        hearder.setStyle("-fx-alignment: center");
        message.setHeading(hearder);

        final Text body = new Text("If it is, please, click yes to proceed with the form");
        body.setStyle("-fx-font-size: 15");
        body.setStyle("-fx-font-family: Roboto");
        body.setStyle("-fx-alignment: center");
        message.setHeading(hearder);

        message.setBody(body);
        JFXDialog dialog = new JFXDialog(dialogPane, message, JFXDialog.DialogTransition.CENTER);
        JFXButton yes = new JFXButton("Yes");
        yes.setOnAction(event -> {
            // change the colors of the old selected page back to the default
            currentVbox.setStyle("-fx-background-radius: 0;");
            currentVbox.setStyle("-fx-background-color:  #E9E9E9");
            // send the dialog to back
            dialogPane.toBack();
            // close the dialog
            dialog.close();
            // remove all the children from the scene panes to render the emergency form
            scenesPane.getChildren().removeAll(scenesPane.getChildren());
            Parent root = null;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/wpi/MochaManticores/fxml/EmergencyForm.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            scenesPane.getChildren().add(root);
        });

        JFXButton no = new JFXButton("No");
        no.setOnAction(event -> {
            dialog.close();
            dialogPane.toBack();
        });

        dialog.setOnDialogClosed(event -> {
            dialogPane.toBack();
        });

        message.setActions(yes, no);
        dialogPane.toFront();
        dialog.show();

    }
    // searches through each letter and displays the HBox
    // service requests


    public void renderMenu(MouseEvent mouseEvent) throws IOException {
        //disables message GUI posts
        App.getClient().closeGUI();

        // if it is an employee the page page is different
        if (App.getClearenceLevel() == 1) {
            super.changeWindowTo("employeeHomePage");
        } else {
            super.changeWindowTo("homePage2");
        }

        currentVbox.setStyle("-fx-background-radius: 0;");
        currentVbox.setStyle("-fx-background-color:  #E9E9E9");

        currentVbox = menuSidePane;

        menuSidePane.setStyle("-fx-background-radius: 20;");
        menuSidePane.setStyle("-fx-background-color: rgba(15,75,145,0.29);");

    }

    public void renderFoodDelivery(MouseEvent mouseEvent) throws IOException {

        App.getClient().closeGUI();
        // removes the children so you don't end up with weird scenes one over the other
        scenesPane.getChildren().removeAll(scenesPane.getChildren());

        // sets parent to be the file to be loaded
        // if it is an employee the page page is different
        Parent root = null;
        if (App.getClearenceLevel() == 1) {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(
                    "/edu/wpi/MochaManticores/fxml/foodDeliveryEmployee.fxml")));
        } else {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/wpi/MochaManticores/fxml/foodDelivery.fxml")));
        }

        // change the colors of the old selected page back to the default
        currentVbox.setStyle("-fx-background-radius: 0;");
        currentVbox.setStyle("-fx-background-color:  #E9E9E9");

        // changes the currentbox
        currentVbox = foodDeliverySidePanel;

        // gives the selected properties for the new selected page
        foodDeliverySidePanel.setStyle("-fx-background-radius: 20;");
        foodDeliverySidePanel.setStyle("-fx-background-color: rgba(15,75,145,0.29);");

        // adds the selected page to the scenesPane so it can be displayed
        scenesPane.getChildren().add(root);
    }


    public void renderMedicineDelivery(MouseEvent mouseEvent) throws IOException {

        App.getClient().closeGUI();
        // removes the children so you don't end up with weird scenes one over the other
        scenesPane.getChildren().removeAll(scenesPane.getChildren());
        Parent root;
        if (App.getClearenceLevel() == 1) {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/wpi/MochaManticores/fxml/medicineDeliveryEmployee.fxml")));

        } else {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/wpi/MochaManticores/fxml/medicineDelivery.fxml")));
        }
        // sets parent to be the file to be loaded


        // change the colors of the old selected page back to the default
        currentVbox.setStyle("-fx-background-radius: 0;");
        currentVbox.setStyle("-fx-background-color:  #E9E9E9");

        // changes the currentbox
        currentVbox = medicineDeliverySidePanel;

        // gives the selected properties for the new selected page
        medicineDeliverySidePanel.setStyle("-fx-background-radius: 20;");
        medicineDeliverySidePanel.setStyle("-fx-background-color: rgba(15,75,145,0.29);");

        // adds the selected page to the scenesPane so it can be displayed
        scenesPane.getChildren().add(root);
    }

    public void renderFloralScene(MouseEvent mouseEvent) throws IOException {
        App.getClient().closeGUI();
        // removes the children so you don't end up with weird scenes one over the other
        scenesPane.getChildren().removeAll(scenesPane.getChildren());
        Parent root;
        if (App.getClearenceLevel() == 1) {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/wpi/MochaManticores/fxml/floralSceneEmployee.fxml")));

        } else {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/wpi/MochaManticores/fxml/floralScene.fxml")));
        }
        // sets parent to be the file to be loaded


        // change the colors of the old selected page back to the default
        currentVbox.setStyle("-fx-background-radius: 0;");
        currentVbox.setStyle("-fx-background-color:  #E9E9E9");

        // changes the currentbox
        currentVbox = floralSceneSidePanel;

        // gives the selected properties for the new selected page
        floralSceneSidePanel.setStyle("-fx-background-radius: 20;");
        floralSceneSidePanel.setStyle("-fx-background-color: rgba(15,75,145,0.29);");

        // adds the selected page to the scenesPane so it can be displayed
        scenesPane.getChildren().add(root);
    }

    public void renderInternalTransportation(MouseEvent mouseEvent) throws IOException {
        App.getClient().closeGUI();
        // removes the children so you don't end up with weird scenes one over the other
        scenesPane.getChildren().removeAll(scenesPane.getChildren());

        // sets parent to be the file to be loaded
        // if it is an employee the page page is different
        Parent root = null;
        if (App.getClearenceLevel() == 1) {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/wpi/MochaManticores/fxml/internalTransportationEmployee.fxml")));
        } else {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/wpi/MochaManticores/fxml/internalTransportation.fxml")));
        }

        // change the colors of the old selected page back to the default
        currentVbox.setStyle("-fx-background-radius: 0;");
        currentVbox.setStyle("-fx-background-color:  #E9E9E9");

        // changes the currentbox
        currentVbox = internalTransportationSidePanel;

        // gives the selected properties for the new selected page
        internalTransportationSidePanel.setStyle("-fx-background-radius: 20;");
        internalTransportationSidePanel.setStyle("-fx-background-color: rgba(15,75,145,0.29);");

        // adds the selected page to the scenesPane so it can be displayed
        scenesPane.getChildren().add(root);

    }

    public void renderExternalTransportation(MouseEvent mouseEvent) throws IOException {
        App.getClient().closeGUI();
        // removes the children so you don't end up with weird scenes one over the other
        scenesPane.getChildren().removeAll(scenesPane.getChildren());

        // sets parent to be the file to be loaded
        Parent root;
        if (App.getClearenceLevel() == 1) {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/wpi/MochaManticores/fxml/extTransportationEmployee.fxml")));
        } else {
            root = root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/wpi/MochaManticores/fxml/extTransportation.fxml")));
        }

        // change the colors of the old selected page back to the default
        currentVbox.setStyle("-fx-background-radius: 0;");
        currentVbox.setStyle("-fx-background-color:  #E9E9E9");

        // changes the currentbox
        currentVbox = externalTransportationSidePanel;

        // gives the selected properties for the new selected page
        externalTransportationSidePanel.setStyle("-fx-background-radius: 20;");
        externalTransportationSidePanel.setStyle("-fx-background-color: rgba(15,75,145,0.29);");

        // adds the selected page to the scenesPane so it can be displayed
        scenesPane.getChildren().add(root);
    }

    public void renderCovidSurvey(MouseEvent mouseEvent) throws IOException {
        App.getClient().closeGUI();
        // removes the children so you don't end up with weird scenes one over the other
        scenesPane.getChildren().removeAll(scenesPane.getChildren());

        // sets parent to be the file to be loaded
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/wpi/MochaManticores/fxml/covidSurvey.fxml")));

        // change the colors of the old selected page back to the default
        currentVbox.setStyle("-fx-background-radius: 0;");
        currentVbox.setStyle("-fx-background-color:  #E9E9E9");

        // changes the currentbox
        currentVbox = surveySideMenu;

        // gives the selected properties for the new selected page
        surveySideMenu.setStyle("-fx-background-radius: 20;");
        surveySideMenu.setStyle("-fx-background-color: rgba(15,75,145,0.29);");

        // adds the selected page to the scenesPane so it can be displayed
        scenesPane.getChildren().add(root);
    }

    public void renderSanitationServices(MouseEvent mouseEvent) throws IOException {
        App.getClient().closeGUI();
        // removes the children so you don't end up with weird scenes one over the other
        scenesPane.getChildren().removeAll(scenesPane.getChildren());

        // sets parent to be the file to be loaded
        Parent root;
        if (App.getClearenceLevel() == 1) {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/wpi/MochaManticores/fxml/sanitationServiceEmployee.fxml")));
        } else {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/wpi/MochaManticores/fxml/sanitationService.fxml")));
        }

        // change the colors of the old selected page back to the default
        currentVbox.setStyle("-fx-background-radius: 0;");
        currentVbox.setStyle("-fx-background-color:  #E9E9E9");

        // changes the currentbox
        currentVbox = sanitationSideMenu;

        // gives the selected properties for the new selected page
        sanitationSideMenu.setStyle("-fx-background-radius: 20;");
        sanitationSideMenu.setStyle("-fx-background-color: rgba(15,75,145,0.29);");

        // adds the selected page to the scenesPane so it can be displayed
        scenesPane.getChildren().add(root);
    }

    public void openEmergencyDIalog(MouseEvent mouseEvent) {
        loadDialog();
    }

    public void renderReligious(MouseEvent mouseEvent) throws IOException {
        App.getClient().closeGUI();

        // removes the children so you don't end up with weird scenes one over the other
        scenesPane.getChildren().removeAll(scenesPane.getChildren());

        // sets parent to be the file to be loaded
        Parent root;
        if (App.getClearenceLevel() == 1) {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/wpi/MochaManticores/fxml/ReligiousRequestEmployee.fxml")));
        } else {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/wpi/MochaManticores/fxml/ReligiousRequest.fxml")));
        }


        // change the colors of the old selected page back to the default
        currentVbox.setStyle("-fx-background-radius: 0;");
        currentVbox.setStyle("-fx-background-color:  #E9E9E9");

        // changes the currentbox
        currentVbox = religionSidePane;

        // gives the selected properties for the new selected page
        religionSidePane.setStyle("-fx-background-radius: 20;");
        religionSidePane.setStyle("-fx-background-color: rgba(15,75,145,0.29);");

        // adds the selected page to the scenesPane so it can be displayed
        scenesPane.getChildren().add(root);
    }

    public void renderLaundry(MouseEvent mouseEvent) throws IOException {
        App.getClient().closeGUI();
        // removes the children so you don't end up with weird scenes one over the other
        scenesPane.getChildren().removeAll(scenesPane.getChildren());

        // sets parent to be the file to be loaded
        Parent root;
        if (App.getClearenceLevel() == 1) {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/wpi/MochaManticores/fxml/laundryFormEmployee.fxml")));
        } else {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/wpi/MochaManticores/fxml/laundryForm.fxml")));
        }

        // change the colors of the old selected page back to the default
        currentVbox.setStyle("-fx-background-radius: 0;");
        currentVbox.setStyle("-fx-background-color:  #E9E9E9");

        // changes the currentbox
        currentVbox = laundrySidePane;

        // gives the selected properties for the new selected page
        laundrySidePane.setStyle("-fx-background-radius: 20;");
        laundrySidePane.setStyle("-fx-background-color: rgba(15,75,145,0.29);");

        // adds the selected page to the scenesPane so it can be displayed
        scenesPane.getChildren().add(root);
    }

    public void renderTranslator(MouseEvent mouseEvent) throws IOException {
        App.getClient().closeGUI();
        // removes the children so you don't end up with weird scenes one over the other
        scenesPane.getChildren().removeAll(scenesPane.getChildren());

        // sets parent to be the file to be loaded
        Parent root;
        if (App.getClearenceLevel() == 1) {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/wpi/MochaManticores/fxml/translatorFormEmployee.fxml")));
        } else {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/wpi/MochaManticores/fxml/translatorForm.fxml")));
        }

        // change the colors of the old selected page back to the default
        currentVbox.setStyle("-fx-background-radius: 0;");
        currentVbox.setStyle("-fx-background-color:  #E9E9E9");

        // changes the currentbox
        currentVbox = translatorSidePane;

        // gives the selected properties for the new selected page
        translatorSidePane.setStyle("-fx-background-radius: 20;");
        translatorSidePane.setStyle("-fx-background-color: rgba(15,75,145,0.29);");

        // adds the selected page to the scenesPane so it can be displayed
        scenesPane.getChildren().add(root);
    }

    public void renderMapEditor(MouseEvent mouseEvent) throws IOException {
        App.getClient().closeGUI();

        if (App.getClearenceLevel() >= 1) {
            super.changeSceneTo("employeeMapPage");
        } else {
            super.changeSceneTo("mapPage");
        }

//        // removes the children so you don't end up with weird scenes one over the other
//    scenesPane.getChildren().removeAll(scenesPane.getChildren());
//
//    Parent root;
//
//    // if it is an employee the page page is different
//    if(App.getClearenceLevel() == 1){
//      root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/wpi/MochaManticores/fxml/employeeMapPage.fxml")));
//    } else {
//      root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/wpi/MochaManticores/fxml/mapPage.fxml")));
//    }
//
//
//    // change the colors of the old selected page back to the default
//    currentVbox.setStyle("-fx-background-radius: 0;");
//    currentVbox.setStyle("-fx-background-color:  #E9E9E9");
//
//    // changes the currentbox
//    currentVbox = mapSidePane;
//
//    // gives the selected properties for the new selected page
//    mapSidePane.setStyle("-fx-background-radius: 20;");
//    mapSidePane.setStyle("-fx-background-color: rgba(15,75,145,0.29);");

//    scenesPane.prefHeightProperty().bind(root.getScene().heightProperty());
//    scenesPane.prefWidthProperty().bind(root.getScene().widthProperty().subtract(sidePanel.widthProperty()));
//
//
//    // adds the selected page to the scenesPane so it can be displayed
//    scenesPane.maxHeightProperty().bind(App.getPrimaryStage().heightProperty());
//    scenesPane.maxWidthProperty().bind(App.getPrimaryStage().widthProperty().subtract(sidePanel.widthProperty()));
//    GridPane.setVgrow(scenesPane, Priority.ALWAYS);

        //scenesPane.getChildren().add(root);

    }


    public void logOut(ActionEvent actionEvent) {
        App.getClient().closeGUI();

        //log out previous user
        try {
            Employee emp = DatabaseManager.getEmployee(App.getCurrentUsername());
            emp.setLoggedIN(false);
            DatabaseManager.modEmployee(App.getCurrentUsername(), emp);
        }catch (InvalidElementException x){
            x.printStackTrace();
        }

        App.setCurrentUsername(null);
        changeSceneTo("loginPage");
    }

    public void userSettings(ActionEvent e) {
        App.getClient().closeGUI();

        super.changeSceneTo("EmployeeEditor");


        //loadErrorDialog(dialogPane, "Coming in a future iteration!");
    }


}
