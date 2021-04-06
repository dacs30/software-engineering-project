package edu.wpi.MochaManticores;

import java.util.Stack;

/**
 * This is a node class to contain information
 * for the occupancy grid of the hospital used by
 * the navigation algorithms.
 * @author aksil
 */
public class MapNode {
    //Declare instance variables
    public int ID;              //Node ID (for hashmap)
    private Stack neighborID;   //Neighbors' Node IDs
    private int clearance;      //Determines accessibility of node

    public MapNode(int ID, Stack neighborID, int clearance) {
    }

    public Stack getNeighbors() {
        /**
         * function: getNeighbors()
         * usage: function to retrieve the private neighbors of the node
         * inputs: none
         * returns: Stack neighbors (IDs of all adjacent nodes)
         */
        return neighborID;
    }

    public void addNeighbor(int newID) {
        /**
         * function: addNeighbor()
         * usage: adds a new neighborID to the stack
         * inputs: int newID (ID of neighbor to be added)
         * returns: void
         */
        this.neighborID.add(newID);
    }

    public void delNeighbor(int delID) {
        /**
         * function: delNeighbor()
         * usage: removes a neighborID from the stack
         * inputs: int delID (ID of neighbor to be removed)
         * returns: void
         */
        this.neighborID.remove(delID);
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
