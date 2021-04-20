package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import edu.wpi.MochaManticores.Algorithms.AStar;
import edu.wpi.MochaManticores.Algorithms.AStar2;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.Nodes.MapSuper;
import edu.wpi.MochaManticores.Nodes.NodeSuper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

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

        public void resetFill() {
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

    @FXML
    private Label placeClicked;

    @FXML
    private Label finalDestinationLabel;

    @FXML
    private VBox pitstopsLabel;

    private String location = "edu/wpi/MochaManticores/images/";

    private String selectedFloor = "";

    public void setSelectedFloor(String selectedFloor) {
        this.selectedFloor = selectedFloor;
    }

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

//        mapWindow.fitWidthProperty().bind(App.getPrimaryStage().widthProperty().subtract(150 + mapWindow.localToScene(mapWindow.getBoundsInLocal()).getMinX()));
//        mapWindow.fitHeightProperty().bind(App.getPrimaryStage().heightProperty().subtract(150 + mapWindow.localToScene(mapWindow.getBoundsInLocal()).getMinY()));

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
        //loadF1();

        System.out.println("1");

        //mapWindow.setFitWidth(App.getPrimaryStage().getWidth() - mapStack.localToScene(mapStack.getBoundsInLocal()).getMinX());
        //mapWindow.setFitHeight(App.getPrimaryStage().getHeight() - mapStack.localToScene(mapStack.getBoundsInLocal()).getMinX());

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mapWindow.setFitWidth(App.getPrimaryStage().getWidth() - mapStack.localToScene(mapStack.getBoundsInLocal()).getMinX());
                mapWindow.setFitHeight(App.getPrimaryStage().getHeight()*.70);

                System.out.println(App.getPrimaryStage().getWidth() + " - " + mapStack.localToScene(mapStack.getBoundsInLocal()).getMinX() + " = " + mapWindow.getFitWidth());
            }
        });
//        mapWindow.setFitWidth(App.getPrimaryStage().getWidth()*.75);
//        mapWindow.setFitHeight(App.getPrimaryStage().getHeight()*.70);
//
//        System.out.println(App.getPrimaryStage().getWidth() + " - " + mapGrid.localToScene(mapGrid.getBoundsInLocal()).getMinX() + " = " + mapWindow.getFitWidth());

        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                System.out.println("MinWidth: " + mapWindow.localToScene(mapWindow.getBoundsInLocal()).getMinX());
                //zoomImg(e);
            }
        };

        mapStack.setOnMouseClicked(eventHandler);
        mapWindow.setOnMouseMoved(eventHandler);

        App.getPrimaryStage().widthProperty().addListener((obs, oldVal, newVal) -> {
            mapWindow.setFitWidth(mapWindow.getFitWidth() + (newVal.doubleValue() - oldVal.doubleValue()));
            drawNodes();
        });

        App.getPrimaryStage().heightProperty().addListener((obs, oldVal, newVal) -> {
            mapWindow.setFitHeight(mapWindow.getFitHeight() + (newVal.doubleValue()- oldVal.doubleValue()));
            drawNodes();
        });


