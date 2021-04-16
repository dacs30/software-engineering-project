package edu.wpi.MochaManticores.Algorithms;

import java.util.Comparator;

/**
 * This class contains a comparator method used to order the priority of
 * AStarNodes in the horizon priority queue
 * @author aksil
 */
public class NodeComparator implements Comparator<AStarNode> {

    /**
     * method: compare()
     * usage: overrides the default compare method to enable priority queue support of the AStarNode class
     * @param o1 an AStarNode to be compared
     * @param o2 an AStarNode to be compared
     * @return 1 if the first node has greater or equal priority (estimatedCost+costToReach), -1 otherwise
     */
    @Override
    public int compare(AStarNode o1, AStarNode o2) {
        if(o1.getPriority() >= o2.getPriority()) {
            return 1;
        }
        else return -1;
    }
}
