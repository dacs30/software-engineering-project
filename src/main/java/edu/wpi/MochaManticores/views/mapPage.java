package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.*;
import edu.wpi.MochaManticores.Algorithms.AStar2;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.Exceptions.DestinationNotAccessibleException;
import edu.wpi.MochaManticores.Exceptions.InvalidElementException;
import edu.wpi.MochaManticores.Nodes.MapSuper;
import edu.wpi.MochaManticores.Nodes.NodeSuper;
import edu.wpi.MochaManticores.database.DatabaseManager;
import edu.wpi.MochaManticores.database.Employee;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class mapPage extends SceneController {

    public mapPage() throws InvalidElementException {
    }

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
        public boolean isStart() {
            return start.get();
        }

        public BooleanProperty startProperty() {
            return start;
        }

        public void setStart(boolean start) {
            this.start.set(start);
        }

        public boolean isEnd() {
            return end.get();
        }

        public BooleanProperty endProperty() {
            return end;
        }

        public void setEnd(boolean end) {
            this.end.set(end);
        }

        Circle c;
        String nodeID;
        double xCoord;
        double yCoord;
        boolean highlighted = false;

        BooleanProperty start = new SimpleBooleanProperty();
        BooleanProperty end = new SimpleBooleanProperty();

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
            c.setStrokeWidth(3);
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
    private JFXButton parkingButton;

    @FXML
    private ScrollPane directionPane;

    @FXML
    private StackPane dialogPane;

    @FXML
    private JFXTabPane tabPane;

    @FXML
    private JFXScrollPane scrollPane;

    @FXML
    private JFXTextField fromLocation;

    @FXML
    private JFXTextField toLocation;

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

    Employee user;

    private PanAndZoomPane panAndZoomPane = new PanAndZoomPane();

    @FXML
    private HBox toHBOX;

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

    private LinkedList<JFXTextField> fields = new LinkedList<JFXTextField>();
    private int fieldIndex = 0;

    /*
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

    }*/

    public class nodeNameWrapper {
        String ID;
        String name;

        public nodeNameWrapper(String ID, String name) {
            this.ID = ID;
            this.name = name;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }


    public void initialize() throws InvalidElementException {
        double height = super.getHeight();
        double width = super.getWidth();
        contentPane.setPrefSize(width, height);
        contentPane.maxWidthProperty().bind(App.getPrimaryStage().widthProperty());
        contentPane.maxHeightProperty().bind(App.getPrimaryStage().heightProperty());
        mapStack.maxWidthProperty().bind(App.getPrimaryStage().widthProperty());
        mapStack.maxHeightProperty().bind(App.getPrimaryStage().heightProperty());

        // Turns off the horizontal scroll and fits the content to the pane width
        directionPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        directionPane.setFitToWidth(true);

        try {
            user = DatabaseManager.getEmployee(App.getCurrentUsername());
        } catch (InvalidElementException ex) {
            App.setCurrentUsername("Guest");
            user = DatabaseManager.getEmployee(App.getCurrentUsername());
        }


        if (user.isCovidStatus()) {
            DatabaseManager.getNode("FEXIT00201").setCovid(true);
        }
        if (!user.getParkingSpace().equals("Parking")) {
            parkingButton.setText("My spot");
        }
        //mapScrollPane.prefWidthProperty().bind(App.getPrimaryStage().widthProperty());
        //GridPane.setHgrow(mapStack, Priority.ALWAYS);

        // event to drag the menu of the mapa around (LEGACY)
//        tabPane.setOnMouseDragged(event -> {
//
//            System.out.println("hey");
//
//            paneContainingTabPane.setManaged(false);
//
//            if (updateDeltas){
//                dX = (event.getSceneX() - paneContainingTabPane.getLayoutX());
//                dY = (event.getSceneY() - paneContainingTabPane.getLayoutY());
//                updateDeltas = false;
//            }
//
//            paneContainingTabPane.relocate(event.getSceneX() - dX, event.getSceneY() - dY);
//            dragged = true;
//
//        });

//        tabPane.setOnMouseReleased(event -> {
//            if (dragged) {
//                if (paneContainingTabPane.getLayoutX() < App.getPrimaryStage().getWidth() / 2) {
//                    GridPane.setHalignment(paneContainingTabPane, HPos.LEFT);
//                    Line line = new Line();
//                    line.setStartX(event.getSceneX());
//                    line.setStartY(event.getSceneY());
//                    line.setEndX(paneContainingTabPane.getWidth() / 2);
//                    line.setEndY(event.getSceneY());
//
//                    PathTransition pathTransition = new PathTransition();
//                    pathTransition.setDuration(Duration.seconds(0.5));
//                    pathTransition.setNode(paneContainingTabPane);
//                    pathTransition.setPath(line);
//
//                    pathTransition.setCycleCount(1);
//
//                    pathTransition.play();
//
//                } else {
//                    GridPane.setHalignment(paneContainingTabPane, HPos.RIGHT);
//
//                    Line line = new Line();
//                    line.setStartX(event.getSceneX());
//                    line.setStartY(event.getSceneY());
//                    line.setEndX(paneContainingTabPane.getBoundsInLocal().getMaxX() - paneContainingTabPane.getWidth() / 2);
//                    line.setEndY(event.getSceneY());
//
//                    Path path = new Path();
//
//                    // setted to -event because I don't know
//                    path.getElements().add(new MoveTo(-event.getX(), event.getSceneY()));
//                    path.getElements().add(new LineTo(paneContainingTabPane.getBoundsInLocal().getMaxX() - 35 - paneContainingTabPane.getWidth() / 2, event.getSceneY()));
//
//                    PathTransition pathTransition = new PathTransition();
//                    pathTransition.setDuration(Duration.seconds(0.5));
//                    pathTransition.setNode(paneContainingTabPane);
//                    pathTransition.setPath(path);
//
//                    pathTransition.setCycleCount(1);
//
//                    pathTransition.play();
//                }
//                paneContainingTabPane.setManaged(true);
//                updateDeltas = true;
//                dragged = false;
//            }

//        });


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

        zoomProperty.bind(panAndZoomPane.myScale);
        deltaY.bind(panAndZoomPane.deltaY);
        panAndZoomPane.getChildren().add(mapStack);
        //AutoCompleteComboBoxListener fromListener = new AutoCompleteComboBoxListener(fromLocation);
//
        //AutoCompleteComboBoxListener toListener = new AutoCompleteComboBoxListener(toLocation);

        /*fromLocation.setEditable(true);
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
        createFilterListener(toLocation);
        createFilterListener(fromLocation);
*/

        ObservableList<nodeNameWrapper> items = FXCollections.observableArrayList();
        DatabaseManager.getElementIDs().forEach(s -> {
            items.add(new nodeNameWrapper(s.getKey(), s.getValue()));
        });

        fromLocation = new JFXTextField();
        toLocation = new JFXTextField();


        textFieldGroup.getChildren().add(0, fromLocation);
        addField.getChildren().add(toLocation);


        setAutoComplete(fromLocation, items);
        setAutoComplete(toLocation, items);

        //fromLocation.textProperty().addListener();

        fields.add(fromLocation);
        fields.add(toLocation);


        SceneGestures sceneGestures = new SceneGestures(panAndZoomPane);

        mapScrollPane.setContent(panAndZoomPane);
        panAndZoomPane.toBack();
        mapScrollPane.addEventFilter(MouseEvent.MOUSE_CLICKED, sceneGestures.getOnMouseClickedEventHandler());
        mapScrollPane.addEventFilter(MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
        mapScrollPane.addEventFilter(MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
        mapScrollPane.addEventFilter(ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());

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
            addPitstopField();
        });


    }

    private int addPitstopField() {
        int ind = textFieldGroup.getChildren().indexOf(toHBOX);

        HBox cont = new HBox();
        JFXTextField toAdd = new JFXTextField();
        toAdd.setPrefWidth(300);
        toAdd.maxWidthProperty().bind(toAdd.prefWidthProperty());
        toAdd.minWidthProperty().bind(toAdd.prefWidthProperty());

        HBox g = new HBox();

        Image img = new Image("/edu/wpi/MochaManticores/images/removeIcon.png");
        ImageView minusImage = new ImageView(img);
        minusImage.setFitWidth(30);
        minusImage.setPreserveRatio(true);

        g.getChildren().add(minusImage);


        cont.getChildren().addAll(toAdd, g);

        cont.setAlignment(Pos.CENTER_LEFT);

        ObservableList<nodeNameWrapper> items = FXCollections.observableArrayList();
        DatabaseManager.getElementIDs().forEach(s -> {
            items.add(new nodeNameWrapper(s.getKey(), s.getValue()));
        });

        setAutoComplete(toAdd, items);

        g.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                textFieldGroup.getChildren().remove(cont);
                pitStops.remove(nodes.get(toAdd.getText()));
                //updateFields();
            }
        });

        cont.setSpacing(10);

        textFieldGroup.getChildren().add(ind, cont);
        fields.add(fields.indexOf(toLocation), toAdd);

        return fields.indexOf(toAdd);
    }

    private String getNodeType() {
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

    public void toAStar() throws DestinationNotAccessibleException {
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

            LinkedList<String> path = star.multiStopRoute(stops, pathToTake.toString());
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
     *
     * @param path
     */
    public void loadDialog(StringBuilder path) {
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
        if (path.toString().equals("Please select at least one node")) {
            body = new Text(path.toString());
            header = new Text("Error");
            header.setFill(Color.RED);
            vbox.setAlignment(Pos.CENTER_LEFT);
        } else {

            body = new Text(path.substring(0, path.toString().length() - 3));

        }


        body.setStyle("-fx-font-size: 15");
        body.setStyle("-fx-font-family: Roboto");
        body.setStyle("-fx-alignment: center");
        body.setTextAlignment(TextAlignment.CENTER);

        if (path.toString().equals("Please select at least one node")) {
            vbox.getChildren().addAll(body);
        } else {
            vbox.getChildren().addAll(start, body, ending);
        }
        message.setHeading(header);
        message.setBody(vbox);
        JFXDialog dialog = new JFXDialog(dialogPane, message, JFXDialog.DialogTransition.CENTER);
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

    public void findNearestLocation(ActionEvent event) throws InvalidElementException, DestinationNotAccessibleException {
        AStar2 star = new AStar2();

        savedRoute.clear();
        dirVBOX.getChildren().clear();
        //pathToTake is used in the dialog box that keeps all the nodes that the user has to pass through
        StringBuilder pathToTake = new StringBuilder();
        LinkedList<NodeSuper> stops = new LinkedList<>();
        if (pitStops.size() != 1) {
            super.loadErrorDialog(dialogPane, "Must select only one node");
            return;
        }

        LinkedList<String> path;
        NodeSuper ns = null;
        ns = DatabaseManager.getNode(pitStops.get(0).getNodeID());
        if (!pathHandicap.isSelected()) {
            if (App.getClearenceLevel() >= 1) {
                path = star.findNearest(ns, getNodeType(), "none");
            } else {
                path = star.findNearest(ns, getNodeType(), "publicOnly");
            }
        } else {
            if (App.getClearenceLevel() >= 1) {
                path = star.findNearest(ns, getNodeType(), "handicap");
            } else {
                path = star.findNearest(ns, getNodeType(), "publicHandicap");
            }

        }

//        System.out.println(path);
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
//        dirVBOX.getChildren().add(startLabel);
        for (String str : path) {
            savedRoute.add(str);
        }
        int count = 0;
        final VBox[] pathCurrentlyOpened = {null};
        String firstFloor = null;
        for (LinkedList<String> floor : App.getAlgoType().pathToText(path)) {
            // floorBlock is a VBox that contains the VBox of the floor and the
            // VBox of HBoxes for that floor path
            VBox floorBlock = new VBox();

            // currentFloorTitle is the VBox with the floor Label and the grey line
            VBox currentFloorTitle = new VBox();

            // grey line at the bottom of the top of the label
            Rectangle line = new Rectangle();
            line.setFill(Color.rgb(10, 10, 10, 0.2));
            line.setWidth(250);
            line.setHeight(1);

            // HBox that contains the Label and the button to open the path
            GridPane currentFloorGrid = new GridPane();
            Label currentFloor = new Label();
            currentFloor.setStyle("-fx-font-family: Roboto");
            currentFloor.setStyle("-fx-font-weight: bold");
            currentFloor.setTextFill(Color.GREY);

            // Button to open the path
            JFXButton seePathButton = new JFXButton("View Path");
            seePathButton.setStyle("-fx-font-size: 15");
            seePathButton.setOnMouseClicked(e->{
                if (floor.getFirst().contains("L1")){
                    loadL1();
                } else if (floor.getFirst().contains("L2")){
                    loadL2();
                } else if (floor.getFirst().contains("G")){
                    loadGround();
                } else if (floor.getFirst().contains("1")){
                    loadF1();
                } else if (floor.getFirst().contains("2")){
                    loadF2();
                } else if (floor.getFirst().contains("3")){
                    loadF3();
                }

                if (floorBlock.getChildren().get(1).isVisible()) {
                    floorBlock.getChildren().get(1).setVisible(false);
                    floorBlock.getChildren().get(1).setManaged(false);
                } else{
                    pathCurrentlyOpened[0].setVisible(false);
                    pathCurrentlyOpened[0].setManaged(false);
                    floorBlock.getChildren().get(1).setVisible(true);
                    floorBlock.getChildren().get(1).setManaged(true);
                    pathCurrentlyOpened[0] = (VBox) floorBlock.getChildren().get(1);
                }
            });

            currentFloorGrid.add(currentFloor, 0, 0);
            currentFloorGrid.add(seePathButton, 0, 1);
            currentFloorGrid.setPrefWidth(300);
            GridPane.setValignment(currentFloor, VPos.CENTER);
            GridPane.setHalignment(currentFloor, HPos.LEFT);
            currentFloorGrid.setAlignment(Pos.CENTER);
            currentFloorGrid.setPadding(new Insets(15, 0, 15, 0));
            currentFloor.setText(floor.getFirst()); //Floor 1:
            currentFloor.setPadding(new Insets(0, 0, 0, 10));

            currentFloorTitle.getChildren().add(line);
            currentFloorTitle.getChildren().add(currentFloorGrid);
            currentFloorTitle.setAlignment(Pos.CENTER);

            floorBlock.getChildren().add(currentFloorTitle);
            VBox pathsOnThisFloor = new VBox();

            // Only the first floor is opened  by default
            if (count != 0) {
                pathsOnThisFloor.setVisible(false);
                pathsOnThisFloor.setManaged(false);
            } else if(count == 0){ // ignore that, only happens once
                pathCurrentlyOpened[0] = pathsOnThisFloor;
            }
            count++;

            // VBox for the paths on this floor
            floorBlock.getChildren().get(0).setOnMouseClicked(e -> {

                System.out.println(floor.getFirst());
                if (floor.getFirst().contains("L1")){
                    loadL1();
                } else if (floor.getFirst().contains("L2")){
                    loadL2();
                } else if (floor.getFirst().contains("G")){
                    loadGround();
                } else if (floor.getFirst().contains("1")){
                    loadF1();
                } else if (floor.getFirst().contains("2")){
                    loadF2();
                } else if (floor.getFirst().contains("3")){
                    loadF3();
                }

                if (floorBlock.getChildren().get(1).isVisible()) {
                    floorBlock.getChildren().get(1).setVisible(false);
                    floorBlock.getChildren().get(1).setManaged(false);
                } else{
                    pathCurrentlyOpened[0].setVisible(false);
                    pathCurrentlyOpened[0].setManaged(false);
                    floorBlock.getChildren().get(1).setVisible(true);
                    floorBlock.getChildren().get(1).setManaged(true);
                    pathCurrentlyOpened[0] = (VBox) floorBlock.getChildren().get(1);
                }
            });

            for (String s : floor) {
                Label p = new Label();

                if (s.contains("Take the stairs up to floor ")) {
                    HBox hbox = new HBox();
                    Image img = new Image("/edu/wpi/MochaManticores/images/upStairsDirectionIcon.png");
                    ImageView stairs = new ImageView(img);
                    stairs.setFitWidth(50);
                    stairs.setPreserveRatio(true);
                    p.setText(s);
                    p.setPadding(new Insets(10, 0, 10, 0));
                    p.setPrefWidth(Control.USE_COMPUTED_SIZE);
                    p.setWrapText(true);
                    hbox.getChildren().add(stairs);
                    hbox.getChildren().add(p);
                    hbox.setAlignment(Pos.CENTER_LEFT);
                    pathsOnThisFloor.getChildren().add(hbox);
                } else if (s.contains("Take the elevator up to floor ")) {
                    HBox hbox = new HBox();
                    Image img = new Image("/edu/wpi/MochaManticores/images/elevatorDirectionIcon.png");
                    ImageView elevator = new ImageView(img);
                    elevator.setFitWidth(50);
                    elevator.setPreserveRatio(true);
                    p.setText(s);
                    p.setPadding(new Insets(10, 0, 10, 0));
                    p.setWrapText(true);
                    p.setPrefWidth(Control.USE_COMPUTED_SIZE);
                    hbox.getChildren().add(elevator);
                    hbox.getChildren().add(p);
                    hbox.setAlignment(Pos.CENTER_LEFT);
                    pathsOnThisFloor.getChildren().add(hbox);
                } else if (s.contains("Take the stairs down to floor ")) {
                    HBox hbox = new HBox();
                    Image img = new Image("/edu/wpi/MochaManticores/images/downStairsDirectionIcon.png");
                    ImageView stairs = new ImageView(img);
                    stairs.setFitWidth(50);
                    stairs.setPreserveRatio(true);
                    p.setText(s);
                    p.setPadding(new Insets(10, 0, 10, 0));
                    p.setWrapText(true);
                    p.setPrefWidth(Control.USE_COMPUTED_SIZE);
                    hbox.getChildren().add(stairs);
                    hbox.getChildren().add(p);
                    hbox.setAlignment(Pos.CENTER_LEFT);
                    pathsOnThisFloor.getChildren().add(hbox);
                } else if (s.contains("Take the elevator down to floor ")) {
                    HBox hbox = new HBox();
                    Image img = new Image("/edu/wpi/MochaManticores/images/elevatorDirectionIcon.png");
                    ImageView elevator = new ImageView(img);
                    elevator.setFitWidth(50);
                    elevator.setPreserveRatio(true);
                    p.setText(s);
                    p.setPadding(new Insets(10, 0, 10, 0));
                    p.setWrapText(true);
                    p.setPrefWidth(Control.USE_COMPUTED_SIZE);
                    hbox.getChildren().add(elevator);
                    hbox.getChildren().add(p);
                    hbox.setAlignment(Pos.CENTER_LEFT);
                    pathsOnThisFloor.getChildren().add(hbox);
                } else if (s.contains("Head straight until you reach ")) {
                    HBox hbox = new HBox();
                    Image img = new Image("/edu/wpi/MochaManticores/images/upArrowDirection.png");
                    ImageView arrowUp = new ImageView(img);
                    arrowUp.setFitWidth(50);
                    arrowUp.setPreserveRatio(true);
                    p.setText(s);
                    p.setPadding(new Insets(10, 0, 10, 0));
                    p.setWrapText(true);
                    p.setPrefWidth(Control.USE_COMPUTED_SIZE);
                    hbox.getChildren().add(arrowUp);
                    hbox.getChildren().add(p);
                    hbox.setAlignment(Pos.CENTER_LEFT);
                    pathsOnThisFloor.getChildren().add(hbox);
                } else if (s.contains("Then turn left")) {
                    HBox hbox = new HBox();
                    Image img = new Image("/edu/wpi/MochaManticores/images/upArrowDirection.png");
                    ImageView arrowUp = new ImageView(img);
                    arrowUp.setPreserveRatio(true);
                    arrowUp.setRotate(-90);
                    arrowUp.setFitWidth(50);
                    p.setText(s);
                    p.setPadding(new Insets(10, 0, 10, 0));
                    p.setWrapText(true);
                    p.setPrefWidth(Control.USE_COMPUTED_SIZE);
                    hbox.getChildren().add(arrowUp);
                    hbox.getChildren().add(p);
                    hbox.setAlignment(Pos.CENTER_LEFT);
                    pathsOnThisFloor.getChildren().add(hbox);
                } else if (s.contains("Then turn right")) {
                    HBox hbox = new HBox();
                    Image img = new Image("/edu/wpi/MochaManticores/images/upArrowDirection.png");
                    ImageView arrowUp = new ImageView(img);
                    arrowUp.setPreserveRatio(true);
                    arrowUp.setRotate(90);
                    arrowUp.setFitWidth(50);
                    p.setText(s);
                    p.setPadding(new Insets(10, 0, 10, 0));
                    p.setWrapText(true);
                    p.setPrefWidth(Control.USE_COMPUTED_SIZE);
                    hbox.getChildren().add(arrowUp);
                    hbox.getChildren().add(p);
                    hbox.setAlignment(Pos.CENTER_LEFT);
                    pathsOnThisFloor.getChildren().add(hbox);
                }

            }

            pathsOnThisFloor.setPadding(new Insets(0, 0, 20, 0));

            floorBlock.getChildren().add(pathsOnThisFloor);
            dirVBOX.getChildren().add(floorBlock);

            for (node n : pitStops) {
                n.resetFill();
            }

            updateFields();
        }

        if (firstFloor.contains("L1")){
            loadL1();
        } else if (firstFloor.contains("L2")){
            loadL2();
        } else if (firstFloor.contains("G")){
            loadGround();
        } else if (firstFloor.contains("1")){
            loadF1();
        } else if (firstFloor.contains("2")){
            loadF2();
        } else if (firstFloor.contains("3")){
            loadF3();
        }

        drawNodes();
        pitStops.getFirst().setStart(true);
        pitStops.getLast().setEnd(true);

        drawNodes();

        double CenterX = pitStops.getFirst().getxCoord();
        double CenterY = pitStops.getFirst().getyCoord();
        //loadDialog(pathToTake); // calling the dialog pane with the path

        //System.out.printf("X:%f Y:%f\n", CenterX, CenterY);

        pitStops = new LinkedList<>();
        double xRatio = 5000 / mapWindow.getFitWidth();
        double yRatio = 3400 / mapWindow.getFitHeight();
        directionPane.setContent(dirVBOX);
        //loadDialog(pathToTake); // calling the dialog pane with the path
        panAndZoomPane.centerOnPoint(CenterX, CenterY);

        tabPane.getSelectionModel().select(0);
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
        setSelectedFloor("3");

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
        for (node n : pitStops) {
            stops.add(DatabaseManager.getNode(n.nodeID));
        }
        if (pitStops.isEmpty()) {
            pathToTake.append("Please select at least one node");
        } else {
            LinkedList<String> path = null;
            try {
                if (DatabaseManager.getEmployee(App.getCurrentUsername()).isCovidStatus()) {
                    if (pathHandicap.isSelected()) {
                        path = App.getAlgoType().multiStopRoute(stops, "covidHandicap");
                    } else {
                        path = App.getAlgoType().multiStopRoute(stops, "covid");
                    }
                } else {
                    if (!pathHandicap.isSelected()) {
                        if (App.getClearenceLevel() >= 1) {
                            path = App.getAlgoType().multiStopRoute(stops, "none");
                        } else {
                            path = App.getAlgoType().multiStopRoute(stops, "publicOnly");
                        }
                    } else {
                        if (App.getClearenceLevel() >= 1) {
                            path = App.getAlgoType().multiStopRoute(stops, "handicap");
                        } else {
                            path = App.getAlgoType().multiStopRoute(stops, "publicHandicap");
                        }

                    }
                }
            } catch (DestinationNotAccessibleException de) {
                loadErrorDialog(dialogPane, "No accessible Path Found! If you think this is a mistake, please contact a staff member.");
                return;
            }
            //CONDITION NEEDS TO BE INPUT HERE
            System.out.println(path);
            //Label startLabel = new Label();
            //String startID = path.removeFirst();
            //startLabel.setText(DatabaseManager.getNode(startID).getLongName());
            //savedRoute.add(startID);
            //startLabel.setTextFill(Color.GREEN);
            //startLabel.setAlignment(Pos.CENTER);
            //Label endLabel = new Label();
            //String endID = path.removeLast();
            //endLabel.setText(DatabaseManager.getNode(endID).getLongName());
            //endLabel.setTextFill(Color.RED);
//            dirVBOX.getChildren().add(startLabel);
            for (String str : path) {
                savedRoute.add(str);
//                Label p = new Label();
//                p.setText(DatabaseManager.getNode(str).getLongName());
//                dirVBOX.getChildren().add(p);
//                System.out.printf("\n%s\n|\n", DatabaseManager.getNode(str).getLongName());
//                pathToTake.append(DatabaseManager.getNode(str).getLongName()).append("\n|\n");//appending the paths
            }
            //savedRoute.add(endID);
            int count = 0;
            String firstFloor = null;
            final VBox[] pathCurrentlyOpened = {null};
            for (LinkedList<String> floor : App.getAlgoType().pathToText(path)) {
                // floorBlock is a VBox that contains the VBox of the floor and the
                // VBox of HBoxes for that floor path
                VBox floorBlock = new VBox();

                // currentFloorTitle is the VBox with the floor Label and the grey line
                VBox currentFloorTitle = new VBox();

                // grey line at the bottom of the top of the label
                Rectangle line = new Rectangle();
                line.setFill(Color.rgb(10, 10, 10, 0.2));
                line.setWidth(250);
                line.setHeight(1);

                // HBox that contains the Label and the button to open the path
                GridPane currentFloorGrid = new GridPane();
                Label currentFloor = new Label();
                currentFloor.setStyle("-fx-font-family: Roboto");
                currentFloor.setStyle("-fx-font-weight: bold");
                currentFloor.setTextFill(Color.GREY);

                // Button to open the path
                JFXButton seePathButton = new JFXButton("View Path");
                seePathButton.setStyle("-fx-font-size: 15");
                seePathButton.setOnMouseClicked(e->{
                    if (floor.getFirst().contains("L1")){
                        loadL1();
                    } else if (floor.getFirst().contains("L2")){
                        loadL2();
                    } else if (floor.getFirst().contains("G")){
                        loadGround();
                    } else if (floor.getFirst().contains("1")){
                        loadF1();
                    } else if (floor.getFirst().contains("2")){
                        loadF2();
                    } else if (floor.getFirst().contains("3")){
                        loadF3();
                    }

                    if (floorBlock.getChildren().get(1).isVisible()) {
                        floorBlock.getChildren().get(1).setVisible(false);
                        floorBlock.getChildren().get(1).setManaged(false);
                    } else{
                        pathCurrentlyOpened[0].setVisible(false);
                        pathCurrentlyOpened[0].setManaged(false);
                        floorBlock.getChildren().get(1).setVisible(true);
                        floorBlock.getChildren().get(1).setManaged(true);
                        pathCurrentlyOpened[0] = (VBox) floorBlock.getChildren().get(1);
                    }
                });

                currentFloorGrid.add(currentFloor, 0, 0);
                currentFloorGrid.add(seePathButton, 0, 1);
                currentFloorGrid.setPrefWidth(300);
                GridPane.setValignment(currentFloor, VPos.CENTER);
                GridPane.setHalignment(currentFloor, HPos.LEFT);
                currentFloorGrid.setAlignment(Pos.CENTER);
                currentFloorGrid.setPadding(new Insets(15, 0, 15, 0));
                currentFloor.setText(floor.getFirst()); //Floor 1:
                currentFloor.setPadding(new Insets(0, 0, 0, 10));

                currentFloorTitle.getChildren().add(line);
                currentFloorTitle.getChildren().add(currentFloorGrid);
                currentFloorTitle.setAlignment(Pos.CENTER);

                floorBlock.getChildren().add(currentFloorTitle);
                VBox pathsOnThisFloor = new VBox();

                // Only the first floor is opened  by default
                if (count != 0) {
                    pathsOnThisFloor.setVisible(false);
                    pathsOnThisFloor.setManaged(false);
                } else if(count == 0){ // ignore that, only happens once
                    pathCurrentlyOpened[0] = pathsOnThisFloor;
                    firstFloor = floor.getFirst();
                }
                count++;

                // VBox for the paths on this floor
                floorBlock.getChildren().get(0).setOnMouseClicked(e -> {

                    System.out.println(floor.getFirst());
                    if (floor.getFirst().contains("L1")){
                        loadL1();
                    } else if (floor.getFirst().contains("L2")){
                        loadL2();
                    } else if (floor.getFirst().contains("G")){
                        loadGround();
                    } else if (floor.getFirst().contains("1")){
                        loadF1();
                    } else if (floor.getFirst().contains("2")){
                        loadF2();
                    } else if (floor.getFirst().contains("3")){
                        loadF3();
                    }

                    if (floorBlock.getChildren().get(1).isVisible()) {
                        floorBlock.getChildren().get(1).setVisible(false);
                        floorBlock.getChildren().get(1).setManaged(false);
                    } else{
                        pathCurrentlyOpened[0].setVisible(false);
                        pathCurrentlyOpened[0].setManaged(false);
                        floorBlock.getChildren().get(1).setVisible(true);
                        floorBlock.getChildren().get(1).setManaged(true);
                        pathCurrentlyOpened[0] = (VBox) floorBlock.getChildren().get(1);
                    }
                });

                for (String s : floor) {
                    Label p = new Label();

                    if (s.contains("Take the stairs up to floor ")) {
                        HBox hbox = new HBox();
                        Image img = new Image("/edu/wpi/MochaManticores/images/upStairsDirectionIcon.png");
                        ImageView stairs = new ImageView(img);
                        stairs.setFitWidth(50);
                        stairs.setPreserveRatio(true);
                        p.setText(s);
                        p.setPadding(new Insets(10, 0, 10, 0));
                        p.setPrefWidth(Control.USE_COMPUTED_SIZE);
                        p.setWrapText(true);
                        hbox.getChildren().add(stairs);
                        hbox.getChildren().add(p);
                        hbox.setAlignment(Pos.CENTER_LEFT);
                        pathsOnThisFloor.getChildren().add(hbox);
                    } else if (s.contains("Take the elevator up to floor ")) {
                        HBox hbox = new HBox();
                        Image img = new Image("/edu/wpi/MochaManticores/images/elevatorDirectionIcon.png");
                        ImageView elevator = new ImageView(img);
                        elevator.setFitWidth(50);
                        elevator.setPreserveRatio(true);
                        p.setText(s);
                        p.setPadding(new Insets(10, 0, 10, 0));
                        p.setWrapText(true);
                        p.setPrefWidth(Control.USE_COMPUTED_SIZE);
                        hbox.getChildren().add(elevator);
                        hbox.getChildren().add(p);
                        hbox.setAlignment(Pos.CENTER_LEFT);
                        pathsOnThisFloor.getChildren().add(hbox);
                    } else if (s.contains("Take the stairs down to floor ")) {
                        HBox hbox = new HBox();
                        Image img = new Image("/edu/wpi/MochaManticores/images/downStairsDirectionIcon.png");
                        ImageView stairs = new ImageView(img);
                        stairs.setFitWidth(50);
                        stairs.setPreserveRatio(true);
                        p.setText(s);
                        p.setPadding(new Insets(10, 0, 10, 0));
                        p.setWrapText(true);
                        p.setPrefWidth(Control.USE_COMPUTED_SIZE);
                        hbox.getChildren().add(stairs);
                        hbox.getChildren().add(p);
                        hbox.setAlignment(Pos.CENTER_LEFT);
                        pathsOnThisFloor.getChildren().add(hbox);
                    } else if (s.contains("Take the elevator down to floor ")) {
                        HBox hbox = new HBox();
                        Image img = new Image("/edu/wpi/MochaManticores/images/elevatorDirectionIcon.png");
                        ImageView elevator = new ImageView(img);
                        elevator.setFitWidth(50);
                        elevator.setPreserveRatio(true);
                        p.setText(s);
                        p.setPadding(new Insets(10, 0, 10, 0));
                        p.setWrapText(true);
                        p.setPrefWidth(Control.USE_COMPUTED_SIZE);
                        hbox.getChildren().add(elevator);
                        hbox.getChildren().add(p);
                        hbox.setAlignment(Pos.CENTER_LEFT);
                        pathsOnThisFloor.getChildren().add(hbox);
                    } else if (s.contains("Head straight until you reach ")) {
                        HBox hbox = new HBox();
                        Image img = new Image("/edu/wpi/MochaManticores/images/upArrowDirection.png");
                        ImageView arrowUp = new ImageView(img);
                        arrowUp.setFitWidth(50);
                        arrowUp.setPreserveRatio(true);
                        p.setText(s);
                        p.setPadding(new Insets(10, 0, 10, 0));
                        p.setWrapText(true);
                        p.setPrefWidth(Control.USE_COMPUTED_SIZE);
                        hbox.getChildren().add(arrowUp);
                        hbox.getChildren().add(p);
                        hbox.setAlignment(Pos.CENTER_LEFT);
                        pathsOnThisFloor.getChildren().add(hbox);
                    } else if (s.contains("Then turn left")) {
                        HBox hbox = new HBox();
                        Image img = new Image("/edu/wpi/MochaManticores/images/upArrowDirection.png");
                        ImageView arrowUp = new ImageView(img);
                        arrowUp.setPreserveRatio(true);
                        arrowUp.setRotate(-90);
                        arrowUp.setFitWidth(50);
                        p.setText(s);
                        p.setPadding(new Insets(10, 0, 10, 0));
                        p.setWrapText(true);
                        p.setPrefWidth(Control.USE_COMPUTED_SIZE);
                        hbox.getChildren().add(arrowUp);
                        hbox.getChildren().add(p);
                        hbox.setAlignment(Pos.CENTER_LEFT);
                        pathsOnThisFloor.getChildren().add(hbox);
                    } else if (s.contains("Then turn right")) {
                        HBox hbox = new HBox();
                        Image img = new Image("/edu/wpi/MochaManticores/images/upArrowDirection.png");
                        ImageView arrowUp = new ImageView(img);
                        arrowUp.setPreserveRatio(true);
                        arrowUp.setRotate(90);
                        arrowUp.setFitWidth(50);
                        p.setText(s);
                        p.setPadding(new Insets(10, 0, 10, 0));
                        p.setWrapText(true);
                        p.setPrefWidth(Control.USE_COMPUTED_SIZE);
                        hbox.getChildren().add(arrowUp);
                        hbox.getChildren().add(p);
                        hbox.setAlignment(Pos.CENTER_LEFT);
                        pathsOnThisFloor.getChildren().add(hbox);
                    }

                }

                pathsOnThisFloor.setPadding(new Insets(0, 0, 20, 0));

                floorBlock.getChildren().add(pathsOnThisFloor);
                dirVBOX.getChildren().add(floorBlock);

                for (node n : pitStops) {
                    n.resetFill();
                }

                updateFields();
            }

            System.out.println(dirVBOX.getChildren().get(0));


            if (firstFloor.contains("L1")){
                loadL1();
            } else if (firstFloor.contains("L2")){
                loadL2();
            } else if (firstFloor.contains("G")){
                loadGround();
            } else if (firstFloor.contains("1")){
                loadF1();
            } else if (firstFloor.contains("2")){
                loadF2();
            } else if (firstFloor.contains("3")){
                loadF3();
            }

            drawNodes();
            pitStops.getFirst().setStart(true);
            pitStops.getLast().setEnd(true);

            drawNodes();
            //TODO:change start and end node colors

//            pitStops.getFirst().c.setFill(Color.GREEN);
//            pitStops.getFirst().c.setRadius(4);
//
//
//            pitStops.getLast().c.setFill(Color.RED);
//            pitStops.getLast().c.setRadius(4);


            double CenterX = pitStops.getFirst().getxCoord();
            double CenterY = pitStops.getFirst().getyCoord();


//            if(CenterX < mapWindow.getFitWidth()/2){
//                CenterX = (mapWindow.getFitWidth()/2) - CenterX;
//                CenterX *=-1;
//            }else{
//                CenterX -= mapWindow.getFitWidth()/2;
//            }
//            if(CenterY < mapWindow.getFitHeight()/2){
//                CenterY = (mapWindow.getFitHeight()/2) - CenterY;
//                CenterY *=-1;
//            }else{
//                CenterY -= mapWindow.getFitHeight()/2;
//            }
            System.out.printf("X:%f Y:%f\n", CenterX, CenterY);

            pitStops = new LinkedList<>();
            double xRatio = 5000 / mapWindow.getFitWidth();
            double yRatio = 3400 / mapWindow.getFitHeight();
            directionPane.setContent(dirVBOX);
            //loadDialog(pathToTake); // calling the dialog pane with the path
            panAndZoomPane.centerOnPoint(CenterX, CenterY);

            //panAndZoomPane.centerOnPoint(mapWindow.getFitWidth()/2,mapWindow.getFitHeight()/2);
        }
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

    public void clearLines(ActionEvent e) {
        savedRoute.clear();
        pitStops.clear();
        drawNodes();
        updateFields();
        dirVBOX.getChildren().clear();
        panAndZoomPane.resetZoom();
    }

    public void drawNodes() {
        nodePane.getChildren().clear();
        double xRatio = 5000 / mapWindow.getFitWidth();
        double yRatio = 3400 / mapWindow.getFitHeight();
        Iterator<NodeSuper> mapIter = MapSuper.getMap().values().iterator();
        for (int i = 0; i < MapSuper.getMap().size(); i++) {
            NodeSuper n = mapIter.next();
            System.out.println(n.getFloor() + ":" + selectedFloor);
            if (n.getFloor().equals(selectedFloor)) {
                Circle spot = new Circle(n.getXcoord() / xRatio, n.getYcoord() / yRatio, 3, Color.WHITE);
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
                        mouseOverNode(e, 4);
                    }
                };
                EventHandler<MouseEvent> small = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        mouseOverNode(e, 3);
                    }
                };
                spot.setOnMouseClicked(highlight);
                spot.setOnMouseEntered(large);
                spot.setOnMouseExited(small);
                spot.hoverProperty().addListener((observable, oldValue, newVaue) -> {
                    if (newVaue) {
                        Tooltip.install(spot, new Tooltip(n.getLongName()));
                    }
                });
                node nodeToAdd =  new node(spot, n.getID());

                nodeToAdd.startProperty().addListener((observable, oldValue, newValue) ->{
                    if(newValue){
                        System.out.println(nodeToAdd.getNodeID()+" GREEN");
                        nodeToAdd.c.setFill(Color.GREEN);
                        nodeToAdd.c.setStyle("-fx-background-color: GREEN;");
                    }
                });

                nodeToAdd.endProperty().addListener((observable, oldValue, newValue) ->{
                    if(newValue){
                        System.out.println(nodeToAdd.getNodeID()+" RED");
                        nodeToAdd.c.setFill(Color.RED);
                        nodeToAdd.c.setStyle("-fx-background-color: RED;");
                    }
                });
                nodes.put(n.getID(),nodeToAdd);
                nodePane.getChildren().addAll(nodes.get(n.getID()).c);
            }
        }
        LinkedList<Line> lines = new LinkedList();
        for (int i = 0; i < savedRoute.size() - 1; i++) {
            NodeSuper curNode = null;
            NodeSuper endNode = null;
            try {
                curNode = DatabaseManager.getNode(savedRoute.get(i));
                endNode = DatabaseManager.getNode(savedRoute.get(i + 1));
            } catch (InvalidElementException e) {
                e.printStackTrace();
            }

            Line edge = new Line(curNode.getXcoord() / xRatio, curNode.getYcoord() / yRatio, endNode.getXcoord() / xRatio, endNode.getYcoord() / yRatio);
            //Rectangle edgeRectangle = new Rectangle(, );
            edge.setStrokeWidth(4);
            if (curNode.getFloor().equals(selectedFloor)) {
                //edgeRectangle.setFill(new ImagePattern(new Image("edu/wpi/MochaManticores/images/02_thesecondfloor.png")));
                //edge.setFill(new ImagePattern(new Image("edu/wpi/MochaManticores/images/02_thesecondfloor.png")));
                Image map = new Image("edu/wpi/MochaManticores/images/tenor.gif");
                edge.setStroke(new ImagePattern(map));
            } else {
                edge.setStroke(Color.GREY);
                edge.getStrokeDashArray().addAll(5d, 15d);
            }
            NodeSuper finalCurNode = curNode;
            NodeSuper finalEndNode = endNode;

            edge.hoverProperty().addListener((observable, oldValue, newVaue) -> {
                if (newVaue) {
                    Tooltip.install(edge, new Tooltip(finalCurNode.getID() + "_" + finalEndNode.getID()));
                }
            });

            lines.add(edge);
        }

        nodePane.getChildren().addAll(lines);
        for (Line l : lines) {
            l.toBack();
        }
    }

    public void highlightNode(MouseEvent e) {
        Circle src = ((Circle) e.getSource());
        src.setFill(Color.valueOf("#0F4B91"));

        Iterator<node> iter = nodes.values().iterator();

        for (int i = 0; i < nodes.size(); i++) {
            node n = iter.next();
            if (n.c.equals(src)) {
                if (n.isHighlighted()) {
                    src.setFill(Color.WHITE);
                    n.setHighlighted(false);

                    pitStops.remove(n);
                } else {
                    src.setFill(Color.valueOf("#0F4B91"));
                    n.setHighlighted(true);
                    pitStops.add(n);
                    //if (fieldIndex >= fields.size()){
                    //    fieldIndex = addPitstopField();
                    //}
                    //try {
                    //    fields.get(fieldIndex).getEditor().setText(DatabaseManager.getNode(n.getNodeID()).getLongName());
                    //} catch (InvalidElementException invalidElementException) {
                    //    invalidElementException.printStackTrace();
                    //}
                    //fieldIndex++;

                }
                updateFields();
            }
        }
    }

    public void updateFields() {
        if (pitStops.size() > fields.size()) {
            System.out.println("stops > fields");
            for (int i = 0; i < pitStops.size() - fields.size(); i++) {
                addPitstopField();
            }
        } else if (pitStops.size() < fields.size()) {
            System.out.println("stops < fields");
            for (int i = 0; i < fields.size() - pitStops.size(); i++) {
                if (fields.size() > 2) {
                    removePitstopField();
                }

            }
        }
        for (JFXTextField f : fields) {
            f.setText("");
        }
        fields.get(0).setPromptText("Starting Location");
        fields.get(fields.size() - 1).setPromptText("Ending Location");
        for (int i = 0; i < pitStops.size(); i++) {
            fields.get(i).setText(pitStops.get(i).getNodeID());
        }

    }

    private void removePitstopField() {
        fields.remove(1);
        textFieldGroup.getChildren().remove(1);

    }

    public void mouseOverNode(MouseEvent e, double radius) {
        Circle src = ((Circle) e.getSource());
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

    public void saveUserParking() throws InvalidElementException, DestinationNotAccessibleException {
        if (parkingButton.getText().equals("My spot") && !DatabaseManager.getEmployee(App.getCurrentUsername()).getParkingSpace().equals("Parking")) {
            Iterator<node> mapIter = nodes.values().iterator();
            node n = null;
            for (int i = 0; i < nodes.size(); i++) {
                n = mapIter.next();
                if (n.getNodeID().equals(DatabaseManager.getEmployee(App.getCurrentUsername()).getParkingSpace())) {
                    break;
                }
            }
            pitStops.add(n);
            findPath();
            return;
        }
        try {
            Employee temp = DatabaseManager.getEmployee(App.getCurrentUsername());
            String nodeID = pitStops.getLast().getNodeID();
            if (DatabaseManager.getNode(nodeID).getType().equals("PARK")) {
                temp.setParkingSpace(nodeID);
                DatabaseManager.modEmployee(temp.getUsername(), temp);

                loadYesNoDialog(dialogPane, "Parking Spot: " + nodeID + " saved to your user!");

                System.out.println(temp.getUsername() + "parking space has been set to: " + nodeID);
                parkingButton.setText("My spot");
            }
        } catch (InvalidElementException e) {
            System.out.println("no user in database to save parking info to");
        }
    }

    public String convertNodeSuperFloor(String nsFloor) {
        switch (nsFloor) {
            case ("2"):
                return "F2";
            case ("1"):
                return "F1";
            case ("3"):
                return "F3";
            case ("L1"):
                return "LL1";
            case ("L2"):
                return "LL2";
            case ("G"):
                return "G";
            default:
                return nsFloor;
        }
    }

    public void setAutoComplete(JFXTextField test, List<nodeNameWrapper> items) {
        JFXAutoCompletePopup<mapPage.nodeNameWrapper> autoCompletePopup = new JFXAutoCompletePopup<>();
        autoCompletePopup.getSuggestions().addAll(items);

        autoCompletePopup.setSelectionHandler(event -> {
            test.setText(event.getObject().toString());

            // you can do other actions here when text completed

            node n = nodes.get(event.getObject().ID);
            if (n == null) {
                try {
                    NodeSuper ns = DatabaseManager.getNode(event.getObject().ID);
                    floorSelector.getSelectionModel().select(convertNodeSuperFloor(ns.getFloor()));
                    selectFloor();
                    drawNodes();
                } catch (InvalidElementException e) {
                    e.printStackTrace();
                }
            }
            n = nodes.get(event.getObject().ID);
            n.c.setFill(Color.valueOf("#0F4B91"));
            n.setHighlighted(true);
            pitStops.add(n);

        });

        // filtering options
        test.textProperty().addListener(observable -> {
            autoCompletePopup.filter(string -> string.toString().toLowerCase().contains(test.getText().toLowerCase()));
            if (autoCompletePopup.getFilteredSuggestions().isEmpty() || test.getText().isEmpty()) {
                autoCompletePopup.hide();
                // if you remove textField.getText.isEmpty() when text field is empty it suggests all options
                // so you can choose
            } else {
                autoCompletePopup.show(test);
            }
        });
    }
}
