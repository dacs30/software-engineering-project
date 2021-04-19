package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.MochaManticores.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

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
    public ImageView backgroundIMG;

    @FXML
    public ImageView badgeIMG;

    @FXML
    public GridPane contentPane;

    @FXML
    private void initialize(){
      double height = super.getHeight();
      double width = super.getWidth();
      backgroundIMG.setFitHeight(height);
      backgroundIMG.setFitWidth(width);

        backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
        backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());
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
        returnToMain();
    }


    public void back() {
        super.back();
    }
}

