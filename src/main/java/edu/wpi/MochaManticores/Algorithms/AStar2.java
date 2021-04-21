package edu.wpi.MochaManticores.Algorithms;

import edu.wpi.MochaManticores.Nodes.MapSuper;
import edu.wpi.MochaManticores.Nodes.NodeSuper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * AStar implementation using a priority queue to find the most efficient path to the target node
 * @author aksil
 * @author gatch
 */
public class AStar2 {
    //Declare instance variables
    private HashMap<String, NodeSuper> nodes;           //The HashMap containing all nodes on the map
    private PriorityQueue<AStarNode> horizon;           //Contains unvisited nodes adjacent to visited ones (lowest cost first)
    private HashMap<String, AStarNode> visitedNodes;    //Contains all visited nodes
    private HashMap<String, AStarNode> horizonNodes;    //Same as horizon but as a list to enable object retrieval
    private AStarNode currentNode;                      //Current node being explored
    private NodeSuper target;                           //The node being searched for

    //Constructor
    public AStar2() {
        this.nodes = MapSuper.getMap();
    }

    /**
     * method: multiStopRoute()
     * usage: takes in a linked list of nodes and returns a linked list
     *        of node IDs representing a path through all input nodes
     * @param stops LinkedList containing start and target nodes in order
     * @return path LinkedList containing all node IDs in the route
     */
    public LinkedList<String> multiStopRoute(LinkedList<NodeSuper> stops){
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
     * usage: Uses an A* algorithm to calculate the most efficient route to the target
     *        with exploration guided by GBF
     * @param start the node of origin
     * @param target the node to search for
     * @return an LinkedList of Strings containing the IDs of the nodes from the first node to the target
     */
    public LinkedList<String> findRoute(NodeSuper start, NodeSuper target) {
        //Initialize class variables
        this.horizon = new PriorityQueue<AStarNode>(10, new NodeComparator());  //Sorts using NodeComparator
        this.visitedNodes = new HashMap<>();                                                //Initialized as empty
        this.horizonNodes = new HashMap<>();                                                //Initialized as empty
        this.currentNode = new AStarNode(start, target, "NONE", 0);         //Initialized to start node
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
     * @return a boolean representing whether or not the target has been found
     */
    private boolean checkNeighbors() {
        //Initialize local variables
        String currentID = this.currentNode.getID();
        boolean located = false;

        //Loop through all nodes connected to the current node
        for(String ID : this.nodes.get(currentID).getNeighbors()) {

            //Initialize more local variables
            NodeSuper neighbor = this.nodes.get(ID);    //Stores the neighbor node as a local variable for efficiency
            int travelCost = this.currentNode.getCost() + neighbor.getCost(currentID);  //cost from start -> neighbor
            AStarNode newNode = new AStarNode(neighbor, this.target, currentID, travelCost);    //Makes a new A* node

            //If the node has already been visited, see if the new path is faster
            if(this.visitedNodes.containsKey(ID)) {
                this.visitedNodes.get(ID).compareCosts(currentID, travelCost);
            }

            //Otherwise, if it's already on the horizon check to see if this new path is faster
            else if(this.horizon.contains(ID)) {
                if(this.horizonNodes.get(ID).getCost() > travelCost) {
                    //Update the horizonNodes list entry and replace the horizon entry
                    this.horizonNodes.get(ID).compareCosts(currentID, travelCost);
                    this.horizon.remove(ID);
                    this.horizon.add(newNode);
                }
            }

            //Otherwise, add the node to the horizon and check whether or not it's the target
            else {
                this.horizon.add(newNode);
                this.horizonNodes.put(ID, newNode);
                if(neighbor.getID().equals(this.target.getID())) {
                    //Before the loop breaks, add the target node to visitedNodes (prevents an issue in the next loop)
                    this.visitedNodes.put(ID, newNode);
                    //Set the found flag to break the search loop
                    located = true;
                }
            }
        }
        //return true if one of the neighbors is the target, false otherwise
        return located;
    }

    /**
     * function: calcHeuristic()
     * usage: returns the euclidean distance between two nodes,
     *        for use in establishing vertex cost
     * inputs: NodeSuper firstNode (one of the two nodes)
     *         NodeSuper secondNode (the other node)
     * returns: int heuristic (the estimated cost to travel from 1 node to the other)
     */
    public static int calcHeuristic(NodeSuper firstNode, NodeSuper secondNode) {
        //calculate euclidean distance between nodes
        return (int) Math.round(Math.sqrt(Math.pow(firstNode.getXcoord()-secondNode.getXcoord(), 2) + Math.pow(firstNode.getYcoord()-secondNode.getYcoord(), 2)));
    }
}
