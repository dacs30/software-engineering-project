package edu.wpi.MochaManticores.Nodes;

/**
 * This is a NodeSuper subclass containing
 * occupancy information for parking spaces
 * @author aksil
 */
public class ParkingSpot extends NodeSuper{
    //Declare instance variables
    private boolean isOccupied;     //Boolean to track parking spot availability
    private boolean isHandicap;     //Boolean to track parking spot accessibility

    //Constructor
    public ParkingSpot(int xcoord, int ycoord, String floor, String building, String longName, String shortName, String nodeID, String nodeType, VertexList neighbors, boolean isOccupied, boolean isHandicap) {
        super(xcoord, ycoord, floor, building, longName, shortName, nodeID, nodeType, neighbors);
        this.isOccupied = isOccupied;
        this.isHandicap = isHandicap;
    }

    /**
     * function: checkOccupied()
     * usage: function to check whether or not a ParkingSpace
     *        is occupied
     * inputs: none
     * returns: boolean isOccupied
     */
    public boolean checkOccupied() {
        return this.isOccupied;
    }

    /**
     * function: setOccupied()
     * usage: function set the occupancy of a spot
     * inputs: boolean isOccupied (whether or not the space is full)
     * returns: void
     */
    public void setOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    /**
     * function: checkHandicap()
     * usage: function to check whether or not a ParkingSpace
     *        is handicap accessible
     * inputs: none
     * returns: boolean isHandicap
     */
    public boolean checkHandicap() {
        return this.isHandicap;
    }

    /**
     * function: setHandicap()
     * usage: function set the accessibility of a spot
     * inputs: boolean isHandicap (whether or not the space is handicap accessible)
     * returns: void
     */
    public void setHandicap(boolean isHandicap) {
        this.isHandicap = isHandicap;
    }
}
