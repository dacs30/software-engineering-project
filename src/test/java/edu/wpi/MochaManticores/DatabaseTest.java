package edu.wpi.MochaManticores;


import edu.wpi.MochaManticores.Nodes.MapSuper;
import edu.wpi.MochaManticores.Nodes.NodeSuper;
import edu.wpi.MochaManticores.Nodes.VertexList;
import edu.wpi.MochaManticores.database.EdgeManager;
import edu.wpi.MochaManticores.database.Mdb;
import edu.wpi.MochaManticores.database.NodeManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.HashMap;

public class DatabaseTest {
    public static NodeSuper NODE1;
    public static NodeSuper NODE2;
    public static NodeSuper NODE3;
    public static NodeSuper NODE4;
    public static NodeSuper NODE5;
    public static MapSuper TESTMAP1;
    private static DatabaseMetaData meta;
    private static Connection connection = null;
    public static String JDBC_URL = "jdbc:derby:Mdatabase;create=true";

    //Setup
    public static void setUp() throws SQLException, InterruptedException {
        //init the database for testing
        Mdb.databaseStartup();
        connection = Mdb.getConnection();

        //Make the nodes
        HashMap<String, Integer> neighbors1 = new HashMap<>();
        VertexList n1 = new VertexList(neighbors1);
        NODE1 = new NodeSuper(0, 0, "L1", "Building 1", "Test Node 1", "TN1", "NODE1", "TEST", n1);

        HashMap<String, Integer> neighbors2 = new HashMap<>();
        neighbors2.put("NODE1", 10);
        VertexList n2 = new VertexList(neighbors2);
        NODE2 = new NodeSuper(0, 10, "L1", "Building 1", "Test Node 2", "TN2", "NODE2", "TEST", n2);

        HashMap<String, Integer> neighbors3 = new HashMap<>();
        neighbors3.put("NODE1", 14);
        neighbors3.put("NODE2", 10);
        VertexList n3 = new VertexList(neighbors3);
        NODE3 = new NodeSuper(10, 10, "L1", "Building 1", "Test Node 3", "TN3", "NODE3", "STRS", n3);

        HashMap<String, Integer> neighbors4 = new HashMap<>();
        neighbors4.put("NODE3", 50);
        VertexList n4 = new VertexList(neighbors4);
        NODE4 = new NodeSuper(10, 10, "G", "Building 1", "Test Node 4", "TN4", "NODE4", "STAIRS", n4);

        HashMap<String, Integer> neighbors5 = new HashMap<>();
        neighbors5.put("NODE4", 30);
        VertexList n5 = new VertexList(neighbors5);
        NODE5 = new NodeSuper(-20, 10, "G", "Outside", "Test Node 5", "TN5", "NODE5", "TEST", n5);


    }

    @BeforeAll
    static void start() throws SQLException, InterruptedException {
        setUp();
    }

    @Test
    public void testAddNode() throws SQLException, InterruptedException, FileNotFoundException {
        NodeManager.addNode(connection, NODE1.getID(), String.valueOf(NODE1.getXcoord()), String.valueOf(NODE1.getYcoord()),
                NODE1.getFloor(), NODE1.getBuilding(), NODE1.getType(), NODE1.getLongName(), NODE1.getShortName());
        String sql = "SELECT * FROM NODES WHERE nodeID=?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, NODE1.getID());
        ResultSet results = pstmt.executeQuery();
        results.next();
        String nodeId = results.getString(1).replaceAll("\\s","");
        Assertions.assertEquals(NODE1.getID(), nodeId);
        NodeManager.delNode(connection, NODE1.getID());
    }

    @Test
    public void testDelNode() throws SQLException, FileNotFoundException {
        //add node
        NodeManager.addNode(connection, NODE1.getID(), String.valueOf(NODE1.getXcoord()), String.valueOf(NODE1.getYcoord()),
                NODE1.getFloor(), NODE1.getBuilding(), NODE1.getType(), NODE1.getLongName(), NODE1.getShortName());

        //delete node
        NodeManager.delNode(connection, NODE1.getID());

        //try to query deleted node
        String sql = "SELECT * FROM NODES WHERE nodeID=?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, NODE1.getID());
        ResultSet results = pstmt.executeQuery();
        results.next();
        Assertions.assertThrows(java.sql.SQLException.class, () -> {
            results.getString(1);
        });
    }

