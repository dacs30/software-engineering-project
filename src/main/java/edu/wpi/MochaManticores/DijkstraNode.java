package edu.wpi.MochaManticores;

/**
 * This is a specialized node used only
 * by the Dijkstra class to track it's progress
 * @author gatch
 */

public class DijkstraNode {
    private int id;
    private DijkstraNode prev;
    private int dist;

    public DijkstraNode(int id, DijkstraNode prev, int dist){
        this.id = id;
        this.prev = prev;
        this.dist = dist;
    }

    public int getId(){
        /**
         * function: getId()
         * usage: function to retrieve the private id of the node
         * inputs: none
         * returns: ID of this node
         */
        return this.id;
    }

    public DijkstraNode getPrev(){
        /**
         * function: getPrev()
         * usage: function to get the previous node along a path
         * inputs: none
         * returns: a DijksraNode object that is a copy of the previous node
         */
        return this.prev;
    }

    public int getDist(){
        /**
         * function: getDist()
         * usage: function to get the distance of this node on the path from start to target
         * input: none
         * returns: the integer distance of this node along the path from start to target
         */
        return this.dist;
    }

    public void setId(int id){
        /**
         * function: setId()
         * usage: function to set the ID of this node
         * inputs: integer to set the ID to
         * returns: void
         */
        this.id = id;
    }

    public void setPrev(DijkstraNode prev){
        /**
         * function: setPrev()
         * usage: function to set which node was before this on the path taken
         * inputs: DijkstraNode previous to this
         * returns: void
         */
        this.prev = prev;
    }

    public void setDist(int dist){
        /**
         * function: setDist()
         * usage: function to set the distance of this node along the path from start to target
         * inputs: integer to be the new distance
         * returns: void
         */
        this.dist = dist;
    }
}