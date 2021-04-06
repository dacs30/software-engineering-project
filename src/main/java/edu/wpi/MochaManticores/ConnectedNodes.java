package edu.wpi.MochaManticores;

import java.util.HashMap;
import java.util.Set;

/**
 * This class is used by MapNode to store the
 * IDs of nearby neighbors and the path costs
 * to travel to them.
 * @author aksil
 */
public class ConnectedNodes {
    //Declare instance variables
    private HashMap<Integer, Integer> neighbors; //contains the nodeID (key) and cost (value)

    public ConnectedNodes(HashMap<Integer, Integer> neighbors) {
    }

    public Set getKeyIDs() {
        /**
         * function: getKeyIDs()
         * usage: retrieves a Set containing all of the Integer
         *        node IDs of neighboring MapNodes
         * inputs: none
         * returns: Set (no name) (a Set containing neighboring node IDs)
         */
        return neighbors.keySet();
    }

   public Integer getCost(Integer key) {
       /**
        * function: getCost()
        * usage: retrieves the cost of moving to the target node
        * inputs: Integer key (node ID of the desired neighbor)
        * returns: Integer cost (cost to cross the vertex)
        */
        return neighbors.get(key);
    }

    public void setCost(Integer key, Integer cost) {
        /**
         * function: setCost()
         * usage: modifies the cost value to travel to the target node
         * inputs: Integer key (node ID of the desired neighbor)
         *         Integer cost (new cost)
         * returns: void
         */
        neighbors.replace(key, cost);
    }

    public void newNeighbor(Integer neighborID, Integer pathCost) {
        /**
         * function newNeighbor()
         * usage: adds a new key-value entry to the neighbors hashmap
         * inputs: Integer neighborID (node ID of the new neighbor)
         *         Integer pathCost (path cost to travel to the new node)
         * returns: void
         */
        neighbors.put(neighborID, pathCost);
    }

    public void removeNeighbor(Integer key) {
        /**
         * function: removeNeighbor()
         * usage: removes an existing neighbor from the hashmap
         * inputs: Integer key (node ID of the node to be deleted)
         * returns: void
         */
        neighbors.remove(key);
    }
}
