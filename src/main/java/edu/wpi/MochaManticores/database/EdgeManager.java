package edu.wpi.MochaManticores.database;

import edu.wpi.MochaManticores.Algorithms.AStar;
import edu.wpi.MochaManticores.Nodes.EdgeMapSuper;
import edu.wpi.MochaManticores.Nodes.EdgeSuper;
import edu.wpi.MochaManticores.Nodes.MapSuper;
import edu.wpi.MochaManticores.Nodes.NodeSuper;

import java.io.*;
import java.sql.*;
import java.util.Map;

public class EdgeManager {
    private static final String Edge_csv_path = "data/bwMEdges.csv";
    private static final String CSVdelim = ",";

    public static void loadFromCSV(Connection connection){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(Edge_csv_path));
            String line = reader.readLine();

            while (line != null){
                line = reader.readLine();
                if(line == null) break;
                String[] row = line.split(CSVdelim);

                EdgeManager.addEdge_db(connection,row[0],row[1],row[2]);
            }
        } catch (FileNotFoundException | SQLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void addEdge(Connection connection, String newEdgeID, String newStart, String newEnd) throws SQLException{
        try{
            EdgeManager.addEdge_db(connection,newEdgeID,newStart,newEnd);
            EdgeManager.addEdge_map(newEdgeID,newStart,newEnd);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    //adds edge to CSV
    public static void addEdge_db(Connection connection, String newEdgeID, String newStart, String newEnd) throws SQLException {
        try {
            String sql = "INSERT INTO EDGES (edgeID, startNode, endNode) " +
                    "VALUES (?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, newEdgeID);
            pstmt.setString(2, newStart);
            pstmt.setString(3, newEnd);
            pstmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    //adds edges into map
    public static void addEdge_map(String newEdgeID, String newStart, String newEnd) {
        if (!EdgeMapSuper.getMap().containsKey(newEdgeID)) {
            // add edge to edge super
            EdgeMapSuper.getMap().put(newEdgeID, new EdgeSuper(newEdgeID, newStart, newEnd));

            //get neighbor nodes
            NodeSuper startNode = MapSuper.getMap().get(newStart);
            NodeSuper endNode = MapSuper.getMap().get(newEnd);

            if(startNode != null && endNode != null){
                //add neighbor edges
                startNode.addNeighbor(newEnd, AStar.calcHeuristic(startNode, endNode));
                endNode.addNeighbor(newStart, AStar.calcHeuristic(endNode, startNode));
            }

        } else {
            System.out.println("A Node with this EdgeID already exists.");
        }
    }

    public static void saveEdges(Connection connection)throws SQLException, FileNotFoundException{
        PrintWriter pw = new PrintWriter(new File(Edge_csv_path));
        StringBuilder sb = new StringBuilder();

        String sql = "SELECT * FROM EDGES";
        Statement stmt = connection.createStatement();
        ResultSet results = stmt.executeQuery(sql);
        ResultSetMetaData rsmd = results.getMetaData();

        for(int i = 1; i <= rsmd.getColumnCount(); i++) {
            sb.append(rsmd.getColumnName(i));
            sb.append(",");
        }
        sb.append("\n");
        while (results.next()) {
            //writing to csv file
            for(int i = 1; i <= rsmd.getColumnCount(); i++) {
                sb.append(results.getString(i));
                sb.append(",");
            }
            sb.append("\n");
        }
        results.close();
        pw.write(sb.toString());
        pw.close();
    }

    // useless
    public static void updateEdgesMap(Connection connect) throws SQLException {
        String sql = "SELECT * FROM EDGES";
        Statement stmt = connect.createStatement();
        ResultSet results = stmt.executeQuery(sql);
        while (results.next()) {
            EdgeManager.addEdge_map(results.getString(1),results.getString(2),results.getString(3));
        }
    }


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

    }

    public static void showEdgeInformation(Connection connection) throws SQLException{
        StringBuilder sb = new StringBuilder();

        String sql = "SELECT * FROM EDGES";
        Statement stmt = connection.createStatement();
        ResultSet results = stmt.executeQuery(sql);
        ResultSetMetaData rsmd = results.getMetaData();

        for(int i = 1; i <= rsmd.getColumnCount(); i++) {
            sb.append(rsmd.getColumnName(i));
            sb.append(",");
        }
        sb.append("\n");
        while (results.next()) {
            for(int i = 1; i <= rsmd.getColumnCount(); i++) {
                sb.append(results.getString(i));
                sb.append(",");
            }
            sb.append("\n");
        }
        results.close();


        System.out.println(sb.toString());
    }
}
