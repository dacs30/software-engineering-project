package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.MochaManticores.Algorithms.AStar2;
import edu.wpi.MochaManticores.Exceptions.DestinationNotAccessibleException;
import edu.wpi.MochaManticores.Nodes.MapSuper;
import edu.wpi.MochaManticores.Nodes.NodeSuper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

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
        pathField.setText("Path:\nSTART\n|\n");
        AStar2 star = new AStar2();
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
        try {
            LinkedList<String> path = star.multiStopRoute(stops, "none"); //ADD CONDITION FOR PATHING
            if (path != null) {
                System.out.println("Path found:");
            }
            for (String str :
                    path) {
                pathField.setText(pathField.getText() + "\n" + str + "\n|\n");
            }
            pathField.setText(pathField.getText() + "\nEND");
        } catch (DestinationNotAccessibleException destinationNotAccessibleException){
            String error = "Destination not accessible";
        }
    }

    public void back(){
        super.back();
    }



}
