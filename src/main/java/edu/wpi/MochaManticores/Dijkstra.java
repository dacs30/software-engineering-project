package edu.wpi.MochaManticores;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * This is a basic Dijkstra search algorithm
 * for finding the shortest path between two nodes
 * to be used in the making of the A* algorithm
 * @author gatch
 */

public class Dijkstra {
    private HashMap<Integer, MapNode> nodes;
    private HashMap<Integer, DijkstraNode> openSet;
    private HashMap<Integer, DijkstraNode> closedSet = new HashMap<>();

    public Dijkstra(){
        this.nodes     = new HashMap<>();
        this.openSet   = new HashMap<>();
    }

    public HashMap<Integer, MapNode> getNodes(){
        /**
         * function: getNodes()
         * usage: function to access the map of nodes being pathed
         * input: none
         * returns: the map of nodes being pathed
         */
        return this.nodes;
    }

    public HashMap<Integer, DijkstraNode> getOpenSet(){
        /**
         * function: getOpenSet()
         * usage: function to access the nodes not yet visited
         * input: none
         * returns: the unvisited nodes
         */
        return this.openSet;
    }

    public HashMap<Integer, DijkstraNode> getClosedSet(){
        /**
         * function getClosedSet()
         * usage: function to access the visited nodes
         * input: none
         * returns: the visited nodes
         */
        return this.closedSet;
    }

    public LinkedList<Integer> shortestPath(MapNode source, MapNode dest){
        /**
         * function: shortestPath()
         * usage: finds the shortest path from the source to the destination
         * input: the starting point node and the endpoint node
         * returns: a LinkedList of the node IDs along the found path
         */

        LinkedList<Integer> path = new LinkedList<>();
        if (source.ID == dest.ID){
            path.add(source.ID);
            return path;
        }
        DijkstraNode end = new DijkstraNode(dest.ID, null, 0);
        //openSet   = ; add function for reset
        closedSet = new HashMap<>();
        openSet.remove(end.getId());
        closedSet.put(end.getId(), end);
        DijkstraNode target = openSet.get(source.ID);
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

    public boolean fan(DijkstraNode dnode, DijkstraNode target, LinkedList<DijkstraNode> queue){
        /**
         * function: fan()
         * usage: goes through each neighbor of the current node, sets it's distance from the
         *        current node, marks it as having been visited and checks if it is the target
         * input: the current node dnode, the target node target and the queue of nodes to be checked
         * returns: true if the target is found and false otherwise
         */
        for (Object o : this.getNodes().get(dnode.getId()).getNeighbors()) {
            int i = (Integer) o;
            if (openSet.containsKey(i)) {
                DijkstraNode node = openSet.get(i);
                openSet.remove(i);
                node.setDist(dnode.getDist() + 1);
                node.setPrev(dnode);
                closedSet.put(i, node);
                queue.add(node);
                if (node.getId() == target.getId()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void tracePath(LinkedList<Integer> path, DijkstraNode target){
        /**
         * function: tracePath()
         * usage: takes the target node and follows the previous nodes to the end node
         *        adding their IDs to the path
         * input: a LinkedList to contain the ID path and the target DijkstraNode
         * returns: void
         */
        DijkstraNode dijkstraNode = closedSet.get(target.getId());
        for(int i = 0; i < dijkstraNode.getDist() + 1; i++){
            path.add(dijkstraNode.getId());
            dijkstraNode = dijkstraNode.getPrev();
        }
    }
}
