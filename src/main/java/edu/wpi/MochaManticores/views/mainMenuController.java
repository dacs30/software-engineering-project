package edu.wpi.MochaManticores.views;

import edu.wpi.MochaManticores.App;
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

        backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
        backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());
    }

    public void gotoService(ActionEvent e){
        super.changeSceneTo("landingPage");
    }

    public void gotoMap(ActionEvent e){
        changeSceneTo("newMapPage");
    }

    public void back(){
        super.back();
    }

}
