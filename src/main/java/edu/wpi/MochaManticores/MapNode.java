package edu.wpi.MochaManticores;

import java.util.Set;

/**
 * This is a node class to contain information
 * for the NodeMap of the hospital used by
 * the navigation algorithms.
 * @author aksil
 */
public class MapNode {
    //Declare instance variables
    public int ID;                      //Node ID (for hashmap)
    private ConnectedNodes neighbors;   //Neighbors' Node IDs and costs
    private int clearance;              //Determines accessibility of node
    private int xCoord;                 //X axis coordinate of the node
    private int yCoord;                 //Y axis coordinate of the node
    private int floor;                  //Floor which the node is on

    //Constructor
    public MapNode(int ID, ConnectedNodes neighbors, int clearance, int xCoord, int yCoord, int floor) {
        this.ID = ID;
        this.neighbors = neighbors;
        this.clearance = clearance;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.floor = floor;
    }

    public Set<Integer> getNeighbors() {
        /**
         * function: getNeighbors()
         * usage: function to retrieve the private neighbors of the node
         * inputs: none
         * returns: Stack neighbors (IDs of all adjacent nodes)
         */
        return this.neighbors.getKeyIDs();
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

    public void editCost(Integer ID, Integer newCost) {
        /**
         * function: editCost()
         * usage: function to edit the cost to travel to a neighboring node
         * inputs: Integer ID (ID of neighbor)
         *         Integer newCost (new cost)
         * returns: void
         */
        this.neighbors.setCost(ID, newCost);
    }

    public int getCost(Integer ID) {
        /**
         * function: getCost()
         * usage: returns cost to travel to a neighboring node
         * inputs: Integer ID (ID of neighbor)
         * returns: Integer cost (the cost to travel to the node)
         */
        return this.neighbors.getCost(ID);
    }

    public int getClearance() {
        /**
         * function: getClearance()
         * usage: returns the clearance of the MapNode
         * inputs: none
         * returns: int clearance (determines who can travel through this node)
         */
        return this.clearance;
    }

    public int getXCoord() {
        /**
         * function: getXCoord()
         * usage: returns the X coordinate of the MapNode
         * inputs: none
         * returns: int xCoord (X axis coordinate of this node)
         */
        return this.xCoord;
    }

    public int getYCoord() {
        /**
         * function: getYCoord()
         * usage: returns the Y coordinate of the MapNode
         * inputs: none
         * returns: int yCoord (Y axis coordinate of this node)
         */
        return this.yCoord;
    }

    public void setCoords(int xCoord, int yCoord) {
        /**
         * function: setCoords()
         * usage: sets the X and Y coordinates of an existing MapNode
         * inputs: int xCoord (new X coordinate)
         *         int yCoord (new Y coordinate)
         * returns: void
         */
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public int getFloor() {
        /**
         * function: getFloor()
         * usage: returns the floor number of the MapNode
         * inputs: none
         * returns: int floor (floor of this node)
         */
        return this.floor;
    }

    public void setFloor(int newFloor) {
        /**
         * function: setFloor()
         * usage: replaces the floor number of the MapNode
         * inputs: int newFloor (the new floor of the node)
         * returns: void
         */
        this.floor = newFloor;
    }
}
