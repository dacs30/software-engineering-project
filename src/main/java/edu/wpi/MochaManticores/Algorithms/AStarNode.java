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

    //A* Constructor
    public AStarNode(NodeSuper node, NodeSuper target, String lastID, int costToReach) {
        this.ID = node.getID();
        this.lastID = lastID;
        this.costToReach = costToReach;
        this.estimatedCost = calcGBF(node, target);
        this.priority = costToReach + estimatedCost;
    }

    //Dijkstra's Constructor
    public AStarNode(NodeSuper node, String lastID, int costToReach) {
        this.ID = node.getID();
        this.lastID = lastID;
        this.costToReach = costToReach;
        this.estimatedCost = 0;
        this.priority = costToReach;
    }

    //GBF Constructor
    public AStarNode(NodeSuper node, NodeSuper target, String lastID) {
        this.ID = node.getID();
        this.lastID = lastID;
        this.costToReach = 0;
        this.estimatedCost = calcGBF(node, target);
        this.priority = estimatedCost;
    }

    /**
     * method: compareCosts()
     * usage: compares the current cost to reach this node with a new possible route,
     *        alters the node if a new route is selected. Used by A*
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

    /**
     * function: calcGBF()
     * usage: returns the estimated cost between two nodes,
     *        favoring nodes in the same building and on the same floor
     * inputs: NodeSuper firstNode (one of the two nodes)
     *         NodeSuper secondNode (the other node)
     * returns: int heuristic (the estimated cost to travel from 1 node to the other)
     */
    public static int calcGBF(NodeSuper firstNode, NodeSuper secondNode) {
        //Establish function constants
        int buildingOffset = 100;   //determines how much the algorithm prefers nodes in the same building as the target node
        int floorOffset = 50;       //determines how much the algorithm prefers nodes on the same floor as the target node

        //calculate euclidean distance between nodes
        int heuristic = (int) Math.round(Math.sqrt(Math.pow(firstNode.getXcoord()-secondNode.getXcoord(), 2) + Math.pow(firstNode.getYcoord()-secondNode.getYcoord(), 2)));

        //add offset cost for being outside of the target building
        if(!firstNode.getBuilding().equals(secondNode.getBuilding())) {
            heuristic += buildingOffset;
            //if inside the target building, add offset for being on a different floor
        } else if(!firstNode.getFloor().equals(secondNode.getFloor())) {
            heuristic += floorOffset;
        }
        return heuristic;
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
