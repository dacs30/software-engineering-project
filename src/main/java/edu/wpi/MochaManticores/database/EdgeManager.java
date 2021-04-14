package edu.wpi.MochaManticores.database;

import edu.wpi.MochaManticores.Algorithms.AStar;
import edu.wpi.MochaManticores.Nodes.EdgeMapSuper;
import edu.wpi.MochaManticores.Nodes.EdgeSuper;
import edu.wpi.MochaManticores.Nodes.MapSuper;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    public static void updateEdge(Connection connection, String oldEdgeID, String newEdgeID, String oldStart, String newStart, String oldEnd, String newEnd) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement("UPDATE EDGES SET edgeID=?, startNode=?, endNode=? WHERE edgeID=?");
        pstmt.setString(1, newEdgeID);
        pstmt.setString(2, newStart);
        pstmt.setString(3, newEnd);
        pstmt.setString(4, oldEdgeID);
        pstmt.executeUpdate();

        EdgeMapSuper.getMap().remove(oldEdgeID);
        EdgeSuper edge = new EdgeSuper(newEdgeID, newStart, newEnd);
        EdgeMapSuper.getMap().put(newEdgeID, edge);

        MapSuper.getMap().get(oldStart).delNeighbor(oldEnd);
        MapSuper.getMap().get(newStart).addNeighbor(newEnd, AStar.calcHeuristic(MapSuper.getMap().get(newStart),
                                                                                            MapSuper.getMap().get(newEnd)));

    }

    public static void addEdge(Connection connection, String newEdgeID, String newStart, String newEnd) throws SQLException {
        String sql = "INSERT INTO EDGES (edgeID, startNode, endNode) " +
                "VALUES (?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, newEdgeID);
        pstmt.setString(2, newStart);
        pstmt.setString(3, newEnd);
        pstmt.executeUpdate();

        if(!EdgeMapSuper.getMap().containsKey(newEdgeID)) {
            EdgeMapSuper.getMap().put(newEdgeID, new EdgeSuper(newEdgeID, newStart, newEnd));
        }
        else {
            System.out.println("A Node with this EdgeID already exists.");
        }

    }

    public static void showEdgeInformation(String edgeInfo) {
        System.out.println(edgeInfo);
    }
}
