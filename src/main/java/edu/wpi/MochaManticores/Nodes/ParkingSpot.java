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
    public ParkingSpot(int xcoord, int ycoord, int floor, String building, String longName, String shortName, String nodeID, VertexList neighbors, boolean isOccupied, boolean isHandicap) {
        super(xcoord, ycoord, floor, building, longName, shortName, nodeID, neighbors);
        this.isOccupied = isOccupied;
        this.isHandicap = isHandicap;
    }
}
