package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.*;
import edu.wpi.MochaManticores.Algorithms.AStar2;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.Editors.mapEdit;
import edu.wpi.MochaManticores.Nodes.EdgeMapSuper;
import edu.wpi.MochaManticores.Nodes.EdgeSuper;
import edu.wpi.MochaManticores.Nodes.MapSuper;
import edu.wpi.MochaManticores.Nodes.NodeSuper;
import edu.wpi.MochaManticores.database.EdgeManager;
import edu.wpi.MochaManticores.database.Mdb;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import edu.wpi.MochaManticores.views.nodePage;
import edu.wpi.MochaManticores.views.edgesPage;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class mapEditor extends SceneController {

    @FXML
    public JFXTextField xCoordField;
    @FXML
    public JFXTextField yCoordField;
    @FXML
    public JFXTextField bldgField;
    @FXML
    public JFXTextField floorField;
    @FXML
    public JFXTextField longNameField;
    @FXML
    public JFXTextField shortNameField;
    @FXML
    public Label edgeIDField;
    @FXML
    public JFXTextField startNodeID;
    @FXML
    public JFXTextField endNodeID;
    @FXML
    public VBox contextBox;
    @FXML
    public Group addNode;
    @FXML
    public Group addEdge;
    @FXML
    public Group deleteNode;
    @FXML
    public Group deleteEdge;
    @FXML
    private JFXTextField nodeTypeField;

    public class node {
        Circle c;
        String nodeID;
        double xCoord;
        double yCoord;
        NodeSuper nodeRef;
        boolean highlighted;
        List<Line> edges;

        public node(Circle c, String nodeID, NodeSuper nodeRef) {
            this.c = c;
            this.nodeID = nodeID;
            this.nodeRef = nodeRef;
            xCoord = c.getCenterX();
            yCoord = c.getCenterY();
            this.highlighted = false;
            this.edges = new ArrayList<>();
        }

        public List<Line> getEdges() {
            return edges;
        }

        public void setEdges(List<Line> edges) {
            this.edges = edges;
        }

        public void addEdge(Line l){
            this.edges.add(l);
        }

        public void removeEdge(Line l){
            this.edges.remove(l);
        }

        public NodeSuper getNodeRef() {
            return nodeRef;
        }

        public void setNodeRef(NodeSuper nodeRef) {
            this.nodeRef = nodeRef;
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

        public void resetFill() {
            c.setFill(Color.WHITE);
            c.setStrokeWidth(1);
            c.setStroke(Color.valueOf("#FF6B35"));
        }

        public boolean isHighlighted() {
            return highlighted;
        }

        public void setHighlighted(boolean highlighted) {
            this.highlighted = highlighted;
        }

        public void update(NodeSuper newNode){
            this.nodeID = newNode.getID();
            this.xCoord = newNode.getXcoord();
            this.yCoord = newNode.getYcoord();
            nodeRef = MapSuper.getMap().get(newNode.getID());
        }
    }

    public class edge {
        Line l;
        String edgeID;
        String startID;
        String endID;
        double x1;
        double x2;
        double y1;
        double y2;
        boolean highlighted;
        //EdgeSuper edgeRef;

        public edge(Line l, String edgeID, String startID, String endID) {
            this.l = l;
            this.edgeID = edgeID;
            this.startID = startID;
            this.endID = endID;
            //this.edgeRef = edgeRef;
            this.x1 = l.getStartX();
            this.x2 = l.getEndX();
            this.y1 = l.getStartY();
            this.y2 = l.getEndY();
            this.highlighted = false;
        }

        public Line getL() {
            return l;
        }

        public void setL(Line l) {
            this.l = l;
        }

        public String getEdgeID() {
            return edgeID;
        }

        public void setEdgeID(String edgeID) {
            this.edgeID = edgeID;
        }

        public double getX1() {
            return x1;
        }

        public void setX1(double x1) {
            this.x1 = x1;
        }

        public double getX2() {
            return x2;
        }

        public void setX2(double x2) {
            this.x2 = x2;
        }

        public double getY1() {
            return y1;
        }

        public void setY1(double y1) {
            this.y1 = y1;
        }

        public double getY2() {
            return y2;
        }

        public void setY2(double y2) {
            this.y2 = y2;
        }

        public boolean isHighlighted() {
            return highlighted;
        }

        public void setHighlighted(boolean highlighted) {
            this.highlighted = highlighted;
        }
    }

    private HashMap<String, node> nodes = new HashMap();

    private HashMap<String, edge> edges = new HashMap<String, edge>();

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
    private VBox nodeInfoBox;

    @FXML
    private VBox edgeInfoBox;

    @FXML
    private GridPane innerMapGrid;

    @FXML
    private JFXComboBox floorSelector;

    @FXML
    private StackPane dialogPane;

    @FXML
    private JFXTextField nodeIDField;

    private String location = "edu/wpi/MochaManticores/images/";

    private String selectedFloor = "";

    @FXML
    private JFXButton nodeSubmit;
    @FXML
    private JFXButton edgeSubmit;
    @FXML
    private JFXButton cancelChanges;
    @FXML
    private JFXButton cancelChanges1;

    private boolean nodeClicked = false;
    private Node prevCircle;

    private mapEdit editor = new mapEdit();
    private double[] newCoords = new double[2];

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

        mapWindow.setPreserveRatio(false);


        //loadL1();

        floorSelector.getItems().addAll("LL1",
                "LL2",
                "G",
                "F1",
                "F2",
                "F3");

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mapWindow.setFitWidth(App.getPrimaryStage().getWidth() - mapStack.localToScene(mapStack.getBoundsInLocal()).getMinX() - 50);
                mapWindow.setFitHeight(App.getPrimaryStage().getHeight() - mapStack.localToScene(mapStack.getBoundsInLocal()).getMinY() - 50);
            }
        });

        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                //zoomImg(e);
            }
        };
        mapWindow.setOnMouseMoved(eventHandler);

        App.getPrimaryStage().widthProperty().addListener((obs, oldVal, newVal) -> {
            mapWindow.setFitWidth(App.getPrimaryStage().getWidth() - mapStack.localToScene(mapStack.getBoundsInLocal()).getMinX() - 50);
            drawNodes();
            drawEdges();
        });

        App.getPrimaryStage().heightProperty().addListener((obs, oldVal, newVal) -> {
            mapWindow.setFitHeight(App.getPrimaryStage().getHeight() - mapStack.localToScene(mapStack.getBoundsInLocal()).getMinY() - 50);
            drawNodes();
            drawEdges();
        });

        drawNodes();
        drawEdges();

        // Setting button handlers
        EventHandler<ActionEvent> handleSubmitNode = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                submitNodeEdit();
            }

        };

        EventHandler<ActionEvent> handleSubmitEdge = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                submitEdgeEdit();
            }

        };

        nodeSubmit.setOnAction(handleSubmitNode);
        edgeSubmit.setOnAction(handleSubmitEdge);

        addNode.managedProperty().bind(addNode.visibleProperty());
        deleteNode.managedProperty().bind(deleteNode.visibleProperty());
        addEdge.managedProperty().bind(addEdge.visibleProperty());
        deleteEdge.managedProperty().bind(deleteEdge.visibleProperty());

        addNode.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                edgeInfoBox.setVisible(false);
                nodeInfoBox.setVisible(true);
                edgeInfoBox.toBack();

                contextBox.toBack();
                contextBox.setVisible(false);
                nodeClicked = false;

                double xRatio = 5000 / mapWindow.getFitWidth();
                double yRatio = 3400 / mapWindow.getFitHeight();

                int newX = (int) (newCoords[0] * xRatio);
                int newY = (int) (newCoords[1] * yRatio);

                nodeIDField.setText("");
                xCoordField.setText(Integer.toString(newX));
                yCoordField.setText(Integer.toString(newY));
                bldgField.setText("");
                floorField.setText(selectedFloor);
                nodeTypeField.setText("");
                longNameField.setText("");
                shortNameField.setText("");

                Circle spot = new Circle(newCoords[0], newCoords[1], 4, Color.WHITE);
                spot.setStrokeWidth(1);
                spot.setStroke(Color.valueOf("#FF6B35"));
                EventHandler<MouseEvent> highlight = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        nodeClicked = !nodeClicked;
                        if(e.getButton().equals(MouseButton.PRIMARY)){
                            highlightNode(e);
                        } else if (e.getButton() == MouseButton.SECONDARY){
                            contextBox.toFront();
                            contextBox.setVisible(true);
                            addNode.setVisible(false);
                            deleteNode.setVisible(true);
                            addEdge.setVisible(true);
                            deleteEdge.setVisible(false);

                            contextBox.relocate(e.getSceneX(), e.getSceneY());
                        }
                        // Populate textual editing field
                    }
                };
                EventHandler<MouseEvent> large = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        mouseOverNode(e, 5);
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

                nodePane.getChildren().addAll(spot);

            }
        });
        deleteNode.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                edgeInfoBox.setVisible(false);
                nodeInfoBox.setVisible(true);
                edgeInfoBox.toBack();

                contextBox.toBack();
                contextBox.setVisible(false);
                nodeClicked = false;

                Iterator<node> iter = nodes.values().iterator();
                for (int i = 0; i < nodes.size(); i++){
                    node n = iter.next();
                    if (n.c.equals(prevCircle)){
                        try {
                            editor.deleteNode(n.getNodeID());
                            nodePane.getChildren().remove(prevCircle);
                        } catch (SQLException | FileNotFoundException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                }
            }
        });


        EventHandler<MouseEvent> addNodeBox = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.SECONDARY && !nodeClicked){
                    contextBox.toFront();
                    contextBox.setVisible(true);
                    addNode.setVisible(true);
                    deleteNode.setVisible(false);
                    addEdge.setVisible(false);
                    deleteEdge.setVisible(false);

                    contextBox.relocate(event.getSceneX(), event.getSceneY());
                    newCoords[0] = event.getX(); newCoords[1] = event.getY();

                    //System.out.println(contextBox.getLayoutX() + " " + contextBox.getLayoutY());
                } else if (event.getButton() == MouseButton.PRIMARY){
                    contextBox.toBack();
                    contextBox.setVisible(false);
                    nodeClicked = false;
                }
            }
        };

        nodePane.setOnMouseClicked(addNodeBox);

        //Initializing the dialog pane
        dialogPane.toBack();

