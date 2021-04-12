package edu.wpi.MochaManticores.database;

import edu.wpi.MochaManticores.Nodes.MapSuper;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class EdgeManager {
    private static final String Edge_csv_path = "data/MapMEdges.csv";

    public static void createEdge(Connection connection, String Node1, String Node2) throws SQLException, FileNotFoundException {
        String edgeId = Node1 + "_" + Node2;
        String sql = "INSERT INTO EDGES (edgeID, startNode, endNode) " +
                "VALUES (?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, edgeId);
        pstmt.setString(2, Node1);
        pstmt.setString(3, Node2);
        pstmt.executeUpdate();

    }

    public static void addEdgeToMap(String node1ID, String node2ID){
        MapSuper.getMap().get(node1ID).addNeighbor(node2ID, 0);
        MapSuper.getMap().get(node2ID).addNeighbor(node1ID, 0);
    }

    public static void addEdgeToMap_results(ResultSet results) throws SQLException{
        try{
            MapSuper.getMap().get(results.getString(2)).addNeighbor(results.getString(3), 0);
            MapSuper.getMap().get(results.getString(3)).addNeighbor(results.getString(2), 0);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void showEdgeInformation(String edgeInfo) {
        System.out.println(edgeInfo);
    }
}
