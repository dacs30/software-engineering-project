package edu.wpi.MochaManticores.Algorithms;

import edu.wpi.MochaManticores.Nodes.NodeSuper;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * This is a basic Dijkstra search algorithm
 * for finding the shortest path between two nodes
 * to be used in the making of the A* algorithm
 * @author gatch
 */

public class Dijkstra {
    private HashMap<String, NodeSuper> nodes;
    private HashMap<String, DijkstraNode> openSet;
    private HashMap<String, DijkstraNode> closedSet = new HashMap<>();

    public Dijkstra(HashMap<String, NodeSuper> nodes){
        this.nodes     = nodes;
        this.openSet   = new HashMap<>();
    }

    public HashMap<String, NodeSuper> getNodes(){
        /**
         * function: getNodes()
         * usage: function to access the map of nodes being pathed
         * input: none
         * returns: the map of nodes being pathed
         */
        return this.nodes;
    }

    public HashMap<String, DijkstraNode> getOpenSet(){
        /**
         * function: getOpenSet()
         * usage: function to access the nodes not yet visited
         * input: none
         * returns: the unvisited nodes
         */
        return this.openSet;
    }

    public HashMap<String, DijkstraNode> getClosedSet(){
        /**
         * function getClosedSet()
         * usage: function to access the visited nodes
         * input: none
         * returns: the visited nodes
         */
        return this.closedSet;
    }

    public LinkedList<String> shortestPath(NodeSuper source, NodeSuper dest){
        /**
         * function: shortestPath()
         * usage: finds the shortest path from the source to the destination
         * input: the starting point node and the endpoint node
         * returns: a LinkedList of the node IDs along the found path
         */

        LinkedList<String> path = new LinkedList<>();
        if (source.getID().equals(dest.getID())){
            path.add(source.getID());
            return path;
        }
        DijkstraNode end = new DijkstraNode(dest.getID(), null, 0);
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

    public boolean fan(DijkstraNode dnode, DijkstraNode target, LinkedList<DijkstraNode> queue){
        /**
         * function: fan()
         * usage: goes through each neighbor of the current node, sets it's distance from the
         *        current node, marks it as having been visited and checks if it is the target
         * input: the current node dnode, the target node target and the queue of nodes to be checked
         * returns: true if the target is found and false otherwise
         */
        for (Object o : this.getNodes().get(dnode.getId()).getNeighbors()) {//need to redo neighbor iteration
            String  i = (String) o;
            if (openSet.containsKey(i)) {
                DijkstraNode node = openSet.get(i);
                openSet.remove(i);
                node.setDist(dnode.getDist() + 1);
                node.setPrev(dnode);
                closedSet.put(i, node);
                queue.add(node);
                if (node.getId().equals(target.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void tracePath(LinkedList<String > path, DijkstraNode target){
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

    public LinkedList<String> depthFirstSearch(NodeSuper source, NodeSuper dest){
        /**
         * function: depthFirstSearch()
         * usage: finds a path from the source to the destination exploring each branch fully before
         *        trying the next
         * input: two MapNodes, one for the start point and one for the end point
         * returns:a LinkedList containing the IDs of the nodes along the resulting path
         */
        LinkedList<String> path = new LinkedList<>();
        if (source.getID().equals(dest.getID())){
            path.add(source.getID());
            return path;
        }
        DijkstraNode end = new DijkstraNode(dest.getID(), null, 0);
        closedSet = new HashMap<>();
        openSet.remove(end.getId());
        closedSet.put(end.getId(), end);
        DijkstraNode target = openSet.get(source.getID());

        this.DFSFan(end, target);
        if (closedSet.containsKey(target.getId())){
            tracePath(path, target);
            path.add(end.getId());
        }
        return path;
    }

    public boolean DFSFan(DijkstraNode currentNode, DijkstraNode target){
        /**
         * function: DFSFan()
         * usage: checks if a node is the target then moves to one of it's neighbors
         * input: two DijkstraNodes representing the target and current node
         * returns: true if target found false otherwise
         */
        for (Object o : this.getNodes().get(currentNode.getId()).getNeighbors()){
            String i = (String) o;
            if (openSet.containsKey(i)) {
                DijkstraNode nextNode = openSet.get(i);
                openSet.remove(i);
                nextNode.setDist(currentNode.getDist() + 1);
                nextNode.setPrev(currentNode);
                closedSet.put(i, nextNode);
                if (nextNode.getId().equals(target.getId())) {
                    return true;
                }
                DFSFan(nextNode, target);
            }
        }
        return false;
    }


}
