package edu.wpi.MochaManticores.views;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class mapPage extends SceneController{



    @FXML
    private ImageView backgroundIMG;

    @FXML
    private ImageView mapWindow;

    @FXML
    private GridPane contentPane;

    @FXML
    private Label locationTitle;

    @FXML
    private StackPane mapStack;

    @FXML
    private GridPane mapGrid;

    private String location = "edu/wpi/MochaManticores/images/";

    public void initialize(){
        double height = super.getHeight();
        double width = super.getWidth();
        backgroundIMG.setFitHeight(height);
        backgroundIMG.setFitWidth(width);
        contentPane.setPrefSize(width,height);
        loadLOne();
        setDeminsions();
    }

    public void back(){
        super.back();
    }

    public void loadLOne(){
        locationTitle.setText("Lower Level 1");
        mapWindow.setImage(new Image(location + "00_thelowerlevel1.png"));
    }

    public void loadLTwo(){
        locationTitle.setText("Lower Level 2");
        mapWindow.setImage(new Image(location + "00_thelowerlevel2.png"));
    }


    public void loadGround(){
        locationTitle.setText("Ground Floor");
        mapWindow.setImage(new Image(location + "00_thegroundfloor.png"));
    }

    public void loadFOne(){
        locationTitle.setText("Floor 1");
        mapWindow.setImage(new Image(location + "01_thefirstfloor.png"));
    }

    public void loadFTwo(){
        locationTitle.setText("Floor 2");
        mapWindow.setImage(new Image(location + "02_thesecondfloor.png"));
    }

    public void loadFThree(){
        locationTitle.setText("Floor 3");
        mapWindow.setImage(new Image(location + "03_thethirdfloor.png"));
    }

    private void setDeminsions(){
//
//        mapWindow.setFitHeight();
//        mapWindow.setFitWidth();
    }

    //public void load
}
