package edu.wpi.MochaManticores.views;
//TODO:comment :(

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.Nodes.MapSuper;
import edu.wpi.MochaManticores.Nodes.NodeSuper;
import edu.wpi.MochaManticores.database.EdgeManager;
import edu.wpi.MochaManticores.database.Mdb;
import edu.wpi.MochaManticores.database.NodeManager;
import edu.wpi.MochaManticores.Editors.mapEdit;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class nodePage extends SceneController{
    public JFXTextField mapName;
    public JFXButton backButton;
    public TableView<Node> dispTable;
    public TableColumn<Node, String> xcoord;
    public TableColumn<Node, String> ycoord;
    public TableColumn<Node, String> floor;
    public TableColumn<Node, String> building;
    public TableColumn<Node, String> longName;
    public TableColumn<Node, String> shortName;
    public TableColumn<Node, String> nodeID;
    public ObservableList<Node> listOfNodes = FXCollections.observableArrayList();

    @FXML
    private ImageView backgroundIMG;

    @FXML
    private GridPane contentPane;

    @FXML
    public GridPane selectionPage;

    @FXML
    public GridPane editPage;

    @FXML
    public StackPane dialogPane;
    public JFXTextField nodeIDField;
    public JFXTextField shortNameField;
    public JFXTextField logNameField;
    public JFXTextField buildingField;
    public JFXTextField floorField;
    public JFXTextField ycoordField;
    public JFXTextField xcoordField;
    public JFXTextField nodeTypeField;
    public Node selectedNode;

    private mapEdit editor = new mapEdit();

    public class Node extends RecursiveTreeObject<Node>{
        public StringProperty xcoord;
        public StringProperty ycoord;
        public StringProperty floor;
        public StringProperty building;
        public StringProperty longName;
        public StringProperty shortName;
        public StringProperty nodeID;
        public StringProperty nodeType;

        public String getNodeType() {
            return nodeType.get();
        }

        public StringProperty nodeTypeProperty() {
            return nodeType;
        }

        public void setNodeType(String nodeType) {
            this.nodeType.set(nodeType);
        }

        public Set<String> neighbors;

        public StringProperty[] fields;


        public Node(String xcoord, String ycoord, String floor, String building, String longName, String shortName, String nodeID, Set<String> neighbors,String nodeType) {
            this.xcoord = new SimpleStringProperty(xcoord);
            this.ycoord = new SimpleStringProperty(ycoord);
            this.floor = new SimpleStringProperty(floor);
            this.building = new SimpleStringProperty(building);
            this.longName = new SimpleStringProperty(longName);
            this.shortName = new SimpleStringProperty(shortName);
            this.nodeID = new SimpleStringProperty(nodeID);
            this.neighbors = neighbors;
            this.nodeType = new SimpleStringProperty(nodeType);
            fields = new StringProperty[]{this.xcoord, this.ycoord,this.floor,this.building,this.longName,this.shortName,this.nodeID};
        }

        public Node(StringProperty[] fields, Set<String> neighbors){
            this.xcoord = fields[0];
            this.ycoord = fields[1];
            this.floor = fields[2];
            this.building = fields[3];
            this.longName = fields[4];
            this.shortName = fields[5];
            this.nodeID = fields[6];
            this.neighbors = neighbors;
        }

        public Set<String> getNeighbors() {
            return neighbors;
        }

        public void setNeighbors(Set<String> neighbors) {
            this.neighbors = neighbors;
        }

        public void setFields() {
            fields = new StringProperty[]{xcoord, ycoord,floor,building,longName,shortName,nodeID};
        }

        public void setXcoord(String xcoord) {
            this.xcoord.set(xcoord);
        }

        public void setYcoord(String ycoord) {
            this.ycoord.set(ycoord);
        }

        public void setFloor(String floor) {
            this.floor.set(floor);
        }

        public void setBuilding(String building) {
            this.building.set(building);
        }

        public void setLongName(String longName) {
            this.longName.set(longName);
        }

        public void setShortName(String shortName) {
            this.shortName.set(shortName);
        }

        public void setNodeID(String nodeID) {
            this.nodeID.set(nodeID);
        }

        @Override
        public String toString(){
            String s = "";
            s+= ("X-coord: "+getXcoord() + "\n");
            s+= ("Y-coord: "+getYcoord() + "\n");
            s+= ("Floor: "+getFloor() + "\n");
            s+= ("Building: "+getBuilding() + "\n");
            s+= ("Long Name: "+getLongName() + "\n");
            s+= ("Short Name: "+getShortName() + "\n");
            s+= ("NodeID: "+getNodeID() + "\n");
            return s;
        }

        public StringProperty[] getFields() {
            return fields;
        }

        public String getXcoord() {
            return xcoord.get();
        }

        public StringProperty xcoordProperty() {
            return xcoord;
        }

        public String getYcoord() {
            return ycoord.get();
        }

        public StringProperty ycoordProperty() {
            return ycoord;
        }

        public String getFloor() {
            return floor.get();
        }

        public StringProperty floorProperty() {
            return floor;
        }

        public String getBuilding() {
            return building.get();
        }

        public StringProperty buildingProperty() {
            return building;
        }

        public String getLongName() {
            return longName.get();
        }

        public StringProperty longNameProperty() {
            return longName;
        }

        public String getShortName() {
            return shortName.get();
        }

        public StringProperty shortNameProperty() {
            return shortName;
        }

        public String getNodeID() {
            return nodeID.get();
        }

        public StringProperty nodeIDProperty() {
            return nodeID;
        }
    }

    public void initialize() {
        xcoord = new TableColumn<Node, String>("X-coordinates");
        xcoord.setMinWidth(100);
        xcoord.setCellValueFactory(
                new PropertyValueFactory<Node, String>("xcoord"));

        ycoord = new TableColumn<Node, String>("Y-coordinates");
        ycoord.setMinWidth(100);
        ycoord.setCellValueFactory(
                new PropertyValueFactory<Node, String>("ycoord"));
        floor = new TableColumn<>("floor");
        floor.setMinWidth(100);
        floor.setCellValueFactory(
                new PropertyValueFactory<Node, String>("floor"));

        building = new TableColumn<Node, String>("building");
        building.setMinWidth(100);
        building.setCellValueFactory(
                new PropertyValueFactory<Node, String>("building"));

        longName = new TableColumn<>("longName");
        longName.setMinWidth(100);
        longName.setCellValueFactory(
                new PropertyValueFactory<Node, String>("longName"));

        shortName = new TableColumn<Node, String>("shortName");
        shortName.setMinWidth(100);
        shortName.setCellValueFactory(
                new PropertyValueFactory<Node, String>("shortName"));
        nodeID = new TableColumn<>("nodeID");
        nodeID.setMinWidth(100);
        nodeID.setCellValueFactory(
                new PropertyValueFactory<Node, String>("nodeID"));

        listOfNodes = buildTable("");
        editPage.setVisible(false);
        selectionPage.setVisible(true);



        double height = super.getHeight();
        double width = super.getWidth();
        backgroundIMG.setFitHeight(height);
        backgroundIMG.setFitWidth(width);
        contentPane.setPrefSize(width,height);
        dialogPane.setPrefSize(width,height);

        backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
        backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());
    }

    public void displayTable() {

    }

    public void searchPressed(ActionEvent e){
        String searchTerm = mapName.getText();

        if(mapName.getText().equals("")){
            buildTable("");
        }else{
            buildTable(searchTerm);
        }
    }

    public void searchTyped(KeyEvent e){
        String searchTerm = mapName.getText();

        if(mapName.getText().equals("")){
            buildTable("");
        }else{
            buildTable(searchTerm);
        }
    }

    private ObservableList<Node> buildTable(String searchTerm){
        ObservableList<Node> nodes = FXCollections.observableArrayList();
        Iterator<NodeSuper> mapIter = MapSuper.getMap().values().iterator();
        for (int i = 0; i <MapSuper.getMap().size(); i++) {
            NodeSuper n = mapIter.next();
            Node nodeToAdd = new Node(Integer.toString(n.getXcoord()),
                    Integer.toString(n.getYcoord()),
                    n.getFloor(),
                    n.getBuilding(),
                    n.getLongName(),
                    n.getShortName(),
                    n.getID(),
                    n.getNeighbors(),
                    n.getType());
            for (int j = 0; j < nodeToAdd.getFields().length; j++) {
                if(nodeToAdd.getFields()[j].get().toLowerCase().contains(searchTerm.toLowerCase()) || searchTerm.equals("")){
                    nodes.add(nodeToAdd);
                    break;
                }
            }

        }

        dispTable.setItems(nodes);
        dispTable.getColumns().setAll(xcoord,ycoord,floor,building,longName,shortName,nodeID);
        return nodes;
    }

    public void cancel(ActionEvent e){
        buildTable("");
        mapName.setText("");
    }

    public void editButton(ActionEvent e){
        Node n = dispTable.getSelectionModel().getSelectedItem();
        if (n == null){
            loadErrorDialog();
        }else{
            StringProperty fields[] = new StringProperty[n.getFields().length];
            Set<String> neigh = new HashSet<>();
            for (int i = 0; i < n.getFields().length; i++) {
                fields[i] = n.getFields()[i];
            }
            for (int i = 0; i < n.getNeighbors().size(); i++) {
                neigh.add((String) n.getNeighbors().toArray()[i]);
            }
            selectedNode = new Node(fields, neigh);
            System.out.println("Node Info:\n"+n);
            loadEditPage(n);
        }
    }

    public void addButton(ActionEvent e){
            loadEditPage(null);
    }

    public void downloadCSV(ActionEvent e){
        super.downloadCSV(e, "\\bwMNodes.csv", "data/bwMNodes.csv");
    }

    public void loadCustomCSV(ActionEvent e){
        FileChooser f = new FileChooser();
        File file = f.showOpenDialog(App.getPrimaryStage());
        if (file == null){
            return;
        }
        System.out.println(file.getAbsolutePath());

        try {
            Mdb.databaseChangeCSVs(EdgeManager.getEdge_csv_path(), file.getAbsolutePath());
            cancel(null);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



    public void loadErrorDialog(){
        super.loadErrorDialog(dialogPane,"Please select a table entry before editing.");
    }

    public void loadEditPage(Node node){
        selectionPage.setVisible(false);
        editPage.setVisible(true);
        if(node != null){
            xcoordField.setText(node.getXcoord());
            ycoordField.setText(node.getYcoord());
            floorField.setText(node.getFloor());
            buildingField.setText(node.getBuilding());
            logNameField.setText(node.getLongName());
            shortNameField.setText(node.getShortName());
            nodeIDField.setText(node.getNodeID());
            nodeTypeField.setText(node.getNodeType());
        }
    }

    public void cleanEditPage(){
        xcoordField.setText("");
        ycoordField.setText("");
        floorField.setText("");
        buildingField.setText("");
        logNameField.setText("");
        shortNameField.setText("");
        nodeIDField.setText("");
        nodeTypeField.setText("");
    }

//    public void submitEdit(ActionEvent e) throws SQLException, FileNotFoundException {
//        Connection connection = null;
//        try {
//            connection = getConnection();
//        } catch (SQLException sqlException) {
//            return;
//        }
//
//        if (!checkInput()) {
//            loadEmptyDialog();
//        } else {
//            NodeSuper nodeSuper = null;
//            try {
//                nodeSuper = MapSuper.getMap().get(selectedNode.getNodeID());
//            }
//            catch (NullPointerException exception) {
//                NodeManager.addNode(connection,
//                nodeIDField.getText(),
//                xcoordField.getText(),
//                ycoordField.getText(),
//                floorField.getText(),
//                buildingField.getText(),
//                "TEST",
//                logNameField.getText(),
//                shortNameField.getText());
//                cancelEdit(e);
//                return;
//            }
//
//            Node n = null;
//            for (Node node : listOfNodes) {
//                if (node.getNodeID().equals(selectedNode.getNodeID())) {
//                    n = updateNode(node);
//                    break;
//                }
//            }
//            if (n == null) {
//                System.out.println("Cannot Find Node in List of Nodes");
//                return;
//            }
//
//            NodeManager.updateNode(connection, n.nodeID.get(), nodeSuper.getID(), Integer.parseInt(n.getXcoord()),
//                    Integer.parseInt(n.ycoord.get()), n.getFloor(), n.getBuilding(), nodeSuper.getType(), n.getLongName(), n.getShortName());
//            //TODO:Talk to CSV Manager
//            //NODETYPE IS NOT CHANGED AS WELL AS NEIGHBORS
//            cancelEdit(e);
//        }
//    }

    public void submitEdit(ActionEvent e) throws SQLException, FileNotFoundException {
        NodeSuper nodeSuper;
        String selectedID;
        if(!checkInput()){ //Ensures that the user has inputted values
            loadEmptyDialog();
        }else{
            try{ //selectedNode != null so Node is being EDITED
                nodeSuper = new NodeSuper(
                        Integer.parseInt(xcoordField.getText()),
                        Integer.parseInt(ycoordField.getText()),
                        floorField.getText(),
                        buildingField.getText(),
                        logNameField.getText(),
                        shortNameField.getText(),
                        nodeIDField.getText(),
                        nodeTypeField.getText(),
                        MapSuper.getMap().get(selectedNode.getNodeID()).getVertextList());
                selectedID = selectedNode.getNodeID();
                Node n = null;
                for (Node node : listOfNodes) {
                    if (node.getNodeID().equals(selectedNode.getNodeID())) {
                        n = updateNode(node);
                        break;
                    }
                }
                if (n == null) {
                    System.out.println("Cannot Find Node in List of Nodes");
                    return;
                }
            } catch (NullPointerException nullPtrException){ //selectedNode == null so Node is being ADDED

                nodeSuper = new NodeSuper(
                        Integer.parseInt(xcoordField.getText()),
                        Integer.parseInt(ycoordField.getText()),
                        floorField.getText(),
                        buildingField.getText(),
                        logNameField.getText(),
                        shortNameField.getText(),
                        nodeIDField.getText(),
                        nodeTypeField.getText(),
                        null);
                selectedID = "";
            }
            boolean success = editor.submitEditNodeToDB(nodeSuper, selectedID);
            cancelEdit(e);

        }
    }

    public void loadEmptyDialog(){
        super.loadErrorDialog(dialogPane, "Looks like some of the fields are empty.");
    }

    public boolean checkInput(){
        return  editor.checkInput(Arrays.asList(xcoordField.getText(), ycoordField.getText(), floorField.getText(), buildingField.getText(), logNameField.getText(),shortNameField.getText(), nodeIDField.getText(), nodeTypeField.getText()));
    }

    public Node updateNode(Node n){
        n.setXcoord(xcoordField.getText());
        n.setYcoord(ycoordField.getText());
        n.setFloor(floorField.getText());
        n.setBuilding(buildingField.getText());
        n.setLongName(logNameField.getText());
        n.setShortName(shortNameField.getText());
        n.setNodeID(nodeIDField.getText());
        n.setFields();
        System.out.println(n);
        System.out.println(selectedNode);



        System.out.println("printed");
        return n;
    }

    public void cancelEdit(ActionEvent e){
        editPage.setVisible(false);
        selectionPage.setVisible(true);
        cancel(e);

    }

    public void back(ActionEvent e){
        if(editPage.isVisible()){
            cancelEdit(e);
        }else{
            super.back();
        }

    }

    public void delNode() throws SQLException, FileNotFoundException{
        if(checkInput()){
            Connection connection = null;
            try {
                connection = DriverManager.getConnection(Mdb.JDBC_URL);
            } catch (SQLException sqlException) {
                System.out.println("Connection failed. Check output console.");
                sqlException.printStackTrace();
                return;
            }
            NodeManager.delNode(connection, selectedNode.getNodeID());
            cancelEdit(null);
        }
    }
}
