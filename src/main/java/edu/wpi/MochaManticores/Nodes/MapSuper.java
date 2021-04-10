package edu.wpi.MochaManticores.Nodes;

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
    private HashMap<String, NodeSuper> myMap;    //map of MapNodes organized by nodeID

    //Constructor
    public MapSuper(String name, HashMap<String, NodeSuper> myMap) {
        this.name = name;
        this.myMap = myMap;
    }

    public NodeSuper getNode(String ID) {
        /**
         * function: getNode()
         * usage: returns the node corresponding to the input ID
         * inputs: String ID (the node ID of the desired node)
         * returns: NodeSuper (unnamed) (the specified MapNode)
         */
        return this.myMap.get(ID);
    }

    public void addNode(NodeSuper newNode) {
        /**
         * function: addNode()
         * usage: adds a NodeSuper to myMap, and modifies neighboring nodes
         * inputs: NodeSuper newNode (Node to be added)
         * returns: void
         */
        String newKey = newNode.getID();
        this.myMap.put(newKey, newNode);

        Set<String> myNeighbors = newNode.getNeighbors();
        for(String ID : myNeighbors) {
            Integer thisCost = newNode.getCost(ID);
            this.myMap.get(ID).addNeighbor(newKey, thisCost);
        }
    }

    public void delNode(String targetID) {
        /**
         * function: delNode()
         * usage: removes a MapNode from myMap and modifies neighboring nodes
         * inputs: String targetID (Node ID of the node to be removed)
         * returns: void
         */
        Set<String> myNeighbors = this.myMap.get(targetID).getNeighbors();
        for(String ID : myNeighbors) {
            this.myMap.get(ID).delNeighbor(targetID);
        }
        this.myMap.remove(targetID);
    }

    public void editVertexCost(String ID1, String ID2, Integer newCost) {
        /**
         * function: editVertexCost()
         * usage: edits the cost of travel from one node to another
         * inputs: String ID1 (Node ID of the first node to be modified)
         *         String ID2 (Node ID of the second node to be modified)
         * returns: void
         */
        this.myMap.get(ID1).editCost(ID2, newCost);
        this.myMap.get(ID2).editCost(ID1, newCost);
    }

    public void removeVertex(String ID1, String ID2) {
        /**
         * function: removeVertex()
         * usage: removes a traversable connection between two nodes
         * inputs: String ID1 (Node ID of the first node to be modified)
         *         String ID2 (Node ID of the second node to be modified)
         * returns: void
         */
        this.myMap.get(ID1).delNeighbor(ID2);
        this.myMap.get(ID2).delNeighbor(ID1);
    }

    public void addVertex(String ID1, String ID2, Integer cost) {
        /**
         * function: addVertex()
         * usage: adds a traversable vertex between two nodes
         * inputs: String ID1 (Node ID of the first node to be modified)
         *         String ID2 (Node ID of the second node to be modified)
         * returns: void
         */
        this.myMap.get(ID1).addNeighbor(ID2, cost);
        this.myMap.get(ID2).addNeighbor(ID1, cost);
    }
}
