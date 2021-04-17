package edu.wpi.MochaManticores;


import edu.wpi.MochaManticores.Nodes.MapSuper;
import edu.wpi.MochaManticores.Nodes.NodeSuper;
import edu.wpi.MochaManticores.Nodes.VertexList;
import edu.wpi.MochaManticores.database.Mdb;
import edu.wpi.MochaManticores.database.NodeManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.HashMap;

public class DatabaseTest {
    public NodeSuper NODE1;
    public NodeSuper NODE2;
    public NodeSuper NODE3;
    public NodeSuper NODE4;
    public NodeSuper NODE5;
    public MapSuper TESTMAP1;
    private static DatabaseMetaData meta;
    private static Connection connection = null;
    public static String JDBC_URL = "jdbc:derby:Mdatabase;create=true";

    //Setup
    public void setUp() throws SQLException, InterruptedException {
        //init the database for testing
        Mdb.databaseStartup();
        connection = Mdb.getConnection();

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

        this.TESTMAP1 = new MapSuper("TESTMAP1");

    }

    @Test
    public void testAddNode() throws SQLException, InterruptedException, FileNotFoundException {
        this.setUp();
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

}
