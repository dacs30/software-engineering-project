package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.*;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.MochaManticores.Nodes.NodeSuper;
import edu.wpi.MochaManticores.Nodes.MapSuper;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;
import java.util.Observable;

public class mapPage extends SceneController{
    public JFXTextField mapName;
    public JFXButton backButton;
    public TableView dispTable;


    public class Node extends RecursiveTreeObject<Node>{
        public StringProperty xcoord;
        public StringProperty ycoord;
        public StringProperty floor;
        public StringProperty building;
        public StringProperty longName;
        public StringProperty shortName;
        public StringProperty nodeID;

        public Node(String xcoord, String ycoord, String floor, String building, String longName, String shortName, String nodeID) {
            this.xcoord = new SimpleStringProperty(xcoord);
            this.ycoord = new SimpleStringProperty(ycoord);
            this.floor = new SimpleStringProperty(floor);
            this.building = new SimpleStringProperty(building);
            this.longName = new SimpleStringProperty(longName);
            this.shortName = new SimpleStringProperty(shortName);
            this.nodeID = new SimpleStringProperty(nodeID);
        }

        @Override
        public String toString(){
            return getNodeID();
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
        TableColumn xcoord = new TableColumn("X-coordinates");
        xcoord.setMinWidth(100);
        xcoord.setCellValueFactory(
                new PropertyValueFactory<Node, String>("xcoord"));

        TableColumn ycoord = new TableColumn("Y-coordinates");
        ycoord.setMinWidth(100);
        ycoord.setCellValueFactory(
                new PropertyValueFactory<Node, String>("ycoord"));
        TableColumn floor = new TableColumn("floor");
        floor.setMinWidth(100);
        floor.setCellValueFactory(
                new PropertyValueFactory<Node, String>("floor"));

        TableColumn building = new TableColumn("building");
        building.setMinWidth(100);
        building.setCellValueFactory(
                new PropertyValueFactory<Node, String>("building"));

        TableColumn longName = new TableColumn("longName");
        longName.setMinWidth(100);
        longName.setCellValueFactory(
                new PropertyValueFactory<Node, String>("longName"));

        TableColumn shortName = new TableColumn("shortName");
        shortName.setMinWidth(100);
        shortName.setCellValueFactory(
                new PropertyValueFactory<Node, String>("shortName"));
        TableColumn nodeID = new TableColumn("nodeID");
        nodeID.setMinWidth(100);
        nodeID.setCellValueFactory(
                new PropertyValueFactory<Node, String>("nodeID"));

        Iterator<NodeSuper> mapIter = MapSuper.getMap().values().iterator();
        ObservableList<Node> listOfNodes = FXCollections.observableArrayList();
        
        for (int i = 0; i <MapSuper.getMap().size(); i++) {
            NodeSuper n = mapIter.next();
            //listOfNodes.add(new Node("x"+i, "y"+i, "f"+i, "b"+i, "longName"+i,"shortName"+i,""+i));
            listOfNodes.add(new Node(Integer.toString(n.getXcoord()),
                                     Integer.toString(n.getYcoord()),
                                     n.getFloor(),
                                     n.getBuilding(),
                                     n.getLongName(),
                                     n.getShortName(),
                                     n.getID()));
        }


        System.out.println(listOfNodes);
        dispTable.setItems(listOfNodes);
        dispTable.getColumns().setAll(xcoord,ycoord,floor,building,longName,shortName,nodeID);
    }
    public void displayTable() {

    }

    public void cancel(ActionEvent e){
        returnToMain(e);
    }

    public void submit(ActionEvent e){
        dispTable.getFocusModel().focus(0);
        //returnToMain(e);
    }

    public void back(ActionEvent e){
        super.back(e);
    }
}
