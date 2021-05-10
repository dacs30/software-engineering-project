package edu.wpi.MochaManticores.Algorithms;

import edu.wpi.MochaManticores.Nodes.MapSuper;
import edu.wpi.MochaManticores.Nodes.NodeSuper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * PathPlanner superclass containing variables and helper functions for subclasses
 * @author aksil
 * @author gatcht
 */
public class PathPlannerSuper {
    //Declare instance variables
    protected final HashMap<String, NodeSuper> nodes;     //The HashMap containing all nodes on the map
    protected PriorityQueue<AStarNode> horizon;           //Contains unvisited nodes adjacent to visited ones (lowest cost first)
    protected HashMap<String, AStarNode> visitedNodes;    //Contains all visited nodes
    protected HashMap<String, AStarNode> horizonNodes;    //Same as horizon but as a list to enable object retrieval
    protected AStarNode currentNode;                      //Current node being explored
    protected NodeSuper target;                           //The node being searched for
    protected String condition;                           //Conditions which must be met by nodes in the path

    //Constructor
    public PathPlannerSuper() {
        this.nodes = MapSuper.getMap();
    }

    /**
     * method: traceBack()
     * usage: retraces the path formed in findRoute(), removing unwanted elevator and staircase nodes
     * @return route LinkedList containing all node IDs in the route
     */
    protected LinkedList<String> traceBack(String startID) {
        //Initialize local variables
        LinkedList<String> route = new LinkedList<>();  //Initialized as empty
        String traceBackNode = startID;          //Last ID on the route list will be the destination
        boolean lastLevelChange = false;  //This variable set to true when the traceBackNode is of type "STAI" or "ELEV"

        while (true) {
            //Check if current node is a staircase or elevator
            String currType = this.nodes.get(traceBackNode).getType();
            boolean currLevelChange = (currType.equals("STAI") || currType.equals("ELEV"));

            //Add the node to the route unless the last node and current node are both staircases or elevators
            if (!(lastLevelChange && currLevelChange)) {
                route.addFirst(traceBackNode);
                traceBackNode = this.visitedNodes.get(traceBackNode).getLastID();
                if(traceBackNode == "NONE") {
                    break;  //Once the start node has been found, break the loop
                }
            }

            //If both are elevators or staircases, test whether or not this node should be included in the path
            else {

                //Declare local variables
                String currNode = traceBackNode;    //New variable to store the current node
                traceBackNode = this.visitedNodes.get(traceBackNode).getLastID();   //update traceback node

                //Check whether or not this is the origin node
                if(traceBackNode == "NONE") {
                    route.addFirst(currNode);   //If so, add it to the path and break the loop
                    break;
                }

                //Otherwise, check whether or not the next node is also a staircase or elevator
                else {
                    String nextType = this.nodes.get(traceBackNode).getType();
                    boolean nextLevelChange = (nextType.equals("STAI") || nextType.equals("ELEV"));
                    if (!nextLevelChange) {
                        //If the next node isn't a continuation of the staircase or elevator, add current node
                        route.addFirst(currNode);
                    }
                }
            }
            lastLevelChange = currLevelChange;  //Update the lastLevelChange variable
        }
        return route;   //return the path (ordered target -> start)
    }

    /**
     * method: isAccessible()
     * usage: Checks the accessibility of the given input node
     * @param neighbor NodeSuper representing the node to be checked
     * @return isAccessible boolean representing accessibility
     */
    protected boolean isAccessible(NodeSuper neighbor) {
        //Tests whether or not a node is accessible to the user
        boolean isAccessible = false;
        switch (this.condition) {
            case "covid":
                if (!neighbor.isRestricted() && !neighbor.isCovid()) {
                    isAccessible = true;
                }
                break;
            case "covidHandicap":
                if (!neighbor.isRestricted() && !neighbor.isCovid() && neighbor.isHandicap()) {
                    isAccessible = true;
                }
                break;
            case "none":
                isAccessible = true;
                break;
            case "handicap":
                if (neighbor.isHandicap()) {
                    isAccessible = true;
                }
                break;
            case "publicOnly":
                if (!neighbor.isRestricted()) {
                    isAccessible = true;
                }
                break;
            case "publicHandicap":
                if (neighbor.isHandicap() && !neighbor.isRestricted()) {
                    isAccessible = true;
                }
                break;
        }
        return isAccessible;
    }

