package edu.wpi.MochaManticores.database;


import java.io.FileNotFoundException;
import java.sql.*;

public class Mdb {

    private static DatabaseMetaData meta;
    public static String JDBC_URL = "jdbc:derby:Mdatabase;create=true";
    private static final String Edge_csv_path = "data/MapMEdges.csv";
    private static final String Node_csv_path = "data/MapMNodes.csv";
    private static Connection connection = null;


    public static void databaseStartup() throws SQLException, FileNotFoundException {
        System.out.println("-------Embedded Apache Derby Connection Testing --------");
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Apache Derby Driver not found. Add the classpath to your module.");
            System.out.println("For IntelliJ do the following:");
            System.out.println("File | Project Structure, Modules, Dependency tab");
            System.out.println("Add by clicking on the green plus icon on the right of the window");
            System.out.println("Select JARs or directories. Go to the folder where the database JAR is located");
            System.out.println("Click OK, now you can compile your program and run it.");
            e.printStackTrace();
            return;
        }

        System.out.println("Apache Derby driver registered!\n");

        try {
            connection = DriverManager.getConnection(JDBC_URL);
            meta = connection.getMetaData();
        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return;
        }
        Statement stmt = connection.createStatement();
        CSVmanager nodeCSV = new CSVmanager(Node_csv_path);
        CSVmanager edgeCSV = new CSVmanager(Edge_csv_path);
        //create data tables
        try {
            ResultSet rs = meta.getTables(null, "APP", "NODES", null);
            if(!rs.next()) {
                System.out.println("Creating Node Table");
                String sql = "CREATE TABLE NODES" +
                        "(nodeID CHAR(10) not NULL, " +
                        " xcoord INT, " +
                        " ycoord INT, " +
                        " floor VARCHAR(3), " +
                        " building VARCHAR(50), " +
                        " nodeType CHAR(4), " +
                        " longName VARCHAR(255)," +
                        " shortName VARCHAR(255), " +
                        " PRIMARY KEY (nodeID))";
                stmt.executeUpdate(sql);
                nodeCSV.load_node_csv(connection);
            }

            nodeCSV.addNodesToMap(connection);

            rs = meta.getTables(null, "APP", "EDGES", null);
            if(!rs.next()) {
                String sql;
                System.out.println("Creating Edges Table");
                sql = "CREATE TABLE EDGES" +
                        "(edgeID CHAR(21) not NULL, " +
                        " startNode CHAR(10), " +
                        " endNode CHAR(10), " +
                        " PRIMARY KEY (edgeID))";
                stmt.executeUpdate(sql);
                edgeCSV.load_edges_csv(connection);
            }
            edgeCSV.addEdgesToMap(connection);

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void databaseShutdown()throws SQLException, FileNotFoundException{
        System.out.println(" shutting down database ");
        try {
            // save into csv
            CSVmanager nodeCSV = new CSVmanager(Node_csv_path);
            CSVmanager edgeCSV = new CSVmanager(Edge_csv_path);

            nodeCSV.saveNodesinCSV(connection);
            edgeCSV.saveEdgesInCSV(connection);
            // clean shutdown database
            connection.close();
        }catch(SQLException | FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public static Connection get_connection(){return connection;}

    public static String getEdge_csv_path() {
        return Edge_csv_path;
    }

    public static String getNode_csv_path() {
        return Node_csv_path;
    }
}