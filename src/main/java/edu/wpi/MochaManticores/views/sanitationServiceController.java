package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.*;
import edu.wpi.MochaManticores.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class sanitationServiceController extends SceneController {

    @FXML
    private JFXTextField loc;

    @FXML
    private JFXTextField safetyHaz;

    @FXML
    private JFXTextField type;

    @FXML
    private JFXTextField equipment;

    @FXML
    private JFXTextArea description;

    @FXML
    public JFXButton submitButton;

    @FXML
    public JFXButton cancelButton;

    @FXML
    public JFXButton helpBtn;

    @FXML
    public JFXTextField empBox;

    @FXML
    public ImageView backgroundIMG;

    @FXML
    public ImageView badgeIMG;

    @FXML
    public GridPane contentGrid;

    @FXML
    private StackPane dialogPane;

    @FXML
    private Label empTitle;

    @FXML
    private JFXButton backBtn;


    /**
     * Initializes a page with an image and fits the map to screen
     * then creates starting and ending nodes using the edges
     *
     * @return void
     */
    @FXML
    private void initialize(){
        double height = App.getPrimaryStage().getScene().getHeight();
        double width = App.getPrimaryStage().getScene().getWidth();
        backgroundIMG.setFitHeight(height);
        backgroundIMG.setFitWidth(width);
        contentGrid.setPrefSize(width,height);

        dialogPane.toBack();
        backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
        backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());

        if(App.getClearenceLevel()<=0){
            empBox.setVisible(false);
            empTitle.setVisible(false);
        }

    }

    public void helpButton(ActionEvent actionEvent){loadHelpDialogue();}

    public void goBack(ActionEvent e) {
        back();
    }

    /**
     * Loads the submitted dialog and confirms the request was submitted
     *
     * @return void
     */

    public void loadSubmitDialog(){
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
            goBack(null);
        });

        dialog.setOnDialogClosed(event -> {
            dialogPane.toBack();
            dialog.close();
        });

        message.setActions(ok);
        dialog.show();
    }
    /**
     * Loads the help dialog for the user to
     * complete the service request
     *
     * @return void
     */

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

        final Text body = new Text("Location: The room number, hallway, waiting area, etc. of the sanitation request.\n" +
                "Safety Hazards: This is where you indicate any safety hazards such as biohazards or densely populated area near the location. \n" +
                "Sanitation Type: This is where you indicate what the request is for such as room cleaning or spill.\n" +
                "Equipment Needed: This is where you indicate what equipment may be needed to complete the request.\n " +
                "Description: This is where you indicate the rest of the necesary information to complete the request.");
        body.setStyle("-fx-font-size: 40");
        body.setStyle("-fx-font-family: Roboto");
        body.setStyle("-fx-alignment: center");

        message.setBody(body);


        JFXDialog dialog = new JFXDialog(dialogPane, message,JFXDialog.DialogTransition.CENTER);

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

    @FXML
    private void submit(ActionEvent e) {
        try {
            System.out.println(loc.getText());
            System.out.println(safetyHaz.getText());
            System.out.println(type.getText());
            System.out.println(equipment.getText());
            System.out.println(description.getText());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        loadSubmitDialog();
    }

    public void back() {
        super.back();
    }
}

