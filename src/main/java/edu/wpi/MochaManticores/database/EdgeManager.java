package edu.wpi.MochaManticores.database;

import edu.wpi.MochaManticores.Nodes.MapSuper;

import java.io.*;
import java.sql.*;
import java.util.Map;

public class EdgeManager {
    private static final String Edge_csv_path = "data/MapMEdges.csv";

    public static void load_edges_csv(Connection connect){
        String CSVpath = Edge_csv_path;
        try{
            BufferedReader reader = new BufferedReader(new FileReader(CSVpath));
            String line = reader.readLine();

            while (line != null){
                line = reader.readLine();
                String[] row = line.split(",");
                EdgeManager.createEdge(connect,row[1],row[2]);
            }
        } catch (FileNotFoundException | SQLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void addEdgesToMap(Connection connection) throws SQLException{
        try{
            String sql = "SELECT * FROM EDGES";
            Statement stmt = connection.createStatement();
            ResultSet results = stmt.executeQuery(sql);

            while(results.next()){
                EdgeManager.addEdgeToMap_results(results);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    //save EDGES CSV
    public static void saveEdgesInCSV(Connection connect) throws FileNotFoundException, SQLException {
        PrintWriter pw = new PrintWriter(new File(Edge_csv_path));
        StringBuilder sb = new StringBuilder();

        String sql = "SELECT * FROM EDGES";
        Statement stmt = connect.createStatement();
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
        pw.write(sb.toString());
        pw.close();
    }

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
    }

    public static void addEdgeToMap_results(ResultSet results) throws SQLException{
        try{
            MapSuper.getMap().get(results.getString(2)).addNeighbor(results.getString(3), 0);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void showEdgeInformation(String edgeInfo) {
        System.out.println(edgeInfo);
    }
}
