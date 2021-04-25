package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import edu.wpi.MochaManticores.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class LoginPage extends SceneController{

    @FXML
    private ImageView backgroundIMG;

    @FXML
    private GridPane contentPane;


    @FXML
    private StackPane dialogPane;

    /**
     * Initializes a page with an image and fits the map to screen
     * then creates starting and ending nodes using the edges
     *
     * @return void
     */

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

    /**
     * Loads the emergency dialogue and if 'yes' is completed,
     * the page is changed to the emergency form
     *
     * @return void
     */

    public void loadDialog(){

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


    public void onMouseClickedContinue(ActionEvent e) {
        App.setClearenceLevel(0);
        changeSceneTo("mainMenu");
    }

    public void emergencyBtnClicked(ActionEvent e) {
        dialogPane.toFront();
        loadDialog();
    }

    public void staffMenu(ActionEvent e) {
        App.setClearenceLevel(1);
        changeSceneTo("employeeLogin");
    }

    public void exitApp(ActionEvent e){
        super.exitApp(e);
    }
}
