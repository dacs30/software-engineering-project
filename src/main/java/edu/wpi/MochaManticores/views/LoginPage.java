package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.database.Mdb;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import edu.wpi.MochaManticores.database.EmployeeManager;

import java.sql.Connection;


public class LoginPage extends SceneController{

    @FXML
    private ImageView backgroundIMG;

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

    public void initialize(){
        double height = super.getHeight();
        double width = super.getWidth();
        backgroundIMG.setFitHeight(height);
        backgroundIMG.setFitWidth(width);
        contentPane.setPrefSize(width,height);

        dialogPane.toBack();

        backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
        backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());

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
        JFXButton yes = new JFXButton("YES");
        yes.setOnAction(event -> {
            dialog.close();
            dialogPane.toBack();
            changeSceneTo("EmergencyForm");
        });

        JFXButton no = new JFXButton("NO");
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
        JFXButton ok = new JFXButton("OK");
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

    public void onMouseClickedContinue(ActionEvent e) {
        App.setClearenceLevel(0);
        changeSceneTo("mainMenu");
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
        Connection connection = Mdb.getConnection();
        // try the login with the inputed credentials
        // error if fail
        System.out.println(employeeUsername.getText());
        try {
            EmployeeManager.checkEmployeeLogin(connection, employeeUsername.getText(), employeePassword.getText());
            changeSceneTo("staffMainMenu");
        } catch (Exception e) {
            // Validators
            employeeUsername.setText(null);
            employeePassword.setText(null);
            RequiredFieldValidator wrongCreditals = new RequiredFieldValidator();
            employeeUsername.getValidators().add(wrongCreditals);
            employeePassword.getValidators().add(wrongCreditals);
            wrongCreditals.setMessage("Wrong credentials");
            employeeUsername.validate();
            employeePassword.validate();
        }
    }
}
