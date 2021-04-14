package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.MochaManticores.Algorithms.AStar;
import edu.wpi.MochaManticores.Nodes.MapSuper;
import edu.wpi.MochaManticores.Nodes.NodeSuper;
import edu.wpi.MochaManticores.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

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

    @FXML
    private Text pathField;

    public void initialize(){

        Iterator<NodeSuper> mapIter = MapSuper.getMap().values().iterator();
        for (int i = 0; i < MapSuper.getMap().size(); i++) {
            NodeSuper n = mapIter.next();
            if(n.getType().equals("PARK") || n.getType().equals("WALK")){
                nodes.add(n);
                longNames.add(n.getLongName());
            }

        }

        startNodeSelection.getItems().addAll(longNames);
        endNodeSelection.getItems().addAll(longNames);
        pathField.setText("Path:\nSTART\n|\n");

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
            }
            if(node.getLongName().equals(endNodeSelection.getSelectionModel().getSelectedItem())){
                NodeSuper end = node;
                stops.addLast(end);
            }
            }

        LinkedList<String> path = star.path(stops);
        if(path != null){
            System.out.println("Path found:");
        }
        for (String str :
                path) {
            pathField.setText(pathField.getText()+"\n"+str+"\n|\n");
        }
            pathField.setText(pathField.getText()+"\nEND");
    }

    public void back(){
        super.back();
    }

}
