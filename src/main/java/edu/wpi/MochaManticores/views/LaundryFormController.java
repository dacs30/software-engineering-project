package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.MochaManticores.App;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

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
}
