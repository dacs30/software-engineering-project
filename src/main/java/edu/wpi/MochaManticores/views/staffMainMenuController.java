package edu.wpi.MochaManticores.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class staffMainMenuController extends SceneController{


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

    public void gotoEdge(ActionEvent e){
        super.changeSceneTo("edgesPage");
    }

    public void gotoNode(ActionEvent e){
        super.changeSceneTo("mapPage");
    }

    public void back(){
        super.back();
    }

}
