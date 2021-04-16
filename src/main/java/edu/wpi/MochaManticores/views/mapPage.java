package edu.wpi.MochaManticores.views;

import com.sun.prism.image.ViewPort;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
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

    Rectangle2D noZoom;
    Rectangle2D zoomPort;
    public void initialize(){
        double height = super.getHeight();
        double width = super.getWidth();
        backgroundIMG.setFitHeight(height);
        backgroundIMG.setFitWidth(width);
        contentPane.setPrefSize(width,height);
        loadLOne();
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                zoomImg(e);
            }
        };
        mapWindow.setOnMouseMoved(eventHandler);
        mapWindow.setFitHeight(850);
        mapWindow.setFitWidth(1250);
//        mapWindow.setFitHeight(mapStack.getHeight()/2);
//        mapWindow.setFitWidth(mapStack.getWidth()/2);
    }

    public void back(){
        super.back();
    }

    private void setZoom(Image img, double x, double y, Rectangle2D z){
        noZoom = new Rectangle2D(0, 0, img.getWidth() , img.getHeight());
        zoomPort = new Rectangle2D(x, y, (double).25*img.getWidth() , (double).25*img.getHeight());

        if(mapWindow.getFitHeight()> 850){
            mapWindow.setFitHeight(850);
        }
        if(mapWindow.getFitWidth()>1250){
            mapWindow.setFitWidth(1250);
        }
        mapWindow.setImage(img);
        mapWindow.setViewport(z);
    }

    public void loadLOne(){
        locationTitle.setText("Lower Level 1");
        Image img = new Image(location + "00_thelowerlevel1.png");
        setZoom(img,0,0, noZoom);
    }

    public void loadLTwo(){
        locationTitle.setText("Lower Level 2");
        Image img = new Image(location + "00_thelowerlevel2.png");
        setZoom(img,0,0, noZoom);
    }

    public void loadGround(){
        locationTitle.setText("Ground Floor");
        Image img = new Image(location + "00_thegroundfloor.png");
        setZoom(img,0,0, noZoom);
    }

    public void loadFOne(){
        locationTitle.setText("Floor 1");
        Image img = new Image(location + "01_thefirstfloor.png");
        setZoom(img, 0,0, noZoom);
    }

    public void loadFTwo(){
        locationTitle.setText("Floor 2");
        Image img = new Image(location + "02_thesecondfloor.png");
        setZoom(img, 0,0, noZoom);
    }

    public void loadFThree(){
        locationTitle.setText("Floor 3");
        Image img = new Image(location + "00_thegroundfloor.png");
        setZoom(img, 0,0,noZoom);
    }

    @FXML
    public void goToRouteExample(ActionEvent e){
        super.changeSceneTo("routeExample");
    }

    public void zoomImg(MouseEvent e){
        ImageView source = (ImageView) e.getSource();
        Image src = source.getImage();
        double curX = e.getX();
        double curY = e.getY();

        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                setZoom(src,0,0,noZoom);            }
        };

        mapWindow.setOnMouseExited(eventHandler);


        setZoom(src,curX*4,curY*4,zoomPort);


        //System.out.printf("X: %f\nY: %f\n\n",curX,curY);

    }
}
