package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.database.DatabaseManager;
import edu.wpi.MochaManticores.database.Mdb;
import edu.wpi.MochaManticores.messaging.messageClient;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import edu.wpi.MochaManticores.database.EmployeeManager;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


public class EmployeeLogin extends SceneController{

    @FXML
    private ImageView backgroundIMG;

    @FXML
    private GridPane contentPane;

    @FXML
    private StackPane dialogPane;

    @FXML
    private TextField empUserName;

    @FXML
    private PasswordField empPassword;

    public void initialize(){
        double height = getHeight();
        double width = getWidth();
        backgroundIMG.setFitHeight(height);
        backgroundIMG.setFitWidth(width);
        contentPane.setPrefSize(width,height);

        dialogPane.toBack();

        backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
        backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());
        EventHandler<KeyEvent> enter = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                System.out.println(e.getCharacter());
                if(e.getCharacter().equals("\r")){
                    onMouseClickedContinue(null);
                }
            }
        };
        empPassword.setOnKeyTyped(enter);

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

        EventHandler<KeyEvent> enter = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if(e.getCharacter().equals("\r")){
                    dialog.close();
                    dialogPane.setDisable(true);
                    dialogPane.toBack();
                }
            }
        };
        dialog.setOnKeyTyped(enter);

        message.setActions(ok);
        dialog.show();
    }

    // checks the login
    public void onMouseClickedContinue(ActionEvent actionEvent) {
        // try the login with the inputed credentials
        // error if fail
        try {
            DatabaseManager.checkEmployeeLogin(empUserName.getText(), empPassword.getText());

            App.getClient().startClient();

            changeSceneTo("landingPage");

        } catch (Exception e) {
            // popup the error dialog
            dialogPane.toFront();
            loadLoginErrorDialog();
            empUserName.setText(null);
            empPassword.setText(null);
        }
    }

    public void emergencyBtnClicked(ActionEvent actionEvent) {
        dialogPane.toFront();
        loadEmergencyDialog();
    }

    public void patientMenu(ActionEvent actionEvent) {
        changeSceneTo("loginPage");
    }
}
