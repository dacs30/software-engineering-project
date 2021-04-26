package edu.wpi.MochaManticores.Algorithms;

import edu.wpi.MochaManticores.Nodes.MapSuper;
import edu.wpi.MochaManticores.Nodes.NodeSuper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * GBF implementation using a priority queue to find the most efficient path to the target node
 * @author aksil
 */
public class GBF implements PathPlanning{
    //Declare instance variables
    private HashMap<String, NodeSuper> nodes;           //The HashMap containing all nodes on the map
    private PriorityQueue<AStarNode> horizon;           //Contains unvisited nodes adjacent to visited ones (lowest cost first)
    private HashMap<String, AStarNode> visitedNodes;    //Contains all visited nodes
    private HashMap<String, AStarNode> horizonNodes;    //Same as horizon but as a list to enable object retrieval
    private AStarNode currentNode;                      //Current node being explored
    private NodeSuper target;                           //The node being searched for
    private String condition;                           //Conditions which must be met by nodes in the path

    //Constructor
    public GBF() {
        this.nodes = MapSuper.getMap();
    }

    /**
     * method: multiStopRoute()
     * usage: takes in a linked list of nodes and returns a linked list
     *        of node IDs representing a path through all input nodes
     * @param stops LinkedList containing start and target nodes in order
     * @param condition condition code for the path "publicOnly, handicap, none, publicHandicap"
     * @return path LinkedList containing all node IDs in the route
     */
    @Override
    public LinkedList<String> multiStopRoute(LinkedList<NodeSuper> stops, String condition){
        this.condition = condition;
        LinkedList<String> path = new LinkedList<>();
        if (stops.size() == 1){
            path.add(stops.getFirst().getID());
        } else if(stops.size() == 2){
            path = findRoute(stops.getFirst(), stops.getLast());
        } else {
            path = findRoute(stops.pop(), stops.getFirst());
            for (int i = 0; i < stops.size() - 1; i++) {
                path.removeLast();
                path.addAll(findRoute(stops.pop(), stops.getFirst()));
            }
        }
        return path;
    }

    /**
     * method: findRoute()
     * usage: Uses a Greedy Best First Search algorithm to calculate the most efficient route to the target
     * @param start the node of origin
     * @param target the node to search for
     * @return a LinkedList of Strings containing the IDs of the nodes from the first node to the target
     */
    @Override
    public LinkedList<String> findRoute(NodeSuper start, NodeSuper target) {
        //Initialize class variables
        this.horizon = new PriorityQueue<AStarNode>(10, new NodeComparator());  //Sorts using NodeComparator
        this.visitedNodes = new HashMap<>();                                                //Initialized as empty
        this.horizonNodes = new HashMap<>();                                                //Initialized as empty
        this.currentNode = new AStarNode(start, target, "NONE");         //Initialized to start node
        this.target = target;                           //Initializes the target variable

        //Initialize local variables
        LinkedList<String> route = new LinkedList<>();  //Initialized as empty
        String traceBackNode = target.getID();          //First ID on the route list will be the target node

        //Explore the horizon until the target node is found
        while(true) {
            //checkNeighbors adds to the horizon and compares routes, returns false unless the target is found
            this.visitedNodes.put(this.currentNode.getID(), this.currentNode);
            if(checkNeighbors()) {
                break;
            }
            this.currentNode = this.horizon.poll();
            this.horizonNodes.remove(this.currentNode.getID());
        }

        //Once the target has been found, retrace steps back to the start node
        while(true) {
            route.addFirst(traceBackNode);
            traceBackNode = this.visitedNodes.get(traceBackNode).getLastID();
            //Once the start node has been found, break the loop
            if(traceBackNode == "NONE") {
                break;
            }
        }
        //return the path (ordered target -> start)
        return route;
    }

    /**
     * method: checkNeighbors()
     * usage: explores the neighbors of the currentNode, updating path costs and adding to the horizon
     * @return a string containing either the target node ID or "false" if the target has not been found
     */
    private boolean checkNeighbors() {
        //Initialize local variables
        String currentID = this.currentNode.getID();
        boolean located = false;

        //Loop through all nodes connected to the current node
        for(String ID : this.nodes.get(currentID).getNeighbors()) {

            //Initialize more local variables
            NodeSuper neighbor = this.nodes.get(ID);    //Stores the neighbor node as a local variable for efficiency

            //Tests whether or not a node is accessible to the user
            boolean isAccessible = false;
            switch(this.condition) {
                case "none":
                    isAccessible = true;
                    break;
                case "handicap":
                    if(neighbor.isHandicap()) {isAccessible = true;}
                    break;
                case "publicOnly":
                    if(!neighbor.isRestricted()) {isAccessible = true;}
                    break;
                case "publicHandicap":
                    if(neighbor.isHandicap() && !neighbor.isRestricted()) {isAccessible = true;}
                    break;
            }

            //If the node is accessible, continue
            if(isAccessible) {
                AStarNode newNode;

                newNode = new AStarNode(neighbor, this.target, currentID);    //Makes a new A* node

                //If the node hasn't been visited or added to the horizon, check to see if it's the target node and add it to the horizon
                if(!(this.horizon.contains(ID) || this.visitedNodes.containsKey(ID))) {
                    this.horizon.add(newNode);
                    this.horizonNodes.put(ID, newNode);

                    //Check whether or not it's the target node
                    if(neighbor.getID().equals(this.target.getID())) {
                        //Before the loop breaks, add the target node to visitedNodes (prevents an issue in the next loop)
                        this.visitedNodes.put(ID, newNode);
                        //Set the found flag to break the search loop
                        located = true;
                    }
                }
            }
        }
        //return true if one of the neighbors is the target, false otherwise
        return located;
    }
}
