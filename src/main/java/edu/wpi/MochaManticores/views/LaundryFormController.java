package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import edu.wpi.MochaManticores.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class LaundryFormController {

    @FXML
    private JFXComboBox<String> soil;

    @FXML
    private JFXComboBox<String> wTemp;

    @FXML
    private JFXComboBox<String> dTemp;

    @FXML
    private GridPane contentGrid;

    @FXML
    private ImageView backgroundIMG;

    @FXML
    private JFXButton submitButton;
    @FXML
    private ImageView helpButton;
    @FXML
    private StackPane dialogPane;


    public void initialize(){
        double height = App.getPrimaryStage().getScene().getHeight();
        double width = App.getPrimaryStage().getScene().getWidth();
        backgroundIMG.setFitHeight(height);
        backgroundIMG.setFitWidth(width);
        contentGrid.setPrefSize(width, height);

        backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
        backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());


        soil.getItems().clear();
        soil.getItems().addAll("Light", "Medium", "Heavy");

        wTemp.getItems().clear();
        wTemp.getItems().addAll("Hot", "Warm", "Cold");

        dTemp.getItems().clear();
        dTemp.getItems().addAll("High", "Medium", "Low", "Delicate", "No Heat");
    }

    public void submitEvent(ActionEvent actionEvent) {
        loadSubmitDialog();
    }

    private void loadDialog(){
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
                "Soil Level: Please select the level of soil for the wash cycle.\n" +
                "Delicates: Please select whether the laundry is consider delicate or not.\n" +
                "Temperature: Please select the temperature that you will wash and dry at.\n" +
                "Extra Cycles: Please indicate the number of extra cycles you need.");
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


    public void loadSubmitDialog(){
        dialogPane.toFront();
        dialogPane.setDisable(false);
        JFXDialogLayout message = new JFXDialogLayout();
        message.setMaxHeight(Region.USE_PREF_SIZE);
        message.setMaxHeight(Region.USE_PREF_SIZE);

        final Text hearder = new Text("Submitted request.");
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
        JFXDialog dialog = new JFXDialog(dialogPane, message,JFXDialog.DialogTransition.CENTER);
        JFXButton ok = new JFXButton("Done");
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

    public void openHelp(MouseEvent mouseEvent) {
        dialogPane.toFront();
        loadDialog();
    }
}
