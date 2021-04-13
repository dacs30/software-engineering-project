package edu.wpi.MochaManticores.database;

import edu.wpi.MochaManticores.Algorithms.GreedyBestFirst;
import edu.wpi.MochaManticores.Nodes.MapSuper;
import edu.wpi.MochaManticores.Nodes.NodeSuper;
import edu.wpi.MochaManticores.Nodes.VertexList;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.HashMap;

public class NodeManager {
    private static final String Node_csv_path = "data/MapMNodes.csv";



    public static void updateNodeCoords(Connection connection, String id, int xcoord, int ycoord) throws SQLException, FileNotFoundException {
        PreparedStatement pstmt = connection.prepareStatement("UPDATE NODES SET xcoord=?, ycoord=? WHERE nodeID=?");
        pstmt.setInt(1, xcoord);
        pstmt.setInt(2, ycoord);
        pstmt.setString(3, id);
        pstmt.executeUpdate();
        CSVmanager nodeCSV = new CSVmanager(Node_csv_path);
        nodeCSV.putNodesInMap(connection);
        NodeSuper tempNode = MapSuper.getMap().get(id);
        tempNode.setCoords(xcoord, ycoord);
        MapSuper.getMap().put(id, tempNode);
    }

    public static void updateNode(Connection connection, String newNodeID, String oldNodeID, int xcoord, int ycoord, String floor,
                                  String building, String nodeType, String longName, String shortName) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement("UPDATE NODES SET nodeID=?, xcoord=?, ycoord=?, building=?, nodeType=?, longName=?, shortName=?" +
                "WHERE nodeID=?");
        pstmt.setString(1, newNodeID);
        pstmt.setString(2, String.valueOf(xcoord));
        pstmt.setString(3, String.valueOf(ycoord));
        pstmt.setString(4, building);
        pstmt.setString(5, nodeType);
        pstmt.setString(6, longName);
        pstmt.setString(7, shortName);
        pstmt.setString(8, oldNodeID);
        pstmt.executeUpdate();


        VertexList oldNeighbors = MapSuper.getMap().get(oldNodeID).getVertextList();
        NodeSuper node = new NodeSuper(xcoord, ycoord, floor, building, longName, shortName, newNodeID, nodeType, oldNeighbors);
        MapSuper.getMap().remove(oldNodeID);
        MapSuper.getMap().put(newNodeID, node);
    }

    public static void updateNodeName(Connection connection, String id, String newName) throws SQLException, FileNotFoundException {
        PreparedStatement pstmt = connection.prepareStatement("UPDATE NODES SET longName=? WHERE nodeID=?");
        pstmt.setString(1, newName);
        pstmt.setString(2, id);
        pstmt.executeUpdate();
        CSVmanager nodeCSV = new CSVmanager(Node_csv_path);
        NodeSuper tempNode = MapSuper.getMap().get(id);
        tempNode.setLongName(newName);
        MapSuper.getMap().put(id, tempNode);
    }

    public static void createNode(Connection connection,String nodeID, int xcoord, int ycoord, String floor, String building, String nodeType, String longName, String shortName,
                                  String neighborID) throws SQLException, FileNotFoundException{
        String sql = "INSERT INTO NODES (nodeID, xcoord, ycoord, floor, building, nodeType, longName, shortName) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);

        pstmt.setString(1, nodeID);
        pstmt.setInt(2, xcoord);
        pstmt.setInt(3, ycoord);
        pstmt.setString(4, floor);
        pstmt.setString(5, building);
        pstmt.setString(6, nodeType);
        pstmt.setString(7, longName);
        pstmt.setString(8, shortName);
        pstmt.executeUpdate();

        CSVmanager nodeCSV = new CSVmanager(Node_csv_path);
        NodeSuper newNode = new NodeSuper(xcoord, ycoord, floor, building, longName, shortName, nodeID, nodeType, new VertexList(new HashMap<>()));
        newNode.addNeighbor(neighborID, GreedyBestFirst.calcHeuristic(newNode, MapSuper.getMap().get(neighborID)));
        MapSuper.getMap().put(nodeID, newNode);

    }

    public static void showNodeInformation(String nodeInfo) {
        System.out.println(nodeInfo);
    }

}
