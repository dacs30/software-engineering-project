package edu.wpi.MochaManticores;


import edu.wpi.MochaManticores.Exceptions.InvalidElementException;
import edu.wpi.MochaManticores.Nodes.EdgeSuper;
import edu.wpi.MochaManticores.Nodes.MapSuper;
import edu.wpi.MochaManticores.Nodes.NodeSuper;
import edu.wpi.MochaManticores.Nodes.VertexList;
import edu.wpi.MochaManticores.database.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
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
        DatabaseManager.startup();
        connection = DatabaseManager.getConnection();

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

    //clean database test
    @Test
    public void cleanDB(){
        DatabaseManager.resetTable(sel.NODE,"data/bwMNodes.csv");
    }

    // employee database test cases
    @Test
    public void addEmployee() throws SQLException, InvalidElementException {
        Employee employee = new Employee("testUser",
                "password",
                "firstName",
                "lastName",
                "DEFAULT",
                "1",
                "false");

        DatabaseManager.addEmployee(employee);

        Assertions.assertTrue(employee.getUsername().equals(DatabaseManager.getEmployee(employee.getUsername()).getUsername()));
        DatabaseManager.delElement(sel.EMPLOYEE,employee.getUsername());
    }

    @Test
    public void removeEmployee() throws SQLException, InvalidElementException {
        Employee employee = new Employee("testUser",
                "password",
                "firstName",
                "lastName",
                "DEFAULT",
                "1",
                "false");

        DatabaseManager.addEmployee(employee);
        DatabaseManager.delElement(sel.EMPLOYEE, employee.getUsername());

        //Assertions.assertTrue(DatabaseManager.getEmployee(employee.getUsername())==null);
    }

    @Test
    public void modifyEmployee() throws SQLException, InvalidElementException {
        Employee employee = new Employee("testUser",
                "password",
                "firstName",
                "lastName",
                "DEFAULT",
                "1",
                "false");

        DatabaseManager.addEmployee(employee);
        Employee employeeMOD = new Employee("testUserMODDED",
                "password",
                "firstName",
                "lastName",
                "DEFAULT",
                "1",
                "false");

        DatabaseManager.modEmployee(employee.getUsername(),employeeMOD);

        Assertions.assertTrue(DatabaseManager.getEmployee(employeeMOD.getUsername()).getUsername().equals(employeeMOD.getUsername()));
        DatabaseManager.delElement(sel.EMPLOYEE, employeeMOD.getUsername());
    }

    @Test
    public void loginEmployee(){}

    @Test
    public void loginAdmin(){}


    @Test
    public void testAddNode() throws SQLException, InterruptedException, FileNotFoundException {
        DatabaseManager.addNode(NODE1);
        String sql = "SELECT * FROM NODES WHERE nodeID=?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, NODE1.getID());
        ResultSet results = pstmt.executeQuery();
        results.next();
        String nodeId = results.getString(1).replaceAll("\\s","");
        Assertions.assertEquals(NODE1.getID(), nodeId);
        DatabaseManager.delElement(sel.NODE,NODE1.getID());
    }

    @Test
    public void testDelNode() throws SQLException, FileNotFoundException {
        //add node
        DatabaseManager.addNode(NODE1);

        //delete node
        DatabaseManager.delElement(sel.NODE,NODE1.getID());

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
        DatabaseManager.addNode(NODE1);
        //retrieve from database
        String sql = "SELECT * FROM NODES WHERE nodeID=?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, NODE1.getID());
        ResultSet results = pstmt.executeQuery();
        results.next();

        //get initial short name
        String initialName = results.getString(8).replaceAll("\\s","");

        //update short name
        NodeSuper temp = NODE1;
        temp.setShortName("CTN1");
        DatabaseManager.modNode(NODE1.getID(), temp);

        //query the changed node
        pstmt.setString(1, NODE1.getID());
        results = pstmt.executeQuery();
        results.next();
        String changedName = results.getString(8).replaceAll("\\s","");

        //check to see if the initial name is not the same as the changed name
        Assertions.assertNotEquals(initialName, changedName);

        //delete node from the database
        DatabaseManager.delElement(sel.NODE,NODE1.getID());
    }

    @Test
    public void testModifyNodeID() throws FileNotFoundException, SQLException {
        //add node
        DatabaseManager.addNode(NODE1);
        //retrieve from database
        String sql = "SELECT * FROM NODES WHERE nodeID=?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, NODE1.getID());
        ResultSet results = pstmt.executeQuery();
        results.next();

        //get initial nodeID
        String initialID = results.getString(1).replaceAll("\\s","");
        //update the nodeID
        NodeSuper temp = NODE1;
        temp.setID("CNODE1");
        DatabaseManager.modNode(NODE1.getID(),temp);

        //query the changed node
        pstmt.setString(1, "CNODE1");
        results = pstmt.executeQuery();
        results.next();
        String changedID = results.getString(1).replaceAll("\\s","");

        //check to see if the initial nodeID is not the same as the changed nodeID
        Assertions.assertNotEquals(initialID, changedID);

        //delete node from the database
        DatabaseManager.delElement(sel.NODE,changedID);
    }

    @Test
    public void testAddEdge() throws SQLException, FileNotFoundException {
        //add nodes 1 and 2
        DatabaseManager.addNode(NODE1);
        DatabaseManager.addNode(NODE2);

        //add the edge
        String edgeID = NODE1.getID()+"_"+NODE2.getID();
        DatabaseManager.addEdge(new EdgeSuper(edgeID, NODE1.getID(), NODE2.getID()));

        String sql = "SELECT * FROM EDGES WHERE edgeID=?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, edgeID);
        ResultSet results = pstmt.executeQuery();
        results.next();

        String edgeIDDatabase = results.getString(1).replaceAll("\\s","");

        Assertions.assertEquals(edgeID, edgeIDDatabase);

        DatabaseManager.delElement(sel.EDGE,edgeID);
        DatabaseManager.delElement(sel.NODE,NODE1.getID());
        DatabaseManager.delElement(sel.NODE,NODE2.getID());
    }

    @Test
    public void testDelEdge() throws SQLException, FileNotFoundException {
        //add nodes 1 and 2
        DatabaseManager.addNode(NODE1);
        DatabaseManager.addNode(NODE2);

        //add the edge
        String edgeID = NODE1.getID()+"_"+NODE2.getID();
        DatabaseManager.addEdge(new EdgeSuper(edgeID, NODE1.getID(), NODE2.getID()));

        //delete the edge
        DatabaseManager.delElement(sel.EDGE,edgeID);

        //try and query the deleted edge
        String sql = "SELECT * FROM EDGES WHERE edgeID=?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, edgeID);
        ResultSet results = pstmt.executeQuery();
        results.next();

        Assertions.assertThrows(java.sql.SQLException.class, () -> {
            results.getString(1);
        });

        DatabaseManager.delElement(sel.NODE, NODE1.getID());
        DatabaseManager.delElement(sel.NODE, NODE2.getID());
    }

    @Test
    public void testModEdge() throws SQLException, FileNotFoundException {
        //add nodes 1 and 2
        DatabaseManager.addNode(NODE1);
        DatabaseManager.addNode(NODE2);
        DatabaseManager.addNode(NODE3);

        //add the edge
        String edgeID = NODE1.getID()+"_"+NODE2.getID();
        DatabaseManager.addEdge(new EdgeSuper(edgeID, NODE1.getID(), NODE2.getID()));

        String initialEdgeID = edgeID;

        DatabaseManager.modEdge(edgeID, new EdgeSuper( NODE1.getID() + "_" + NODE3.getID(), NODE1.getID(), NODE3.getID()));

        String sql = "SELECT * FROM EDGES WHERE edgeID=?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, NODE1.getID()+"_"+NODE3.getID());
        ResultSet results = pstmt.executeQuery();
        results.next();

        String newEdgeID = results.getString(1).replaceAll("\\s","");

        Assertions.assertNotEquals(initialEdgeID, newEdgeID);

        DatabaseManager.delElement(sel.EDGE, newEdgeID);
        DatabaseManager.delElement(sel.NODE,NODE1.getID());
        DatabaseManager.delElement(sel.NODE,NODE2.getID());
        DatabaseManager.delElement(sel.NODE,NODE3.getID());
    }

    // ### HASHMAP TESTING ###
    @Test
    public void testAddNodeMapSuper() throws SQLException, InterruptedException, FileNotFoundException {
        DatabaseManager.addNode(NODE1);

        Assertions.assertTrue(MapSuper.getMap().containsKey(NODE1.getID()));

        DatabaseManager.delElement(sel.NODE,NODE1.getID());

    }

    @Test
    public void testDelNodeMapSuper() throws SQLException, InterruptedException, FileNotFoundException {
        DatabaseManager.addNode(NODE1);

        DatabaseManager.delElement(sel.NODE,NODE1.getID());

        Assertions.assertTrue(!MapSuper.getMap().containsKey(NODE1.getID()));
    }

    @Test
    public void testModNodeINFOMapSuper() throws SQLException, FileNotFoundException {
        DatabaseManager.addNode(NODE1);
        DatabaseManager.modNode(NODE1.getID(),new NodeSuper(-100,-100,"tst","test","test","test", NODE1.getID(),"tst", NODE1.getVertextList()));

        Assertions.assertTrue(MapSuper.getMap().get(NODE1.getID()).getXcoord() == -100);
        Assertions.assertTrue(MapSuper.getMap().get(NODE1.getID()).getYcoord() == -100);
        Assertions.assertTrue(MapSuper.getMap().get(NODE1.getID()).getFloor() == "tst");
        Assertions.assertTrue(MapSuper.getMap().get(NODE1.getID()).getBuilding() == "test");
        Assertions.assertTrue(MapSuper.getMap().get(NODE1.getID()).getType() == "tst");
        Assertions.assertTrue(MapSuper.getMap().get(NODE1.getID()).getLongName() == "test");
        Assertions.assertTrue(MapSuper.getMap().get(NODE1.getID()).getShortName() == "test");

        DatabaseManager.delElement(sel.NODE,NODE1.getID());
    }

    @Test
    public void testAddNeighborsMapSuper() throws SQLException, InterruptedException, FileNotFoundException {
        DatabaseManager.addNode(NODE1);
        DatabaseManager.addNode(NODE2);

        String edgeID = NODE1.getID()+"_"+NODE2.getID();
        DatabaseManager.addEdge(new EdgeSuper(edgeID, NODE1.getID(), NODE2.getID()));

        Assertions.assertTrue(MapSuper.getMap().get(NODE1.getID()).getNeighbors().contains(NODE2.getID()));
        Assertions.assertTrue(MapSuper.getMap().get(NODE2.getID()).getNeighbors().contains(NODE1.getID()));

        DatabaseManager.delElement(sel.EDGE, edgeID);
        DatabaseManager.delElement(sel.NODE,NODE1.getID());
        DatabaseManager.delElement(sel.NODE,NODE2.getID());

    }

    @Test
    public void testDelNeighborsMapSuper()throws SQLException, InterruptedException, FileNotFoundException {
        DatabaseManager.addNode(NODE1);
        DatabaseManager.addNode(NODE2);

        String edgeID = NODE1.getID()+"_"+NODE2.getID();
        DatabaseManager.addEdge(new EdgeSuper(edgeID, NODE1.getID(), NODE2.getID()));

        Assertions.assertTrue(MapSuper.getMap().get(NODE1.getID()).getNeighbors().contains(NODE2.getID()));
        Assertions.assertTrue(MapSuper.getMap().get(NODE2.getID()).getNeighbors().contains(NODE1.getID()));

        DatabaseManager.delElement(sel.EDGE, edgeID);

        Assertions.assertTrue(!MapSuper.getMap().get(NODE1.getID()).getNeighbors().contains(NODE2.getID()));
        Assertions.assertTrue(!MapSuper.getMap().get(NODE2.getID()).getNeighbors().contains(NODE1.getID()));

        DatabaseManager.delElement(sel.NODE,NODE1.getID());
        DatabaseManager.delElement(sel.NODE,NODE2.getID());

    }

    @Test
    public void testModEdgeNeighborsMapSuper() throws SQLException, InterruptedException, FileNotFoundException {
        DatabaseManager.addNode(NODE1);
        DatabaseManager.addNode(NODE2);
        DatabaseManager.addNode(NODE3);

        String edgeID = NODE1.getID()+"_"+NODE2.getID();
        String edgeIDNEW = NODE1.getID()+"_"+NODE3.getID();

        DatabaseManager.addEdge(new EdgeSuper(edgeID, NODE1.getID(), NODE2.getID()));

        Assertions.assertTrue(MapSuper.getMap().get(NODE1.getID()).getNeighbors().contains(NODE2.getID()));
        Assertions.assertTrue(MapSuper.getMap().get(NODE2.getID()).getNeighbors().contains(NODE1.getID()));

        DatabaseManager.modEdge(edgeID, new EdgeSuper( NODE1.getID() + "_" + NODE3.getID(), NODE1.getID(), NODE3.getID()));

        Assertions.assertTrue(MapSuper.getMap().get(NODE1.getID()).getNeighbors().contains(NODE3.getID()));
        Assertions.assertTrue(MapSuper.getMap().get(NODE3.getID()).getNeighbors().contains(NODE1.getID()));
        Assertions.assertFalse(MapSuper.getMap().get(NODE1.getID()).getNeighbors().contains(NODE2.getID()));
        Assertions.assertFalse(MapSuper.getMap().get(NODE2.getID()).getNeighbors().contains(NODE1.getID()));

        DatabaseManager.delElement(sel.EDGE, edgeID);
        DatabaseManager.delElement(sel.EDGE, edgeIDNEW);
        DatabaseManager.delElement(sel.NODE,NODE1.getID());
        DatabaseManager.delElement(sel.NODE,NODE2.getID());
        DatabaseManager.delElement(sel.NODE,NODE3.getID());

    }




}
