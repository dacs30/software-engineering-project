package edu.wpi.MochaManticores.Nodes;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a map class to contain the edge nodes
 * @author saiv
 */
public class EdgeMapSuper {
    public String name;     //name of the EdgeMapSuper
    private static final HashMap<String, EdgeSuper> edgeMap = new HashMap<>();      //map of edges organized by edgeId

    //constructor
    public EdgeMapSuper(String name) {
        this.name = name;
    }

    /**
     * function: getMap()
     * returns the map of edges
     * @return a HashMap of the edges
     */
    public static HashMap<String, EdgeSuper> getMap() {
        return edgeMap;
    }

    /**
     * function: insertEdgeNode(String, EdgeSuper)
     * @param edgeId a String that is the edge id of the edge node
     * @param edgeNode an EdgeSuper that is the edgeNode being inserted into the edgeMap
     */
    public static void insertEdgeNode(String edgeId, EdgeSuper edgeNode) {
        edgeMap.put(edgeId, edgeNode);
    }
}
