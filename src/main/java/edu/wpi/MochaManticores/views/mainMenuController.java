package edu.wpi.MochaManticores.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class mainMenuController extends SceneController{


    @FXML
    private ImageView backgroundIMG;

    @FXML
    private GridPane contentPane;

    public void initialize(){
        double height = super.getHeight();
        double width = super.getWidth();
        backgroundIMG.setFitHeight(height);
        backgroundIMG.setFitWidth(width);
        contentPane.setPrefSize(width,height);
    }

    public void gotoService(ActionEvent e){
        super.changeSceneTo(e, "landingPage");
    }

    public void gotoMap(ActionEvent e){
        super.changeSceneTo(e, "mapPageTEMP");
    }

    public void back(ActionEvent e){
        super.back(e);
    }

}
