package edu.wpi.MochaManticores;

/**
 * This is a POI subclass containing information
 * occupancy information for parking spaces
 * @author aksil
 */
public class ParkingSpace extends POI {
    //Declare instance variables
    private boolean isOccupied;     //records whether or not the space is occupied

    //Constructor
    public ParkingSpace(int ID, ConnectedNodes neighbors, int clearance, String name, boolean isOccupied) {
        super(ID, neighbors, clearance, name);
        this.isOccupied = isOccupied;
    }

    public boolean checkOccupied() {
        /**
         * function: checkOccupied()
         * usage: function to check whether or not a ParkingSpace
         *        is occupied
         * inputs: none
         * returns: boolean isOccupied
         */
        return this.isOccupied;
    }
}