    @Test
    public void testModifyNode() throws SQLException, FileNotFoundException {
        //add node
        NodeManager.addNode(connection, NODE1.getID(), String.valueOf(NODE1.getXcoord()), String.valueOf(NODE1.getYcoord()),
                NODE1.getFloor(), NODE1.getBuilding(), NODE1.getType(), NODE1.getLongName(), NODE1.getShortName());

        //retrieve from database
        String sql = "SELECT * FROM NODES WHERE nodeID=?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, NODE1.getID());
        ResultSet results = pstmt.executeQuery();
        results.next();

        //get initial short name
        String initialName = results.getString(8).replaceAll("\\s","");

        //update short name
        NodeManager.updateNode(connection, NODE1.getID(), NODE1.getID(), NODE1.getXcoord(),
                NODE1.getYcoord(), NODE1.getFloor(), NODE1.getBuilding(), NODE1.getType(), NODE1.getLongName(), "CTN1");

        //query the changed node
        pstmt.setString(1, NODE1.getID());
        results = pstmt.executeQuery();
        results.next();
        String changedName = results.getString(8).replaceAll("\\s","");

        //check to see if the initial name is not the same as the changed name
        Assertions.assertNotEquals(initialName, changedName);

        //delete node from the database
        NodeManager.delNode(connection, NODE1.getID());
    }

    @Test
    public void testModifyNodeID() throws FileNotFoundException, SQLException {
        //add node
        NodeManager.addNode(connection, NODE1.getID(), String.valueOf(NODE1.getXcoord()), String.valueOf(NODE1.getYcoord()),
                NODE1.getFloor(), NODE1.getBuilding(), NODE1.getType(), NODE1.getLongName(), NODE1.getShortName());

        //retrieve from database
        String sql = "SELECT * FROM NODES WHERE nodeID=?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, NODE1.getID());
        ResultSet results = pstmt.executeQuery();
        results.next();

        //get initial nodeID
        String initialID = results.getString(1).replaceAll("\\s","");

        //update the nodeID
        NodeManager.updateNode(connection, "CNODE1", NODE1.getID(), NODE1.getXcoord(),
                NODE1.getYcoord(), NODE1.getFloor(), NODE1.getBuilding(), NODE1.getType(), NODE1.getLongName(), NODE1.getShortName());

        //query the changed node
        pstmt.setString(1, "CNODE1");
        results = pstmt.executeQuery();
        results.next();
        String changedID = results.getString(1).replaceAll("\\s","");

        //check to see if the initial nodeID is not the same as the changed nodeID
        Assertions.assertNotEquals(initialID, changedID);

        //delete node from the database
        NodeManager.delNode(connection, changedID);
    }

    @Test
    public void testAddEdge() throws SQLException, FileNotFoundException {
        //add nodes 1 and 2
        NodeManager.addNode(connection, NODE1.getID(), String.valueOf(NODE1.getXcoord()), String.valueOf(NODE1.getYcoord()),
                NODE1.getFloor(), NODE1.getBuilding(), NODE1.getType(), NODE1.getLongName(), NODE1.getShortName());
        NodeManager.addNode(connection, NODE2.getID(), String.valueOf(NODE2.getXcoord()), String.valueOf(NODE2.getYcoord()),
                NODE2.getFloor(), NODE2.getBuilding(), NODE2.getType(), NODE2.getLongName(), NODE2.getShortName());

        //add the edge
        String edgeID = NODE1.getID()+"_"+NODE2.getID();
        EdgeManager.addEdge(connection, edgeID, NODE1.getID(), NODE2.getID());

        String sql = "SELECT * FROM EDGES WHERE edgeID=?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, edgeID);
        ResultSet results = pstmt.executeQuery();
        results.next();

        String edgeIDDatabase = results.getString(1).replaceAll("\\s","");

        Assertions.assertEquals(edgeID, edgeIDDatabase);

        EdgeManager.delEdge(connection, edgeID);
        NodeManager.delNode(connection, NODE1.getID());
        NodeManager.delNode(connection, NODE2.getID());
    }

