package edu.wpi.MochaManticores.Nodes;

import edu.wpi.MochaManticores.Algorithms.AStar2;
import edu.wpi.MochaManticores.Algorithms.PathPlanning;

import java.util.HashMap;
import java.util.Set;

/**
 * This is a map class to contain information
 * for an occupancy grid of the hospital used by
 * the navigation algorithms.
 * @author aksil
 */
public class MapSuper {
    //Declare instance variables
    public String name;
    private static final HashMap<String, NodeSuper> myMap = new HashMap<>();    //map of MapNodes organized by nodeID

    //Constructor
    public MapSuper(String name) {
        this.name = name;
    }

    /**
     * function: getNode()
     * usage: returns the node corresponding to the input ID
     * inputs: String ID (the node ID of the desired node)
     * returns: NodeSuper (unnamed) (the specified MapNode)
     */
    public static NodeSuper getNode(String ID) {
        return myMap.get(ID);
    }

    /**
     * function: addNode()
     * usage: adds a NodeSuper to myMap, and modifies neighboring nodes
     * inputs: NodeSuper newNode (Node to be added)
     * returns: void
     */
    public static void addNode(NodeSuper newNode) {
        String newKey = newNode.getID();
        myMap.put(newKey, newNode);

        Set<String> myNeighbors = newNode.getNeighbors();
        for(String ID : myNeighbors) {
            Integer thisCost = newNode.getCost(ID);
            myMap.get(ID).addNeighbor(newKey, thisCost);
        }
    }

    /**
     * function: delNode()
     * usage: removes a MapNode from myMap and modifies neighboring nodes
     * inputs: String targetID (Node ID of the node to be removed)
     * returns: void
     */
    public void delNode(String targetID) {
        Set<String> myNeighbors = myMap.get(targetID).getNeighbors();
        for(String ID : myNeighbors) {
            myMap.get(ID).delNeighbor(targetID);
        }
        myMap.remove(targetID);
    }

    /**
     * function: editVertexCost()
     * usage: edits the cost of travel from one node to another
     * inputs: String ID1 (Node ID of the first node to be modified)
     *         String ID2 (Node ID of the second node to be modified)
     * returns: void
     */
    public void editVertexCost(String ID1, String ID2, Integer newCost) {
        myMap.get(ID1).editCost(ID2, newCost);
        myMap.get(ID2).editCost(ID1, newCost);
    }

    /**
     * function: removeVertex()
     * usage: removes a traversable connection between two nodes
     * inputs: String ID1 (Node ID of the first node to be modified)
     *         String ID2 (Node ID of the second node to be modified)
     * returns: void
     */
    public void removeVertex(String ID1, String ID2) {
        myMap.get(ID1).delNeighbor(ID2);
        myMap.get(ID2).delNeighbor(ID1);
    }

    /**
     * function: addVertex()
     * usage: adds a traversable vertex between two nodes
     * inputs: String ID1 (Node ID of the first node to be modified)
     *         String ID2 (Node ID of the second node to be modified)
     * returns: void
     */
    public void addVertex(String ID1, String ID2, Integer cost) {
        myMap.get(ID1).addNeighbor(ID2, cost);
        myMap.get(ID2).addNeighbor(ID1, cost);
    }

    /**
     * function: getMap()
     * usage: returns the map of nodes
     * @return HashMap<String, NodeSuper>
     */
    public static HashMap<String, NodeSuper> getMap() {
        return myMap;
    }

}
