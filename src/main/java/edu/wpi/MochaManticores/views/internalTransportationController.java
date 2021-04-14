package edu.wpi.MochaManticores.views;
import com.jfoenix.controls.*;
import edu.wpi.MochaManticores.App;
import java.awt.*;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class internalTransportationController extends SceneController{


    ObservableList<String> typeOfTransportList = FXCollections
            .observableArrayList("Wheelchair","Walker","Medical Bed");

    // Information of Patient/One Being Transported
    @FXML
    private JFXTextField patientID;
    @FXML
    private JFXTextField numberOfStaff;
    @FXML
    private JFXTextField destination;
    @FXML
    private JFXComboBox transportComboBox;
    @FXML
    private GridPane contentGrid;
    @FXML
    private ImageView backgroundIMG;
    @FXML
    private StackPane dialogPane;

    @FXML
    private void initialize() {
        double height = App.getPrimaryStage().getScene().getHeight();
        double width = App.getPrimaryStage().getScene().getWidth();
        backgroundIMG.setFitHeight(height);
        backgroundIMG.setFitWidth(width);
        contentGrid.setPrefSize(width,height);

        transportComboBox.setItems(typeOfTransportList);

        dialogPane.setDisable(false);
    }

    public void goBack(ActionEvent actionEvent) {
        back();
    }

    public void submitEvent(ActionEvent actionEvent) {
        loadSubmitDialog();
    }

    public void loadSubmitDialog(){
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
        JFXDialog dialog = new JFXDialog(dialogPane, message,JFXDialog.DialogTransition.CENTER);
        JFXButton ok = new JFXButton("OK");
        ok.setOnAction(event -> {
            changeSceneTo("landingPage");
        });

        dialog.setOnDialogClosed(event -> {
            dialogPane.toBack();
            dialog.close();
        });

        message.setActions(ok);
        dialog.show();
    }
}
