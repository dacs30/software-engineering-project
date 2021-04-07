package edu.wpi.MochaManticores;

import java.util.HashMap;
import java.util.Set;

/**
 * This is a map class to contain information
 * for an occupancy grid of the hospital used by
 * the navigation algorithms.
 * @author aksil
 */
public class NodeMap {
    //Declare instance variables
    private HashMap<Integer, MapNode> myMap;    //map of MapNodes organized by nodeID

    //Constructor
    public NodeMap(HashMap<Integer, MapNode> myMap) {
    }

    public void addNode(MapNode newNode) {
        /**
         * function: addNode()
         * usage: adds a MapNode to myMap, and modifies neighboring nodes
         * inputs: MapNode newNode (Node to be added)
         * returns: void
         */
        Integer newKey = newNode.ID;
        this.myMap.put(newKey, newNode);

        Set<Integer> myNeighbors = newNode.getNeighbors();
        for(Integer ID : myNeighbors) {
            Integer thisCost = newNode.getCost(ID);
            this.myMap.get(ID).addNeighbor(newKey, thisCost);
        }
    }

    public void delNode(Integer targetID) {
        /**
         * function: delNode()
         * usage: removes a MapNode from myMap and modifies neighboring nodes
         * inputs: Integer targetID (Node ID of the node to be removed)
         * returns: void
         */
        Set<Integer> myNeighbors = this.myMap.get(targetID).getNeighbors();
        for(Integer ID : myNeighbors) {
            this.myMap.get(ID).delNeighbor(targetID);
        }
        this.myMap.remove(targetID);
    }

    public void editVertexCost(Integer ID1, Integer ID2, Integer newCost) {
        /**
         * function: editVertexCost()
         * usage: edits the cost of travel from one node to another
         * inputs: Integer ID1 (Node ID of the first node to be modified)
         *         Integer ID2 (Node ID of the second node to be modified)
         * returns: void
         */
        this.myMap.get(ID1).editCost(ID2, newCost);
        this.myMap.get(ID2).editCost(ID1, newCost);
    }

    public void removeVertex(Integer ID1, Integer ID2) {
        /**
         * function: removeVertex()
         * usage: removes a traversable connection between two nodes
         * inputs: Integer ID1 (Node ID of the first node to be modified)
         *         Integer ID2 (Node ID of the second node to be modified)
         * returns: void
         */
        this.myMap.get(ID1).delNeighbor(ID2);
        this.myMap.get(ID2).delNeighbor(ID1);
    }

    public void addVertex(Integer ID1, Integer ID2, Integer cost) {
        /**
         * function: addVertex()
         * usage: adds a traversable vertex between two nodes
         * inputs: Integer ID1 (Node ID of the first node to be modified)
         *         Integer ID2 (Node ID of the second node to be modified)
         * returns: void
         */
        this.myMap.get(ID1).addNeighbor(ID2, cost);
        this.myMap.get(ID2).addNeighbor(ID1, cost);
    }
}
