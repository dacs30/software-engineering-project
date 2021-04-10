package edu.wpi.MochaManticores.Nodes;

import java.util.HashMap;
import java.util.Set;

/**
 * This class is used by MapNode to store the
 * IDs of nearby neighbors and the path costs
 * to travel to them.
 * @author aksil
 */
public class VertexList {
    //Declare instance variables
    HashMap<String, Integer> neighbors; //contains the nodeID (key) and cost (value)

    //Constructor
    public VertexList(HashMap<String, Integer> neighbors) {
        this.neighbors = neighbors;
    }

    /**
     * function: getKeyIDs()
     * usage: retrieves a Set containing all of the String
     *        node IDs of neighboring MapNodes
     * inputs: none
     * returns: Set (no name) (a Set containing neighboring node IDs)
     */
    public Set<String> getKeyIDs() {
        return this.neighbors.keySet();
    }

    /**
     * function: getCost()
     * usage: retrieves the cost of moving to the target node
     * inputs: String key (node ID of the desired neighbor)
     * returns: Integer cost (cost to cross the vertex)
     */
    public Integer getCost(String key) {
        return this.neighbors.get(key);
    }

    /**
     * function: setCost()
     * usage: modifies the cost value to travel to the target node
     * inputs: String key (node ID of the desired neighbor)
     *         Integer cost (new cost)
     * returns: void
     */
    public void setCost(String key, Integer cost) {
        this.neighbors.replace(key, cost);
    }

    /**
     * function newNeighbor()
     * usage: adds a new key-value entry to the neighbors hashmap
     * inputs: String neighborID (node ID of the new neighbor)
     *         Integer pathCost (path cost to travel to the new node)
     * returns: void
     */
    public void newNeighbor(String neighborID, Integer pathCost) {
        this.neighbors.put(neighborID, pathCost);
    }

    /**
     * function: removeNeighbor()
     * usage: removes an existing neighbor from the hashmap
     * inputs: String key (node ID of the node to be deleted)
     * returns: void
     */
    public void removeNeighbor(String key) {
        this.neighbors.remove(key);
    }
}
