package edu.wpi.MochaManticores.database;

import edu.wpi.MochaManticores.Algorithms.AStar;
import edu.wpi.MochaManticores.Nodes.EdgeMapSuper;
import edu.wpi.MochaManticores.Nodes.EdgeSuper;
import edu.wpi.MochaManticores.Nodes.MapSuper;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class EdgeManager {
    private static final String Edge_csv_path = "data/bwMEdges.csv";
    private static final CSVmanager edgeCSV = new CSVmanager(Edge_csv_path);


    public static void updateEdge(Connection connection, String oldEdgeID, String newEdgeID, String oldStart, String newStart, String oldEnd, String newEnd) throws SQLException, FileNotFoundException {
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

        edgeCSV.updateEdgesInMap(connection);

    }

    public static void addEdge(Connection connection, String newEdgeID, String newStart, String newEnd) throws SQLException, FileNotFoundException {
        String sql = "INSERT INTO EDGES (edgeID, startNode, endNode) " +
                "VALUES (?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, newEdgeID);
        pstmt.setString(2, newStart);
        pstmt.setString(3, newEnd);
        pstmt.executeUpdate();

        if(!EdgeMapSuper.getMap().containsKey(newEdgeID)) {
            EdgeMapSuper.getMap().put(newEdgeID, new EdgeSuper(newEdgeID, newStart, newEnd));
            MapSuper.getMap().get(newStart).addNeighbor(newEnd, AStar.calcHeuristic(MapSuper.getMap().get(newStart),
                                                                                        MapSuper.getMap().get(newEnd)));
            edgeCSV.updateEdgesInMap(connection);
        }
        else {
            System.out.println("A Node with this EdgeID already exists.");
        }

    }

    public static void delEdge(Connection connection, String edgeId) throws SQLException, FileNotFoundException {
        PreparedStatement pstmt = connection.prepareStatement("DELETE FROM EDGES WHERE edgeID=?");
        pstmt.setString(1, edgeId);
        pstmt.executeUpdate();

        EdgeMapSuper.delEdgeNode(edgeId);

        //update CSV files
        edgeCSV.updateEdgesInMap(connection);
    }

    public static void showEdgeInformation(String edgeInfo) {
        System.out.println(edgeInfo);
    }
}
