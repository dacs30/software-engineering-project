package edu.wpi.MochaManticores.views;

import com.gluonhq.charm.down.plugins.StatusBarService;
import com.jfoenix.animation.alert.CenterTransition;
import com.jfoenix.controls.*;
import edu.wpi.MochaManticores.Algorithms.AStar2;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.Exceptions.InvalidElementException;
import edu.wpi.MochaManticores.Nodes.MapSuper;
import edu.wpi.MochaManticores.Nodes.NodeSuper;
import edu.wpi.MochaManticores.database.DatabaseManager;
import io.opencensus.trace.Link;
import javafx.animation.PathTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.*;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.ScrollEvent;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.geometry.Pos;
import javafx.util.Duration;
import javafx.util.Pair;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class mapPage extends SceneController{

    public void downloadCSVs(ActionEvent actionEvent) {
        String path = getPath();
        if (path.equals("")) {

        } else {
            File dst = new File(path + "\\bwMEdges.csv");
            try {
                File source = new File("data/bwMEdges.csv");
                Files.copy(source.toPath(), dst.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public class node {
        Circle c;
        String nodeID;
        double xCoord;
        double yCoord;
        boolean highlighted = false;

        public boolean isHighlighted() {
            return highlighted;
        }

        public void setHighlighted(boolean highlighted) {
            this.highlighted = highlighted;
        }

        public Circle getC() {
            return c;
        }

        public void setC(Circle c) {
            this.c = c;
        }

        public String getNodeID() {
            return nodeID;
        }

        public void setNodeID(String nodeID) {
            this.nodeID = nodeID;
        }

        public double getxCoord() {
            return xCoord;
        }

        public void setxCoord(double xCoord) {
            this.xCoord = xCoord;
        }

        public double getyCoord() {
            return yCoord;
        }

        public void setyCoord(double yCoord) {
            this.yCoord = yCoord;
        }

        public node(Circle c, String nodeID) {
            this.c = c;
            this.nodeID = nodeID;
            xCoord = c.getCenterX();
            yCoord = c.getCenterY();
        }

        public void resetFill() {
            c.setFill(Color.WHITE);
            c.setStrokeWidth(2);
            c.setStroke(Color.valueOf("#FF6B35"));
        }
    }

    @FXML
    private JFXComboBox floorSelector;

    @FXML
    private JFXToggleButton pathHandicap;

    private final HashMap<String, node> nodes = new HashMap();

    private LinkedList<node> pitStops = new LinkedList<>();
    private final LinkedList<String> savedRoute = new LinkedList<>();

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
    private ScrollPane directionPane;

    @FXML
    private StackPane dialogPane;

    @FXML
    private JFXTabPane tabPane;

    @FXML
    private JFXScrollPane scrollPane;

    @FXML
    private JFXComboBox fromLocation;

    @FXML
    private JFXComboBox toLocation;

    @FXML
    private JFXButton routeExample;

    @FXML
    private ScrollPane mapScrollPane;

    @FXML
    private JFXComboBox nearestLocationSelector;

    @FXML
    private ScrollPane textFieldScrollPane;

    @FXML
    private VBox textFieldGroup;

    @FXML
    private HBox addField;

    @FXML
    private AnchorPane tabPaneAnchor;

    @FXML
    private AnchorPane paneContainingTabPane;

    private final String location = "edu/wpi/MochaManticores/images/";

    private String selectedFloor = "";

    public void setSelectedFloor(String selectedFloor) {
        this.selectedFloor = selectedFloor;
    }

    VBox dirVBOX = new VBox();

    Rectangle2D noZoom;
    Rectangle2D zoomPort;

    private final DoubleProperty zoomProperty = new SimpleDoubleProperty(1.0d);
    private final DoubleProperty deltaY = new SimpleDoubleProperty(0.0d);

    private double dX;
    private double dY;
    private boolean updateDeltas = true;
    private boolean dragged = false;

    private void createFilterListener(JFXComboBox comboBox) {

        // Create the listener to filter the list as user enters search terms
        FilteredList<String> filteredList = new FilteredList<>(comboBox.getItems());

        // Add listener to our ComboBox textfield to filter the list
        comboBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            comboBox.show();
            filteredList.setPredicate(item -> {


                // If the TextField is empty, return all items in the original list
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Check if the search term is contained anywhere in our list
                return item.toLowerCase().contains(newValue.toLowerCase().trim());

            });
        });

        // Finally, let's add the filtered list to our ComboBox
        comboBox.setItems(filteredList);

    }

    public void initialize() {
        double height = super.getHeight();
        double width = super.getWidth();
        contentPane.setPrefSize(width, height);
        contentPane.maxWidthProperty().bind(App.getPrimaryStage().widthProperty());
        contentPane.maxHeightProperty().bind(App.getPrimaryStage().heightProperty());
        mapStack.maxWidthProperty().bind(App.getPrimaryStage().widthProperty());
        mapStack.maxHeightProperty().bind(App.getPrimaryStage().heightProperty());
        //mapScrollPane.prefWidthProperty().bind(App.getPrimaryStage().widthProperty());
        //GridPane.setHgrow(mapStack, Priority.ALWAYS);

        // event to drag the menu of the mapa around
        tabPane.setOnMouseDragged(event -> {

            System.out.println("hey");

            paneContainingTabPane.setManaged(false);

            if (updateDeltas){
                dX = (event.getSceneX() - paneContainingTabPane.getLayoutX());
                dY = (event.getSceneY() - paneContainingTabPane.getLayoutY());
                updateDeltas = false;
            }

            paneContainingTabPane.relocate(event.getSceneX() - dX, event.getSceneY() - dY);
            dragged = true;

        });

        tabPane.setOnMouseReleased(event -> {
            if (dragged) {
                if (paneContainingTabPane.getLayoutX() < App.getPrimaryStage().getWidth() / 2) {
                    GridPane.setHalignment(paneContainingTabPane, HPos.LEFT);
                    Line line = new Line();
                    line.setStartX(event.getSceneX());
                    line.setStartY(event.getSceneY());
                    line.setEndX(paneContainingTabPane.getWidth() / 2);
                    line.setEndY(event.getSceneY());

                    PathTransition pathTransition = new PathTransition();
                    pathTransition.setDuration(Duration.seconds(0.5));
                    pathTransition.setNode(paneContainingTabPane);
                    pathTransition.setPath(line);

                    pathTransition.setCycleCount(1);

                    pathTransition.play();

                } else {
                    GridPane.setHalignment(paneContainingTabPane, HPos.RIGHT);

                    Line line = new Line();
                    line.setStartX(event.getSceneX());
                    line.setStartY(event.getSceneY());
                    line.setEndX(paneContainingTabPane.getBoundsInLocal().getMaxX() - paneContainingTabPane.getWidth() / 2);
                    line.setEndY(event.getSceneY());

                    Path path = new Path();

                    // setted to -event because I don't know
                    path.getElements().add(new MoveTo(-event.getX(), event.getSceneY()));
                    path.getElements().add(new LineTo(paneContainingTabPane.getBoundsInLocal().getMaxX() - 35 - paneContainingTabPane.getWidth() / 2, event.getSceneY()));

                    PathTransition pathTransition = new PathTransition();
                    pathTransition.setDuration(Duration.seconds(0.5));
                    pathTransition.setNode(paneContainingTabPane);
                    pathTransition.setPath(path);

                    pathTransition.setCycleCount(1);

                    pathTransition.play();
                }
                paneContainingTabPane.setManaged(true);
                updateDeltas = true;
                dragged = false;
            }

        });


        mapWindow.setPreserveRatio(false);

        //MouseDragEvent.

        floorSelector.setValue("F1");

       loadF1();


        nearestLocationSelector.getItems().addAll("Bathroom", //REST
                                                            "Exit", //EXIT
                                                            "Service", //SERV
                                                            "Retail"); //RETL


        floorSelector.getItems().addAll("LL1",
                "LL2",
                "G",
                "F1",
                "F2",
                "F3");

        mapScrollPane.setPannable(true);
        mapScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mapScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        PanAndZoomPane panAndZoomPane = new PanAndZoomPane();
        zoomProperty.bind(panAndZoomPane.myScale);
        deltaY.bind(panAndZoomPane.deltaY);
        panAndZoomPane.getChildren().add(mapStack);
        //AutoCompleteComboBoxListener fromListener = new AutoCompleteComboBoxListener(fromLocation);
//
        //AutoCompleteComboBoxListener toListener = new AutoCompleteComboBoxListener(toLocation);

        fromLocation.setEditable(true);
        //fromLocation.setOnKeyTyped(new AutoCompleteComboBoxListener<>(fromLocation));
        ObservableList<String> items = FXCollections.observableArrayList();
        DatabaseManager.getElementIDs().forEach(s -> {
            items.add(s.toString());
        });
        fromLocation.setItems(items);
        createFilterListener(fromLocation);

        toLocation.setEditable(true);
        //fromLocation.setOnKeyTyped(new AutoCompleteComboBoxListener<>(fromLocation));
        ObservableList<String> items2 = FXCollections.observableArrayList();
        DatabaseManager.getElementIDs().forEach(s -> {
            items2.add(s.toString());
        });
        toLocation.setItems(items2);
        createFilterListener(fromLocation);



        SceneGestures sceneGestures = new SceneGestures(panAndZoomPane);

        mapScrollPane.setContent(panAndZoomPane);
        panAndZoomPane.toBack();
        mapScrollPane.addEventFilter( MouseEvent.MOUSE_CLICKED, sceneGestures.getOnMouseClickedEventHandler());
        mapScrollPane.addEventFilter( MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
        mapScrollPane.addEventFilter( MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
        mapScrollPane.addEventFilter( ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());

        //mapWindow.fitWidthProperty().bind(mapStack.widthProperty());
        //mapWindow.fitHeightProperty().bind(mapStack.heightProperty());
        mapWindow.fitWidthProperty().bind(mapScrollPane.widthProperty());
        mapWindow.fitHeightProperty().bind(mapScrollPane.heightProperty());
        mapWindow.fitWidthProperty().addListener((obs, oldVal, newVal) -> {
            drawNodes();
        });
        mapWindow.fitHeightProperty().addListener((obs, oldVal, newVal) -> {
            drawNodes();
        });
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                mapWindow.setFitWidth(mapStack.getWidth());
//                mapWindow.setFitHeight(mapStack.getHeight());
//            }
//        });

        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                //zoomImg(e);
            }
        };
        mapWindow.setOnMouseMoved(eventHandler);

//        App.getPrimaryStage().widthProperty().addListener((obs, oldVal, newVal) -> {
//            mapWindow.setFitWidth(App.getPrimaryStage().getWidth() - mapStack.localToScene(mapStack.getBoundsInLocal()).getMinX() - 50);
//            drawNodes();
//        });
//
//        App.getPrimaryStage().heightProperty().addListener((obs, oldVal, newVal) -> {
//            mapWindow.setFitHeight(App.getPrimaryStage().getHeight() - mapStack.localToScene(mapStack.getBoundsInLocal()).getMinY() - 50);
//            drawNodes();
//        });


//        mapWindow.setFitHeight(super.getHeight() / 1.5);
//        mapWindow.setFitWidth(super.getWidth() / 1.5);
//        mapWindow.setFitHeight(mapStack.getHeight()/2);
//        mapWindow.setFitWidth(mapStack.getWidth()/2);
        drawNodes();




        dirVBOX.setMaxWidth(Region.USE_COMPUTED_SIZE);




        //Initializing the dialog pane
        dialogPane.toBack();

        addField.setOnMouseClicked(event -> {
            int ind = textFieldGroup.getChildren().indexOf(fromLocation);
            Label toAdd = new Label();
            toAdd.setText("Add Stop");
            toAdd.setPrefWidth(300);
            toAdd.maxWidthProperty().bind(toAdd.prefWidthProperty());
            toAdd.minWidthProperty().bind(toAdd.prefWidthProperty());

            HBox hbox = new HBox();

            hbox.getChildren().add(toAdd);

            Image img = new Image("/edu/wpi/MochaManticores/images/removeIcon.png");
            ImageView minusImage = new ImageView(img);
            minusImage.setFitWidth(30);
            minusImage.setPreserveRatio(true);

            hbox.getChildren().add(minusImage);

            minusImage.setOnMouseClicked(e -> {
                textFieldGroup.getChildren().remove(ind + 1);
            });

            hbox.setAlignment(Pos.CENTER_LEFT);

            textFieldGroup.getChildren().add(ind + 1, hbox);
        });


    }

    private String getNodeType(){
        String type = (String) nearestLocationSelector.getSelectionModel().getSelectedItem();
        System.out.println(type);
        switch (type) {
            case "Bathroom":
                return "REST";
            case "Exit":
                return "EXIT";
            case "Service":
                return "SERV";
            case "Retail":
                return "RETL";
            default:
                return "REST";
        }
    }

    public void toAStar() {
        AStar2 star = new AStar2();
        //pathToTake is used in the dialog box that keeps all the nodes that the user has to pass through
        StringBuilder pathToTake = new StringBuilder();
        LinkedList<NodeSuper> stops = new LinkedList<>();
        for (mapPage.node n :
                pitStops) {
            stops.add(MapSuper.getMap().get(n.nodeID));
        }
        if (pitStops.isEmpty()) {
            pathToTake.append("Please select at least one node");
        } else {

            LinkedList<String> path = star.multiStopRoute(stops,pathToTake.toString());
            System.out.println(path);
            Label startLabel = new Label();
            Label endLabel = new Label();
            startLabel.setText(path.removeFirst());
            startLabel.setTextFill(Color.GREEN);
            endLabel.setText(path.removeLast());
            endLabel.setTextFill(Color.RED);
            dirVBOX.getChildren().add(startLabel);
            for (String str :
                    path) {
                Label p = new Label();
                p.setText(str);
                dirVBOX.getChildren().add(p);
//                System.out.printf("\n%s\n|\n", MapSuper.getMap().get(str).getLongName());
//                pathToTake.append(MapSuper.getMap().get(str).getLongName()).append("\n|\n");//appending the paths
            }
            dirVBOX.getChildren().add(endLabel);
            directionPane.setContent(dirVBOX);
            directionPane.setFitToWidth(true);
            LinkedList<Line> lines = new LinkedList();

            for (int i = 0; i < path.size(); i++) {
                try {
                    mapPage.node start = nodes.get(path.get(i));
                    mapPage.node end = nodes.get(path.get(i + 1));
                    double startX = start.xCoord;
                    double startY = start.yCoord;
                    double endX = end.xCoord;
                    double endY = end.yCoord;
                    Line l = new Line(startX, startY, endX, endY);
                    l.setStroke(Color.BLACK);
                    l.setStrokeWidth(5);
                    lines.add(l);
                } catch (Exception e) {
                    System.out.println("Got here");
                }

            }
            nodePane.getChildren().addAll(lines);
            for (mapPage.node n :
                    pitStops) {
                n.resetFill();
            }
            pitStops = new LinkedList<>();
        }

        //loadDialog(pathToTake); // calling the dialog pane with the path

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

    public void findNearestLocation(ActionEvent e) throws InvalidElementException {
        AStar2 star = new AStar2();

        savedRoute.clear();
        dirVBOX.getChildren().clear();
        //pathToTake is used in the dialog box that keeps all the nodes that the user has to pass through
        StringBuilder pathToTake = new StringBuilder();
        LinkedList<NodeSuper> stops = new LinkedList<>();
        if (pitStops.size() != 1){
            super.loadErrorDialog(dialogPane, "Must select only one node");
            return;
        }

        LinkedList<String> path;
        NodeSuper ns = null;
        ns = DatabaseManager.getNode(pitStops.get(0).getNodeID());
        if (!pathHandicap.isSelected()){
            if (App.getClearenceLevel() >= 1){
                path = star.findNearest(ns, getNodeType(), "none");
            } else {
                path = star.findNearest(ns, getNodeType(), "publicOnly");
            }
        } else {
            if (App.getClearenceLevel() >= 1){
                path = star.findNearest(ns, getNodeType(), "handicap");
            } else {
                path = star.findNearest(ns, getNodeType(), "publicHandicap");
            }

        }

        System.out.println(path);
        Label startLabel = new Label();
        String startID = path.removeFirst();
        startLabel.setText(DatabaseManager.getNode(startID).getLongName());
        savedRoute.add(startID);
        startLabel.setTextFill(Color.GREEN);
        startLabel.setAlignment(Pos.CENTER);
        Label endLabel = new Label();
        String endID = path.removeLast();
        endLabel.setText(DatabaseManager.getNode(endID).getLongName());
        endLabel.setTextFill(Color.RED);
        dirVBOX.getChildren().add(startLabel);
        for (String str :
                path) {
            savedRoute.add(str);
            Label p = new Label();
            p.setText(DatabaseManager.getNode(str).getLongName());
            dirVBOX.getChildren().add(p);
//                System.out.printf("\n%s\n|\n", DatabaseManager.getNode(str).getLongName());
//                pathToTake.append(DatabaseManager.getNode(str).getLongName()).append("\n|\n");//appending the paths
        }
        savedRoute.add(endID);
        dirVBOX.getChildren().add(endLabel);

        for (node n :
                pitStops) {
            n.resetFill();
        }
        pitStops = new LinkedList<>();

        drawNodes();

        directionPane.setContent(dirVBOX);
        //loadDialog(pathToTake); // calling the dialog pane with the path
    }

    public void back(ActionEvent e) {
        super.back();
    }

    public void back2(MouseEvent e) {
        super.back();
    }

    private void setZoom(Image img, double x, double y, Rectangle2D z) {
        noZoom = new Rectangle2D(0, 0, img.getWidth(), img.getHeight());
        zoomPort = new Rectangle2D(x, y, .25 * img.getWidth(), .25 * img.getHeight());

        mapWindow.setImage(img);
        mapWindow.setViewport(z);
    }

    public void loadL1() {
        //locationTitle.setText("Lower Level 1");
        setSelectedFloor("L1");
        setZoom(new Image(location + "00_thelowerlevel1.png"), 0, 0, noZoom);
        drawNodes();

    }

    public void loadL2() {
        //locationTitle.setText("Lower Level 2");
        setSelectedFloor("L2");

        setZoom(new Image(location + "00_thelowerlevel2.png"), 0, 0, noZoom);
        drawNodes();

    }

    public void loadGround() {
        //locationTitle.setText("Ground Floor");
        setSelectedFloor("G");

        setZoom(new Image(location + "00_thegroundfloor.png"), 0, 0, noZoom);
        drawNodes();

    }

    public void loadF1() {
        //locationTitle.setText("Floor 1");
        setSelectedFloor("1");

        setZoom(new Image(location + "01_thefirstfloor.png"), 0, 0, noZoom);
        drawNodes();

    }

    public void loadF2() {
        //locationTitle.setText("Floor 2");
        setSelectedFloor("2");


        setZoom(new Image(location + "02_thesecondfloor.png"), 0, 0, noZoom);
        drawNodes();

    }

    public void loadF3() {
        //locationTitle.setText("Floor 3");
        setSelectedFloor("L3");

        setZoom(new Image(location + "03_thethirdfloor.png"), 0, 0, noZoom);
        drawNodes();
    }

    public void drawCoord(MouseEvent e) {
        double xRatio = 5000 / mapWindow.getFitWidth();
        double yRatio = 3400 / mapWindow.getFitHeight();

        System.out.printf("(%f,%f)\n", e.getX() * xRatio, e.getY() * yRatio);
    }

    public void findPath() throws InvalidElementException {
        savedRoute.clear();
        dirVBOX.getChildren().clear();
        //pathToTake is used in the dialog box that keeps all the nodes that the user has to pass through
        StringBuilder pathToTake = new StringBuilder();
        LinkedList<NodeSuper> stops = new LinkedList<>();
        for (node n :
                pitStops) {
            stops.add(DatabaseManager.getNode(n.nodeID));
        }
        if (pitStops.isEmpty()) {
            pathToTake.append("Please select at least one node");
        } else {
            LinkedList<String> path;
            if (!pathHandicap.isSelected()){
                if (App.getClearenceLevel() >= 1){
                    path = App.getAlgoType().multiStopRoute(stops, "none");
                } else {
                    path = App.getAlgoType().multiStopRoute(stops, "publicOnly");
                }
            } else {
                if (App.getClearenceLevel() >= 1){
                    path = App.getAlgoType().multiStopRoute(stops, "handicap");
                } else {
                    path = App.getAlgoType().multiStopRoute(stops, "publicHandicap");
                }

            }
             //CONDITION NEEDS TO BE INPUT HERE
            System.out.println(path);
            Label startLabel = new Label();
            String startID = path.removeFirst();
            startLabel.setText(DatabaseManager.getNode(startID).getLongName());
            savedRoute.add(startID);
            startLabel.setTextFill(Color.GREEN);
            startLabel.setAlignment(Pos.CENTER);
            Label endLabel = new Label();
            String endID = path.removeLast();
            endLabel.setText(DatabaseManager.getNode(endID).getLongName());
            endLabel.setTextFill(Color.RED);
            dirVBOX.getChildren().add(startLabel);
            for (String str :
                    path) {
                savedRoute.add(str);
                Label p = new Label();
                p.setText(DatabaseManager.getNode(str).getLongName());
                dirVBOX.getChildren().add(p);
//                System.out.printf("\n%s\n|\n", DatabaseManager.getNode(str).getLongName());
//                pathToTake.append(DatabaseManager.getNode(str).getLongName()).append("\n|\n");//appending the paths
            }
            savedRoute.add(endID);
            dirVBOX.getChildren().add(endLabel);

            for (node n :
                    pitStops) {
                n.resetFill();
            }
            pitStops = new LinkedList<>();
        }

        drawNodes();

        directionPane.setContent(dirVBOX);
        //loadDialog(pathToTake); // calling the dialog pane with the path

    }

    /**
     * selects floor from comboBox loads the map image
     */
    public void selectFloor() {
        String floor = (String) floorSelector.getSelectionModel().getSelectedItem();
        System.out.println(floor);
        switch (floor) {
            case "LL1":
                loadL1();
                break;
            case "LL2":
                loadL2();
                break;
            case "G":
                loadGround();
                break;
            case "F1":
                loadF1();
                break;
            case "F2":
                loadF2();
                break;
            case "F3":
                loadF3();
                break;
            default:
        }
    }

    public void clearLines(ActionEvent e){
        savedRoute.clear();
        drawNodes();
        dirVBOX.getChildren().clear();
    }

    public void drawNodes() {
        nodePane.getChildren().clear();
        double xRatio = 5000 / mapWindow.getFitWidth();
        double yRatio = 3400 / mapWindow.getFitHeight();
        Iterator<NodeSuper> mapIter = MapSuper.getMap().values().iterator();
        for (int i = 0; i < MapSuper.getMap().size(); i++) {
            NodeSuper n = mapIter.next();
            if (n.getFloor().equals(selectedFloor)) {
                Circle spot = new Circle(n.getXcoord() / xRatio, n.getYcoord() / yRatio, 2, Color.WHITE);
                spot.setStrokeWidth(1);
                spot.setStroke(Color.valueOf("#FF6B35"));
                EventHandler<MouseEvent> highlight = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        highlightNode(e);
                    }
                };
                EventHandler<MouseEvent> large = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        mouseOverNode(e,4);
                    }
                };
                EventHandler<MouseEvent> small = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        mouseOverNode(e,2);
                    }
                };
                spot.setOnMouseClicked(highlight);
                spot.setOnMouseEntered(large);
                spot.setOnMouseExited(small);
                nodes.put(n.getID(), new node(spot, n.getID()));
                nodePane.getChildren().addAll(nodes.get(n.getID()).c);
            }
        }
        LinkedList<Line> lines = new LinkedList();
        for (int i = 0; i < savedRoute.size()-1; i++){
            NodeSuper curNode = null;
            NodeSuper endNode = null;
            try {
                curNode = DatabaseManager.getNode(savedRoute.get(i));
                endNode = DatabaseManager.getNode(savedRoute.get(i+1));
            } catch (InvalidElementException e) {
                e.printStackTrace();
            }

            Line edge = new Line(curNode.getXcoord() / xRatio, curNode.getYcoord() / yRatio, endNode.getXcoord() / xRatio, endNode.getYcoord() / yRatio);
            edge.setStrokeWidth(4);
            if (curNode.getFloor().equals(selectedFloor)){
                edge.setStroke(Color.BLACK);
            } else {
                edge.setStroke(Color.GREY);
            }

            lines.add(edge);
        }

        nodePane.getChildren().addAll(lines);
        for (Line l : lines){
            l.toBack();
        }
    }

    public void highlightNode(MouseEvent e) {
        Circle src = ((Circle)e.getSource());
        src.setFill(Color.valueOf("#0F4B91"));

        Iterator<node> iter = nodes.values().iterator();

        for (int i = 0; i < nodes.size(); i++) {
            node n = iter.next();
            if (n.c.equals(src)) {
                if (n.isHighlighted()){
                    src.setFill(Color.WHITE);
                    n.setHighlighted(false);
                    pitStops.remove(n);
                } else {
                    src.setFill(Color.valueOf("#0F4B91"));
                    n.setHighlighted(true);
                    pitStops.add(n);

                }
            }
        }
    }

    public void mouseOverNode(MouseEvent e, double radius){
        Circle src = ((Circle)e.getSource());
        Iterator<node> iter = nodes.values().iterator();
        for (int i = 0; i < nodes.size(); i++) {
            node n = iter.next();
            if (n.c.equals(src)) {
                n.c.setRadius(radius);
            }
        }

    }

    @FXML
    public void goToRouteExample(ActionEvent e) {
        drawNodes();
        try {
            findPath();
        } catch (InvalidElementException invalidElementException) {
            invalidElementException.printStackTrace();
        }
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
