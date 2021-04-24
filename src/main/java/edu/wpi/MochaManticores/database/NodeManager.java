package edu.wpi.MochaManticores.database;

import edu.wpi.MochaManticores.Exceptions.InvalidElementException;
import edu.wpi.MochaManticores.Nodes.*;
import edu.wpi.MochaManticores.views.nodePage;

import java.io.*;
import java.sql.*;
import java.util.HashMap;

public class NodeManager extends Manager<NodeSuper>{
    private static String Node_csv_path = "data/bwMNodes.csv";
    private static Connection connection = null;
    private static final String CSVdelim = ",";

    NodeManager(Connection connection, String Node_csv_path){
        this.connection = connection;
        if(Node_csv_path != null){
            this.Node_csv_path = Node_csv_path;
        }
    }

    public void loadFromCSV(){
        //loads database and sets hashmap
        try{
            BufferedReader reader = new BufferedReader(new FileReader(Node_csv_path));
            String line = reader.readLine();

            while (line != null){
                line = reader.readLine();
                if(line == null) break;
                String[] row = line.split(CSVdelim);

                NodeSuper node = new NodeSuper(Integer.parseInt(row[1]),Integer.parseInt(row[2]),
                        row[3],row[4],row[6],row[7],row[0],row[5],new VertexList(new HashMap<>()));
                addElement_db(node);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void addElement(NodeSuper node){
        addElement_db(node);
        addElement_map(node);
    }

    //adds to database and hashmap
    public void addElement_db(NodeSuper node){

        try {
            String sql = "INSERT INTO NODES (nodeID, xcoord, ycoord, floor, building, nodeType, longName, shortName) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, node.getID());
            pstmt.setInt(2, node.getXcoord());
            pstmt.setInt(3, node.getYcoord());
            pstmt.setString(4,node.getFloor());
            pstmt.setString(5, node.getBuilding());
            pstmt.setString(6, node.getType());
            pstmt.setString(7, node.getLongName());
            pstmt.setString(8, node.getShortName());
            pstmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void addElement_map(NodeSuper node){
        if(!MapSuper.getMap().containsKey(node.getID())) {
            MapSuper.addNode(node);
        }
        else {
            System.out.printf("This node %s already exists\n", node.getID());
        }
    }

    public void delElement(String nodeID) throws SQLException{
        //remove node from database
        PreparedStatement pstmt = connection.prepareStatement("DELETE FROM NODES WHERE nodeID=?");
        pstmt.setString(1, nodeID);
        pstmt.executeUpdate();

        // remove node from map
        MapSuper.getMap().remove(nodeID);
    }

    public void modElement(String oldNodeID, NodeSuper node) throws SQLException {
        delElement(oldNodeID);
        addElement(node);
    }

    //saves values in database to a CSV file
    public void saveElements() throws  SQLException,FileNotFoundException{
        PrintWriter pw = new PrintWriter(new File(Node_csv_path));
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

    public NodeSuper getElement(String NodeID) throws InvalidElementException{
        // unlike employeeManager, we get nodes from the map so that they include neighbors
        if(MapSuper.getMap().containsKey(NodeID)){
            return MapSuper.getNode(NodeID);
        }else{
            throw new InvalidElementException();
        }
    }

    public String getCSV_path() {
        return Node_csv_path;
    }

    public void setCSV_path(String node_csv_path) {
        Node_csv_path = node_csv_path;
    }

    public void cleanTable() throws SQLException {
        String sql = "DELETE FROM NODES";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        int result = pstmt.executeUpdate();

        //clean hashmap
        MapSuper.getMap().clear();

    }

    //TODO currently this iterates through database adding all elements to the map, should be unnessiary now
    public void updateElementMap() throws SQLException {
        String sql = "SELECT * FROM NODES";
        Statement stmt = connection.createStatement();
        ResultSet results = stmt.executeQuery(sql);

        while (results.next()) {
            NodeSuper node = new NodeSuper(results.getString(1), results.getString(2),
                    results.getString(3), results.getString(4),
                    results.getString(5), results.getString(6),
                    results.getString(7), results.getString(8));
        }
    }
}
