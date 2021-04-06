package edu.wpi.MochaManticores;

import java.util.Stack;

/**
 * This is a POI subclass containing information
 * occupancy information for parking spaces
 * @author aksil
 */
public class ParkingSpace extends POI {

    private boolean isOccupied;     //records whether or not the space is occupied

    public ParkingSpace(int ID, Stack neighborID, int clearance, String name, boolean isOccupied) {
        super(ID, neighborID, clearance, name);
    }

    public boolean checkOccupied() {
        /**
         * function: checkOccupied()
         * usage: function to check whether or not a ParkingSpace
         *        is occupied
         * inputs: none
         * returns: boolean isOccupied
         */
        return isOccupied;
    }
}