//        floorSelector.getSelectionModel().select(3);
//        loadF1();

    }

    public void submitEdgeEdit(){
        EdgeSuper editedEdge = null;
        String selectedID;
        if (!editor.checkInput(Arrays.asList(edgeIDField.getText(), startNodeID.getText(), endNodeID.getText()))) { // IF fields are blank, submit error
            mapEditor.super.loadErrorDialog(dialogPane, "Please do not leave fields blank!");
        } else {
            EdgeSuper oldEdge = EdgeMapSuper.getMap().get(edgeIDField.getText());
            if (oldEdge != null) {
                editedEdge = new EdgeSuper(
                        edgeIDField.getText(),
                        startNodeID.getText(),
                        endNodeID.getText());
                selectedID = edgeIDField.getText();
                try {
                    editor.submitEditEdgeToDB(editedEdge, selectedID, oldEdge.getStartingNode(), oldEdge.getEndingNode());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else { // New Edge
                editedEdge = new EdgeSuper(
                        edgeIDField.getText(),
                        startNodeID.getText(),
                        endNodeID.getText());
                selectedID = "";
                try {
                    editor.submitEditEdgeToDB(editedEdge, selectedID, "", "");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        edgeIDField.setText(startNodeID.getText()+"_"+endNodeID.getText());
        selectFloor();
    }

    public void submitNodeEdit(){
        NodeSuper editedNode;
        String selectedID;
        if (!editor.checkInput(Arrays.asList(xCoordField.getText(),
                yCoordField.getText(),
                floorField.getText(),
                bldgField.getText(),
                longNameField.getText(),
                shortNameField.getText(),
                nodeIDField.getText(),
                nodeTypeField.getText()))) { // IF fields are blank, submit error
            loadErrorDialog(dialogPane, "Please do not leave fields blank!");
        } else if (nodeIDField.getLength() != 10) {
            loadErrorDialog(dialogPane, "Node IDs must be of length 10!");
        } else {
            if (MapSuper.getMap().get(nodeIDField.getText()) != null) {
                editedNode = new NodeSuper(
                        Integer.parseInt(xCoordField.getText()),
                        Integer.parseInt(yCoordField.getText()),
                        floorField.getText(),
                        bldgField.getText(),
                        longNameField.getText(),
                        shortNameField.getText(),
                        nodeIDField.getText(),
                        nodeTypeField.getText(),
                        MapSuper.getMap().get(nodeIDField.getText()).getVertextList());
                selectedID = nodeIDField.getText();
                try {
                    editor.submitEditNodeToDB(editedNode, selectedID);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else { // New Node
                editedNode = new NodeSuper(Integer.parseInt(xCoordField.getText()),
                        Integer.parseInt(yCoordField.getText()),
                        floorField.getText(),
                        bldgField.getText(),
                        longNameField.getText(),
                        shortNameField.getText(),
                        nodeIDField.getText(),
                        nodeTypeField.getText(),
                        null);
                selectedID = "";
                try {
                    editor.submitEditNodeToDB(editedNode, selectedID);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        selectFloor();
    }


    public boolean checkInput(){
        return  editor.checkInput(Arrays.asList(xCoordField.getText(),
                yCoordField.getText(),
                floorField.getText(),
                bldgField.getText(),
                longNameField.getText(),
                shortNameField.getText(),
                nodeIDField.getText(),
                "TEST"));
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
                ;
        }
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
        drawEdges();

    }

    public void loadL2() {
        locationTitle.setText("Lower Level 2");
        setSelectedFloor("L2");

        Image img = new Image(location + "00_thelowerlevel2.png");
        setZoom(img, 0, 0, noZoom);
        drawNodes();
        drawEdges();

    }

    public void loadGround() {
        locationTitle.setText("Ground Floor");
        setSelectedFloor("G");

        Image img = new Image(location + "00_thegroundfloor.png");
        setZoom(img, 0, 0, noZoom);
        drawNodes();
        drawEdges();

    }

    public void loadF1() {
        locationTitle.setText("Floor 1");
        setSelectedFloor("1");

        Image img = new Image(location + "01_thefirstfloor.png");
        setZoom(img, 0, 0, noZoom);
        drawNodes();
        drawEdges();

    }

    public void loadF2() {
        locationTitle.setText("Floor 2");
        setSelectedFloor("2");

        Image img = new Image(location + "02_thesecondfloor.png");
        setZoom(img, 0, 0, noZoom);
        drawNodes();
        drawEdges();

    }

    public void loadF3() {
        locationTitle.setText("Floor 3");
        setSelectedFloor("L3");

        Image img = new Image(location + "03_thethirdfloor.png");
        setZoom(img, 0, 0, noZoom);
        drawNodes();
        drawEdges();
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
        for (node n :
                pitStops) {
            stops.add(MapSuper.getMap().get(n.nodeID));
        }
        if (pitStops.isEmpty()) {
            pathToTake.append("Please select at least one node");
        } else {

            LinkedList<String> path = star.multiStopRoute(stops);
            System.out.println(path);
            for (String str :
                    path) {
                System.out.printf("\n%s\n|\n", MapSuper.getMap().get(str).getLongName());
                pathToTake.append(MapSuper.getMap().get(str).getLongName()).append("\n|\n");//appending the paths
            }
            LinkedList<Line> lines = new LinkedList();

            for (int i = 0; i < path.size(); i++) {
                try {
                    node start = nodes.get(path.get(i));
                    node end = nodes.get(path.get(i + 1));
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
        nodes.clear();
        double xRatio = 5000 / mapWindow.getFitWidth();
        double yRatio = 3400 / mapWindow.getFitHeight();
        Iterator<NodeSuper> mapIter = MapSuper.getMap().values().iterator();
        for (int i = 0; i < MapSuper.getMap().size(); i++) {
            NodeSuper n = mapIter.next();
            if (n.getFloor().equals(selectedFloor)) {
                Circle spot = new Circle(n.getXcoord() / xRatio, n.getYcoord() / yRatio, 4, Color.WHITE);
                spot.setStrokeWidth(1);
                spot.setStroke(Color.valueOf("#FF6B35"));
                EventHandler<MouseEvent> highlight = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        nodeClicked = !nodeClicked;
                        if(e.getButton().equals(MouseButton.PRIMARY)){
                            highlightNode(e);
                        } else if (e.getButton() == MouseButton.SECONDARY){
                            contextBox.toFront();
                            contextBox.setVisible(true);
                            addNode.setVisible(false);
                            deleteNode.setVisible(true);
                            addEdge.setVisible(true);
                            deleteEdge.setVisible(false);

                            contextBox.relocate(e.getSceneX(), e.getSceneY());

                            prevCircle = (Node) e.getSource();
                        }
                        // Populate textual editing field
                    }
                };
                EventHandler<MouseEvent> large = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        mouseOverNode(e, 5);
                    }
                };
                EventHandler<MouseEvent> small = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        mouseOverNode(e, 3);
                    }
                };
                EventHandler<MouseEvent> drag = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Circle src = (Circle) event.getSource();
                        Point2D p = nodePane.sceneToLocal(event.getSceneX(), event.getSceneY());
                        double newX = p.getX();
                        double newY = p.getY();
                        double xRatio = 5000 / mapWindow.getFitWidth();
                        double yRatio = 3400 / mapWindow.getFitHeight();

                        System.out.println(event.getSceneX() + " " + event.getSceneY());
                        System.out.println(newX + " " + newY);
                        System.out.println(event.getX() + " " + event.getY());


                        src.setCenterX(newX);
                        src.setCenterY(newY);

                        Iterator<node> iter = nodes.values().iterator();
                        for(int i =0; i < nodes.size(); i ++){
                            node n = iter.next();
                            if (n.c.equals(src)){


                                //                                try {
//                                    editor.submitEditNodeToDB(new NodeSuper((int)(newX * xRatio),
//                                            (int) (newY * yRatio),
//                                            n.getNodeRef().getFloor(),
//                                            n.getNodeRef().getBuilding(),
//                                            n.getNodeRef().getLongName(),
//                                            n.getNodeRef().getShortName(),
//                                            n.getNodeRef().getID(),
//                                            n.getNodeRef().getType(),
//                                            n.getNodeRef().getVertextList()), n.getNodeID());
//                                } catch (SQLException | FileNotFoundException throwables) {
//                                    throwables.printStackTrace();
//                                }

                            }
                        }
                    }
                };
                spot.setOnMouseDragged(drag);
                spot.setOnMouseClicked(highlight);
                spot.setOnMouseEntered(large);
                spot.setOnMouseExited(small);
                nodes.put(n.getID(), new node(spot, n.getID(), n));
                //drawEdges2(nodes.get(n.getID()));
                nodePane.getChildren().addAll(nodes.get(n.getID()).c);
            }
        }
    }

    private void drawEdges(){
        double xRatio = 5000 / mapWindow.getFitWidth();
        double yRatio = 3400 / mapWindow.getFitHeight();

        Iterator<EdgeSuper> allEdges = EdgeMapSuper.getMap().values().iterator();

        HashMap<String, NodeSuper> allNodes = MapSuper.getMap();
        while (allEdges.hasNext()){
            EdgeSuper e = allEdges.next();

            NodeSuper start = allNodes.get(e.getStartingNode());
            NodeSuper end = allNodes.get(e.getEndingNode());
            if (start.getFloor().equals(selectedFloor) || end.getFloor().equals(selectedFloor)){
                //System.out.println(e);
                Line l = new Line(
                        start.getXcoord() / xRatio,
                        start.getYcoord() / yRatio,
                        end.getXcoord() / xRatio,
                        end.getYcoord() / yRatio);
                node nS = nodes.get(start.getID());
                node nE = nodes.get(end.getID());
                if (nS != null){
                    nS.addEdge(l);
                    l.startXProperty().bind(nS.c.centerXProperty());
                    l.startYProperty().bind(nS.c.centerYProperty());
                }
                if (nE != null) {
                    nE.addEdge(l);
                    l.endXProperty().bind(nE.c.centerXProperty());
                    l.endYProperty().bind(nE.c.centerYProperty());
                }


                if (!start.getFloor().equals(end.getFloor())){
                    l.setStroke(Color.RED);
                } else {
                    l.setStroke(Color.BLACK);
                }
                l.setStrokeWidth(2);
                edges.put(e.edgeID, new edge(l, e.edgeID, start.getID(), end.getID()));

                EventHandler<MouseEvent> highlight = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if(e.getButton().equals(MouseButton.PRIMARY)){
                            highlightEdge(e);
                        } else if (e.getButton() == MouseButton.SECONDARY){
                            contextBox.toFront();
                            contextBox.setVisible(true);
                            addNode.setVisible(false);
                            deleteNode.setVisible(false);
                            addEdge.setVisible(false);
                            deleteEdge.setVisible(true);

                            contextBox.relocate(e.getSceneX(), e.getSceneY());
                        }
                        // Populate textual editing field
                    }
                };
                EventHandler<MouseEvent> bold = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        mouseOverEdge(e, 4);
                    }
                };

                EventHandler<MouseEvent> unbold = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        mouseOverEdge(e, 2);
                    }
                };
                l.setOnMouseClicked(highlight);
                l.setOnMouseEntered(bold);
                l.setOnMouseExited(unbold);

                nodePane.getChildren().addAll(l);
                l.toBack();
            }
        }

    }

    private void drawEdges2(node node) {
        double xRatio = 5000 / mapWindow.getFitWidth();
        double yRatio = 3400 / mapWindow.getFitHeight();
        HashMap<String, NodeSuper> allNodes = MapSuper.getMap();
        for (String s : node.getNodeRef().getNeighbors()) {
            NodeSuper neigh = allNodes.get(s);
            double startX = node.getxCoord();
            double startY = node.getyCoord();
            double endX = neigh.getXcoord() / xRatio;
            double endY = neigh.getYcoord() / yRatio;
            Line l = new Line(startX, startY, endX, endY);
            l.startXProperty().bind(node.c.centerXProperty());
            l.startYProperty().bind(node.c.centerYProperty());
            if (!node.getNodeRef().getFloor().equals(neigh.getFloor())) {
                l.setStroke(Color.RED);
            } else {
                l.setStroke(Color.BLACK);
            }
            l.setStrokeWidth(2);
            String edgeID = node.getNodeID() + "_" + neigh.getID();
            edges.put(edgeID, new edge(l, edgeID, node.getNodeID(), neigh.getID()));
            node.addEdge(l);

            EventHandler<MouseEvent> highlight = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    if(e.getButton().equals(MouseButton.PRIMARY)){
                        highlightEdge(e);
                    } else if (e.getButton() == MouseButton.SECONDARY){
                        contextBox.toFront();
                        contextBox.setVisible(true);
                        addNode.setVisible(false);
                        deleteNode.setVisible(false);
                        addEdge.setVisible(false);
                        deleteEdge.setVisible(true);

                        contextBox.relocate(e.getSceneX(), e.getSceneY());
                    }
                    // Populate textual editing field
                }
            };
            EventHandler<MouseEvent> bold = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    mouseOverEdge(e, 4);
                }
            };

            EventHandler<MouseEvent> unbold = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    mouseOverEdge(e, 2);
                }
            };
            l.setOnMouseClicked(highlight);
            l.setOnMouseEntered(bold);
            l.setOnMouseExited(unbold);
            nodePane.getChildren().addAll(l);
        }


    }

    public void mouseOverEdge(MouseEvent e, double stroke){
        Line src = (Line) e.getSource();
        Iterator<edge> iter = edges.values().iterator();
        for (int i = 0; i < edges.size(); i++) {
            edge ed = iter.next();
            if(ed.l.equals(src)){
                ed.l.setStrokeWidth(stroke);
            }
        }
    }

    public void highlightNode(MouseEvent e) {

        edgeInfoBox.setVisible(false);
        nodeInfoBox.setVisible(true);
        edgeInfoBox.toBack();

        Circle src = ((Circle) e.getSource());
        if (src.getFill().equals(Color.valueOf("#0F4B91"))){
            //Already highlighted
            src.setFill(Color.WHITE);
        } else {
            //Not highlighted
            src.setFill(Color.valueOf("#0F4B91"));
        }


        Iterator<node> iter = nodes.values().iterator();

        for (int i = 0; i < nodes.size(); i++) {
            node n = iter.next();
            if (n.c.equals(src)) {

                nodeIDField.setText(n.getNodeID());
                xCoordField.setText(Integer.toString(n.getNodeRef().getXcoord()));
                yCoordField.setText(Integer.toString(n.getNodeRef().getYcoord()));
                bldgField.setText(n.getNodeRef().getBuilding());
                floorField.setText(n.getNodeRef().getFloor());
                nodeTypeField.setText(n.getNodeRef().getType());
                longNameField.setText(n.getNodeRef().getLongName());
                shortNameField.setText(n.getNodeRef().getShortName());
                n.setHighlighted(!n.isHighlighted());

                pitStops.add(n);
            }
        }
    }

    public void highlightEdge(MouseEvent e){
        edgeInfoBox.setVisible(true);
        nodeInfoBox.setVisible(false);
        nodeInfoBox.toBack();
        Line src = (Line) e.getSource();

        Iterator<edge> iter = edges.values().iterator();
        for (int i = 0; i < edges.size(); i++) {
            edge ed = iter.next();
            HashMap<String, EdgeSuper> edgeSupers = EdgeMapSuper.getMap();
            if(ed.l.equals(src)){
                EdgeSuper cur = edgeSupers.get(ed.startID + "_" + ed.endID);
                if (cur == null){
                    cur = edgeSupers.get(ed.endID + "_" + ed.startID);
                }
                if (cur == null){
                    System.out.println("EDGE NOT FOUND");
                    return;
                }

                edgeIDField.setText(cur.edgeID);
                startNodeID.setText(cur.getStartingNode().replaceAll("\\s",""));
                endNodeID.setText(cur.getEndingNode().replaceAll("\\s",""));
            }
        }

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
