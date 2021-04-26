package edu.wpi.MochaManticores.Nodes;

/**
 * Class defining an edge node
 * @author saiv
 */
public class EdgeSuper {
    private String edgeID;       //edgeID of the edge node
    private String startingNode;        //nodeID of the startingNode
    private String endingNode;      //nodeID of the endingNode

    //constructor
    public EdgeSuper(String edgeID, String startingNode, String endingNode) {
        this.edgeID = edgeID;
        this.startingNode = startingNode;
        this.endingNode = endingNode;
    }

    /**
     * function: getStartingNode()
     * returns the nodeID of the starting node
     * @return String
     */
    public String getStartingNode() { return this.startingNode; }

    /**
     * function: getEndingNode()
     * returns the nodeID of the ending node
     * @return String
     */
    public String getEndingNode() { return this.endingNode; }

    /**
     * function: sets the startingNode as the given nodeID
     * @param startingNode (NodeId of the starting node)
     */
    public void setStartingNode(String startingNode) {
        this.startingNode = startingNode;
    }

    /**
     * function: sets the ending node as the given nodeID
     * @param endingNode (NodeId of the ending node)
     */
    public void setEndingNode(String endingNode) {
        this.endingNode = endingNode;
    }

    /**
     * function: gets the edge id
     * @return
     */
    public String getEdgeID() {
        return edgeID;
    }

    /**
     * function: sets the edge id
     * @return
     */
    public void setEdgeID(String edgeID) {
        this.edgeID = edgeID;
    }
}
