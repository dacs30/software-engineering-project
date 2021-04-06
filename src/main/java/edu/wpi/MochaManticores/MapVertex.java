package edu.wpi.MochaManticores;

/**
 * This is a vertex class meant to contain
 * the cost to travel between two nodes
 * @author aksil
 */
public class MapVertex {
    //Declare instance variables
    private int nodeID1;
    private int nodeID2;
    private int cost;

    public MapVertex(int nodeID1, int nodeID2, int cost) {
    }

    public int getCost() {
        /**
         * function: getCost()
         * usage: function to retrieve the private cost of the vertex
         * inputs: none
         * returns: int cost (cost to cross the vertex)
         */
        return cost;
    }

    public void setCost(int newCost) {
        /**
         * function: setCost()
         * usage: edits the cost cross an existing vertex
         * inputs: int newCost (desired new cost)
         * returns: void
         */
        this.cost = newCost;
    }
}
