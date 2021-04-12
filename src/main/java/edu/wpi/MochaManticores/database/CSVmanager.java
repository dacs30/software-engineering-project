package edu.wpi.MochaManticores.database;

import edu.wpi.MochaManticores.Nodes.MapSuper;
import edu.wpi.MochaManticores.Nodes.NodeSuper;
import edu.wpi.MochaManticores.Nodes.VertexList;

import java.io.*;
import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;

public class CSVmanager {

    //Attributes
    private String CSVpath;
    private String CSVdelim = ",";

    //Constructor
    public CSVmanager(String path) {
        this.CSVpath = path;
    }

    //load NODE CSV
    public void load_node_csv(Connection connect){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(this.CSVpath));
            String line = reader.readLine();

            while (line != null){
                line = reader.readLine();
                String[] row = line.split(this.CSVdelim);
                NodeManager.createNode(connect,row[0],Integer.parseInt(row[1]),Integer.parseInt(row[2]),row[3],row[4],row[5],row[6],row[7]);
            }
        } catch (FileNotFoundException | SQLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    //load EDGES CSV
    public void load_edges_csv(Connection connect){
        String CSVpath = this.CSVpath;
        try{
            BufferedReader reader = new BufferedReader(new FileReader(CSVpath));
            String line = reader.readLine();

            while (line != null){
                line = reader.readLine();
                String[] row = line.split(this.CSVdelim);
                EdgeManager.createEdge(connect,row[1],row[2]);
            }
        } catch (FileNotFoundException | SQLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    // add nodes to map
    void addNodesToMap(Connection connect)throws SQLException{
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
    void saveNodesinCSV(Connection connect) throws SQLException, FileNotFoundException {
        PrintWriter pw = new PrintWriter(new File(this.CSVpath));
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

    void addEdgesToMap(Connection connection) throws SQLException{
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
     void saveEdgesInCSV(Connection connect) throws FileNotFoundException, SQLException {
        PrintWriter pw = new PrintWriter(new File(this.CSVpath));
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
}
