package edu.wpi.MochaManticores;

import java.util.Set;

/**
 * This is a node class to contain information
 * for the occupancy grid of the hospital used by
 * the navigation algorithms.
 * @author aksil
 */
public class MapNode {
    //Declare instance variables
    public int ID;              //Node ID (for hashmap)
    private ConnectedNodes neighbors;   //Neighbors' Node IDs and costs
    private int clearance;      //Determines accessibility of node

    public MapNode(int ID, ConnectedNodes neighbors, int clearance) {
    }

    public Set getNeighbors() {
        /**
         * function: getNeighbors()
         * usage: function to retrieve the private neighbors of the node
         * inputs: none
         * returns: Stack neighbors (IDs of all adjacent nodes)
         */
        return neighbors.getKeyIDs();
    }

    public void addNeighbor(int newID, int newCost) {
        /**
         * function: addNeighbor()
         * usage: adds a new neighborID to the stack
         * inputs: int newID (ID of neighbor to be added)
         *         int newCost (cost to move to the new node)
         * returns: void
         */
        this.neighbors.newNeighbor(newID, newCost);
    }

    public void delNeighbor(int delID) {
        /**
         * function: delNeighbor()
         * usage: removes a neighborID from the stack
         * inputs: int delID (ID of neighbor to be removed)
         * returns: void
         */
        this.neighbors.removeNeighbor(delID);
    }

    public int getClearance() {
        /**
         * function: getClearance()
         * usage: returns the clearance of the MapNode
         * inputs: none
         * returns: int clearance (determines who can travel through this node)
         */
        return clearance;
    }
}