    /**
     * function: floorCMP()
     * usage: determines whether traversing floors in a direction is up or down
     * inputs: curF the id of the current floor, nextF the id of the next floor
     * returns: true if up, false if down
     */
    protected boolean floorCMP(String curF, String nextF){
        int ffirst;
        int fsecond;
        if (curF.equals("L2")){
            ffirst = 1;
        } else if (curF.equals("L1")){
            ffirst = 2;
        } else {
            ffirst = Integer.parseInt(curF) + 2;
        }
        if (nextF.equals("L2")){
            fsecond = 1;
        } else if (nextF.equals("L1")){
            fsecond = 2;
        } else {
            fsecond = Integer.parseInt(nextF) + 2;
        }
        return ffirst < fsecond;
    }

    /**
     *  function: isLeft()
     *  usage: determines if a node represents a left turn
     *  inputs: curr the id of the current NodeSuper, n the id of the next NodeSuper, x either 1, 0, or -1 based on if
     *          the current direction being traveled has an increasing, unchanging or decreasing xcoordinate, y either 1, 0, or
     *          -1 based on if the current direction being traveled has an increasing, unchanging or decreasing ycoordinate
     *  returns: true if turning towards the next node is a left turn
     */
    protected boolean isLeft(String curr, String n, int x, int y){

        int xn = nodes.get(n).getXcoord() - nodes.get(curr).getXcoord();
        int yn = nodes.get(n).getYcoord() - nodes.get(curr).getYcoord();

        if (xn > 0){
            xn = 1;
        } else if(xn < 0){
            xn = -1;
        } else {
            xn = 0;
        }
        if (yn > 0){
            yn = 1;
        } else if(yn < 0){
            yn = -1;
        } else {
            yn = 0;
        }

        if (x == 1 && y == 0){
            return yn == 1;
        } else if (x == 1 && y == 1){
            return (xn == 0 && yn == 1) || (xn == -1 && yn == 1) || (xn == -1 && yn == 0);
        } else if (x == 0 && y == 1){
            return xn == -1;
        } else if (x == -1 && y == 1){
            return (xn == 0 && yn == -1) || (xn == -1 && yn == -1) || (xn == -1 && yn == 0);
        } else if (x == -1 && y == 0){
            return yn == -1;
        } else if (x == -1 && y == -1){
            return (xn == 0 && yn == -1) || (xn == 1 && yn == -1) || (xn == 1 && yn == 0);
        } else if (x == 0 && y == -1){
            return xn == 1;
        } else if (x == 1 && y == -1){
            return (xn == 1 && yn == 0) || (xn == 1 && yn == 1) || (xn == 0 && yn == 1);
        }
        return false;
    }

    /**
     *  function: is Right()
     *  usage: determines if a node represents a right turn
     *  inputs: curr the id of the current NodeSuper, n the id of the next NodeSuper, x either 1, 0, or -1 based on if
     *          the current direction being traveled has an increasing, unchanging or decreasing xcoordinate, y either 1, 0, or
     *          -1 based on if the current direction being traveled has an increasing, unchanging or decreasing ycoordinate
     *  returns: true if turning towards the next node is a right turn
     */
    public boolean isRight(String curr, String n, int x, int y){

        int xn = nodes.get(n).getXcoord() - nodes.get(curr).getXcoord();
        int yn = nodes.get(n).getYcoord() - nodes.get(curr).getYcoord();

        if (xn > 0){
            xn = 1;
        } else if(xn < 0){
            xn = -1;
        } else {
            xn = 0;
        }
        if (yn > 0){
            yn = 1;
        } else if(yn < 0){
            yn = -1;
        } else {
            yn = 0;
        }

        if (x == 1 && y == 0){
            return yn == -1;
        } else if (x == 1 && y == 1){
            return (xn == 0 && yn == -1) || (xn == -1 && yn == -1) || (xn == 1 && yn == 0);
        } else if (x == 0 && y == 1){
            return xn == 1;
        } else if (x == -1 && y == 1){
            return (xn == 0 && yn == 1) || (xn == 1 && yn == 1) || (xn == 1 && yn == 0);
        } else if (x == -1 && y == 0){
            return yn == 1;
        } else if (x == -1 && y == -1){
            return (xn == 0 && yn == 1) || (xn == -1 && yn == 1) || (xn == -1 && yn == 0);
        } else if (x == 0 && y == -1){
            return xn == -1;
        } else if (x == 1 && y == -1){
            return (xn == -1 && yn == 0) || (xn == -1 && yn == -1) || (xn == 0 && yn == -1);
        }
        return false;
    }
}
