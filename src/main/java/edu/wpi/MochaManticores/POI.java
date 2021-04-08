package edu.wpi.MochaManticores;

/**
 * This is a point of interest class intended
 * to contain additional information about
 * locations on the map
 * @author aksil
 */
public class POI extends MapNode {
    //Declare instance variables
    String name;    //name of the location

    //Constructor
    public POI(int ID, ConnectedNodes neighbors, int clearance, String name) {
        super(ID, neighbors, clearance);
        this.name = name;
    }

    public String getName() {
        /**
         * function: getName()
         * usage: function to retrieve the name of the node
         * inputs: none
         * returns: String name (name of the POI)
         */
        return this.name;
    }
}
