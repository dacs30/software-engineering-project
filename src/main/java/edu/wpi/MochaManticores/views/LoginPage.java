package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.Exceptions.InvalidElementException;
import edu.wpi.MochaManticores.Exceptions.InvalidLoginException;
import edu.wpi.MochaManticores.database.DatabaseManager;
import edu.wpi.MochaManticores.database.Employee;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


public class LoginPage extends SceneController{

    @FXML
    private ImageView backgroundIMG;

    @FXML
    private JFXTextField IDField;

    @FXML
    private Label aboutLink;

    @FXML
    private GridPane contentPane;

    @FXML
    private StackPane dialogPane;

    @FXML
    private GridPane patientLoginPage;

    @FXML
    private GridPane employeeLoginPage;

    @FXML
    private JFXPasswordField employeePassword;

    @FXML
    private JFXTextField employeeUsername;

    @FXML
    private JFXButton viewMapButton;

    public void initialize(){
        double height = super.getHeight();
        double width = super.getWidth();
        backgroundIMG.setFitHeight(height);
        backgroundIMG.setFitWidth(width);
        contentPane.setPrefSize(width,height);

        dialogPane.toBack();

        backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
        backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());
        EventHandler<KeyEvent> enter = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                //System.out.println(e.getCharacter());
                if(e.getCharacter().equals("\r")){
                    loginStaff(null);
                }
            }
        };
        employeePassword.setOnKeyTyped(enter);
        aboutLink.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                App.getPrimaryStage().getScene().setCursor(Cursor.HAND);
            }
        });
        aboutLink.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                App.getPrimaryStage().getScene().setCursor(Cursor.DEFAULT);
            }
        });

    }

    public void loadEmergencyDialog(){
        JFXDialogLayout message = new JFXDialogLayout();
        message.setMaxHeight(Region.USE_PREF_SIZE);
        message.setMaxHeight(Region.USE_PREF_SIZE);

        final Text hearder = new Text("Are you sure it is an emergency?");
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
        JFXDialog dialog = new JFXDialog(dialogPane, message,JFXDialog.DialogTransition.CENTER);
        JFXButton yes = new JFXButton("Yes");
        yes.setOnAction(event -> {
            dialog.close();
            dialogPane.toBack();
            changeSceneTo("EmergencyForm");
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
        dialog.show();
    }

    public void loadLoginErrorDialog(){
        JFXDialogLayout message = new JFXDialogLayout();
        message.setMaxHeight(Region.USE_PREF_SIZE);
        message.setMaxHeight(Region.USE_PREF_SIZE);

        final Text hearder = new Text("ERROR");
        hearder.setStyle("-fx-font-weight: bold");
        hearder.setStyle("-fx-font-size: 30");
        hearder.setStyle("-fx-font-family: Roboto");
        hearder.setStyle("-fx-alignment: center");
        hearder.setFill(Color.valueOf("#E74C3C"));
        message.setHeading(hearder);

        final Text body = new Text("Inputed credentials don't match our system");
        body.setStyle("-fx-font-size: 15");
        body.setStyle("-fx-font-family: Roboto");
        body.setStyle("-fx-alignment: center");
        message.setHeading(hearder);

        message.setBody(body);
        JFXDialog dialog = new JFXDialog(dialogPane, message,JFXDialog.DialogTransition.CENTER);
        JFXButton ok = new JFXButton("Ok");
        ok.setOnAction(event -> {
            dialog.close();
            dialogPane.toBack();
        });

        dialog.setOnDialogClosed(event -> {
            dialogPane.toBack();
        });

        message.setActions(ok);
        dialog.show();
    }

    public void onMouseClickedContinue(ActionEvent e) throws InvalidElementException {
        //ensure patient id is entered
        //save the patient id as App.setCurrentUsername
        //if there is no employee with that username then create it

        String name;
        if (IDField.getText().equals("")){
            name = "Guest";
        } else {
            name = IDField.getText();
        }

        try {
            Employee temp = DatabaseManager.getEmpManager().getElement(name.toLowerCase());
            //if emp exists and is logged in display an error

            if(temp.isLoggedIN()){
                IDField.clear();
                RequiredFieldValidator wrongCreditals = new RequiredFieldValidator();
                IDField.getValidators().add(wrongCreditals);
                wrongCreditals.setMessage(name + " is already logged in");
                IDField.validate();
                return;
            }

            if(!temp.getPassword().equals("")){
                IDField.clear();
                RequiredFieldValidator wrongCreditals = new RequiredFieldValidator();
                IDField.getValidators().add(wrongCreditals);
                wrongCreditals.setMessage(name + " is an employee");
                IDField.validate();
                return;
            }

        } catch (Exception exception) {

            Employee employee = new Employee(name, "", IDField.getText(), IDField.getText(), Employee.employeeType.PATIENT,
                    0, false, false, "Parking",false);
            DatabaseManager.getEmpManager().addElement(employee);
        }

        //set name and clearance
        App.setClearenceLevel(0);
        App.setCurrentUsername(name);

        // start new message client
        App.getClient().startClient();

        //Next page
        changeSceneTo("landingPage");
    }

    public void emergencyBtnClicked(ActionEvent e) {
        dialogPane.toFront();
        loadEmergencyDialog();
    }

    public void exitApp(ActionEvent e){
        super.exitApp(e);
    }

    public void changeToStaffLogin(ActionEvent actionEvent) {
        employeeLoginPage.toFront();
    }

    public void changeToPatientLogin(ActionEvent actionEvent) {
        patientLoginPage.toFront();
    }

    public void loginStaff(ActionEvent actionEvent) {
        // try the login with the inputed credentialssetCurrentUsername
        // error if fail
        //System.out.println(employeeUsername.getText());

        try {
            if(employeePassword.getText() == null || employeePassword.getText().equals("")){
                employeePassword.setText(null);
                RequiredFieldValidator wrongCreditals = new RequiredFieldValidator();
                employeePassword.getValidators().add(wrongCreditals);
                wrongCreditals.setMessage("Please input a password");
                employeePassword.validate();
                return;
            }


            Employee temp = DatabaseManager.checkEmployeeLogin(employeeUsername.getText().toLowerCase(), employeePassword.getText());


            if(temp.isLoggedIN()){
                employeeUsername.setText(null);
                employeePassword.setText(null);
                RequiredFieldValidator wrongCreditals = new RequiredFieldValidator();
                employeeUsername.getValidators().add(wrongCreditals);
                wrongCreditals.setMessage("User is already logged in");
                employeeUsername.validate();
                return;
            }

            // sets to employee level
            App.setClearenceLevel(1);
            App.setCurrentUsername(employeeUsername.getText());
            // start new message client
            App.getClient().startClient();
            changeSceneTo("landingPage");
        } catch (InvalidLoginException | InvalidElementException e) {
            // Validators
            employeeUsername.setText(null);
            employeePassword.setText(null);
            RequiredFieldValidator wrongCreditals = new RequiredFieldValidator();
            employeeUsername.getValidators().add(wrongCreditals);
            employeePassword.getValidators().add(wrongCreditals);
            wrongCreditals.setMessage("Incorrect username or password");
            employeeUsername.validate();
            employeePassword.validate();
        }
    }

    public void openMap(ActionEvent actionEvent) {
        changeSceneTo("mapPage");
    }

    public void about(MouseEvent e){
        changeSceneTo("aboutPage");
    }
}
