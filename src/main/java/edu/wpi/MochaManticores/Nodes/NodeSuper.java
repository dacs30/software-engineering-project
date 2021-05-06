package edu.wpi.MochaManticores.Nodes;

import java.util.HashMap;
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
    private String floor;                       //Floor number of the node
    private String building;                    //Building the node is in
    private String longName;                    //Name of the node
    private String shortName;                   //Abbreviated node name
    private String nodeID;                      //Unique node identifier
    private String nodeType;                    //Type of node (elevator, door, etc.)
    private final String teamAssigned = "m";    //Node created by D21 team M
    private VertexList neighbors;               //A list of connected nodes with vertex costs
    private boolean isHandicap;
    private boolean isRestricted;
    private boolean isCovid;

    //Constructor
    public NodeSuper(int xcoord, int ycoord, String floor, String building, String longName, String shortName, String nodeID, String nodeType, VertexList neighbors) {
        this.xcoord = xcoord;
        this.ycoord = ycoord;
        this.floor = floor;
        this.building = building;
        this.longName = longName;
        this.shortName = shortName;
        this.nodeID = nodeID;
        this.nodeType = nodeType;
        this.neighbors = neighbors;
        this.isHandicap = !nodeType.equals("STAI");
        this.isRestricted = false;
        this.isCovid = false;
        if(neighbors == null){
            this.neighbors = new VertexList(new HashMap<>());
        }

    }

    public boolean isCovid() {
        return isCovid;
    }

    public void setCovid(boolean covid) {
        isCovid = covid;
    }

    /**
     * function: getNeighbors()
     * usage: function to retrieve the private neighbors of the node
     * inputs: none
     * returns: Stack neighbors (IDs of all adjacent nodes)
     */
    public Set<String> getNeighbors() {
        return this.neighbors.getKeyIDs();
    }

    public VertexList getVertextList(){
        return this.neighbors;
    }
    /**
     * function: addNeighbor()
     * usage: adds a new neighborID to the stack
     * inputs: String newID (ID of neighbor to be added)
     *         int newCost (cost to move to the new node)
     * returns: void
     */
    public void addNeighbor(String newID, int newCost) {
        this.neighbors.newNeighbor(newID, newCost);
    }

    /**
     * function: delNeighbor()
     * usage: removes a neighborID from the stack
     * inputs: String delID (ID of neighbor to be removed)
     * returns: void
     */
    public void delNeighbor(String delID) {
        this.neighbors.removeNeighbor(delID);
    }

    /**
     * function: editCost()
     * usage: function to edit the cost to travel to a neighboring node
     * inputs: String ID (ID of neighbor)
     *         Integer newCost (new cost)
     * returns: void
     */
    public void editCost(String ID, Integer newCost) {
        this.neighbors.setCost(ID, newCost);
    }

    /**
     * function: getCost()
     * usage: returns cost to travel to a neighboring node
     * inputs: String ID (ID of neighbor)
     * returns: Integer cost (the cost to travel to the node)
     */
    public int getCost(String ID) {
        return this.neighbors.getCost(ID);
    }

    /**
     * function: getXcoord()
     * usage: returns the X coordinate of the MapNode
     * inputs: none
     * returns: int xcoord (X axis coordinate of this node)
     */
    public int getXcoord() {
        return this.xcoord;
    }

    /**
     * function: getYcoord()
     * usage: returns the Y coordinate of the MapNode
     * inputs: none
     * returns: int ycoord (Y axis coordinate of this node)
     */
    public int getYcoord() {
        return this.ycoord;
    }

    /**
     * function: setCoords()
     * usage: sets the X and Y coordinates of an existing MapNode
     * inputs: int xcoord (new X coordinate)
     *         int ycoord (new Y coordinate)
     * returns: void
     */
    public void setCoords(int xcoord, int ycoord) {
        this.xcoord = xcoord;
        this.ycoord = ycoord;
    }

    /**
     * function: getFloor()
     * usage: returns the floor number of the MapNode
     * inputs: none
     * returns: String floor (floor of this node)
     */
    public String getFloor() {
        return this.floor;
    }

    /**
     * function: setFloor()
     * usage: replaces the floor number of the MapNode
     * inputs: int newFloor (the new floor of the node)
     * returns: void
     */
    public void setFloor(String newFloor) {
        this.floor = newFloor;
    }

    /**
     * function: getBuilding()
     * usage: returns the building of the MapNode
     * inputs: none
     * returns: String building (building of this node)
     */
    public String getBuilding() {
        return this.building;
    }

    /**
     * function: setBuilding()
     * usage: replaces the building of the MapNode
     * inputs: String newBuilding (the new building of the node)
     * returns: void
     */
    public void setBuilding(String newBuilding) {
        this.building = newBuilding;
    }

    /**
     * function: getLongName()
     * usage: returns the name of the MapNode
     * inputs: none
     * returns: String longName (name of this node)
     */
    public String getLongName() {
        return this.longName;
    }

    /**
     * function: setLongName()
     * usage: replaces the long name of the MapNode
     * inputs: String newLong (the new name of the node)
     * returns: void
     */
    public void setLongName(String newLong) {
        this.longName = newLong;
    }

    /**
     * function: getShortName()
     * usage: returns the short name of the MapNode
     * inputs: none
     * returns: String shortName (name of this node)
     */
    public String getShortName() {
        return this.shortName;
    }

    /**
     * function: setShortName()
     * usage: replaces the short name of the MapNode
     * inputs: String newShort (the new name of the node)
     * returns: void
     */
    public void setShortName(String newShort) {
        this.shortName = newShort;
    }

    /**
     * function: getID()
     * usage: returns the ID of the MapNode
     * inputs: none
     * returns: String nodeID (MapNode ID)
     */
    public String getID() {
        return this.nodeID;
    }

    /**
     * function: setID()
     * usage: replaces the ID of the MapNode
     * inputs: String newID (the new ID of the MapNode)
     * returns: void
     */
    public void setID(String newID) {
        this.nodeID = newID;
    }

    /**
     * function: getType()
     * usage: returns the type of the MapNode
     * inputs: none
     * returns: String nodeType (type of node)
     */
    public String getType() {
        return this.nodeType;
    }

    /**
     * function: setType()
     * usage: replaces the type of the MapNode
     * inputs: String newType (the new type of the MapNode)
     * returns: void
     */
    public void setType(String newType) {
        this.nodeID = newType;
    }

    public boolean isRestricted() {
        return isRestricted;
    }

    public boolean isHandicap() {
        return isHandicap;
    }
}
