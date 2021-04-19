package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.sun.prism.image.ViewPort;
import edu.wpi.MochaManticores.Algorithms.AStar;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.Nodes.MapSuper;
import edu.wpi.MochaManticores.Nodes.NodeSuper;
import javafx.event.ActionEvent;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
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
import javafx.scene.text.Text;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class mapPage extends SceneController {

    public class node {
        Circle c;
        String nodeID;
        double xCoord;
        double yCoord;

        public node(Circle c, String nodeID) {
            this.c = c;
            this.nodeID = nodeID;
            xCoord = c.getCenterX();
            yCoord = c.getCenterY();
        }

        public void resetFill(){
            c.setFill(Color.WHITE);
            c.setStrokeWidth(1);
            c.setStroke(Color.DARKGRAY);
        }
    }


    private HashMap<String, node> nodes = new HashMap();
    
    private LinkedList<node> pitStops = new LinkedList<>();
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

    @FXML
    private Pane nodePane;

    @FXML
    private GridPane innerMapGrid;

    @FXML
    private StackPane dialogPane;

    private String location = "edu/wpi/MochaManticores/images/";


    Rectangle2D noZoom;
    Rectangle2D zoomPort;

    public void initialize() {
        double height = super.getHeight();
        double width = super.getWidth();
        backgroundIMG.setFitHeight(height);
        backgroundIMG.setFitWidth(width);
        contentPane.setPrefSize(width, height);

        backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
        backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());

//        contentPane.minHeightProperty().bind(App.getPrimaryStage().heightProperty());
//        contentPane.maxHeightProperty().bind(App.getPrimaryStage().heightProperty());
//
//        contentPane.minWidthProperty().bind(App.getPrimaryStage().widthProperty());
//        contentPane.maxWidthProperty().bind(App.getPrimaryStage().widthProperty());

        mapWindow.fitWidthProperty().bind(App.getPrimaryStage().widthProperty().subtract(150 + mapWindow.localToScene(mapWindow.getBoundsInLocal()).getMinX()));
        mapWindow.fitHeightProperty().bind(App.getPrimaryStage().heightProperty().subtract(150 + mapWindow.localToScene(mapWindow.getBoundsInLocal()).getMinY()));

//        nodePane.widthProperty().bind(App.getPrimaryStage().widthProperty().subtract(150 + nodePane.localToScene(nodePane.getBoundsInLocal()).getMinX()));
//        nodePane.heightProperty().bind(App.getPrimaryStage().heightProperty().subtract(150 + nodePane.localToScene(nodePane.getBoundsInLocal()).getMinY()));

        //System.out.println("~~~" + mapWindow.localToScene(mapWindow.getBoundsInLocal()).getMinX());

        //mapStack.setMaxHeight(innerMapGrid.heightProperty().doubleValue());

//        mapStack.maxHeightProperty().bind(innerMapGrid.getRowConstraints().get(0).maxHeightProperty());
//        mapStack.maxWidthProperty().bind(innerMapGrid.getColumnConstraints().get(0).maxWidthProperty());
//
//
//
//        System.out.printf("innerMapGrid (%f,%f)\n",innerMapGrid.widthProperty().get(),innerMapGrid.heightProperty().get());
        mapWindow.setPreserveRatio(false);
        //mapWindow.fitHeightProperty().bind(mapStack.widthProperty());
        //mapWindow.fitHeightProperty().bind(mapStack.heightProperty());

        loadFOne();
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                //zoomImg(e);
            }
        };
        mapWindow.setOnMouseMoved(eventHandler);

        App.getPrimaryStage().widthProperty().addListener((obs, oldVal, newVal) -> {
            drawNodes();
        });

        App.getPrimaryStage().heightProperty().addListener((obs, oldVal, newVal) -> {
            drawNodes();
        });


