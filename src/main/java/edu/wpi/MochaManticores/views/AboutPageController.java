package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.JFXButton;
import edu.wpi.MochaManticores.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AboutPageController extends SceneController{

    @FXML
    public Label aboutText;

    @FXML
    private Label creditsText;

    @FXML
    public JFXButton creditsButton;

    @FXML
    private GridPane aboutGrid;

    @FXML
    private GridPane creditsGrid;

    @FXML
    public ImageView backgroundIMG;

    public void initialize() {
        double height = App.getPrimaryStage().getScene().getHeight();
        double width = App.getPrimaryStage().getScene().getWidth();
        backgroundIMG.setFitWidth(width);
        backgroundIMG.setFitHeight(height);

        backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
        backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());

    }

    public void back(ActionEvent e){
        super.back();
    }

}
