package edu.wpi.MochaManticores;

import edu.wpi.MochaManticores.Algorithms.AStar;
import edu.wpi.MochaManticores.Nodes.MapSuper;
import edu.wpi.MochaManticores.Nodes.NodeSuper;
import edu.wpi.MochaManticores.Nodes.VertexList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlgoTest {

    @BeforeAll
    public static void setup() {}

    @AfterAll
    public static void cleanup() {}

    @Test
    public void testAlgos() {
        HashMap<String, Integer> neighbors1 = new HashMap<>();
        VertexList n1 = new VertexList(neighbors1);
        NodeSuper NODE1 = new NodeSuper(0, 0, "L1", "Building 1", "Test Node 1", "TN1", "NODE1", "TEST", n1);

        HashMap<String, Integer> neighbors2 = new HashMap<>();
        neighbors2.put("NODE1", 10);
        VertexList n2 = new VertexList(neighbors2);
        NodeSuper NODE2 = new NodeSuper(0, 10, "L1", "Building 1", "Test Node 2", "TN2", "NODE2", "TEST", n2);

        HashMap<String, Integer> neighbors3 = new HashMap<>();
        neighbors3.put("NODE1", 14);
        neighbors3.put("NODE2", 10);
        VertexList n3 = new VertexList(neighbors3);
        NodeSuper NODE3 = new NodeSuper(10, 10, "L1", "Building 1", "Test Node 3", "TN3", "NODE3", "STAIRS", n3);

        HashMap<String, Integer> neighbors4 = new HashMap<>();
        neighbors4.put("NODE3", 50);
        VertexList n4 = new VertexList(neighbors4);
        NodeSuper NODE4 = new NodeSuper(10, 10, "G", "Building 1", "Test Node 4", "TN4", "NODE4", "STAIRS", n4);

        HashMap<String, Integer> neighbors5 = new HashMap<>();
        neighbors5.put("NODE4", 30);
        VertexList n5 = new VertexList(neighbors5);
        NodeSuper NODE5 = new NodeSuper(-20, 10, "G", "Outside", "Test Node 5", "TN5", "NODE5", "TEST", n5);

        MapSuper TESTMAP1 = new MapSuper("TESTMAP1");
        TESTMAP1.addNode(NODE1);
        TESTMAP1.addNode(NODE2);
        TESTMAP1.addNode(NODE3);
        TESTMAP1.addNode(NODE4);
        TESTMAP1.addNode(NODE5);
        assertEquals("Building 1", NODE3.getBuilding());
        assertEquals(10, AStar.calcHeuristic(NODE1, NODE2));
        assertEquals(14, AStar.calcHeuristic(NODE3, NODE1));
        assertEquals(64, AStar.calcHeuristic(NODE4, NODE1));
    }
}
