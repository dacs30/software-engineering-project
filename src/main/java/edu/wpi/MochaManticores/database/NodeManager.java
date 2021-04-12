package edu.wpi.MochaManticores.database;

import edu.wpi.MochaManticores.Nodes.MapSuper;
import edu.wpi.MochaManticores.Nodes.NodeSuper;
import edu.wpi.MochaManticores.Nodes.VertexList;

import java.io.*;
import java.sql.*;
import java.util.HashMap;

public class NodeManager {
    private static final String Node_csv_path = "data/MapMNodes.csv";

    //load NODE CSV
    public static void load_node_csv(Connection connect){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(Node_csv_path));
            String line = reader.readLine();

            while (line != null){
                line = reader.readLine();
                String[] row = line.split(",");
                NodeManager.createNode(connect,row[0],Integer.parseInt(row[1]),Integer.parseInt(row[2]),row[3],row[4],row[5],row[6],row[7]);
            }
        } catch (FileNotFoundException | SQLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // add nodes to map
    public static void addNodesToMap(Connection connect)throws SQLException{
        try{
            String sql = "SELECT * FROM NODES";
            Statement stmt = connect.createStatement();
            ResultSet results = stmt.executeQuery(sql);

            while(results.next()){
                NodeManager.addNodeToMap_result(results);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //save NODE CSV
    public static void saveNodesinCSV(Connection connect) throws SQLException, FileNotFoundException {
        PrintWriter pw = new PrintWriter(new File(Node_csv_path));
        StringBuilder sb = new StringBuilder();


        String sql = "SELECT * FROM NODES";
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

    public static void updateNodePosition(Connection connection, String id,  int xcoord, int ycoord) throws SQLException, FileNotFoundException {
        PreparedStatement pstmt = connection.prepareStatement("UPDATE NODES SET xcoord=?, ycoord=? WHERE nodeID=?");
        pstmt.setInt(1, xcoord);
        pstmt.setInt(2, ycoord);
        pstmt.setString(3, id);
        pstmt.executeUpdate();
        //CSVmanager nodeCSV = new CSVmanager(Node_csv_path);
        //nodeCSV.addNodesToMap(connection);
        NodeSuper tempNode = MapSuper.getMap().get(id);
        tempNode.setCoords(xcoord, ycoord);
        MapSuper.getMap().put(id, tempNode);
    }

    public static void updateNodeName(Connection connection, String id, String newName) throws SQLException, FileNotFoundException {
        PreparedStatement pstmt = connection.prepareStatement("UPDATE NODES SET longName=? WHERE nodeID=?");
        pstmt.setString(1, newName);
        pstmt.setString(2, id);
        pstmt.executeUpdate();
        //CSVmanager nodeCSV = new CSVmanager(Node_csv_path);
        NodeSuper tempNode = MapSuper.getMap().get(id);
        tempNode.setLongName(newName);
        MapSuper.getMap().put(id, tempNode);
    }

    public static void createNode(Connection connection,String nodeID, int xcoord, int ycoord, String floor, String building, String nodeType, String longName, String shortName) throws SQLException, FileNotFoundException{
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
    }

    public static void addNodeToMap_result(ResultSet results) throws SQLException{
        try {
            NodeSuper tempNode = new NodeSuper(Integer.parseInt(results.getString(2)), Integer.parseInt(results.getString(3)),
                    results.getString(4), results.getString(5), results.getString(7), results.getString(8),
                    results.getString(1), results.getString(6), new VertexList(new HashMap<>()));
            MapSuper.getMap().put(tempNode.getID(), tempNode);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void addNodeToMap(String nodeID, int xcoord, int ycoord, String floor, String building, String nodeType, String longName, String shortName){
        NodeSuper newNode = new NodeSuper(xcoord, ycoord, floor, building, longName, shortName, nodeID, nodeType, new VertexList(new HashMap<>()));
        MapSuper.getMap().put(nodeID, newNode);
    }

    public static void showNodeInformation(String nodeInfo) {
        System.out.println(nodeInfo);
    }

}
