package edu.wpi.MochaManticores.database;

import java.io.FileNotFoundException;
import java.sql.*;

public class NodeManager {
    private static final String Node_csv_path = "data/nodes.csv";

    public static void updateNode(Connection connection, String id,  int xcoord, int ycoord) throws SQLException, FileNotFoundException {
        PreparedStatement pstmt = connection.prepareStatement("UPDATE NODES SET xcoord=?, ycoord=? WHERE nodeID=?");
        pstmt.setInt(1, xcoord);
        pstmt.setInt(2, ycoord);
        pstmt.setString(3, id);
        pstmt.executeUpdate();
        CSVmanager nodeCSV = new CSVmanager(Node_csv_path);
        nodeCSV.save_node_csv(connection);
    }

    public static void updateNodeName(Connection connection, String id, String newName) throws SQLException, FileNotFoundException {
        PreparedStatement pstmt = connection.prepareStatement("UPDATE NODES SET longName=? WHERE nodeID=?");
        pstmt.setString(1, newName);
        pstmt.setString(2, id);
        pstmt.executeUpdate();
        CSVmanager nodeCSV = new CSVmanager(Node_csv_path);
        nodeCSV.save_node_csv(connection);
    }

    public static void showNodeInformation(String nodeInfo) {
        System.out.println(nodeInfo);
    }

}
