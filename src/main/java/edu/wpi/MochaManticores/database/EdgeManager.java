package edu.wpi.MochaManticores.database;

import edu.wpi.MochaManticores.Algorithms.AStar2;
import edu.wpi.MochaManticores.Exceptions.InvalidElementException;
import edu.wpi.MochaManticores.Nodes.EdgeMapSuper;
import edu.wpi.MochaManticores.Nodes.EdgeSuper;
import edu.wpi.MochaManticores.Nodes.MapSuper;
import edu.wpi.MochaManticores.Nodes.NodeSuper;
import edu.wpi.MochaManticores.views.edgesPage;

import java.io.*;
import java.sql.*;

public class EdgeManager extends Manager<EdgeSuper>{
    private static String Edge_csv_path = "data/bwMEdges.csv";
    private static Connection connection = null;
    private static final String CSVdelim = ",";

    EdgeManager(Connection connection, String Edge_csv_path){
        this.connection = connection;
        if(Edge_csv_path != null){
            this.Edge_csv_path = Edge_csv_path;
        }
    }

    public void loadFromCSV(){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(Edge_csv_path));
            String line = reader.readLine();

            while (line != null){
                line = reader.readLine();
                if(line == null) break;
                String[] row = line.split(CSVdelim);

                EdgeSuper edge = new EdgeSuper(row[0],row[1],row[2]);
                addElement_db(edge);
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void addElement(EdgeSuper edge){
        addElement_db(edge);
        addElement_map(edge);
    }
    //adds edge to CSV
    public void addElement_db(EdgeSuper edge){
        try {
            String sql = "INSERT INTO EDGES (edgeID, startNode, endNode) " +
                    "VALUES (?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, edge.getEdgeID());
            pstmt.setString(2, edge.getStartingNode());
            pstmt.setString(3, edge.getEndingNode());
            pstmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    //adds edges into map
    public void addElement_map(EdgeSuper edge) {
        if (!EdgeMapSuper.getMap().containsKey(edge.getEdgeID())) {
            // add edge to edge super
            EdgeMapSuper.getMap().put(edge.getEdgeID(), edge);

            //get neighbor nodes
            NodeSuper startNode = MapSuper.getMap().get(edge.getStartingNode());
            NodeSuper endNode = MapSuper.getMap().get(edge.getEndingNode());

            if(startNode != null && endNode != null){
                //add neighbor edges and make them bi-directional
                startNode.addNeighbor(edge.getEndingNode(), AStar2.calcHeuristic(startNode, endNode));
                endNode.addNeighbor(edge.getStartingNode(), AStar2.calcHeuristic(endNode, startNode));
            }

        } else {
            System.out.println("A Node with this EdgeID already exists.");
        }
    }

    public void delElement(String edgeID) throws SQLException {
        //remove edge from database
        PreparedStatement pstmt = connection.prepareStatement("DELETE FROM EDGES WHERE edgeID=?");
        pstmt.setString(1, edgeID);
        pstmt.executeUpdate();

        //remove as neighbors from the nodes
        String[] edges = edgeID.split("_");
        MapSuper.getMap().get(edges[0]).delNeighbor(edges[1]);
        MapSuper.getMap().get(edges[1]).delNeighbor(edges[0]);

        // remove node from map
        EdgeMapSuper.delEdgeNode(edgeID);
    }

    public void modElement(String oldEdgeID, EdgeSuper edge) throws SQLException {
        if(MapSuper.getMap().containsKey(edge.getStartingNode()) && MapSuper.getMap().containsKey(edge.getEndingNode())) {
            delElement(oldEdgeID);
            addElement(edge);
        } else {
            System.out.println("The New Start Node or the New End Node is Invalid");
        }

    }

    public void saveElements()throws SQLException, FileNotFoundException{
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

    public EdgeSuper getElement(String edgeID) throws InvalidElementException

    public void showEdgeInformation() throws SQLException{
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

    public String getCSV_path() {
        return Edge_csv_path;
    }

    public void setCSV_path(String edge_csv_path) {
        Edge_csv_path = edge_csv_path;
    }

    public void cleanTable() throws SQLException {
        String sql = "DELETE FROM EDGES";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        int result = pstmt.executeUpdate();

        //clean hashmap
        EdgeMapSuper.getMap().clear();
    }

    // useless
    public void updateElementMap() throws SQLException {
        String sql = "SELECT * FROM EDGES";
        Statement stmt = connection.createStatement();
        ResultSet results = stmt.executeQuery(sql);
        while (results.next()) {
            create edge
            addEdge_map(edge);
        }
    }
}
