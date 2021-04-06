package edu.wpi.MochaManticores.database;

import org.apache.derby.impl.jdbc.EmbedResultSet;

import javax.print.attribute.ResolutionSyntax;
import java.sql.*;
public class demo {

    private static DatabaseMetaData meta;
    private static Connection connection;

    public static void main(String[] args) {
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

        System.out.println("Apache Derby driver registered!");
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:derby:Mdatabase;create=true");
            meta = connection.getMetaData();
        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return;
        }

        //create data tables
        System.out.println("creating tables");
        try {
            Statement stmt = connection.createStatement();
            String sql;

            // Node table
            ResultSet rs = meta.getTables(null, "APP", "NODES", null);
            if (!rs.next()) {
                sql = "CREATE TABLE NODES " +
                        "(nodeID CHAR(10) not NULL, " +
                        " xcoord INT, " +
                        " ycoord INT, " +
                        " floor VARCHAR(3), " +
                        " building VARCHAR(50), " +
                        " nodeType CHAR(4), " +
                        " longName VARCHAR(255) UNIQUE," +
                        " shortName VARCHAR(255), " +
                        " PRIMARY KEY (nodeID))";
                stmt.executeUpdate(sql);
                CSVmanager nodeCSV = new CSVmanager("nodes.csv");
                nodeCSV.load_node_csv(connection);
            }

            rs = meta.getTables(null, "APP", "EDGES", null);
            if (!rs.next()) {
                sql = "CREATE TABLE EDGES " +
                        "(edgeID CHAR(21) not NULL, " +
                        " startNode CHAR(10), " +
                        " endNode CHAR(10), " +
                        " PRIMARY KEY (edgeID))";
                stmt.executeUpdate(sql);
                CSVmanager edgeCSV = new CSVmanager("edges.csv");
                edgeCSV.load_edges_csv(connection);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        System.out.println("Apache Derby connection established!");
    }
}