//        mapWindow.setFitHeight(super.getHeight() / 1.5);
//        mapWindow.setFitWidth(super.getWidth() / 1.5);
//        mapWindow.setFitHeight(mapStack.getHeight()/2);
//        mapWindow.setFitWidth(mapStack.getWidth()/2);
        drawNodes();


        //Initializing the dialog pane
        dialogPane.toBack();

        //center Vbox
        pitstopsLabel.setAlignment(Pos.CENTER);


    }

    /**
     * Loads the dialog box with the path generated by the A* algorithm
     * @param path
     */
    public void loadDialog(StringBuilder path){
        dialogPane.toFront();
        dialogPane.setDisable(false);

        JFXDialogLayout message = new JFXDialogLayout();
        message.setMaxHeight(Region.USE_PREF_SIZE);
        message.setMaxHeight(Region.USE_PREF_SIZE);


        final VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setMaxHeight(Region.USE_COMPUTED_SIZE);
        vbox.setMaxWidth(Region.USE_COMPUTED_SIZE);



        Text header = new Text("Path created");
        header.setStyle("-fx-font-weight: bold");
        header.setStyle("-fx-font-size: 30");
        header.setStyle("-fx-font-family: Roboto");
        header.setStyle("-fx-alignment: center");
        Text start = new Text("Your location");
        Text ending = new Text("Ending location");
        ending.setFill(Color.RED);
        start.setFill(Color.GREEN);
        final Text body;
        if(path.toString().equals("Please select at least one node")){
           body = new Text(path.toString());
           header = new Text("Error");
           header.setFill(Color.RED);
            vbox.setAlignment(Pos.CENTER_LEFT);
        }else {

            body = new Text(path.substring(0, path.toString().length() - 3));

        }


        body.setStyle("-fx-font-size: 15");
        body.setStyle("-fx-font-family: Roboto");
        body.setStyle("-fx-alignment: center");
        body.setTextAlignment(TextAlignment.CENTER);

        if(path.toString().equals("Please select at least one node")) {
            vbox.getChildren().addAll(body);
        }else{
            vbox.getChildren().addAll(start, body, ending);
        }
        message.setHeading(header);
        message.setBody(vbox);
        JFXDialog dialog = new JFXDialog(dialogPane, message,JFXDialog.DialogTransition.CENTER);
        JFXButton ok = new JFXButton("OK");
        ok.setOnAction(event -> {
            dialog.close();
            dialogPane.toBack();
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

    public void loadL1() {
        locationTitle.setText("Lower Level 1");
        Image img = new Image(location + "00_thelowerlevel1.png");
        setSelectedFloor("L1");
        setZoom(img, 0, 0, noZoom);
        drawNodes();

    }

    public void loadL2() {
        locationTitle.setText("Lower Level 2");
        setSelectedFloor("L2");

        Image img = new Image(location + "00_thelowerlevel2.png");
        setZoom(img, 0, 0, noZoom);
        drawNodes();

    }

    public void loadGround() {
        locationTitle.setText("Ground Floor");
        setSelectedFloor("G");

        Image img = new Image(location + "00_thegroundfloor.png");
        setZoom(img, 0, 0, noZoom);
        drawNodes();

    }

    public void loadF1() {
        locationTitle.setText("Floor 1");
        setSelectedFloor("1");

        Image img = new Image(location + "01_thefirstfloor.png");

        setZoom(img, 0, 0, noZoom);
        drawNodes();



    }

    public void loadF2() {
        locationTitle.setText("Floor 2");
        setSelectedFloor("2");

        Image img = new Image(location + "02_thesecondfloor.png");
        setZoom(img, 0, 0, noZoom);
        drawNodes();

    }

    public void loadF3() {
        locationTitle.setText("Floor 3");
        setSelectedFloor("L3");

        Image img = new Image(location + "03_thethirdfloor.png");
        setZoom(img, 0, 0, noZoom);
        drawNodes();
    }

    public void drawCoord(MouseEvent e) {
        double xRatio = 5000 / mapWindow.getFitWidth();
        double yRatio = 3400 / mapWindow.getFitHeight();

        System.out.printf("(%f,%f)\n", e.getX() * xRatio, e.getY() * yRatio);
    }

    public void toAStar() {
        AStar2 star = new AStar2();
        //pathToTake is used in the dialog box that keeps all the nodes that the user has to pass through
        StringBuilder pathToTake = new StringBuilder(new String());
        LinkedList<NodeSuper> stops = new LinkedList<>();
        for (node n : pitStops) {
            stops.add(MapSuper.getMap().get(n.nodeID));
        }
        if(pitStops.isEmpty()){
            pathToTake.append("Please select at least one node");
        }else{

            LinkedList<String> path = star.multiStopRoute(stops);

            for(int i = 1; i < path.size(); i++){
                Text aPartOfPath = new Text(path.get(i));
                aPartOfPath.setStyle("-fx-text-fill: white");
                aPartOfPath.setStyle("-fx-text-alignment: center");
                pitstopsLabel.getChildren().add(aPartOfPath);
            }

            System.out.println(path);
            for (String str : path) {
                System.out.printf("\n%s\n|\n", MapSuper.getMap().get(str).getLongName());
                pathToTake.append(MapSuper.getMap().get(str).getLongName()).append("\n|\n");//appending the paths
            }
            LinkedList<Line> lines = new LinkedList();

            for (int i = 0; i < path.size(); i++) {
                try {
                    node start = nodes.get(path.get(i));
                    node end = nodes.get(path.get(i+1));
                    double startX= start.xCoord;
                    double startY = start.yCoord;
                    double endX= end.xCoord;
                    double endY= end.yCoord;
                    Line l = new Line(startX,startY,endX,endY);
                    l.setStroke(Color.BLACK);
                    l.setStrokeWidth(5);
                    lines.add(l);
                } catch (Exception e){
                    System.out.println("Got here");
                }

            }
            nodePane.getChildren().addAll(lines);
            for (node n :
                    pitStops) {
                n.resetFill();
            }
            pitStops = new LinkedList<>();
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
            if(n.getFloor().equals(selectedFloor)){
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
    }

    public void highlightNode(MouseEvent e) {
        Circle src = ((Circle)e.getSource());
        src.setFill(Color.RED);

        Iterator<node> iter = nodes.values().iterator();

        for (int i = 0; i < nodes.size(); i++) {
            node n = iter.next();
            if (n.c.equals(src)) {
                //n.c.setFill(Color.RED);
                if(pitStops.isEmpty()){
                    // adds the text of the location where the user is to the top when clicked
                    placeClicked.setText(MapSuper.getMap().get(n.nodeID).getLongName());
                }
                if(pitStops.size() == 1){
                    // set the second destination clicked label
                    finalDestinationLabel.setText(MapSuper.getMap().get(n.nodeID).getLongName());
                }
                if(pitStops.size() > 1){
                    //set all the pitstops in the vbox for the pitstops
                    Label aPitStop = new Label(MapSuper.getMap().get(n.nodeID).getLongName());
                    pitstopsLabel.getChildren().add(aPitStop);
                }
                pitStops.add(n);
            }
        }
    }

    @FXML
    public void goToRouteExample(ActionEvent e) {
        drawNodes();
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
