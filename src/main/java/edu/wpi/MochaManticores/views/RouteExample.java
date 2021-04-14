package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.MochaManticores.Algorithms.AStar;
import edu.wpi.MochaManticores.Nodes.MapSuper;
import edu.wpi.MochaManticores.Nodes.NodeSuper;
import edu.wpi.MochaManticores.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

public class RouteExample extends SceneController {



    ObservableList<NodeSuper> nodes = FXCollections.observableArrayList();
    ObservableList<String> longNames = FXCollections.observableArrayList();


    @FXML
    private JFXComboBox startNodeSelection;


    @FXML
    private JFXComboBox endNodeSelection;

    public void initialize(){

        Iterator<NodeSuper> mapIter = MapSuper.getMap().values().iterator();
        for (int i = 0; i < MapSuper.getMap().size(); i++) {
            NodeSuper n = mapIter.next();
            nodes.add(n);
            longNames.add(n.getLongName());
        }

        startNodeSelection.getItems().addAll(longNames);
        endNodeSelection.getItems().addAll(longNames);

        //startNodeSelection.getItems().ad
    }


    public void getPath(ActionEvent e){
        AStar star = new AStar();
        LinkedList<NodeSuper> stops = new LinkedList<>();
        for (NodeSuper node :
                nodes) {
            if (node.getLongName().equals(startNodeSelection.getSelectionModel().getSelectedItem())) {
                NodeSuper start = node;
                stops.addFirst(start);
            }else if(node.getLongName().equals(endNodeSelection.getSelectionModel().getSelectedItem())){
                NodeSuper end = node;
                stops.addLast(end);
            }
            }

        LinkedList<String> path = star.path(stops);
        for (String str :
                path) {
            System.out.println(str);
        }
    }

}
