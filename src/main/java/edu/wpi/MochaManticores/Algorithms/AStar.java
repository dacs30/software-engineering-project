package edu.wpi.MochaManticores.Algorithms;

import edu.wpi.MochaManticores.Nodes.MapSuper;
import edu.wpi.MochaManticores.Nodes.NodeSuper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

public class AStar {
    private HashMap<String, NodeSuper> nodes;
    private HashMap<String, DijkstraNode> fullSet;
    private HashMap<String, DijkstraNode> openSet;
    private HashMap<String, DijkstraNode> closedSet;

    public AStar(){
        this.nodes = MapSuper.getMap();
        this.fullSet = new HashMap<>();
        for (Object o : this.nodes.keySet()){
            String s = (String) o;
            fullSet.put(s, new DijkstraNode(s, null, 0));
        }
    }
    //potential fix for multi floor pathing:
    //get stair-elevator preference
    //if multi floor find closest stair-elevator
    //path start to stair-elevator
    //path elevator-end
    //add paths
    //redo overlap handling

    /**
     * function: path()
     * usage: finds the best path between any number of nodes based on the heuristic
     * input: a list of nodes that must be included in the path, with the first being the start point and the last being the end point
     *        the list MUST have at least 2 nodes in it
     * returns: a Linkedlist of the node IDs along the found path
     */
    public LinkedList<String> path(LinkedList<NodeSuper> stops){

        this.openSet = fullSet;
        this.closedSet = new HashMap<>();
        LinkedList<String> path = new LinkedList<>();
        path.addAll(shortestPath(stops.pop(), stops.getFirst()));
        if (path.size() > 2) { //fix for when there were entries missing along a correct path
            for (int i = 0; i < stops.size() - 2; i++) {
                path.removeLast();
                path.addAll(shortestPath(stops.pop(), stops.getFirst()));
            }
        }
        return path;
    }

    /**
     * function: shortestPath()
     * usage: finds the shortest path from the source to the destination
     * input: the starting point node and the endpoint node
     * returns: a LinkedList of the node IDs along the found path
     */
    public LinkedList<String> shortestPath(NodeSuper source, NodeSuper destination){

        LinkedList<String> path = new LinkedList<>();
        if (source.getID().equals(destination.getID())){
            path.add(source.getID());
            return path;
        }
        DijkstraNode end = new DijkstraNode(destination.getID(), null, 0);
        closedSet = new HashMap<>();
        openSet.remove(end.getId());
        closedSet.put(end.getId(), end);
        DijkstraNode target = openSet.get(source.getID());
        LinkedList<DijkstraNode> queue = new LinkedList<>();
        queue.add(end);

        while (!queue.isEmpty()){
            if (this.fan(queue.pop(), target, queue)){
                queue.clear();
            }
        }
        if (closedSet.containsKey(target.getId())){
            tracePath(path, target);
            path.add(end.getId());
        }
        return path;
    }

    /**
     * function: fan()
     * usage: goes through each neighbor of a node setting them as visited, marking the path traveled so far, checking if the target has been found and
     *         if not figuring which neighbor to search next based on the heuristic
     * input: dijkstranode which is the current node, target which is the node being searched for, and queue which is the next nodes to be checked
     * returns: true if the target is found and false otherwise
     */
    public boolean fan(DijkstraNode dijkstraNode, DijkstraNode target, LinkedList<DijkstraNode> queue){

        DijkstraNode cheapestNeighbor = null;
        boolean first = true;
        String s = dijkstraNode.getId();
        NodeSuper n = this.nodes.get(s);
        Set<String> neigh = n.getNeighbors();
        for (Object o : this.nodes.get(dijkstraNode.getId()).getNeighbors()) {
            String  i = (String) o;
            if (openSet.containsKey(i)) {
                if (first){
                    cheapestNeighbor = openSet.get(i);
                    first = false;
                }
                DijkstraNode node = openSet.get(i);
                if (calcGBF(nodes.get(i), nodes.get(target.getId())) < calcGBF(nodes.get(cheapestNeighbor.getId()), nodes.get(target.getId()))){
                    cheapestNeighbor = openSet.get(i);
                }
                openSet.remove(i);
                node.setDist(dijkstraNode.getDist() + 1);
                node.setPrev(dijkstraNode);
                closedSet.put(i, node);
                if (node.getId().equals(target.getId())) {
                    return true;
                }
            }
        }
        queue.add(cheapestNeighbor);
        return false;
    }

    /**
     * function: tracePath()
     * usage: takes the target node and follows the previous nodes to the end node
     *        adding their IDs to the path
     * input: a LinkedList to contain the ID path and the target DijkstraNode
     * returns: void
     */
    public void tracePath(LinkedList<String > path, DijkstraNode target){
        DijkstraNode dijkstraNode = closedSet.get(target.getId());
        for(int i = 0; i < dijkstraNode.getDist() + 1; i++){
            path.add(dijkstraNode.getId());
            dijkstraNode = dijkstraNode.getPrev();
        }
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
