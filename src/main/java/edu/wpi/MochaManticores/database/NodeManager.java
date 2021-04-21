package edu.wpi.MochaManticores.database;

import edu.wpi.MochaManticores.Nodes.*;
import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

public class NodeManager {
    private static String Node_csv_path = "data/bwMNodes.csv";
    private static final String CSVdelim = ",";

    public static void loadFromCSV(Connection connect){
        //loads database and sets hashmap
        try{
            BufferedReader reader = new BufferedReader(new FileReader(Node_csv_path));
            String line = reader.readLine();

            while (line != null){
                line = reader.readLine();
                if(line == null) break;
                String[] row = line.split(CSVdelim);

                addNode_db(connect, row[0],
                        row[1], row[2],
                        row[3], row[4],
                        row[5], row[6],
                        row[7]);
            }
        } catch (SQLException | IOException e){
            e.printStackTrace();
        }
    }

    public static void addNode(Connection connection, String newNodeID, String xcoord, String ycoord, String floor,
                               String building, String nodeType, String longName, String shortName) throws SQLException{
        try{
            addNode_db(connection,newNodeID,xcoord,ycoord,floor,building,nodeType,longName,shortName);
            addNode_map(newNodeID,xcoord,ycoord,floor,building,nodeType,longName,shortName);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    //adds to database and hashmap
    public static void addNode_db(Connection connection, String newNodeID, String xcoord, String ycoord, String floor,
                               String building, String nodeType, String longName, String shortName) throws SQLException{

        try {
            String sql = "INSERT INTO NODES (nodeID, xcoord, ycoord, floor, building, nodeType, longName, shortName) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, newNodeID);
            pstmt.setInt(2, Integer.parseInt(xcoord));
            pstmt.setInt(3, Integer.parseInt(ycoord));
            pstmt.setString(4,floor);
            pstmt.setString(5, building);
            pstmt.setString(6, nodeType);
            pstmt.setString(7, longName);
            pstmt.setString(8, shortName);
            pstmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void addNode_map(String newNodeID, String xcoord, String ycoord, String floor,
                                   String building, String nodeType, String longName, String shortName) throws SQLException{
        if(!MapSuper.getMap().containsKey(newNodeID)) {
            MapSuper.getMap().put(newNodeID, new NodeSuper(Integer.parseInt(xcoord), Integer.parseInt(ycoord), floor, building, longName, shortName, newNodeID, nodeType, new VertexList(new HashMap<>())));
        }
        else {
            System.out.printf("This node %s already exists\n", newNodeID);
        }
    }

    //saves values in database to a CSV file
    public static void saveNodes(Connection connect) throws  SQLException,FileNotFoundException{
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

    //TODO currently this iterates through database adding all elements to the map, should be unnessiary now
    public static void updateNodesMap(Connection connect) throws SQLException {
        String sql = "SELECT * FROM NODES";
        Statement stmt = connect.createStatement();
        ResultSet results = stmt.executeQuery(sql);

        while (results.next()) {
            addNode_map(results.getString(1).replaceAll("\\s",""), results.getString(2),
                                    results.getString(3), results.getString(4),
                                    results.getString(5), results.getString(6),
                                    results.getString(7), results.getString(8));
        }
    }

    public static void updateNodeCoords(Connection connection, String id, int xcoord, int ycoord) throws SQLException, FileNotFoundException {
        //update node in database
        PreparedStatement pstmt = connection.prepareStatement("UPDATE NODES SET xcoord=?, ycoord=? WHERE nodeID=?");
        pstmt.setInt(1, xcoord);
        pstmt.setInt(2, ycoord);
        pstmt.setString(3, id);
        pstmt.executeUpdate();

        //update node in map
        NodeSuper tempNode = MapSuper.getMap().get(id);
        tempNode.setCoords(xcoord, ycoord);
        MapSuper.getMap().put(id, tempNode);
    }

    public static void updateNode(Connection connection, String newNodeID, String oldNodeID, int xcoord, int ycoord, String floor,
                                  String building, String nodeType, String longName, String shortName) throws SQLException, FileNotFoundException {
        //update node entry in database
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



        // create new node super and replace old node
        VertexList oldNeighbors = MapSuper.getMap().get(oldNodeID).getVertextList();
        NodeSuper node = new NodeSuper(xcoord, ycoord, floor, building, longName, shortName, newNodeID, nodeType, oldNeighbors);
        MapSuper.getMap().remove(oldNodeID);
        MapSuper.getMap().put(newNodeID, node);
    }

    public static void updateNodeName(Connection connection, String id, String newName) throws SQLException, FileNotFoundException {
        //update in database
        PreparedStatement pstmt = connection.prepareStatement("UPDATE NODES SET longName=? WHERE nodeID=?");
        pstmt.setString(1, newName);
        pstmt.setString(2, id);
        pstmt.executeUpdate();

        // create new node super and change name in map
        NodeSuper tempNode = MapSuper.getMap().get(id);
        tempNode.setLongName(newName);
        MapSuper.getMap().put(id, tempNode);
    }

    public static void delNode(Connection connection, String nodeID) throws SQLException, FileNotFoundException {
        //remove node from database
        PreparedStatement pstmt = connection.prepareStatement("DELETE FROM NODES WHERE nodeID=?");
        pstmt.setString(1, nodeID);
        pstmt.executeUpdate();

        // remove node from map
        System.out.println(MapSuper.getMap().get(nodeID).getNeighbors());
        LinkedList<String> neighbors = new LinkedList<>();
        for(String neighborID : MapSuper.getMap().get(nodeID).getNeighbors()) {
            neighbors.add(neighborID);
        }
        for(String neighborID : neighbors) {
            String edgeID = nodeID+"_"+neighborID;
            EdgeManager.delEdge(connection, edgeID);
        }
        MapSuper.getMap().remove(nodeID);
    }

    public static void showNodeInformation(Connection connection) throws SQLException{
        StringBuilder sb = new StringBuilder();

        String sql = "SELECT * FROM NODES";
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

        System.out.println(sb.toString());
    }

    public static ResultSet selectNode(Connection connection, String NodeID) throws SQLException{
        try {
            String sql = "SELECT * FROM NODES WHERE NODEID =" + NodeID;
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(sql);
        }catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println("failed to select NODEID: " + NodeID);
        return null;
    }

    public static String getNode_csv_path() {
        return Node_csv_path;
    }

    public static void setNode_csv_path(String node_csv_path) {
        Node_csv_path = node_csv_path;
    }

    public static void cleanTable(Connection connection) throws SQLException {
        String sql = "DELETE FROM NODES";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        int result = pstmt.executeUpdate();

        //clean hashmap
        MapSuper.getMap().clear();

    }
}
