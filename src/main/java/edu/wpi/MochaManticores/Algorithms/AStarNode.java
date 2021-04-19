package edu.wpi.MochaManticores.Algorithms;

import edu.wpi.MochaManticores.Nodes.NodeSuper;

/**
 * AStarNode is intended to house node information required for AStar2 path planning
 * @author aksil
 */
public class AStarNode{
    //Declare instance variables
    private String ID;          //Node ID
    private String lastID;      //ID of the node which leads to this node
    private int costToReach;    //the current lowest cost to reach this node
    private int estimatedCost;  //heuristic cost to reach the target node
    private int priority;       //the estimated aggregate cost to reach the target node traveling through this node

    //Constructor
    public AStarNode(NodeSuper node, NodeSuper target, String lastID, int costToReach) {
        this.ID = node.getID();
        this.lastID = lastID;
        this.costToReach = costToReach;
        this.estimatedCost = AStar.calcGBF(node, target);
        this.priority = costToReach + estimatedCost;
    }

    /**
     * method: compareCosts()
     * usage: compares the current cost to reach this node with a new possible route,
     *        alters the node if a new route is selected
     * @param lastID
     * @param costToReach
     */
    public void compareCosts(String lastID, int costToReach) {
        if(costToReach < this.costToReach) {
            this.costToReach = costToReach;
            this.lastID = lastID;
            this.priority = this.costToReach + this.estimatedCost;
        }
    }

    /**
     * method: equals()
     * usage: allows the contains() method to be used to screen for nodes with a certain ID
     * @param o a String to compare to the node ID
     * @return a boolean representing whether or not the String is the node ID
     */
    @Override
    public boolean equals(Object o) {
        return ( ((String) o).equals(this.ID));
    }

    //getters and setters
    public String getLastID() {
        return this.lastID;
    }

    public String getID() {
        return this.ID;
    }

    public int getPriority() {
        return this.priority;
    }

    public int getCost() {
        return costToReach;
    }
}
