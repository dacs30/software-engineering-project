package edu.wpi.MochaManticores.Algorithms;

import edu.wpi.MochaManticores.Exceptions.DestinationNotAccessibleException;
import edu.wpi.MochaManticores.Nodes.NodeSuper;

import java.util.LinkedList;

/**
 * PathPlanning is an interface containing major navigation functions for AStar, GBF, and Dijkstra
 * @author aksil
 */
public interface PathPlanning {

    /**
     * method: multiStopRoute()
     * usage: takes in a linked list of nodes and returns a linked list
     *        of node IDs representing a path through all input nodes
     * @param stops LinkedList containing start and target nodes in order
     * @param condition condition code for the path "publicOnly, handicap, none, publicHandicap"
     * @return path LinkedList containing all node IDs in the route
     */
    LinkedList<String> multiStopRoute(LinkedList<NodeSuper> stops, String condition) throws DestinationNotAccessibleException;

    /**
     * method: findRoute()
     * usage: Uses a pathing algorithm to calculate the most efficient route to the target
     * @param start the node of origin
     * @param target the node to search for
     * @return a LinkedList of Strings containing the IDs of the nodes from the first node to the target
     */
    LinkedList<String> findRoute(NodeSuper start, NodeSuper target) throws DestinationNotAccessibleException;
}