//        mapWindow.setFitHeight(super.getHeight() / 1.5);
//        mapWindow.setFitWidth(super.getWidth() / 1.5);
//        mapWindow.setFitHeight(mapStack.getHeight()/2);
//        mapWindow.setFitWidth(mapStack.getWidth()/2);
        drawNodes();


        //Initializing the dialog pane
        dialogPane.toBack();


    }

    /**
     * Loads the dialog box with the path generated by the A* algorithm
     * @param path
     */
    public void loadDialog(StringBuilder path){
        //TODO Center the text of it.

        JFXDialogLayout message = new JFXDialogLayout();
        message.setMaxHeight(Region.USE_PREF_SIZE);
        message.setMaxHeight(Region.USE_PREF_SIZE);

        final Text hearder = new Text("Path created");
        hearder.setStyle("-fx-font-weight: bold");
        hearder.setStyle("-fx-font-size: 30");
        hearder.setStyle("-fx-font-family: Roboto");
        hearder.setStyle("-fx-alignment: center");
        message.setHeading(hearder);

        final Text body = new Text(path.toString());
        body.setStyle("-fx-font-size: 15");
        body.setStyle("-fx-font-family: Roboto");
        body.setStyle("-fx-alignment: center");
        message.setHeading(hearder);

        message.setBody(body);
        JFXDialog dialog = new JFXDialog(dialogPane, message,JFXDialog.DialogTransition.CENTER);
        JFXButton ok = new JFXButton("OK");
        ok.setOnAction(event -> {
            dialog.close();
            dialogPane.toBack();
            changeSceneTo("EmergencyForm");
        });

        dialog.setOnDialogClosed(event -> {
            dialogPane.toBack();
        });

        message.setActions(ok);
        dialog.show();

    }

    public void back() {
        super.back();
    }

    private void setZoom(Image img, double x, double y, Rectangle2D z) {
        noZoom = new Rectangle2D(0, 0, img.getWidth(), img.getHeight());
        zoomPort = new Rectangle2D(x, y, (double) .25 * img.getWidth(), (double) .25 * img.getHeight());

        mapWindow.setImage(img);
        mapWindow.setViewport(z);
    }

    public void loadLOne() {
        locationTitle.setText("Lower Level 1");
        Image img = new Image(location + "00_thelowerlevel1.png");
        setZoom(img, 0, 0, noZoom);
    }

    public void loadLTwo() {
        locationTitle.setText("Lower Level 2");
        Image img = new Image(location + "00_thelowerlevel2.png");
        setZoom(img, 0, 0, noZoom);
    }

    public void loadGround() {
        locationTitle.setText("Ground Floor");
        Image img = new Image(location + "00_thegroundfloor.png");
        setZoom(img, 0, 0, noZoom);
    }

    public void loadFOne() {
        locationTitle.setText("Floor 1");
        Image img = new Image(location + "01_thefirstfloor.png");
        setZoom(img, 0, 0, noZoom);
    }

    public void loadFTwo() {
        locationTitle.setText("Floor 2");
        Image img = new Image(location + "02_thesecondfloor.png");
        setZoom(img, 0, 0, noZoom);
    }

    public void loadFThree() {
        locationTitle.setText("Floor 3");
        Image img = new Image(location + "00_thegroundfloor.png");
        setZoom(img, 0, 0, noZoom);
    }

    public void drawCoord(MouseEvent e) {
        double xRatio = 5000 / mapWindow.getFitWidth();
        double yRatio = 3400 / mapWindow.getFitHeight();

        System.out.printf("(%f,%f)\n", e.getX() * xRatio, e.getY() * yRatio);
    }
    
    public void toAStar(){
        AStar star = new AStar();
        //pathToTake is used in the dialog box that keeps all the nodes that the user has to pass through
        StringBuilder pathToTake = new StringBuilder(new String());
        LinkedList<NodeSuper> stops = new LinkedList<>();
        for (node n :
                pitStops) {
            stops.add(MapSuper.getMap().get(n.nodeID));
        }
        LinkedList<String> path = star.path(stops);
        for (String str :
                path) {
            System.out.printf("\n%s\n|\n",MapSuper.getMap().get(str).getLongName());
            pathToTake.append(MapSuper.getMap().get(str).getLongName()); //appending the paths
        }
        for (node n:
             pitStops) {
            n.resetFill();
        }
        loadDialog(pathToTake); // calling the dialog pane with the path

    }


    public void drawNodes() {
        nodePane.getChildren().clear();
        double xRatio = 5000 / mapWindow.getFitWidth();
        double yRatio = 3400 / mapWindow.getFitHeight();
        Iterator<NodeSuper> mapIter = MapSuper.getMap().values().iterator();
        for (int i = 0; i < MapSuper.getMap().size(); i++) {
            NodeSuper n = mapIter.next();
            Circle spot = new Circle(n.getXcoord() / xRatio, n.getYcoord() / yRatio, 4, Color.WHITE);
            spot.setStrokeWidth(1);
            spot.setStroke(Color.DARKGRAY);
            EventHandler<MouseEvent> highlight = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    highlightNode(e);
                }
            };
            spot.setOnMouseClicked(highlight);
            nodes.put(n.getID(), new node(spot, n.getID()));
            nodePane.getChildren().addAll(nodes.get(n.getID()).c);

        }
    }


    public void highlightNode(MouseEvent e) {
        Circle src = (Circle) e.getSource();
        Iterator<node> iter = nodes.values().iterator();

        for (int i = 0; i < nodes.size(); i++) {
            node n = iter.next();
            if(n.c.equals(src)){
                n.c.setFill(Color.RED);
                pitStops.add(n);
            }
        }
    }

//    @FXML
//    public void goToRouteExample(ActionEvent e) {
//        super.changeSceneTo("routeExample");
//    }

    @FXML
    public void goToRouteExample(ActionEvent e) {
        toAStar();
    }
    
    public void zoomImg(MouseEvent e) {
        ImageView source = (ImageView) e.getSource();
        Image src = source.getImage();
        double curX = e.getX();
        double curY = e.getY();

        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                setZoom(src, 0, 0, noZoom);
            }
        };

        mapWindow.setOnMouseExited(eventHandler);

        double multi = mapWindow.getFitWidth() / mapWindow.getFitHeight();

        setZoom(src, curX * multi, curY * multi, zoomPort);
        //System.out.printf("X: %f\nY: %f\n\n",curX,curY);

    }
}