    @Test
    public void testDelEdge() throws SQLException, FileNotFoundException {
        //add nodes 1 and 2
        NodeManager.addNode(connection, NODE1.getID(), String.valueOf(NODE1.getXcoord()), String.valueOf(NODE1.getYcoord()),
                NODE1.getFloor(), NODE1.getBuilding(), NODE1.getType(), NODE1.getLongName(), NODE1.getShortName());
        NodeManager.addNode(connection, NODE2.getID(), String.valueOf(NODE2.getXcoord()), String.valueOf(NODE2.getYcoord()),
                NODE2.getFloor(), NODE2.getBuilding(), NODE2.getType(), NODE2.getLongName(), NODE2.getShortName());

        //add the edge
        String edgeID = NODE1.getID()+"_"+NODE2.getID();
        EdgeManager.addEdge(connection, edgeID, NODE1.getID(), NODE2.getID());

        //delete the edge
        EdgeManager.delEdge(connection, edgeID);

        //try and query the deleted edge
        String sql = "SELECT * FROM EDGES WHERE edgeID=?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, edgeID);
        ResultSet results = pstmt.executeQuery();
        results.next();

        Assertions.assertThrows(java.sql.SQLException.class, () -> {
            results.getString(1);
        });

        NodeManager.delNode(connection, NODE1.getID());
        NodeManager.delNode(connection, NODE2.getID());
    }

    @Test
    public void testModEdge() throws SQLException, FileNotFoundException {
        //add nodes 1 and 2
        NodeManager.addNode(connection, NODE1.getID(), String.valueOf(NODE1.getXcoord()), String.valueOf(NODE1.getYcoord()),
                NODE1.getFloor(), NODE1.getBuilding(), NODE1.getType(), NODE1.getLongName(), NODE1.getShortName());
        NodeManager.addNode(connection, NODE2.getID(), String.valueOf(NODE2.getXcoord()), String.valueOf(NODE2.getYcoord()),
                NODE2.getFloor(), NODE2.getBuilding(), NODE2.getType(), NODE2.getLongName(), NODE2.getShortName());
        NodeManager.addNode(connection, NODE3.getID(), String.valueOf(NODE3.getXcoord()), String.valueOf(NODE3.getYcoord()),
                NODE3.getFloor(), NODE3.getBuilding(), NODE3.getType(), NODE3.getLongName(), NODE3.getShortName());

        //add the edge
        String edgeID = NODE1.getID()+"_"+NODE2.getID();
        EdgeManager.addEdge(connection, edgeID, NODE1.getID(), NODE2.getID());

        String initialEdgeID = edgeID;

        EdgeManager.updateEdge(connection, edgeID, NODE1.getID(), NODE1.getID(), NODE2.getID(), NODE3.getID());

        String sql = "SELECT * FROM EDGES WHERE edgeID=?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, NODE1.getID()+"_"+NODE3.getID());
        ResultSet results = pstmt.executeQuery();
        results.next();

        String newEdgeID = results.getString(1).replaceAll("\\s","");

        Assertions.assertNotEquals(initialEdgeID, newEdgeID);

        EdgeManager.delEdge(connection, newEdgeID);
        NodeManager.delNode(connection, NODE1.getID());
        NodeManager.delNode(connection, NODE2.getID());
        NodeManager.delNode(connection, NODE3.getID());
    }

    // ### HASHMAP TESTING ###
    @Test
    public void testAddNodeMapSuper() throws SQLException, InterruptedException, FileNotFoundException {
        NodeManager.addNode(connection, NODE1.getID(), String.valueOf(NODE1.getXcoord()), String.valueOf(NODE1.getYcoord()),
                NODE1.getFloor(), NODE1.getBuilding(), NODE1.getType(), NODE1.getLongName(), NODE1.getShortName());

        Assertions.assertTrue(MapSuper.getMap().containsKey(NODE1.getID()));

        NodeManager.delNode(connection, NODE1.getID());

    }

