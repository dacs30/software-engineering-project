package edu.wpi.MochaManticores.Nodes;

import java.util.Set;

/**
 * This is a node class to contain information
 * for the NodeMap of the hospital used by
 * the navigation algorithms.
 * @author aksil
 */
public class NodeSuper {
    //Declare instance variables
    private int xcoord;                         //X coordinate of the node
    private int ycoord;                         //Y coordinate of the node
    private int floor;                          //Floor number of the node
    private String building;                    //Building the node is in
    private String longName;                    //Name of the node
    private String shortName;                   //Abbreviated node name
    private String nodeID;                      //Unique node identifier
    private final String teamAssigned = "m";    //Node created by D21 team M
    private VertexList neighbors;               //A list of connected nodes with vertex costs

    //Constructor
    public NodeSuper(int xcoord, int ycoord, int floor, String building, String longName, String shortName, String nodeID, VertexList neighbors) {
        this.xcoord = xcoord;
        this.ycoord = ycoord;
        this.floor = floor;
        this.building = building;
        this.longName = longName;
        this.shortName = shortName;
        this.nodeID = nodeID;
        this.neighbors = neighbors;
    }

    public Set<String> getNeighbors() {
        /**
         * function: getNeighbors()
         * usage: function to retrieve the private neighbors of the node
         * inputs: none
         * returns: Stack neighbors (IDs of all adjacent nodes)
         */
        return this.neighbors.getKeyIDs();
    }

    public void addNeighbor(String newID, int newCost) {
        /**
         * function: addNeighbor()
         * usage: adds a new neighborID to the stack
         * inputs: String newID (ID of neighbor to be added)
         *         int newCost (cost to move to the new node)
         * returns: void
         */
        this.neighbors.newNeighbor(newID, newCost);
    }

    public void delNeighbor(String delID) {
        /**
         * function: delNeighbor()
         * usage: removes a neighborID from the stack
         * inputs: String delID (ID of neighbor to be removed)
         * returns: void
         */
        this.neighbors.removeNeighbor(delID);
    }

    public void editCost(String ID, Integer newCost) {
        /**
         * function: editCost()
         * usage: function to edit the cost to travel to a neighboring node
         * inputs: String ID (ID of neighbor)
         *         Integer newCost (new cost)
         * returns: void
         */
        this.neighbors.setCost(ID, newCost);
    }

    public int getCost(String ID) {
        /**
         * function: getCost()
         * usage: returns cost to travel to a neighboring node
         * inputs: String ID (ID of neighbor)
         * returns: Integer cost (the cost to travel to the node)
         */
        return this.neighbors.getCost(ID);
    }

    public int getXcoord() {
        /**
         * function: getXcoord()
         * usage: returns the X coordinate of the MapNode
         * inputs: none
         * returns: int xcoord (X axis coordinate of this node)
         */
        return this.xcoord;
    }

    public int getYcoord() {
        /**
         * function: getYcoord()
         * usage: returns the Y coordinate of the MapNode
         * inputs: none
         * returns: int ycoord (Y axis coordinate of this node)
         */
        return this.ycoord;
    }

    public void setCoords(int xcoord, int ycoord) {
        /**
         * function: setCoords()
         * usage: sets the X and Y coordinates of an existing MapNode
         * inputs: int xcoord (new X coordinate)
         *         int ycoord (new Y coordinate)
         * returns: void
         */
        this.xcoord = xcoord;
        this.ycoord = ycoord;
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

    public String getBuilding() {
        /**
         * function: getBuilding()
         * usage: returns the building of the MapNode
         * inputs: none
         * returns: String building (building of this node)
         */
        return this.building;
    }

    public void setBuilding(String newBuilding) {
        /**
         * function: setBuilding()
         * usage: replaces the building of the MapNode
         * inputs: String newBuilding (the new building of the node)
         * returns: void
         */
        this.building = newBuilding;
    }

    public String getLongName() {
        /**
         * function: getLongName()
         * usage: returns the name of the MapNode
         * inputs: none
         * returns: String longName (name of this node)
         */
        return this.longName;
    }

    public void setLongName(String newLong) {
        /**
         * function: setLongName()
         * usage: replaces the long name of the MapNode
         * inputs: String newLong (the new name of the node)
         * returns: void
         */
        this.longName = newLong;
    }

    public String getShortName() {
        /**
         * function: getShortName()
         * usage: returns the short name of the MapNode
         * inputs: none
         * returns: String shortName (name of this node)
         */
        return this.shortName;
    }

    public void setShortName(String newShort) {
        /**
         * function: setShortName()
         * usage: replaces the short name of the MapNode
         * inputs: String newShort (the new name of the node)
         * returns: void
         */
        this.shortName = newShort;
    }

    public String getID() {
        /**
         * function: getID()
         * usage: returns the ID of the MapNode
         * inputs: none
         * returns: String nodeID (MapNode ID)
         */
        return this.nodeID;
    }

    public void setID(String newID) {
        /**
         * function: setID()
         * usage: replaces the ID of the MapNode
         * inputs: String newID (the new ID of the MapNode)
         * returns: void
         */
        this.nodeID = newID;
    }
}
