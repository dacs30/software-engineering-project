package edu.wpi.MochaManticores.views;
//TODO:comment :(
import com.jfoenix.controls.*;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.controls.events.JFXDialogEvent;
import edu.wpi.MochaManticores.Nodes.NodeSuper;
import edu.wpi.MochaManticores.Nodes.MapSuper;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.input.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;
import java.util.Observable;

public class mapPage extends SceneController{
    public JFXTextField mapName;
    public JFXButton backButton;
    public TableView dispTable;
    public TableColumn xcoord;
    public TableColumn ycoord;
    public TableColumn floor;
    public TableColumn building;
    public TableColumn longName;
    public TableColumn shortName;
    public TableColumn nodeID;



    @FXML
    public VBox selectionPage;

    @FXML
    public VBox editPage;

    @FXML
    public StackPane dialogPane;
    public JFXTextField nodeIDField;
    public JFXTextField shortNameField;
    public JFXTextField logNameField;
    public JFXTextField buildingField;
    public JFXTextField floorField;
    public JFXTextField ycoordField;
    public JFXTextField xcoordField;

    public class Node extends RecursiveTreeObject<Node>{
        public StringProperty xcoord;
        public StringProperty ycoord;
        public StringProperty floor;
        public StringProperty building;
        public StringProperty longName;
        public StringProperty shortName;
        public StringProperty nodeID;

        public StringProperty[] fields;

        public Node(String xcoord, String ycoord, String floor, String building, String longName, String shortName, String nodeID) {
            this.xcoord = new SimpleStringProperty(xcoord);
            this.ycoord = new SimpleStringProperty(ycoord);
            this.floor = new SimpleStringProperty(floor);
            this.building = new SimpleStringProperty(building);
            this.longName = new SimpleStringProperty(longName);
            this.shortName = new SimpleStringProperty(shortName);
            this.nodeID = new SimpleStringProperty(nodeID);
            fields = new StringProperty[]{this.xcoord, this.ycoord,this.floor,this.building,this.longName,this.shortName,this.nodeID};
        }

        @Override
        public String toString(){
            return getNodeID();
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
        xcoord = new TableColumn("X-coordinates");
        xcoord.setMinWidth(100);
        xcoord.setCellValueFactory(
                new PropertyValueFactory<Node, String>("xcoord"));

        ycoord = new TableColumn("Y-coordinates");
        ycoord.setMinWidth(100);
        ycoord.setCellValueFactory(
                new PropertyValueFactory<Node, String>("ycoord"));
        floor = new TableColumn("floor");
        floor.setMinWidth(100);
        floor.setCellValueFactory(
                new PropertyValueFactory<Node, String>("floor"));

        building = new TableColumn("building");
        building.setMinWidth(100);
        building.setCellValueFactory(
                new PropertyValueFactory<Node, String>("building"));

        longName = new TableColumn("longName");
        longName.setMinWidth(100);
        longName.setCellValueFactory(
                new PropertyValueFactory<Node, String>("longName"));

        shortName = new TableColumn("shortName");
        shortName.setMinWidth(100);
        shortName.setCellValueFactory(
                new PropertyValueFactory<Node, String>("shortName"));
        nodeID = new TableColumn("nodeID");
        nodeID.setMinWidth(100);
        nodeID.setCellValueFactory(
                new PropertyValueFactory<Node, String>("nodeID"));

        buildTable("");
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

    private void buildTable(String searchTerm){
        Iterator<NodeSuper> mapIter = MapSuper.getMap().values().iterator();
        ObservableList<Node> listOfNodes = FXCollections.observableArrayList();

        for (int i = 0; i <MapSuper.getMap().size(); i++) {
            NodeSuper n = mapIter.next();
            Node nodeToAdd = new Node(Integer.toString(n.getXcoord()),
                    Integer.toString(n.getYcoord()),
                    n.getFloor(),
                    n.getBuilding(),
                    n.getLongName(),
                    n.getShortName(),
                    n.getID());
            for (int j = 0; j < nodeToAdd.getFields().length; j++) {
                if(nodeToAdd.getFields()[j].get().toLowerCase().contains(searchTerm.toLowerCase()) || searchTerm.equals("")){
                    listOfNodes.add(nodeToAdd);
                }
            }

        }

        dispTable.setItems(listOfNodes);
        dispTable.getColumns().setAll(xcoord,ycoord,floor,building,longName,shortName,nodeID);
    }

    public void cancel(ActionEvent e){
        returnToMain(e);
    }

    public void submit(ActionEvent e){
        Node n = (Node) dispTable.getSelectionModel().getSelectedItem();
        if (n == null){
            loadErrorDialog();
        }else{
            System.out.println(n.toString());
            loadEditPage(n);
        }

    }

    public void loadErrorDialog(){
        dialogPane.setDisable(false);
        JFXDialogLayout message = new JFXDialogLayout();
        message.setHeading(new Text("Oops!"));
        message.setBody(new Text("Please select a table entry before editing."));
        JFXDialog dialog = new JFXDialog(dialogPane, message,JFXDialog.DialogTransition.CENTER);
        JFXButton exit = new JFXButton("DONE");
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
                dialogPane.setDisable(true);
            }
        });
        dialog.setOnDialogClosed(new EventHandler<JFXDialogEvent>() {
            @Override
            public void handle(JFXDialogEvent event) {
                dialogPane.setDisable(true);
            }
        });
        message.setActions(exit);
        dialog.show();
    }

    public void loadEditPage(Node node){
        selectionPage.setVisible(false);
        editPage.setVisible(true);
        xcoordField.setText(node.getXcoord());
        ycoordField.setText(node.getYcoord());
        floorField.setText(node.getFloor());
        buildingField.setText(node.getBuilding());
        logNameField.setText(node.getLongName());
        shortNameField.setText(node.getShortName());
        nodeIDField.setText(node.getNodeID());

    }

    public void submitEdit(ActionEvent e){
        //TODO:Talk to CSV Manager
        ;
    }

    public void cancelEdit(ActionEvent e){
        editPage.setVisible(false);
        selectionPage.setVisible(true);

    }

    public void back(ActionEvent e){
        super.back(e);
    }
}