    @Test
    public void testDelNodeMapSuper() throws SQLException, InterruptedException, FileNotFoundException {
        NodeManager.addNode(connection, NODE1.getID(), String.valueOf(NODE1.getXcoord()), String.valueOf(NODE1.getYcoord()),
                NODE1.getFloor(), NODE1.getBuilding(), NODE1.getType(), NODE1.getLongName(), NODE1.getShortName());

        NodeManager.delNode(connection, NODE1.getID());

        Assertions.assertTrue(!MapSuper.getMap().containsKey(NODE1.getID()));
    }

    @Test
    public void testModNodeLongNameMapSuper() throws SQLException, FileNotFoundException {
        NodeManager.addNode(connection, NODE1.getID(), String.valueOf(NODE1.getXcoord()), String.valueOf(NODE1.getYcoord()),
                NODE1.getFloor(), NODE1.getBuilding(), NODE1.getType(), NODE1.getLongName(), NODE1.getShortName());
        NodeManager.updateNodeName(connection, NODE1.getID(), "NODE1_NEW_NAME");

        Assertions.assertTrue(MapSuper.getMap().get(NODE1.getID()).getLongName() == "NODE1_NEW_NAME");

        NodeManager.delNode(connection, NODE1.getID());
    }

    @Test
    public void testModNodeINFOMapSuper() throws SQLException, FileNotFoundException {
        NodeManager.addNode(connection, NODE1.getID(), String.valueOf(NODE1.getXcoord()), String.valueOf(NODE1.getYcoord()),
                NODE1.getFloor(), NODE1.getBuilding(), NODE1.getType(), NODE1.getLongName(), NODE1.getShortName());
        NodeManager.updateNode(connection,NODE1.getID(),NODE1.getID(),-100,-100,"test","test","test","test","test");

        Assertions.assertTrue(MapSuper.getMap().get(NODE1.getID()).getXcoord() == -100);
        Assertions.assertTrue(MapSuper.getMap().get(NODE1.getID()).getYcoord() == -100);
        Assertions.assertTrue(MapSuper.getMap().get(NODE1.getID()).getFloor() == "test");
        Assertions.assertTrue(MapSuper.getMap().get(NODE1.getID()).getBuilding() == "test");
        Assertions.assertTrue(MapSuper.getMap().get(NODE1.getID()).getType() == "test");
        Assertions.assertTrue(MapSuper.getMap().get(NODE1.getID()).getLongName() == "test");
        Assertions.assertTrue(MapSuper.getMap().get(NODE1.getID()).getShortName() == "test");

        NodeManager.delNode(connection, NODE1.getID());
    }

    @Test
    public void testAddNeighborsMapSuper() throws SQLException, InterruptedException, FileNotFoundException {
        NodeManager.addNode(connection, NODE1.getID(), String.valueOf(NODE1.getXcoord()), String.valueOf(NODE1.getYcoord()),
                NODE1.getFloor(), NODE1.getBuilding(), NODE1.getType(), NODE1.getLongName(), NODE1.getShortName());
        NodeManager.addNode(connection, NODE2.getID(), String.valueOf(NODE2.getXcoord()), String.valueOf(NODE2.getYcoord()),
                NODE2.getFloor(), NODE2.getBuilding(), NODE2.getType(), NODE2.getLongName(), NODE2.getShortName());

        String edgeID = NODE1.getID()+"_"+NODE2.getID();
        EdgeManager.addEdge(connection, edgeID, NODE1.getID(), NODE2.getID());

        Assertions.assertTrue(MapSuper.getMap().get(NODE1.getID()).getNeighbors().contains(NODE2.getID()));
        Assertions.assertTrue(MapSuper.getMap().get(NODE2.getID()).getNeighbors().contains(NODE1.getID()));

        EdgeManager.delEdge(connection, edgeID);
        NodeManager.delNode(connection, NODE1.getID());
        NodeManager.delNode(connection, NODE2.getID());

    }

