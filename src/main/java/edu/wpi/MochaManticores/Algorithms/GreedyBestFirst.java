package edu.wpi.MochaManticores.Algorithms;

import edu.wpi.MochaManticores.Nodes.NodeSuper;

/**
 * This interface is intended to contain functions related
 * to the greedy best first search algorithm.
 * @author aksil
 */
public interface GreedyBestFirst {
    //Declare static variables
    int buildingOffset = 100;   //determines how much the algorithm prefers nodes in the same building as the target node
    int floorOffset = 50;       //determines how much the algorithm prefers nodes on the same floor as the target node

    static int calcHeuristic(NodeSuper firstNode, NodeSuper secondNode) {
        /**
         * function: calcHeuristic()
         * usage: returns the estimated cost between two nodes,
         *        favoring nodes in the same building and on the same floor
         * inputs: NodeSuper firstNode (one of the two nodes)
         *         NodeSuper secondNode (the other node)
         * returns: int heuristic (the estimated cost to travel from 1 node to the other)
         */

        //calculate euclidean distance between nodes
        int heuristic = (int) Math.sqrt(Math.pow(firstNode.getXcoord()-secondNode.getXcoord(), 2) + Math.pow(firstNode.getYcoord()-secondNode.getYcoord(), 2));

        //add offset cost for being outside of the target building
        if(firstNode.getBuilding().equals(secondNode.getBuilding())) {
            heuristic += buildingOffset;
            //if inside the target building, add offset for being on a different floor
            } else if(firstNode.getFloor() != secondNode.getFloor()) {
            heuristic += floorOffset;
        }
        return heuristic;
    }
}
