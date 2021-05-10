package edu.wpi.MochaManticores.Algorithms;

import edu.wpi.MochaManticores.Nodes.NodeSuper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Dijkstra's algorithm implementation using a priority queue to find the most efficient path to the target node
 * @author aksil
 */
public class Dijkstra extends PathPlannerSuper implements PathPlanning{
    //Constructor
    public Dijkstra() {
        super();
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
    public LinkedList<String> multiStopRoute(LinkedList<NodeSuper> stops, String condition) {
        this.condition = condition;
        LinkedList<String> path = new LinkedList<>();
        if (stops.size() == 1){
            path.add(stops.getFirst().getID());
        } else if(stops.size() == 2){
            path = findRoute(stops.getFirst(), stops.getLast());
        } else {
            path = findRoute(stops.pop(), stops.getFirst());
            int j = stops.size();
            for (int i = 0; i < j - 1; i++) {
                path.removeLast();
                path.addAll(findRoute(stops.pop(), stops.getFirst()));
            }
        }
        return path;
    }

    /**
     * method: findRoute()
     * usage: Uses Dijkstra's algorithm to calculate the most efficient route to the target
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
        this.currentNode = new AStarNode(start, "NONE", 0);         //Initialized to start node
        this.target = target;                           //Initializes the target variable

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

        //Once the target has been found, retrace steps back to the start node and return the path (target -> start)
        return traceBack(target.getID());
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

            if(isAccessible(neighbor)) {
                int travelCost = this.currentNode.getCost() + neighbor.getCost(currentID);  //cost from start -> neighbor

                AStarNode newNode = new AStarNode(neighbor, currentID, travelCost);    //Makes a new A* node

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
            }
        //return true if one of the neighbors is the target, false otherwise
        return located;
    }

    public LinkedList<LinkedList<String>> pathToText(LinkedList<String> path){
        LinkedList<LinkedList<String>> pathAsText = new LinkedList<>();
        if (path.isEmpty()){
            return pathAsText;
        } else  if (path.size() == 1){
            LinkedList<String> leg1 = new LinkedList<>();
            leg1.add("Floor: " + nodes.get(path.getFirst()).getFloor());
            leg1.add("You are at " + nodes.get(path.getFirst()).getShortName());
            pathAsText.add(leg1);
            return pathAsText;
        }
        int x = 0;
        int y = 0;
        int lx = 0;
        int ly = 0;
        boolean up = true;
        boolean stairs = true;
        while (path.size() > 1){
            pathAsText.add(new LinkedList<>());
            pathAsText.getLast().add("Floor: " + nodes.get(path.getFirst()).getFloor());

            while (nodes.get(path.getFirst()).getFloor().equals(nodes.get(path.get(1)).getFloor()) && path.size() > 1){

                x = nodes.get(path.get(1)).getXcoord() - nodes.get(path.getFirst()).getXcoord();
                y = nodes.get(path.get(1)).getYcoord() - nodes.get(path.getFirst()).getYcoord();

                if (x > 0){
                    x = 1;
                } else if(x < 0){
                    x = -1;
                } else {
                    x = 0;
                }
                if (y > 0){
                    y = 1;
                } else if(y < 0){
                    y = -1;
                } else {
                    y = 0;
                }

                if (lx == 0 && ly ==0){
                    lx = x;
                    ly = y;
                }
                if (x != lx || y != ly){
                    pathAsText.getLast().add("Head straight until you reach " + nodes.get(path.getFirst()).getShortName());
                    if (isLeft(nodes.get(path.getFirst()).getID(), nodes.get(path.get(1)).getID(), lx, ly)){
                        pathAsText.getLast().add("Then turn left");
                    }else if (isRight(nodes.get(path.getFirst()).getID(), nodes.get(path.get(1)).getID(), lx, ly)){
                        pathAsText.getLast().add("Then turn right");
                    }

                }

                path.removeFirst();
            }
            if(path.size() > 1) {
                up = floorCMP(nodes.get(path.getFirst()).getFloor(), nodes.get(path.get(1)).getFloor());
                stairs = nodes.get(path.getFirst()).getType().equals("STAI");
                if (up) {
                    if (stairs) {
                        pathAsText.getLast().add("Take the stairs up to floor " + nodes.get(path.get(1)).getFloor());
                    } else {
                        pathAsText.getLast().add("Take the elevator up to floor " + nodes.get(path.get(1)).getFloor());
                    }
                } else {
                    if (stairs) {
                        pathAsText.getLast().add("Take the stairs down to floor " + nodes.get(path.get(1)).getFloor());
                    } else {
                        pathAsText.getLast().add("Take the elevator down to floor " + nodes.get(path.get(1)).getFloor());
                    }
                }
                path.removeFirst();
                lx = 0;
                ly = 0;
            }

        }
        if (!pathAsText.getLast().getLast().equals("Head straight until you reach " + nodes.get(path.getFirst()).getShortName())){
            pathAsText.getLast().add("Head straight until you reach " + nodes.get(path.getFirst()).getShortName());
        }

        return pathAsText;
    }
}