    @Test
    public void testDelNeighborsMapSuper()throws SQLException, InterruptedException, FileNotFoundException {
        NodeManager.addNode(connection, NODE1.getID(), String.valueOf(NODE1.getXcoord()), String.valueOf(NODE1.getYcoord()),
                NODE1.getFloor(), NODE1.getBuilding(), NODE1.getType(), NODE1.getLongName(), NODE1.getShortName());
        NodeManager.addNode(connection, NODE2.getID(), String.valueOf(NODE2.getXcoord()), String.valueOf(NODE2.getYcoord()),
                NODE2.getFloor(), NODE2.getBuilding(), NODE2.getType(), NODE2.getLongName(), NODE2.getShortName());

        String edgeID = NODE1.getID()+"_"+NODE2.getID();
        EdgeManager.addEdge(connection, edgeID, NODE1.getID(), NODE2.getID());

        Assertions.assertTrue(MapSuper.getMap().get(NODE1.getID()).getNeighbors().contains(NODE2.getID()));
        Assertions.assertTrue(MapSuper.getMap().get(NODE2.getID()).getNeighbors().contains(NODE1.getID()));

        EdgeManager.delEdge(connection,edgeID);

        Assertions.assertTrue(!MapSuper.getMap().get(NODE1.getID()).getNeighbors().contains(NODE2.getID()));
        Assertions.assertTrue(!MapSuper.getMap().get(NODE2.getID()).getNeighbors().contains(NODE1.getID()));

        NodeManager.delNode(connection,NODE1.getID());
        NodeManager.delNode(connection,NODE2.getID());

    }

    @Test
    public void testModEdgeNeighborsMapSuper() throws SQLException, InterruptedException, FileNotFoundException {
        NodeManager.addNode(connection, NODE1.getID(), String.valueOf(NODE1.getXcoord()), String.valueOf(NODE1.getYcoord()),
                NODE1.getFloor(), NODE1.getBuilding(), NODE1.getType(), NODE1.getLongName(), NODE1.getShortName());
        NodeManager.addNode(connection, NODE2.getID(), String.valueOf(NODE2.getXcoord()), String.valueOf(NODE2.getYcoord()),
                NODE2.getFloor(), NODE2.getBuilding(), NODE2.getType(), NODE2.getLongName(), NODE2.getShortName());
        NodeManager.addNode(connection, NODE3.getID(), String.valueOf(NODE3.getXcoord()), String.valueOf(NODE3.getYcoord()),
                NODE3.getFloor(), NODE3.getBuilding(), NODE3.getType(), NODE3.getLongName(), NODE3.getShortName());

        String edgeID = NODE1.getID()+"_"+NODE2.getID();
        String edgeIDNEW = NODE1.getID()+"_"+NODE3.getID();

        EdgeManager.addEdge(connection, edgeID, NODE1.getID(), NODE2.getID());

        Assertions.assertTrue(MapSuper.getMap().get(NODE1.getID()).getNeighbors().contains(NODE2.getID()));
        Assertions.assertTrue(MapSuper.getMap().get(NODE2.getID()).getNeighbors().contains(NODE1.getID()));

        EdgeManager.updateEdge(connection,edgeID, NODE1.getID(),NODE1.getID(),NODE2.getID(),NODE3.getID());

        Assertions.assertTrue(MapSuper.getMap().get(NODE1.getID()).getNeighbors().contains(NODE3.getID()));
        Assertions.assertTrue(MapSuper.getMap().get(NODE3.getID()).getNeighbors().contains(NODE1.getID()));
        Assertions.assertFalse(MapSuper.getMap().get(NODE1.getID()).getNeighbors().contains(NODE2.getID()));
        Assertions.assertFalse(MapSuper.getMap().get(NODE2.getID()).getNeighbors().contains(NODE1.getID()));

        EdgeManager.delEdge(connection, edgeID);
        EdgeManager.delEdge(connection, edgeIDNEW);
        NodeManager.delNode(connection, NODE1.getID());
        NodeManager.delNode(connection, NODE2.getID());
        NodeManager.delNode(connection, NODE3.getID());

    }




}
