package edu.wpi.MochaManticores.database;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EdgeManager {
    private static final String Edge_csv_path = "data/edges.csv";

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

    public static void showEdgeInformation(String edgeInfo) {
        System.out.println(edgeInfo);
    }
}
