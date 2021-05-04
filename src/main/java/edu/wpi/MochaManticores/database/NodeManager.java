package edu.wpi.MochaManticores.database;

import edu.wpi.MochaManticores.Exceptions.InvalidElementException;
import edu.wpi.MochaManticores.Nodes.*;
import edu.wpi.MochaManticores.views.nodePage;
import javafx.util.Pair;

import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;

public class NodeManager extends Manager<NodeSuper>{
    private static String Node_csv_path = "data/MapMAllnodes.csv";
    private static Connection connection = null;
    private static final String CSVdelim = ",";

    NodeManager(Connection connection, String Node_csv_path){
        this.connection = connection;
        if(Node_csv_path != null){
            this.Node_csv_path = Node_csv_path;
        }
    }

    /*
     function loadFromCSV()
     load elements from the CSV
     */
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

    /*
    function: addElement(NodeSuper)
    add element to database and map
     */
    public void addElement(NodeSuper node){
        addElement_db(node);
        addElement_map(node);
    }

    /*
    function: addElement_db
    adds element to just the database
     */
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

    /*
    function: addElement_map
    adds element to just the hashmap
     */
    public void addElement_map(NodeSuper node){
        if(!MapSuper.getMap().containsKey(node.getID())) {
            MapSuper.addNode(node);
        }
        else {
            System.out.printf("This node %s already exists\n", node.getID());
        }
    }

    /*
    function: delElement(s)
    deletes element of given ID string
     */
    public void delElement(String nodeID) throws SQLException{
        //remove node from database
        PreparedStatement pstmt = connection.prepareStatement("DELETE FROM NODES WHERE nodeID=?");
        pstmt.setString(1, nodeID);
        pstmt.executeUpdate();

        // remove node from map
        MapSuper.getMap().remove(nodeID);
    }

    /*
    function: modElement(ID,NodeSuper)
    modifies element of ID s to become element nodeSuper
     */
    public void modElement(String oldNodeID, NodeSuper node) throws SQLException {
        delElement(oldNodeID);
        addElement(node);
    }

    /*
    function: saveElements()
    saves elements to given CSV file
     */
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

    /*
    function: getElement()
    returns NodeSuper object, specified by ID
     */
    public NodeSuper getElement(String NodeID) throws InvalidElementException{
        // unlike employeeManager, we get nodes from the map so that they include neighbors
        if(MapSuper.getMap().containsKey(NodeID)){
            return MapSuper.getNode(NodeID);
        }else{
            throw new InvalidElementException();
        }
    }

    /*
    function: getCSV_path()
    getter for CSV_path
    return string
     */
    public String getCSV_path() {
        return Node_csv_path;
    }

    /*
    function setCSV_path()
    setter for CSV_path
     */
    public void setCSV_path(String node_csv_path) {
        Node_csv_path = node_csv_path;
    }

    /*
    function: cleanTable()
    saves and empties database table
     */
    public void cleanTable() throws SQLException {
        String sql = "DELETE FROM NODES";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        int result = pstmt.executeUpdate();

        //clean hashmap
        MapSuper.getMap().clear();

    }

    /*
    function updateElementMap()
    updates the hashmap to match the data in the database, used only during startup
     */
    public void updateElementMap() throws SQLException {
        String sql = "SELECT * FROM NODES";
        Statement stmt = connection.createStatement();
        ResultSet result = stmt.executeQuery(sql);

        while (result.next()) {
            NodeSuper node = new NodeSuper(Integer.parseInt(result.getString(2)),Integer.parseInt(result.getString(3)),
                    result.getString(4),result.getString(5),
                    result.getString(7),result.getString(8),
                    result.getString(1),result.getString(6),
                    new VertexList(new HashMap<>()));
            addElement_map(node);
        }
    }

    public LinkedList<Pair<String,String>> getElementIDs(){
        LinkedList<Pair<String,String>> idList = new LinkedList<Pair<String,String>>();
        for(NodeSuper node : MapSuper.getMap().values()){
            idList.add(new Pair(node.getID(),node.getLongName()));
        }

        return idList;
    }
}
