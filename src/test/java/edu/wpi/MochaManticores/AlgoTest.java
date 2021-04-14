package edu.wpi.MochaManticores;

import edu.wpi.MochaManticores.Algorithms.AStar;
import edu.wpi.MochaManticores.Nodes.MapSuper;
import edu.wpi.MochaManticores.Nodes.NodeSuper;
import edu.wpi.MochaManticores.Nodes.VertexList;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class intended to contain testing methods for A*
 * @author aksil
 */
public class AlgoTest {
    public NodeSuper NODE1;
    public NodeSuper NODE2;
    public NodeSuper NODE3;
    public NodeSuper NODE4;
    public NodeSuper NODE5;
    public MapSuper TESTMAP1;

    /**
     * function: setUpMap()
     * usage: initializes the test map and nodes for the tests
     * inputs: none
     * returns: void
     */
    public void setUpMap() {

        //Make the nodes
        HashMap<String, Integer> neighbors1 = new HashMap<>();
        VertexList n1 = new VertexList(neighbors1);
        this.NODE1 = new NodeSuper(0, 0, "L1", "Building 1", "Test Node 1", "TN1", "NODE1", "TEST", n1);

        HashMap<String, Integer> neighbors2 = new HashMap<>();
        neighbors2.put("NODE1", 10);
        VertexList n2 = new VertexList(neighbors2);
        this.NODE2 = new NodeSuper(0, 10, "L1", "Building 1", "Test Node 2", "TN2", "NODE2", "TEST", n2);

        HashMap<String, Integer> neighbors3 = new HashMap<>();
        neighbors3.put("NODE1", 14);
        neighbors3.put("NODE2", 10);
        VertexList n3 = new VertexList(neighbors3);
        this.NODE3 = new NodeSuper(10, 10, "L1", "Building 1", "Test Node 3", "TN3", "NODE3", "STAIRS", n3);

        HashMap<String, Integer> neighbors4 = new HashMap<>();
        neighbors4.put("NODE3", 50);
        VertexList n4 = new VertexList(neighbors4);
        this.NODE4 = new NodeSuper(10, 10, "G", "Building 1", "Test Node 4", "TN4", "NODE4", "STAIRS", n4);

        HashMap<String, Integer> neighbors5 = new HashMap<>();
        neighbors5.put("NODE4", 30);
        VertexList n5 = new VertexList(neighbors5);
        this.NODE5 = new NodeSuper(-20, 10, "G", "Outside", "Test Node 5", "TN5", "NODE5", "TEST", n5);

        //Make the map
        this.TESTMAP1 = new MapSuper("TESTMAP1");
        TESTMAP1.addNode(NODE1);
        TESTMAP1.addNode(NODE2);
        TESTMAP1.addNode(NODE3);
        TESTMAP1.addNode(NODE4);
        TESTMAP1.addNode(NODE5);
    }

    @Test
    public void testMap() {
        setUpMap();
        assertEquals("Building 1", NODE3.getBuilding());
    }
    @Test
    public void testGBF1() {
        setUpMap();
        assertEquals(10, AStar.calcHeuristic(NODE1, NODE2));
    }
    @Test
    public void testGBF2() {
        setUpMap();
        assertEquals(14, AStar.calcHeuristic(NODE3, NODE1));
    }
    @Test
    public void testGBF3() {
        setUpMap();
        assertEquals(64, AStar.calcHeuristic(NODE4, NODE1));
    }
}
