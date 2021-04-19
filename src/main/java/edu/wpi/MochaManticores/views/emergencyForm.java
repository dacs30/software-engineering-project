package edu.wpi.MochaManticores.views;

import edu.wpi.MochaManticores.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

public class emergencyForm extends SceneController {

    @FXML
    private ComboBox numPeople;

    @FXML
    private ImageView backgroundIMG;

    @FXML
    private TextField roomNumber;

    public void initialize() {
        double height = super.getHeight();
        double width = super.getWidth();
        backgroundIMG.setFitHeight(height);
        backgroundIMG.setFitWidth(width);

        backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
        backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());

        numPeople.getItems().clear();
        numPeople.getItems().addAll("1", "2", "3");
        numPeople.getSelectionModel().select("1");
    }

    public void backBtn(ActionEvent actionEvent) {
        back();
    }

    public void submitEmergency(ActionEvent actionEvent) {
        System.out.println("Some logic");
    }
}
